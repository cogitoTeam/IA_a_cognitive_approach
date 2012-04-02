package ac.memory.persistence;

import org.neo4j.graphdb.*;

/**
 * Wrapping class for lattice context node
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 * @param <ObjectType>
 *          Object type to store
 * @param <RelatedObjectType>
 *          Related object type
 * @param <StoredObjectType>
 *          Type of object stored in object field
 */
abstract public class AbstractNode<ObjectType>
{
  // START SNIPPET: the-node
  protected final Node underlyingNode;

  protected AbstractNode(Node node)
  {
    this.underlyingNode = node;
  }

  protected Node getUnderlyingNode()
  {
    return underlyingNode;
  }

  @Override
  public abstract boolean equals(Object o);

  @Override
  public abstract String toString();

}
