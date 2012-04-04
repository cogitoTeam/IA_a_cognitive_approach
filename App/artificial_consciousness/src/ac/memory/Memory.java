/**
 * 
 */
package ac.memory;

import java.util.List;

import book.Pair;

import ac.shared.CompleteBoardState;
import ac.shared.GameStatus;
import ac.shared.RelevantPartialBoardState;

/**
 * Interface for class Memory, which declar methods uses by other modules to
 * interact with the memory
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public interface Memory
{

  /**
   * USED BY ANALYSIS Method needed by analysis module to get all the
   * RelevantPartialBoardStates.
   * 
   * @return a list of RelevantPartialBoardState if more are available, null
   *         otherwise
   * @throws MemoryException
   */
  List<RelevantPartialBoardState> getRelevantPartialBoardStates()
      throws MemoryException;

  /**
   * USED BY ANALYSIS Put an option in the memory
   * 
   * @param option 
   * @throws MemoryException
   */
  void putOption(Option option> matching) throws MemoryException;

  /**
   * USED BY REASONING This method transmit the list of Options
   * stored in the active memory with a grade for each one
   * 
   * @return the list of the CompleteBoardState in the ActiveMemory
   * @throws MemoryException
   */
  List<Pair<Option, Double>> getGradedOptions()
      throws MemoryException;

  /**
   * USED BY REASONING Choice done by the reasonning module
   * 
   * @param cbs
   *          The CompleteBoardState chosen
   * @throws MemoryException
   *           When the CBS is not present in the active memory
   */
  void CompleteBoardStateChosen(CompleteBoardState cbs) throws MemoryException;

  /**
   * USED BY REASONING Indicates to the memory that new game begin
   * 
   * @throws MemoryException
   *           When another game is already running
   */
  void BeginOfGame() throws MemoryException;

  /**
   * USED BY REASONING Indicates to the memory that the end of the current
   * game
   * 
   * @param status
   * @param score
   *          can be null if no score is allowed to the current game
   * @throws MemoryException
   *           When no game started
   */
  void EndOfGame(GameStatus status, float score) throws MemoryException;
  
  

}
