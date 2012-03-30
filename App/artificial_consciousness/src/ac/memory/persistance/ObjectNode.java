package ac.memory.persistance;

import org.neo4j.graphdb.*;
import ac.shared.structure.CompleteBoardState;

/**
 * Wrapping class for object node
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 */
public class ObjectNode
{
  static final String ID = "id";
  static final String OBJECT = "object";

  // START SNIPPET: the-node
  private final Node underlyingNode;

  ObjectNode(Node objectNode)
  {
    this.underlyingNode = objectNode;
  }

  protected Node getUnderlyingNode()
  {
    return underlyingNode;
  }

  /**
   * @return the Id of the object
   */
  public Long getId()
  {
    return (Long) underlyingNode.getProperty(ID);
  }

  /**
   * @return the CompleteBoardState
   */
  public CompleteBoardState getObject()
  {
    return (CompleteBoardState) underlyingNode.getProperty(OBJECT);
  }

  @Override
  public int hashCode()
  {
    return underlyingNode.hashCode();
  }

  @Override
  public boolean equals(Object o)
  {
    return o instanceof ObjectNode
        && underlyingNode.equals(((ObjectNode) o).getUnderlyingNode());
  }

  @Override
  public String toString()
  {
    return "Person[" + getId() + "]";
  }
}
