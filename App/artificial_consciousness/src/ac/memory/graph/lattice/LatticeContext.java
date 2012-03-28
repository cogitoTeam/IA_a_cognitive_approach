/**
 * 
 */
package ac.memory.graph.lattice;

import java.util.TreeMap;
import ac.shared.relevant_structure.RelevantStructure;

;

/**
 * Context of a Lattice
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public class LatticeContext {

    private TreeMap<Long, RelevantStructure> attributes;

    /**
     * Add a relevant structure to the context
     * 
     * @param rs
     */
    public void addAttribute(RelevantStructure rs) {
        attributes.put(rs.getId(), rs);
    }

    /**
     * @param id
     * @return a relevant structure
     */
    public RelevantStructure getAttribute(long id) {
        return attributes.get(id);
    }

}
