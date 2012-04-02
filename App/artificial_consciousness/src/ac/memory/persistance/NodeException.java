/**
 * 
 */
package ac.memory.persistance;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 2 avr. 2012
 * @version 0.1
 */
public class NodeException extends Exception
{
  private static final long serialVersionUID = 6070763635287460532L;

  /**
   * Minimal constructor
   */
  public NodeException()
  {
    super();
  }

  /**
   * Constructor with message
   * 
   * @param m
   *          the message
   */
  public NodeException(String m)
  {
    super(m);
  }

  /**
   * Constructor with message and previous exception
   * 
   * @param m
   *          the message
   * @param e
   *          the previous execption
   */
  public NodeException(String m, Exception e)
  {
    super(m, e);
  }

}
