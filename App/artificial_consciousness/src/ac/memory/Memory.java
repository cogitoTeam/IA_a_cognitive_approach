/**
 * 
 */
package ac.memory;

import java.util.List;

import book.Pair;

import ac.shared.structure.CompleteBoardState;
import ac.shared.structure.RelevantPartialBoardState;

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
   * Final status of a game
   * 
   * @author Thibaut Marmin <marminthibaut@gmail.com>
   * @date 28 mars 2012
   * @version 0.1
   */
  enum FinalGameStatus
  {
    WON, LOST, INTERRUPTED, UNDEFINED,
    /**
     * When the valueOf failed, return this status :)
     */
    UNRECOGNIZED;
  }

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
   * USED BY ANALYSIS Put a couple of CompleteBoardState and list of
   * RelevantPartialBoardState witch are matching in the memory
   * 
   * @param cbs
   * @param matching
   * @throws MemoryException
   */
  void putCompleteBoardState(CompleteBoardState cbs,
      List<RelevantPartialBoardState> matching) throws MemoryException;

  /**
   * USED BY REASONING This method transmit the list of CompleteBoardState
   * stored in the active memory with a grade for each one
   * 
   * @return the list of the CompleteBoardState in the ActiveMemory
   * @throws MemoryException
   */
  List<Pair<CompleteBoardState, Double>> getGradedCompleteBoardState()
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
  void EndOfGame(FinalGameStatus status, float score) throws MemoryException;

}
