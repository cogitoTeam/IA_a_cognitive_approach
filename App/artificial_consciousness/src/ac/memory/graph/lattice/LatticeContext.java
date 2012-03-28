/**
 * 
 */
package ac.memory.graph.lattice;

import java.util.TreeSet;

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

    private static TreeSet<RelevantStructure> relevant_structures;

    static {
        setRelevant_structures(new TreeSet<RelevantStructure>());
    }

    /**
     * Add a relevant structure to the context
     * 
     * @param rs
     */
    public static void addRelevantStructure(RelevantStructure rs) {
        relevant_structures.add(rs);
    }

    /**
     * @return the relevant_structures
     */
    public static TreeSet<RelevantStructure> getRelevant_structures() {
        return relevant_structures;
    }

    /**
     * @param relevant_structures
     *            the relevant_structures to set
     */
    public static void setRelevant_structures(
            TreeSet<RelevantStructure> relevant_structures) {
        LatticeContext.relevant_structures = relevant_structures;
    }

}
