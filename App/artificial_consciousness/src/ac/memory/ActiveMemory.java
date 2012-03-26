/**
 * 
 */
package ac.memory;

import java.util.ArrayList;

import ac.shared.AdvancedStateBoardRelevantStructure;
import ac.shared.GradedAdvancedStateBoardRelevantStructure;
import ac.shared.advanced_state_board.AdvancedStateBoard;
import ac.shared.relevant_structure.RelevantStructure;

/**
 * The class Active Memory is in French the "Mémoire primaire". It acts as a
 * buffer and as an interface between other modules and the semantic memory.
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
public class ActiveMemory {

    /**
     * Result of a game
     * 
     * @author Thibaut Marmin <marminthibaut@gmail.com>
     * @date 26 mars 2012
     * @version 0.1
     */
    public enum resultGame {
        /**
         * Won game
         */
        WON,
        /**
         * Lost game
         */
        LOST,
        /**
         * Broken game
         */
        BROKEN
    }

    /**
     * @return A number of RelevantStructure ordered by importance
     */
    public ArrayList<RelevantStructure> getRelevantStructures() {
        // TODO the method
        return null;
    }

    /**
     * @param acrs_list
     *            Push a list of AdvancedStateBoardRelevantStructure in the
     *            primary memory (pushed by the analysis module)
     */
    public void PutAdvancedStateBoardRelevantStructures(
            ArrayList<AdvancedStateBoardRelevantStructure> acrs_list) {
        ; // TODO the method
    }

    /**
     * If Graded ACRS are available in the active memory, they are returned
     * 
     * @return a list of Graded ACRS
     */
    public ArrayList<GradedAdvancedStateBoardRelevantStructure> getGradedAdvancedStateBoardRelevantStructures() {
        // TODO the method
        return null;
    }

    /**
     * @param gacrs
     *            The GACRS which has been chosen by the choice engine
     */
    public void saveGradedAdvancedStateBoardRelevantStructure(
            GradedAdvancedStateBoardRelevantStructure gacrs) {
        // TODO the method
    }

    /**
     * Beginning signal for a game. When you start a new game, you have to call
     * this method.
     */
    public void BeginOfGame() {
        // TODO the method
    }

    /**
     * Ending signal for a game. At the end of a game, you have to call this
     * method with a resultGame.
     * 
     * @param result
     *            Result of the game
     * @param score
     *            Score of the game
     */
    public void EndOfGame(resultGame result, double score) {
        // TODO the method
    }

    /**
     * @return A list of lasts advanced state boards that led to victory.
     */
    public ArrayList<AdvancedStateBoard> GetWonGamesAdvancedStateBoards() {
        // TODO the method
        return null;
    }

    /**
     * @return A list of lasts advanced state boards that led to defeat.
     */
    public ArrayList<AdvancedStateBoard> GetLostGamesAdvancedStateBoards() {
        // TODO the method
        return null;
    }

    /**
     * @return A list of lasts advanced state boards that are the most active in
     *         the semantic memory.
     */
    public ArrayList<AdvancedStateBoard> GetMostActiveAdvancedStateBoards() {
        // TODO the method
        return null;
    }

    /**
     * Put new relevant structure in the semantic memory
     * 
     * @param rs
     *            A relevant structure
     */
    public void PutRelevantStructure(RelevantStructure rs) {
        // TODO the method
    }
}
