/**
 * 
 */
package ac.memory;

import java.util.List;

import ac.util.Pair;

import ac.memory.episodic.Game;
import ac.shared.CompleteBoardState;
import ac.shared.GameStatus;
import ac.shared.RelevantPartialBoardState;
import ac.shared.FOLObjects.Option_FOL;

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

  /* ANALYSIS */

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
  void putOption(Option_FOL option) throws MemoryException;

  /* REASONING */

  /**
   * USED BY REASONING This method transmit the list of Options
   * stored in the active memory with a grade for each one
   * 
   * @return the list of the CompleteBoardState in the ActiveMemory
   * @throws MemoryException
   */
  List<Pair<Option_FOL, Double>> getGradedOptions() throws MemoryException;

  /**
   * USED BY REASONING Choice done by the reasonning module
   * 
   * @param option
   *          The Option chosen
   * @throws MemoryException
   *           When the Option is not present in the active memory
   */
  void OptionChosen(Option_FOL option) throws MemoryException;

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
  void EndOfGame(GameStatus status, int score) throws MemoryException;

  /* INTROSPECTION */

  /**
   * USED BY REASONING (INTROSPECTION) Give n last won games
   * 
   * @param n
   *          number of games
   * @return the list of won games
   */
  List<Game> getLastWonGames(int n);

  /**
   * USED BY REASONING (INTROSPECTION) Give n last lost games
   * 
   * @param n
   *          number of games
   * @return the list of won games
   */
  List<Game> getLastLostGames(int n);

  /**
   * USED BY REASONING (INTROSPECTION) Give n most active relevant partial board
   * state
   * 
   * @param n
   *          number of rpbs returned
   * @return the list of rpbs
   * @throws MemoryException
   */
  List<Pair<RelevantPartialBoardState, Double>> getMostActiveRPBS(int n)
      throws MemoryException;

  /**
   * USED BY REASONING (INTROSPECTION) Give n most active complete board
   * state
   * 
   * @param n
   *          number of cbs returned
   * @return the list of cbs
   * @throws MemoryException
   */
  List<Pair<CompleteBoardState, Double>> getMostActiveCBS(int n)
      throws MemoryException;

  /**
   * USED BY REASONING (INTROSPECTION) Add new RelevantStructure in the memory
   * and return the new id
   * 
   * @param rpbs
   *          the rpbs
   * @return the id generated
   * @throws MemoryException
   */
  long putRelevantStructure(RelevantPartialBoardState rpbs)
      throws MemoryException;

  /**
   * Add new relationship between a cbs and a rpbs
   * @param cbs_id 
   * @param rpbs
   * @throws MemoryException
   */
  void addAssociation(long cbs_id, RelevantPartialBoardState rpbs)
      throws MemoryException;

}
