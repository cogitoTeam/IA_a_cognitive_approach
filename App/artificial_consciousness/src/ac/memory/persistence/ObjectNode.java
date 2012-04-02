/**
 * 
 */
package ac.memory.persistence;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.helpers.collection.IterableWrapper;

import ac.shared.CompleteBoardState;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 */
public class ObjectNode extends
    AbstractLatticeContextNode<ObjectNode, AttributeNode, CompleteBoardState>
{
  private static final Logger logger = Logger.getLogger(ObjectNode.class);

  static final String ID_FIELD = "id_obj";

  /**
   * @param attributeNode
   */
  ObjectNode(Node objectNode)
  {
    super(objectNode, ID_FIELD);
    if (logger.isDebugEnabled())
      logger.debug("Building new ObjectNode");
  }

  @Override
  public String toString()
  {
    return "Object" + super.toString();
  }

  @Override
  protected IterableWrapper<AttributeNode, Path> createObjectsFromPath(
      Traverser iterableToWrap)
  {
    return new IterableWrapper<AttributeNode, Path>(iterableToWrap)
    {
      @Override
      protected AttributeNode underlyingObjectToObject(Path path)
      {
        return new AttributeNode(path.endNode());
      }
    };
  }

  @Override
  protected Relationship getRelationshipTo(AttributeNode object)
  {
    Node node = object.getUnderlyingNode();

    for (Relationship rel : underlyingNode.getRelationships(RelTypes.RELATED,
        Direction.BOTH))
      {
        if (rel.getOtherNode(underlyingNode).equals(node))
          {
            return rel;
          }
      }
    return null;
  }

}
