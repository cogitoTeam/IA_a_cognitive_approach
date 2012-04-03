/**
 * 
 */
package ac.memory.episodic;

import java.util.Date;

import ac.memory.persistence.NodeException;
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

  String toString();
}
