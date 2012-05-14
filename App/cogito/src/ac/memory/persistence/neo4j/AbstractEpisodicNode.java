package ac.memory.persistence.neo4j;

import java.util.Date;

import org.neo4j.graphdb.*;

/**
 * Wrapping class for episodic memory node
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 * @param <ObjectType>
 *          Object type to store
 * @param <StoredObjectType>
 *          Type of object stored in object field
 */
abstract public class AbstractEpisodicNode<ObjectType> extends
    AbstractNode<ObjectType>
{

  static String DATE = "date";

  protected enum EpisodicDirection
  {
    PREVIOUS, NEXT;
  }

  /**
   * @param node
   * @param id_field
   */
  protected AbstractEpisodicNode(Node node)
  {
    super(node);
  }

  /**
   * @return Date of the event
   */
  public Date getDate()
  {
    return new Date((Long) underlyingNode.getProperty(DATE));
  }

  /**
   * @return Previous Node of this Node
   * @throws NodeException
   *           when the current game has no precedent game
   */
  public abstract ObjectType getPrevious();

  /**
   * @return Next Node of this Node
   * @throws NodeException
   *           when the current game has no next game
   */
  public abstract ObjectType getNext();

  /**
   * This methode permit to get the position of the node in the episodic memory.
   * For example, a game at position 2 is the second last game in the entire
   * memory, a move at position 2 is the second last move for the game
   * 
   * @return the position
   */
  public abstract int getPosition();

  @Override
  public int hashCode()
  {
    return underlyingNode.hashCode();
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object o)
  {
    return underlyingNode.equals(((AbstractEpisodicNode<ObjectType>) o)
        .getUnderlyingNode());
  }

  @Override
  public String toString()
  {
    return "AENode[" + getDate() + "]";
  }

}
