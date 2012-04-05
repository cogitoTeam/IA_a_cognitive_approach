/**
 * 
 */
package ac.memory.persistence.neo4j;

import java.util.Collection;
import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Traverser.Order;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 2 avr. 2012
 * @version 0.1
 */
public class MoveNode extends AbstractEpisodicNode<MoveNode>
{
  private static final Logger logger = Logger.getLogger(MoveNode.class);

  /**
   * @param node
   */
  protected MoveNode(Node node)
  {
    super(node);
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistance.AbstractEpisodicNode#getPrevious() */
  @Override
  public MoveNode getPrevious()
  {
    return getMove(EpisodicDirection.PREVIOUS);
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistance.AbstractEpisodicNode#getNext() */
  @Override
  public MoveNode getNext()
  {
    return getMove(EpisodicDirection.NEXT);
  }

  /**
   * @return the game node that owns this move
   * @throws NodeException
   *           when the move has no related game. This should never happen with
   *           a correct database
   */
  public GameNode getGame() throws NodeException
  {
    if (logger.isDebugEnabled())
      logger.debug("Getting the related Game");

    Collection<Node> related = underlyingNode.traverse(Order.DEPTH_FIRST,
        StopEvaluator.DEPTH_ONE, ReturnableEvaluator.ALL_BUT_START_NODE,
        RelTypes.HAS_MOVE, Direction.INCOMING).getAllNodes();

    if (related.size() > 0)
      return new GameNode(related.iterator().next());
    else
      {
        logger.error("Move " + getDate() + " has no related game");
        throw new NodeException("Move " + getDate() + " has no related game");
      }
  }

  /**
   * @return the related board stage of the move
   * @throws NodeException
   *           if the relationship not exists (That should never happen !)
   */
  public ObjectNode getObject() throws NodeException
  {
    Relationship rel = underlyingNode.getSingleRelationship(
        RelTypes.BOARD_STATE, Direction.OUTGOING);

    if (rel != null)
      {
        return new ObjectNode(rel.getEndNode());
      }
    else
      {
        logger.error("Error when getting the related board stage of move "
            + getDate() + ", No relationship BOARD_STATE found");
        throw new NodeException(
            "Error when getting the related board stage of move " + getDate()
                + ", No relationship BOARD_STATE found");
      }
  }

  private MoveNode getMove(EpisodicDirection direction)
  {
    Direction dir = Direction.OUTGOING;
    String s_debug = "previous";
    if (direction.equals(EpisodicDirection.NEXT))
      {
        dir = Direction.INCOMING;
        s_debug = "next";
      }
    if (logger.isDebugEnabled())
      logger.debug("Getting the " + s_debug + " move");

    Relationship rel = underlyingNode.getSingleRelationship(RelTypes.PREV_MOVE,
        dir);

    if (rel != null)
      {
        if (direction.equals(EpisodicDirection.PREVIOUS))
          return new MoveNode(rel.getEndNode());
        else
          return new MoveNode(rel.getStartNode());
      }
    else
      {
        logger.warn("Move " + getDate() + " is the move game and has no "
            + s_debug + " move");
        return null;
      }
  }

  /**
   * @return the related object node
   * @throws NodeException
   *           if relationship doesn't exists
   */
  public ObjectNode getRelatedObject() throws NodeException
  {
    Relationship rel = underlyingNode.getSingleRelationship(
        RelTypes.BOARD_STATE, Direction.OUTGOING);
    if (rel != null)
      {
        return new ObjectNode(rel.getEndNode());
      }
    else
      throw new NodeException("The MoveNode " + getDate()
          + " doesn't have related object");
  }

}
