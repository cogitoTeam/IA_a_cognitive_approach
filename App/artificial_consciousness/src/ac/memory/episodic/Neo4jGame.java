/**
 * 
 */
package ac.memory.episodic;

import java.util.Date;

import org.neo4j.graphdb.Relationship;

import ac.memory.persistence.neo4j.GameNode;
import ac.memory.persistence.neo4j.MoveNode;
import ac.shared.GameStatus;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 3 avr. 2012
 * @version 0.1
 */
public class Neo4jGame implements Game
{
  private final GameNode game;

  /**
   * Default constructor for a Neo4jGame
   * 
   * @param game
   *          the game node
   */
  public Neo4jGame(GameNode game)
  {
    this.game = game;
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

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.Game#setScore(int) */
  @Override
  public void setScore(int score)
  {
    game.setScore(score);
    // Setting all move score
    MoveNode move = game.getLastMove();
    int pos = 0;
    while (move != null)
      {
        pos++;

        move.setMark(EpisodicMemoryUtil.DeacreaseMoveFormula(pos, score,
            getStatus()));

        move = move.getPrevious();
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
        System.out.println("pb !! " + e.getMessage());
      }
    return score;
  }
}
