package ac.memory.semantic.graph.lattice;

import java.util.HashMap;
import ac.shared.structure.CompleteBoardState;
import ac.shared.structure.RelevantPartialBoardState;

/**
 * Concept generated in the lattice (node of a lattice)
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public class Concept
{

  private HashMap<Long, RelevantPartialBoardState> attributes;
  private HashMap<Long, CompleteBoardState> objects;

  private double neutrality;
  private double activity;

  /**
   * @return the attributes of the concept
   */
  public HashMap<Long, RelevantPartialBoardState> getAttributes()
  {
    return attributes;
  }

  /**
   * @param attributes
   *          the attributs to set
   */
  public void setAttributes(HashMap<Long, RelevantPartialBoardState> attributes)
  {
    this.attributes = attributes;
  }

  /**
   * Add a relevantStructure in the concept
   * 
   * @param rs
   */
  public void addAttribute(RelevantPartialBoardState rs)
  {
    this.attributes.put(rs.getId(), rs);
  }

  /**
   * @return the objects
   */
  public HashMap<Long, CompleteBoardState> getObjects()
  {
    return objects;
  }

  /**
   * @param objects
   *          the objects to set
   */
  public void setObjects(HashMap<Long, CompleteBoardState> objects)
  {
    this.objects = objects;
  }

  /**
   * Add an object to the concept
   * 
   * @param object
   */
  public void addObject(CompleteBoardState object)
  {
    this.objects.put(object.getId(), object);
  }

  /**
   * 
   * @return the neutrality of the concept. A neutrality equals to zero means
   *         that the concept is neutral. A positive neutrality and a negative
   *         neutrality mean respectively a positive and a negative concept.
   *         It corresponds to the Objective/Utility value.
   */
  public double getNeutrality()
  {
    return neutrality;
  }

  /**
   * @param neutrality
   *          the neutrality to set
   */
  public void setNeutrality(double neutrality)
  {
    this.neutrality = neutrality;
  }

  /**
   * @return the activity
   */
  public double getActivity()
  {
    return activity;
  }

  /**
   * @param activity
   *          the activity to set
   */
  public void setActivity(double activity)
  {
    this.activity = activity;
  }

}
