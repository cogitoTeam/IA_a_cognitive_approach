/**
 * 
 */
package ac.memory.persistance;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 2 avr. 2012
 * @version 0.1
 */
public class NodeRepositoryException extends Exception
{
  private static final long serialVersionUID = 246884640069099797L;

  /**
   * Minimal constructor
   */
  public NodeRepositoryException()
  {
    super();
  }

  /**
   * Constructor with message
   * 
   * @param m
   *          the message
   */
  public NodeRepositoryException(String m)
  {
    super(m);
  }

  /**
   * Constructor with message and previous exception
   * 
   * @param m
   *          the message
   * @param e
   *          the previous exception
   */
  public NodeRepositoryException(String m, Exception e)
  {
    super(m, e);
  }
}
