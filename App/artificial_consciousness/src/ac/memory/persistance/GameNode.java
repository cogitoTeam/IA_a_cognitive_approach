/**
 * 
 */
package ac.memory.persistance;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

import ac.memory.Memory;
import ac.memory.Memory.FinalGameStatus;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 2 avr. 2012
 * @version 0.1
 */
public class GameNode extends AbstractEpisodicNode<GameNode>
{
  private static final Logger logger = Logger.getLogger(GameNode.class);

  /**
   * @param node
   */
  protected GameNode(Node node)
  {
    super(node);
  }

  /**
   * @return status of the game
   */
  public Memory.FinalGameStatus getStatus()
  {
    return (FinalGameStatus) underlyingNode.getProperty("status");
  }

  /**
   * Set the status of the game
   * 
   * @param status
   *          the status
   */
  public void setStatus(Memory.FinalGameStatus status)
  {
    underlyingNode.setProperty("status", status);
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistance.AbstractEpisodicNode#getPrevious() */
  @Override
  public GameNode getPrevious() throws NodeException
  {
    return getGame(EpisodicDirection.PREVIOUS);
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistance.AbstractEpisodicNode#getNext() */
  @Override
  public GameNode getNext() throws NodeException
  {
    return getGame(EpisodicDirection.NEXT);
  }

  private GameNode getGame(EpisodicDirection direction) throws NodeException
  {
    Direction dir = Direction.OUTGOING;
    String s_debug = "previous";
    if (direction.equals(EpisodicDirection.NEXT))
      {
        dir = Direction.INCOMING;
        s_debug = "next";
      }
    if (logger.isDebugEnabled())
      logger.debug("Getting the " + s_debug + " game");

    Relationship rel = underlyingNode.getSingleRelationship(RelTypes.PREV_GAME,
        dir);

    if (rel != null)
      {
        return new GameNode(rel.getStartNode());
      }
    else
      {
        logger.warn("Game " + getDate() + " is the last game and has no "
            + s_debug + " game");
        throw new NodeException("Game " + getDate()
            + " is the last game and has no " + s_debug + " game");
      }
  }

}
