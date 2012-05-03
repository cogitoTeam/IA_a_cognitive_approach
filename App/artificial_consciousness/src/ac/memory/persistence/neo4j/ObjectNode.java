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
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.helpers.collection.IterableWrapper;
import org.neo4j.kernel.Traversal;
import org.neo4j.kernel.Uniqueness;

import ac.shared.CompleteBoardState;
import ac.shared.GameStatus;

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
   * @param objectNode
   * @param attributeNode
   */
  public ObjectNode(Node objectNode)
  {
    super(objectNode, ID_FIELD);
    if (logger.isDebugEnabled())
      logger.debug("Building new ObjectNode");
  }

  /**
   * @return Move nodes related with this CBS
   */
  public Iterable<MoveNode> getRelatedMoves()
  {
    if (logger.isDebugEnabled())
      logger.debug("Getting the related moves");

    TraversalDescription travDesc = Traversal.description()
        .relationships(RelTypes.BOARD_STATE, Direction.INCOMING).breadthFirst()
        .uniqueness(Uniqueness.NODE_GLOBAL)
        .evaluator(EvaluatorUtil.lengthOfOne());

    return new IterableWrapper<MoveNode, Path>(
        travDesc.traverse(underlyingNode))
    {
      @Override
      protected MoveNode underlyingObjectToObject(Path path)
      {
        return new MoveNode(path.endNode());
      }
    };
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

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistence.neo4j.AbstractLatticeContextNode#getMark() */
  @Override
  public double getMark() throws NodeException
  {
    try
      {
        return (double) underlyingNode.getProperty(MARK_FIELD);
      }
    catch (Exception e)
      {
        logger.error("The object node " + getId() + " has no mark field.");
        throw new NodeException("The object node " + getId()
            + " has no mark field.", e);
      }
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistence.neo4j.AbstractLatticeContextNode#setMark(int) */
  @Override
  public void setMark(double mark) throws NodeException
  {
    Transaction tx = Neo4jService.getInstance().beginTx();
    try
      {
        underlyingNode.setProperty(MARK_FIELD, mark);
        tx.success();
      }
    catch (Exception e)
      {
        throw new NodeException("Error when setting mark for object", e);
      }
    finally
      {
        tx.finish();
      }
  }

  @Override
  public void performMark() throws NodeException
  {
    int total = 0;
    int won = 0;

    try
      {
        for (Iterator<MoveNode> iterator = getRelatedMoves().iterator(); iterator
            .hasNext();)
          {
            MoveNode move = (MoveNode) iterator.next();
            GameStatus status = move.getGame().getStatus();

            if (status.equals(GameStatus.VICTORY))
              {
                total++;
                won++;
              }
            else if (status.equals(GameStatus.DEFEAT))
              total++;

          }
        Transaction tx = Neo4jService.getInstance().beginTx();
        try
          {
            double mark = 0.5;
            if (total != 0)
              mark = (double) won / (double) total;
            underlyingNode.setProperty(MARK_FIELD, mark);

            if (logger.isDebugEnabled())
              logger.debug("Indexing new object mark");
            Neo4jService.getObjMarkIndex().remove(underlyingNode);
            Neo4jService.getObjMarkIndex()
                .add(underlyingNode, MARK_FIELD, mark);

            performDependingAttributeMark();

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

  /**
   * Perform mark for depending attribute
   */
  private void performDependingAttributeMark()
  {
    for (Iterator<Relationship> iterator = underlyingNode.getRelationships(
        RelTypes.RELATED, Direction.OUTGOING).iterator(); iterator.hasNext();)
      {
        Relationship rel = (Relationship) iterator.next();
        AttributeNode attr = new AttributeNode(rel.getEndNode());

        try
          {
            attr.performMark();
          }
        catch (NodeException e)
          {
            logger.warn(
                "Error when performing mark for attribute " + attr.getId(), e);
          }

      }
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.persistence.neo4j.AbstractLatticeContextNode#addRelatedObject
   * (java.lang.Object) */
  @Override
  public void addRelatedObject(AttributeNode attribute)
  {
    if (logger.isDebugEnabled())
      logger.debug("Relate new object to the atribute");
    if (logger.isDebugEnabled())
      logger.debug("Opening transaction");
    Transaction tx = underlyingNode.getGraphDatabase().beginTx();
    try
      {
        if (!this.equals(attribute))
          {
            Relationship related = getRelationshipTo(attribute);
            if (related == null)
              {

                underlyingNode.createRelationshipTo(
                    attribute.getUnderlyingNode(), RelTypes.RELATED);
               /* try
                  {
                    attribute.performMark();
                  }
                catch (NodeException e)
                  {
                    logger.error("Error when perfoming mark", e);
                  }*/

              }
            else
              {
                logger.warn("Relationship already exists");
              }
            tx.success();
          }
      }
    finally
      {
        if (logger.isDebugEnabled())
          logger.debug("Transaction finished");
        tx.finish();
      }

  }

}
