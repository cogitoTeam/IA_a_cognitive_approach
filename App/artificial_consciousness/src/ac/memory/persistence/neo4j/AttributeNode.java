/**
 * 
 */
package ac.memory.persistence.neo4j;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.helpers.collection.IterableWrapper;

import ac.shared.RelevantPartialBoardState;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 */
public class AttributeNode
    extends
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

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistence.neo4j.AbstractLatticeContextNode#getMark() */
  @Override
  public double getMark() throws NodeException
  {
    // TODO Auto-generated method stub
    return (double) 0.5;
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.persistence.neo4j.AbstractLatticeContextNode#setMark(double) */
  @Override
  public void setMark(double mark) throws NodeException
  {
    try
      {
        underlyingNode.getProperty(MARK_FIELD);
      }
    catch (Exception e)
      {
        logger.error("Attribute node " + getId() + " has no mark field.");
        throw new NodeException("Attribute node " + getId()
            + " has no mark field.", e);
      }

  }

  @Override
  public void performMark() throws NodeException
  {
    int nb = 0;
    int total = 0;

    try
      {
        for (Iterator<ObjectNode> iterator = getRelatedObjects().iterator(); iterator
            .hasNext();)
          {
            ObjectNode object = (ObjectNode) iterator.next();

            total += object.getMark();
            nb++;

          }

        Transaction tx = Neo4jService.getInstance().beginTx();
        try
          {
            double mark = 0.5;
            if (nb != 0)
              mark = (double) total / (double) nb;
            underlyingNode.setProperty(MARK_FIELD, mark);

            if (logger.isDebugEnabled())
              logger.debug("Indexing new attribute mark");
            Neo4jService.getAttrMarkIndex().remove(underlyingNode);
            Neo4jService.getAttrMarkIndex().add(underlyingNode, MARK_FIELD,
                mark);

            tx.success();
          }
        catch (Exception e)
          {
            throw e;
          }
        finally
          {
            tx.finish();
          }

      }
    catch (Exception e)
      {
        logger.warn("Error when trying to perfom the mark calcul", e);
        throw new NodeException("Error when trying to perfom the mark calcul",
            e);
      }
  }
}
