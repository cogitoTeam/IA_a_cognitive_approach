package ac.memory.episodic;

import java.util.List;

import ac.shared.CompleteBoardState;
import ac.shared.GameStatus;
import ac.shared.RelevantPartialBoardState;

/**
 * Interface for an episodic memory
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public interface EpisodicMemory
{
  /**
   * @return the more recent game
   * @throws EpisodicMemoryException
   */
  Game getLastGame();

  /**
   * @param number
   *          The number of last games wanted
   * @return A list of game, ordered from the more recent to the less recent
   */
  List<Game> getLastGames(int number);

  /**
   * Add a game in the memory.
   * 
   * @param game
   */
  void newGame();

  /**
   * Add a move in the memory (added for the last game not finished)
   * 
   * @param board_state
   *          the completeBoardState related to this move
   * @param rpbs_ids
   *          list of rpbs ids who are checking with the board_state
   * @throws EpisodicMemoryException
   */
  void newMove(CompleteBoardState board_state, List<Long> rpbs_ids)
      throws EpisodicMemoryException;

  /**
   * @param status
   *          status set to the last game
   * @param score
   *          the final score (always absolute value)
   */
  void finishGame(GameStatus status, int score);

  String toString();

}
