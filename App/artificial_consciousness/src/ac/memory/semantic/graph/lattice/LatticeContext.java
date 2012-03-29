/**
 * 
 */
package ac.memory.semantic.graph.lattice;

import java.util.Map;

import ac.shared.structure.CompleteBoardState;
import ac.shared.structure.RelevantPartialBoardState;

/**
 * Interface for a Lattice Context
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public interface LatticeContext {

    /**
     * @param attribute
     *            the attribute to add
     */
    void addAttribute(RelevantPartialBoardState attribute);

    /**
     * @param objet
     *            object to add
     */
    void addObject(CompleteBoardState objet);

    /**
     * Get status in the lattice by an attribute and an object
     * 
     * @param attribute
     *            the attribute
     * @param object
     *            the object
     * @return the status (boolean)
     */
    boolean getStatus(CompleteBoardState object,
            RelevantPartialBoardState attribute);

    /**
     * Set the status for an attribute and an object
     * 
     * @param attribute
     *            the attribute
     * @param object
     *            the object
     * @param value
     *            the status (boolean)
     */
    void setStatus(CompleteBoardState object,
            RelevantPartialBoardState attribute, boolean value);

    /**
     * Get status in the lattice by id of the attribute and id of the object
     * 
     * @param id_attribute
     *            id of the attribute
     * @param id_object
     *            id of the object
     * @return the status (boolean)
     */
    boolean getStatus(long id_object, long id_attribute);

    /**
     * Set the status for an attribute and an object (by ids)
     * 
     * @param id_attribute
     *            id of the attribute
     * @param id_object
     *            id of the object
     * @param value
     *            checked or not
     */
    void setStatus(long id_object, long id_attribute, boolean value);

    /**
     * Get all the attributes checked for the object in parameter
     * 
     * @param object
     *            the object
     * @return a Map of attributes
     */
    Map<Long, RelevantPartialBoardState> getAttributes(CompleteBoardState object);

    /**
     * Get all the object checked for the attribute in parameter
     * 
     * @param attribute
     *            the attribute
     * @return a Map of objects
     */
    Map<Long, CompleteBoardState> getObjects(RelevantPartialBoardState attribute);

    /**
     * Get all the attributes checked for the object in parameter
     * 
     * @param id_object
     *            id of the object
     * @return a Map of attributes
     */
    Map<Long, RelevantPartialBoardState> getAttributes(long id_object);

    /**
     * Get all the object checked for the attribute in parameter
     * 
     * @param id_attribute
     *            id of the attribute
     * @return a Map of objects
     */
    Map<Long, CompleteBoardState> getObjects(long id_attribute);

}
