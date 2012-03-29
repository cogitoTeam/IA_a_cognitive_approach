/**
 * 
 */
package ac.memory.semantic.graph.lattice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

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
    private Map<Long, TreeSet<Long>> matrix;

    /**
     * Default constructor
     */
    public HashMapLatticeContext() {
        this.attributes = new HashMap<Long, RelevantPartialBoardState>();
        this.objects = new HashMap<Long, CompleteBoardState>();
        this.matrix = new HashMap<Long, TreeSet<Long>>();
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
        this.matrix.put(object.getId(), new TreeSet<Long>());

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
    public Map<Long, RelevantPartialBoardState> getAttributesByObject(
            CompleteBoardState object) {
        return getAttributesByObject(object.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ac.memory.graph.lattice.LatticeContext#getObjects(ac.shared.structure
     * .RelevantPartialBoardState)
     */
    @Override
    public Map<Long, CompleteBoardState> getObjectsByAttribute(
            RelevantPartialBoardState attribute) {
        return getObjectsByAttribute(attribute.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see ac.memory.graph.lattice.LatticeContext#getAttributes(long)
     */
    @Override
    public Map<Long, RelevantPartialBoardState> getAttributesByObject(
            long id_object) {
        HashMap<Long, RelevantPartialBoardState> map = new HashMap<Long, RelevantPartialBoardState>();

        for (Long attribute_id : this.matrix.get(id_object)) {
            map.put(attribute_id, this.attributes.get(attribute_id));
        }

        return map;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ac.memory.graph.lattice.LatticeContext#getObjects(long)
     */
    @Override
    public Map<Long, CompleteBoardState> getObjectsByAttribute(long id_attribute) {
        HashMap<Long, CompleteBoardState> map = new HashMap<Long, CompleteBoardState>();

        for (Iterator<CompleteBoardState> iterator = this.objects.values()
                .iterator(); iterator.hasNext();) {
            CompleteBoardState objet = iterator.next();
            Long object_id = objet.getId();

            for (Long id_att : this.matrix.get(object_id)) {
                if (id_att.equals(id_attribute)) {
                    map.put(object_id, this.objects.get(object_id));
                    break;
                }
            }

        }

        return map;
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

    /*
     * (non-Javadoc)
     * 
     * @see ac.memory.semantic.graph.lattice.LatticeContext#getObjects()
     */
    @Override
    public Map<Long, CompleteBoardState> getObjects() {
        return this.objects;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ac.memory.semantic.graph.lattice.LatticeContext#getAttributes()
     */
    @Override
    public Map<Long, RelevantPartialBoardState> getAttributes() {
        return this.attributes;
    }

}
