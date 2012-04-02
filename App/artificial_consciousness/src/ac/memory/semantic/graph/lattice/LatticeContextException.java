/**
 * 
 */
package ac.memory.semantic.graph.lattice;

/**
 * Custom Exception for LatticeContext
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 1 avr. 2012
 * @version 0.1
 */
public class LatticeContextException extends Exception
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Default constructor
   */
  public LatticeContextException()
  {
    super();
  }

  /**
   * New exception with message
   * 
   * @param s
   *          the message
   */
  public LatticeContextException(String s)
  {
    super(s);
  }

  /**
   * New Exception with precedent exception and message
   * 
   * @param s
   *          the message
   * @param e
   *          the previous exception
   */
  public LatticeContextException(String s, Exception e)
  {
    super(s, e);
  }
}
