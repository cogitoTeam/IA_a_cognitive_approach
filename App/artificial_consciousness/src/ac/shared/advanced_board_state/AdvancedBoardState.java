/**
 * 
 */
package ac.shared.advanced_board_state;

/**
 * Advanced BoardState class
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
public class AdvancedBoardState {

    private String id;
    private AdvancedBoardStateCore advanced_board_state_core;

    /**
     * Default constructor of an advanced boardstate
     * 
     * @param id
     *            the id's advanced boardstate
     */
    public AdvancedBoardState(String id) {
        this(id, new AdvancedBoardStateCore());
    }

    /**
     * Constructor with id and advanced boardstate core in parameter
     * 
     * @param id
     * @param advanced_board_state_core
     */
    public AdvancedBoardState(String id,
            AdvancedBoardStateCore advanced_board_state_core) {
        this.setId(id);
        this.setAdvanced_board_state_core(advanced_board_state_core);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the advanced_board_state_core
     */
    public AdvancedBoardStateCore getAdvanced_board_state_core() {
        return advanced_board_state_core;
    }

    /**
     * @param advanced_board_state_core
     *            the advanced_board_state_core to set
     */
    public void setAdvanced_board_state_core(
            AdvancedBoardStateCore advanced_board_state_core) {
        this.advanced_board_state_core = advanced_board_state_core;
    }

}
