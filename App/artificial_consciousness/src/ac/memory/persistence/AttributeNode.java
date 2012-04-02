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

import ac.shared.RelevantPartialBoardState;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 */
public class AttributeNode extends
    AbstractLatticeContextNode<AttributeNode, ObjectNode, RelevantPartialBoardState>
{
  private static final Logger logger = Logger.getLogger(AttributeNode.class);

  static final String ID_FIELD = "id_attr";

  /**
   * @param attributeNode
   */
  AttributeNode(Node attributeNode)
  {
    super(attributeNode, ID_FIELD);
    if (logger.isDebugEnabled())
      logger.debug("Building new AttributeNode");
  }

  @Override
  public String toString()
  {
    return "Attribute" + super.toString();
  }

  @Override
  protected IterableWrapper<ObjectNode, Path> createObjectsFromPath(
      Traverser iterableToWrap)
  {
    return new IterableWrapper<ObjectNode, Path>(iterableToWrap)
    {
      @Override
      protected ObjectNode underlyingObjectToObject(Path path)
      {
        return new ObjectNode(path.endNode());
      }
    };
  }

  @Override
  protected Relationship getRelationshipTo(ObjectNode object)
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
