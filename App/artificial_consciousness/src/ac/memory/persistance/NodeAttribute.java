package ac.memory.persistance;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.*;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.helpers.collection.IterableWrapper;
import org.neo4j.kernel.Traversal;
import org.neo4j.kernel.Uniqueness;

import ac.shared.structure.RelevantPartialBoardState;

/**
 * Wrapping class for attribute node
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 */
public class NodeAttribute
{
  static final String ID = "id";
  static final String TYPE_FIELD = "type";
  static final String TYPE_VALUE = "attribute";
  static final String OBJECT = "object";

  // START SNIPPET: the-node
  private final Node underlyingNode;

  NodeAttribute(Node attributeNode)
  {
    this.underlyingNode = attributeNode;
  }

  protected Node getUnderlyingNode()
  {
    return underlyingNode;
  }

  /**
   * @return the id of the attribute
   */
  public Long getId()
  {
    return (Long) underlyingNode.getProperty(ID);
  }

  /**
   * @return the RelevantePartialBoardState of the attribute
   * @throws IOException
   *           Should never happen
   * @throws ClassNotFoundException
   *           Error when unserialize
   */
  public RelevantPartialBoardState getRelevantPartialBoardState()
      throws IOException, ClassNotFoundException
  {
    // / UNSERIALIZE
    ByteArrayInputStream bis = new ByteArrayInputStream(
        (byte[]) underlyingNode.getProperty(OBJECT));
    ObjectInput in = new ObjectInputStream(bis);
    return (RelevantPartialBoardState) in.readObject();
  }

  @Override
  public int hashCode()
  {
    return underlyingNode.hashCode();
  }

  @Override
  public boolean equals(Object o)
  {
    return o instanceof NodeAttribute
        && underlyingNode.equals(((NodeAttribute) o).getUnderlyingNode());
  }

  @Override
  public String toString()
  {
    return TYPE_VALUE + "[" + getId() + "]";
  }

  /**
   * @return Objects related to this Attribute
   */
  public Iterable<NodeObject> getRelatedObjects()
  {

    @SuppressWarnings("deprecation")
    TraversalDescription travDesc = Traversal.description().breadthFirst()
        .relationships(RelTypes.ATTR_TO_OBJ).uniqueness(Uniqueness.NODE_GLOBAL)
        .depthFirst().filter(Traversal.returnAll());

    return createObjectsFromPath(travDesc.traverse(underlyingNode));
  }

  /**
   * Associate new objet to this attribute
   * 
   * @param object
   *          the object to associate
   */
  public void addRelatedObject(NodeObject object)
  {
    Transaction tx = underlyingNode.getGraphDatabase().beginTx();
    try
      {
        if (!this.equals(object))
          {
            Relationship attrToObjectRel = getAttributesRelationshipTo(object);
            if (attrToObjectRel == null)
              {
                underlyingNode.createRelationshipTo(object.getUnderlyingNode(),
                    RelTypes.ATTR_TO_OBJ);
              }
            tx.success();
          }
      }
    finally
      {
        tx.finish();
      }
  }

  /**
   * Remove a relationship between the Attribute and an object
   * 
   * @param object
   *          the object
   */
  public void removeRelatedObject(NodeObject object)
  {
    Transaction tx = underlyingNode.getGraphDatabase().beginTx();
    try
      {
        if (!this.equals(object))
          {
            Relationship objectRel = getAttributesRelationshipTo(object);
            if (objectRel != null)
              {
                objectRel.delete();
              }
            tx.success();
          }
      }
    finally
      {
        tx.finish();
      }
  }

  private IterableWrapper<NodeObject, Path> createObjectsFromPath(
      Traverser iterableToWrap)
  {
    return new IterableWrapper<NodeObject, Path>(iterableToWrap)
    {
      @Override
      protected NodeObject underlyingObjectToObject(Path path)
      {
        return new NodeObject(path.endNode());
      }
    };
  }

  private Relationship getAttributesRelationshipTo(NodeObject object)
  {
    Node objectNode = object.getUnderlyingNode();

    for (Relationship rel : underlyingNode
        .getRelationships(RelTypes.ATTR_TO_OBJ))
      {
        if (rel.getOtherNode(underlyingNode).equals(objectNode))
          {
            return rel;
          }
      }
    return null;
  }

}
