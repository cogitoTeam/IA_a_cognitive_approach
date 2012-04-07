/**
 * 
 */
package ac.memory.episodic;

import java.util.Date;
import java.util.HashSet;

import org.apache.log4j.Logger;

import ac.memory.persistence.neo4j.GameNode;
import ac.memory.persistence.neo4j.MoveNode;
import ac.memory.persistence.neo4j.Neo4jService;
import ac.memory.persistence.neo4j.NodeException;
import ac.memory.persistence.neo4j.NodeRepositoryException;
import ac.memory.persistence.neo4j.ObjectNode;
import ac.memory.persistence.neo4j.ObjectNodeRepository;
import ac.shared.GameStatus;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 3 avr. 2012
 * @version 0.1
 */
public class Neo4jGame implements Game
{
  private static final Logger logger = Logger.getLogger(Neo4jGame.class);
  private final GameNode game;
  private ObjectNodeRepository obj_repo;

  /**
   * Default constructor for a Neo4jGame
   * 
   * @param game
   *          the game node
   */
  public Neo4jGame(GameNode game)
  {
    this.game = game;
    this.obj_repo = new ObjectNodeRepository(Neo4jService.getInstance(),
        Neo4jService.getObjIndex());
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.Game#getLastMove() */
  @Override
  public Move getLastMove()
  {
    MoveNode last_move = game.getLastMove();
    if (last_move != null)
      return new Neo4jMove(last_move);
    else
      return null;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.Game#getDate() */
  @Override
  public Date getDate()
  {
    return game.getDate();
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.Game#getPreviousGame() */
  @Override
  public Game getPreviousGame()
  {
    GameNode prev_game = game.getPrevious();
    if (prev_game != null)
      return new Neo4jGame(prev_game);
    else
      return null;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.Game#getNextGame() */
  @Override
  public Game getNextGame()
  {
    GameNode next_game = game.getNext();
    if (next_game != null)
      return new Neo4jGame(game.getNext());
    else
      return null;
  }

  @Override
  public String toString()
  {
    String ret = "      Game[ status : " + getStatus() + "score : "
        + getScore();

    Move move = getLastMove();
    while (move != null)
      {
        ret += "\n      " + move;
        move = move.getPreviousMove();
      }

    ret += "]";
    return ret;
  }

  @Override
  public GameStatus getStatus()
  {
    return game.getStatus();
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.Game#setStatus(ac.shared.GameStatus) */
  @Override
  public void setStatus(GameStatus status)
  {
    game.setStatus(status);
  }

  /**
   * This method set a score to a Game. It can take many time because this
   * method calculate mark for moves, cbs and rpbs
   */
  @Override
  public void setScore(int score)
  {
    game.setScore(score);

    HashSet<Long> impacted_obj = new HashSet<>();

    // Setting all move score
    if (logger.isDebugEnabled())
      logger.debug("Setting all move score");
    MoveNode move = game.getLastMove();

    int pos = 0;
    while (move != null)
      {
        pos++;

        move.setMark(EpisodicMemoryUtil.DeacreaseMoveFormula(pos, score,
            getStatus()));

        try
          {
            impacted_obj.add(move.getObject().getId());
          }
        catch (NodeException e)
          {
          }

        move = move.getPrevious();
      }

    if (logger.isDebugEnabled())
      logger.debug(impacted_obj.size() + " impacted cbs");

    for (Long id : impacted_obj)
      {
        try
          {
            ObjectNode obj = obj_repo.getNodeById(id);
            obj.performMark();
          }
        catch (NodeRepositoryException | NodeException e)
          {
            logger.warn("Error when trying to perform the mark");
            continue;
          }
      }
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.Game#getScore() */
  @Override
  public int getScore()
  {
    int score = 0;
    try
      {
        score = game.getScore();
      }
    catch (Exception e)
      {
        logger.warn("Problem when getting game score");
      }
    return score;
  }
}
