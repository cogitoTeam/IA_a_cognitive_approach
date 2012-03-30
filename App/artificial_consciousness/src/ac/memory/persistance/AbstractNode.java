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
 * @param <ObjectType>
 *          Object type to store
 * @param <RelatedObjectType>
 *          Related object type
 */
abstract public class AbstractNode<ObjectType, RelatedObjectType>
{
  static final String ID = "id";
  static final String OBJECT = "object";

  // START SNIPPET: the-node
  protected final Node underlyingNode;

  protected AbstractNode(Node node)
  {
    this.underlyingNode = node;
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
   * Return the RelevantPartialBoardState unserialized
   * 
   * @return the RelevantePartialBoardState of the attribute
   * @throws IOException
   *           Should never happen
   * @throws ClassNotFoundException
   *           Error when unserialize
   */
  public ObjectType getObject() throws IOException, ClassNotFoundException
  {
    // / UNSERIALIZE
    ByteArrayInputStream bis = new ByteArrayInputStream(
        (byte[]) underlyingNode.getProperty(OBJECT));
    ObjectInput in = new ObjectInputStream(bis);
    return (ObjectType) in.readObject();
  }

  /**
   * @return Node related to this Attribute
   */
  public Iterable<RelatedObjectType> getRelatedObjects()
  {
    @SuppressWarnings("deprecation")
    TraversalDescription travDesc = Traversal.description().breadthFirst()
        .relationships(RelTypes.RELATED).uniqueness(Uniqueness.NODE_GLOBAL)
        .depthFirst().filter(Traversal.returnAll());

    return createObjectsFromPath(travDesc.traverse(underlyingNode));
  }

  /**
   * Associate new objet to this attribute
   * 
   * @param object
   *          the object to associate
   */
  @SuppressWarnings("unchecked")
  public void addRelatedObject(RelatedObjectType object)
  {
    Transaction tx = underlyingNode.getGraphDatabase().beginTx();
    try
      {
        if (!this.equals(object))
          {
            Relationship related = getAttributesRelationshipTo(object);
            if (related == null)
              {
                underlyingNode.createRelationshipTo(
                    ((AbstractNode<ObjectType, RelatedObjectType>) object)
                        .getUnderlyingNode(), RelTypes.RELATED);
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
  public void removeRelatedObject(RelatedObjectType object)
  {
    Transaction tx = underlyingNode.getGraphDatabase().beginTx();
    try
      {
        if (!this.equals(object))
          {
            Relationship related = getAttributesRelationshipTo(object);
            if (related != null)
              {
                related.delete();
              }
            tx.success();
          }
      }
    finally
      {
        tx.finish();
      }
  }

  @Override
  public int hashCode()
  {
    return underlyingNode.hashCode();
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object o)
  {
    return underlyingNode
        .equals(((AbstractNode<ObjectType, RelatedObjectType>) o)
            .getUnderlyingNode());
  }

  @Override
  public String toString()
  {
    return "Node[" + getId() + "]";
  }

  protected abstract IterableWrapper<RelatedObjectType, Path> createObjectsFromPath(
      Traverser iterableToWrap);

  protected abstract Relationship getAttributesRelationshipTo(
      RelatedObjectType object);

}
