/**
 * 
 */
package ac.shared.relevant_structure;

import java.util.LinkedList;

import ac.shared.advanced_board_state.AdvancedBoardState;

/**
 * Class which represents a Relevant structure (attribute of the lattice)
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
public class RelevantStructure implements Comparable<RelevantStructure> {

    private LinkedList<AdvancedBoardState> state_board;
    private double weight;

    /**
     * @param abs
     * @return True if the ABD in parameter has this relevant structure
     */
    public Boolean haveABS(AdvancedBoardState abs) {
        for (AdvancedBoardState a : state_board) {
            if (a.equals(abs))
                return true;
        }

        return false;
    }

    /**
     * @return the state_board
     */
    public LinkedList<AdvancedBoardState> getState_board() {
        return state_board;
    }

    /**
     * @param state_board
     *            the state_board to set
     */
    public void setState_board(LinkedList<AdvancedBoardState> state_board) {
        this.state_board = state_board;
    }

    public int compareTo(RelevantStructure rs) {
        if (getWeight() < rs.getWeight())
            return -1;
        else if (getWeight() > rs.getWeight())
            return 1;
        else
            return 0;
    }

    /**
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @param weight
     *            the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

}
