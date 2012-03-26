/**
 * 
 */
package ac.shared.advanced_state_board;

/**
 * Advanced StateBoard class
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
public class AdvancedStateBoard {

    private String id;
    private AdvancedStateBoardCore advanced_state_board_core;

    /**
     * Default constructor of an advanced stateboard
     * 
     * @param id
     *            the id's advanced stateboard
     */
    public AdvancedStateBoard(String id) {
        this(id, new AdvancedStateBoardCore());
    }

    /**
     * Constructor with id and advanced stateboard core in parameter
     * 
     * @param id
     * @param advanced_state_board_core
     */
    public AdvancedStateBoard(String id,
            AdvancedStateBoardCore advanced_state_board_core) {
        this.setId(id);
        this.setAdvanced_state_board_core(advanced_state_board_core);
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
     * @return the advanced_state_board_core
     */
    public AdvancedStateBoardCore getAdvanced_state_board_core() {
        return advanced_state_board_core;
    }

    /**
     * @param advanced_state_board_core
     *            the advanced_state_board_core to set
     */
    public void setAdvanced_state_board_core(
            AdvancedStateBoardCore advanced_state_board_core) {
        this.advanced_state_board_core = advanced_state_board_core;
    }

}
