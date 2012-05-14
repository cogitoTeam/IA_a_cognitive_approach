/**
 * 
 */
package ac.memory.persistence.neo4j;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import ac.shared.GameStatus;

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
  public GameStatus getStatus()
  {
    try
      {
        return GameStatus
            .valueOf((String) underlyingNode.getProperty("status"));
      }
    catch (Exception e)
      {
        return GameStatus.UNRECOGNIZED;
      }
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(GameStatus status)
  {
    Transaction tx = Neo4jService.getInstance().beginTx();
    try
      {
        underlyingNode.setProperty("status", status.toString());
        tx.success();
      }
    finally
      {
        tx.finish();
      }
  }

  /**
   * @param score
   *          the score of the game
   */
  public void setScore(int score)
  {
    Transaction tx = Neo4jService.getInstance().beginTx();
    try
      {
        underlyingNode.setProperty("score", score);
        tx.success();
      }
    finally
      {
        tx.finish();
      }
  }

  /**
   * @return the score
   * @throws NodeException
   */
  public int getScore() throws NodeException
  {
    try
      {
        Object score = underlyingNode.getProperty("score");
        return (int) score;
      }
    catch (Exception e)
      {
        throw new NodeException("Score not stored", e);
      }
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistance.AbstractEpisodicNode#getPrevious() */
  @Override
  public GameNode getPrevious()
  {
    return getGame(EpisodicDirection.PREVIOUS);
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistance.AbstractEpisodicNode#getNext() */
  @Override
  public GameNode getNext()
  {
    return getGame(EpisodicDirection.NEXT);
  }

  /**
   * Get the last move of the game
   * 
   * @return the last move
   * @throws NodeException
   *           if the LAST_MOVE relationship not exists
   */
  public MoveNode getLastMove()
  {
    if (logger.isDebugEnabled())
      logger.debug("Getting the last move of the game " + getDate());

    Relationship rel = underlyingNode.getSingleRelationship(RelTypes.LAST_MOVE,
        Direction.OUTGOING);

    if (rel != null)
      {
        return new MoveNode(rel.getEndNode());
      }
    else
      {
        logger.warn("Game " + getDate() + " has no last move");
        return null;
      }
  }

  private GameNode getGame(EpisodicDirection direction)
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
        if (direction.equals(EpisodicDirection.PREVIOUS))
          return new GameNode(rel.getEndNode());
        else
          return new GameNode(rel.getStartNode());
      }
    else
      {
        logger.warn("Game " + getDate() + " is the last game and has no "
            + s_debug + " game");
        return null;
      }
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistence.neo4j.AbstractEpisodicNode#getPosition() */
  @Override
  public int getPosition()
  {
    int pos = 1;
    Relationship rel = underlyingNode.getSingleRelationship(RelTypes.PREV_GAME,
        Direction.INCOMING);
    while (rel != null)
      {
        ++pos;
        rel = rel.getStartNode().getSingleRelationship(RelTypes.PREV_GAME,
            Direction.INCOMING);
      }
    return pos;
  }

}
