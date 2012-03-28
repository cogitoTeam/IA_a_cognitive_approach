/**
 * 
 */
package ac.shared.structure;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public abstract class AbstractBoardState {

    protected long id;

    // TODO list of defining facts

    /**
     * Default constructor of an advanced boardstate
     * 
     * @param id
     *            the id's advanced boardstate
     */
    public AbstractBoardState(long id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

}
