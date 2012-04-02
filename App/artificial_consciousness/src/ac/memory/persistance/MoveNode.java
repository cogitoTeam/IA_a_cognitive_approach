/**
 * 
 */
package ac.memory.persistance;

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
  public MoveNode getPrevious() throws NodeException
  {
    return getMove(EpisodicDirection.PREVIOUS);
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistance.AbstractEpisodicNode#getNext() */
  @Override
  public MoveNode getNext() throws NodeException
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

  private MoveNode getMove(EpisodicDirection direction) throws NodeException
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
        return new MoveNode(rel.getStartNode());
      }
    else
      {
        logger.warn("Move " + getDate() + " is the move game and has no "
            + s_debug + " move");
        throw new NodeException("Move " + getDate()
            + " is the last move and has no " + s_debug + " move");
      }
  }

}
