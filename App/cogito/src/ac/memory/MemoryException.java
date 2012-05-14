/**
 * 
 */
package ac.memory;

/**
 * Custom Exception class for Memory
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public class MemoryException extends Exception
{

  private static final long serialVersionUID = 6919397900486000745L;

  /**
   * Default constructor
   */
  public MemoryException()
  {
    super();
  }

  /**
   * Exception with message
   * 
   * @param s
   *          the message
   */
  public MemoryException(String s)
  {
    super(s);
  }

  /**
   * Exception with precedent exception and message
   * 
   * @param s
   *          the message
   * @param e
   *          the previous exception
   */
  public MemoryException(String s, Exception e)
  {
    super(s, e);
  }
}
