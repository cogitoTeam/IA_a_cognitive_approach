/**
 * 
 */
package ac.memory.episodic;

import java.util.Date;

import ac.shared.CompleteBoardState;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 3 avr. 2012
 * @version 0.1
 */
public interface Move
{
  /**
   * @return date of the move
   */
  Date getDate();

  /**
   * @return the CompleBoardState related to this Move
   * @throws EpisodicMemoryException
   */
  CompleteBoardState getCompleteBoardState() throws EpisodicMemoryException;

  /**
   * @return the related game
   * @throws EpisodicMemoryException
   */
  Game getGame() throws EpisodicMemoryException;

  /**
   * @return the previous move
   * @throws EpisodicMemoryException
   */
  Move getPreviousMove();

  /**
   * @return the next move
   * @throws EpisodicMemoryException
   */
  Move getNextMove();

  String toString();
}
