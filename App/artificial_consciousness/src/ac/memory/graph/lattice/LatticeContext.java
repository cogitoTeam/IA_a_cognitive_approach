/**
 * 
 */
package ac.memory.graph.lattice;

import java.util.TreeMap;
import ac.shared.structure.RelevantPartialBoardState;

;

/**
 * Context of a Lattice
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public class LatticeContext {

    private TreeMap<Long, RelevantPartialBoardState> attributes;

    /**
     * Add a relevant structure to the context
     * 
     * @param rs
     */
    public void addAttribute(RelevantPartialBoardState rs) {
        attributes.put(rs.getId(), rs);
    }

    /**
     * @param id
     * @return a relevant structure
     */
    public RelevantPartialBoardState getAttribute(long id) {
        return attributes.get(id);
    }

}
