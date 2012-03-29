/**
 * 
 */
package ac.memory.semantic.graph.lattice.util;

import java.util.Iterator;

import ac.memory.semantic.graph.lattice.LatticeContext;
import ac.shared.structure.CompleteBoardState;
import ac.shared.structure.RelevantPartialBoardState;

/**
 * Tool to use Pcbo
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 29 mars 2012
 * @version 0.1
 */
public class PcboUtil {

    /**
     * Generate a string sequence in FIMI format
     * 
     * @param context
     *            the context
     * @return the FIMI String sequence
     */
    public static String toFimi(LatticeContext context) {

        String ret = "";
        for (CompleteBoardState object : context.getObjects().values()) {

            for (Iterator<RelevantPartialBoardState> iterator = context
                    .getAttributesByObject(object).values().iterator(); iterator
                    .hasNext();) {
                RelevantPartialBoardState attribute = (RelevantPartialBoardState) iterator
                        .next();

                ret += attribute.getId() + " ";

            }
            ret += "\n";
        }
        return ret;
    }
}
