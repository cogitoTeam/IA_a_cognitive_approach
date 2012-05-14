/**
 * 
 */
package ac.memory.episodic;

import java.util.Date;

import ac.memory.persistence.neo4j.NodeException;
import ac.shared.GameStatus;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 3 avr. 2012
 * @version 0.1
 */
public interface Game
{
  /**
   * @return the last move
   * @throws EpisodicMemoryException
   */
  Move getLastMove();

  /**
   * @return date of the game
   */
  Date getDate();

  /**
   * @return the previous game
   * @throws EpisodicMemoryException
   * @throws NodeException
   *           if no previous game in memory
   */
  Game getPreviousGame();

  /**
   * @return the next game
   * @throws EpisodicMemoryException
   * @throws NodeException
   *           if no next game in memory
   */
  Game getNextGame();

  /**
   * @param status
   *          set status to the game
   */
  void setStatus(GameStatus status);

  /**
   * Set the score of the game
   * WARNING : the score must to be positive, even if the status is DEFEAT.
   * 
   * @param score
   *          the score
   */
  void setScore(int score);

  /**
   * @return score of the game (0 if not defined)
   */
  int getScore();

  /**
   * @return the game status
   */
  GameStatus getStatus();

  String toString();
}
