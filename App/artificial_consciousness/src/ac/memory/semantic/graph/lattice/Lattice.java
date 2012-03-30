/**
 * 
 */
package ac.memory.semantic.graph.lattice;

import java.util.HashMap;
import java.util.Map;

import grph.Grph;

/**
 * The lattice
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public class Lattice
{
  Grph lattice;
  Map<Integer, Concept> concepts;

  /**
   * Default constructor of the Lattice
   */
  public Lattice()
  {
    this.lattice = new Grph();
    this.concepts = new HashMap<Integer, Concept>();
  }
}
