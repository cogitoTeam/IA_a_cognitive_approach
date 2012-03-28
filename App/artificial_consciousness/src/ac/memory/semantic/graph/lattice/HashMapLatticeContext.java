/**
 * 
 */
package ac.memory.semantic.graph.lattice;

import java.util.HashMap;
import java.util.Map;
import ac.shared.structure.CompleteBoardState;
import ac.shared.structure.RelevantPartialBoardState;

/**
 * Context of a Lattice
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */

public class HashMapLatticeContext implements LatticeContext {

    private HashMap<Long, RelevantPartialBoardState> attributes;
    private HashMap<Long, CompleteBoardState> objects;

    // Attribute x Object
    private HashMap<Long, HashMap<Long, Boolean>> matrix;

    /*
     * (non-Javadoc)
     * 
     * @see
     * ac.memory.graph.lattice.LatticeContext#addAttribute(ac.shared.structure
     * .RelevantPartialBoardState)
     */
    @Override
    public void addAttribute(RelevantPartialBoardState attribute) {
        this.attributes.put(attribute.getId(), attribute);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ac.memory.graph.lattice.LatticeContext#addObject(ac.shared.structure.
     * CompleteBoardState)
     */
    @Override
    public void addObject(CompleteBoardState object) {
        this.objects.put(object.getId(), object);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ac.memory.graph.lattice.LatticeContext#getStatus(ac.shared.structure.
     * RelevantPartialBoardState, ac.shared.structure.CompleteBoardState)
     */
    @Override
    public boolean getStatus(RelevantPartialBoardState attribute,
            CompleteBoardState object) {
        return getStatus(attribute.getId(), object.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ac.memory.graph.lattice.LatticeContext#setStatus(ac.shared.structure.
     * RelevantPartialBoardState, ac.shared.structure.CompleteBoardState,
     * boolean)
     */
    @Override
    public void setStatus(RelevantPartialBoardState attribute,
            CompleteBoardState object, boolean value) {
        setStatus(attribute.getId(), object.getId(), value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ac.memory.graph.lattice.LatticeContext#getStatus(long, long)
     */
    @Override
    public boolean getStatus(long id_attribute, long id_object) {
        return this.matrix.get(id_attribute).get(id_object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ac.memory.graph.lattice.LatticeContext#setStatus(long, long)
     */
    @Override
    public void setStatus(long id_attribute, long id_object, boolean value) {
        this.matrix.get(id_attribute).put(id_object, value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ac.memory.graph.lattice.LatticeContext#getAttributes(ac.shared.structure
     * .CompleteBoardState)
     */
    @Override
    public Map<Long, RelevantPartialBoardState> getAttributes(
            CompleteBoardState object) {
        HashMap<Long, RelevantPartialBoardState> map = new HashMap<Long, RelevantPartialBoardState>();
        // TODO
        return map;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ac.memory.graph.lattice.LatticeContext#getObjects(ac.shared.structure
     * .RelevantPartialBoardState)
     */
    @Override
    public Map<Long, CompleteBoardState> getObjects(
            RelevantPartialBoardState attribute) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see ac.memory.graph.lattice.LatticeContext#getAttributes(long)
     */
    @Override
    public Map<Long, RelevantPartialBoardState> getAttributes(long id_object) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see ac.memory.graph.lattice.LatticeContext#getObjects(long)
     */
    @Override
    public Map<Long, CompleteBoardState> getObjects(long id_attribute) {
        // TODO Auto-generated method stub
        return null;
    }

}
