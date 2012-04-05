/**
 * 
 */
package ac.util;

/**
 * Pair class
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 4 avr. 2012
 * @version 0.1
 * @param <First>
 *          Type of first element
 * @param <Second>
 *          Type of second element
 */
public class Pair<First, Second>
{
  /**
   * The first element
   */
  public First first;
  /**
   * The second element
   */
  public Second second;

  /**
   * Default constructor
   * 
   * @param a
   *          the first element
   * @param b
   *          the second element
   */
  public Pair(First a, Second b)
  {
    first = a;
    second = b;
  }

  /**
   * @return the first element
   */
  public First getFirst()
  {
    return first;
  }

  /**
   * @return the second element
   */
  public Second getSecond()
  {
    return second;
  }
}
