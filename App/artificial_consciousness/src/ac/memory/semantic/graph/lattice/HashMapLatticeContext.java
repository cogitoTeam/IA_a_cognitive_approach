/**
 * 
 */
package ac.memory.semantic.graph.lattice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

    private Map<Long, RelevantPartialBoardState> attributes;
    private Map<Long, CompleteBoardState> objects;

    // Object x Attribute
    private Map<Long, HashSet<Long>> matrix;

    /**
     * Default constructor
     */
    public HashMapLatticeContext() {
        this.attributes = new HashMap<Long, RelevantPartialBoardState>();
        this.objects = new HashMap<Long, CompleteBoardState>();
        this.matrix = new HashMap<Long, HashSet<Long>>();
    }

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
        this.matrix.put(object.getId(), new HashSet<Long>());

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ac.memory.graph.lattice.LatticeContext#getStatus(ac.shared.structure.
     * RelevantPartialBoardState, ac.shared.structure.CompleteBoardState)
     */
    @Override
    public boolean getStatus(CompleteBoardState object,
            RelevantPartialBoardState attribute) {
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
    public void setStatus(CompleteBoardState object,
            RelevantPartialBoardState attribute, boolean value) {
        setStatus(object.getId(), attribute.getId(), value);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ac.memory.graph.lattice.LatticeContext#getStatus(long, long)
     */
    @Override
    public boolean getStatus(long id_object, long id_attribute) {
        return this.matrix.get(id_object).contains(id_object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ac.memory.graph.lattice.LatticeContext#setStatus(long, long)
     */
    @Override
    public void setStatus(long id_object, long id_attribute, boolean value) {
        boolean contains = this.matrix.get(id_object).contains(id_attribute);

        if (contains && !value)
            this.matrix.get(id_object).remove(id_attribute);
        else if (!contains && value)
            this.matrix.get(id_object).add(id_attribute);
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

    /*
     * (non-Javadoc)
     * 
     * @see ac.memory.graph.lattice.LatticeContext#getAttributes(long)
     */
    @Override
    public Map<Long, RelevantPartialBoardState> getAttributes(long id_object) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ac.memory.graph.lattice.LatticeContext#getObjects(long)
     */
    @Override
    public Map<Long, CompleteBoardState> getObjects(long id_attribute) {
        // TODO Auto-generated method stub
        return null;
    }

    public String toString() {
        String ret = "[[ LATTICE CONTEXT";
        for (Iterator<CompleteBoardState> iterator = objects.values()
                .iterator(); iterator.hasNext();) {
            CompleteBoardState object = (CompleteBoardState) iterator.next();
            ret += "\n  " + object.getId() + " | ";

            for (Iterator<Long> iterator2 = matrix.get(object.getId())
                    .iterator(); iterator2.hasNext();) {
                Long id_attribute = (Long) iterator2.next();
                ret += id_attribute + ", ";
            }
        }
        ret += "\n]]";
        return ret;
    }

}
