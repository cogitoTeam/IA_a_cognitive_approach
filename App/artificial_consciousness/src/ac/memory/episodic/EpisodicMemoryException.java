/**
 * 
 */
package ac.memory.episodic;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 3 avr. 2012
 * @version 0.1
 */
public class EpisodicMemoryException extends Exception
{
  private static final long serialVersionUID = 693653661090125979L;

  /**
   * Minimal constructor
   */
  public EpisodicMemoryException()
  {
    super();
  }

  /**
   * Constructor with a message
   * 
   * @param m
   *          the message
   */
  public EpisodicMemoryException(String m)
  {
    super(m);
  }

  /**
   * Constructor with a message and the previous exception
   * 
   * @param m
   *          the message
   * @param e
   *          the previous exception
   */
  public EpisodicMemoryException(String m, Exception e)
  {
    super(m, e);
  }

}
