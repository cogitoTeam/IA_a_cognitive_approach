/**
 * 
 */
package ac.memory.semantic.graph.lattice;

import java.util.HashSet;
import java.util.Map;

import book.Pair;

import ac.shared.structure.CompleteBoardState;
import ac.shared.structure.RelevantPartialBoardState;

/**
 * Interface for a Lattice Context
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public interface LatticeContext
{

  /**
   * @param attribute
   *          the attribute to add
   * @throws LatticeContextException
   */
  void addAttribute(RelevantPartialBoardState attribute)
      throws LatticeContextException;

  /**
   * @param objet
   *          object to add
   * @throws LatticeContextException
   */
  void addObject(CompleteBoardState objet) throws LatticeContextException;

  /**
   * Get status in the lattice by an attribute and an object
   * 
   * @param attribute
   *          the attribute
   * @param object
   *          the object
   * @return the status (boolean)
   * @throws LatticeContextException
   */
  boolean getStatus(CompleteBoardState object,
      RelevantPartialBoardState attribute) throws LatticeContextException;

  /**
   * Set the status for an attribute and an object
   * 
   * @param attribute
   *          the attribute
   * @param object
   *          the object
   * @param value
   *          the status (boolean)
   * @throws LatticeContextException
   */
  void setStatus(CompleteBoardState object,
      RelevantPartialBoardState attribute, boolean value)
      throws LatticeContextException;

  /**
   * Get status in the lattice by id of the attribute and id of the object
   * 
   * @param id_attribute
   *          id of the attribute
   * @param id_object
   *          id of the object
   * @return the status (boolean)
   * @throws LatticeContextException
   */
  boolean getStatus(long id_object, long id_attribute)
      throws LatticeContextException;

  /**
   * Set the status for an attribute and an object (by ids)
   * 
   * @param id_attribute
   *          id of the attribute
   * @param id_object
   *          id of the object
   * @param value
   *          checked or not
   * @throws LatticeContextException
   */
  void setStatus(long id_object, long id_attribute, boolean value)
      throws LatticeContextException;

  /**
   * Get all the attributes checked for the object in parameter
   * 
   * @param object
   *          the object
   * @return a Map of attributes
   * @throws LatticeContextException
   */
  Map<Long, RelevantPartialBoardState> getAttributesByObject(
      CompleteBoardState object) throws LatticeContextException;

  /**
   * Get all the object checked for the attribute in parameter
   * 
   * @param attribute
   *          the attribute
   * @return a Map of objects
   * @throws LatticeContextException
   */
  Map<Long, CompleteBoardState> getObjectsByAttribute(
      RelevantPartialBoardState attribute) throws LatticeContextException;

  /**
   * Get all the attributes checked for the object in parameter
   * 
   * @param id_object
   *          id of the object
   * @return a Map of attributes
   * @throws LatticeContextException
   */
  Map<Long, RelevantPartialBoardState> getAttributesByObject(long id_object)
      throws LatticeContextException;

  /**
   * Get all the object checked for the attribute in parameter
   * 
   * @param id_attribute
   *          id of the attribute
   * @return a Map of objects
   * @throws LatticeContextException
   */
  Map<Long, CompleteBoardState> getObjectsByAttribute(long id_attribute)
      throws LatticeContextException;

  /**
   * @return all objects present in the context
   * @throws LatticeContextException
   */
  Map<Long, CompleteBoardState> getObjects() throws LatticeContextException;

  /**
   * @return all objects present in the context, with there related attributes
   * @throws LatticeContextException
   */
  Map<Long, Pair<CompleteBoardState, HashSet<RelevantPartialBoardState>>> getObjectsWithAttributes()
      throws LatticeContextException;

  /**
   * @return all attributes present
   * @throws LatticeContextException
   */
  Map<Long, RelevantPartialBoardState> getAttributes()
      throws LatticeContextException;

}
