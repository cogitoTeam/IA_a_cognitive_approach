package ac.memory.persistence.neo4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.traversal.*;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.helpers.collection.IterableWrapper;
import org.neo4j.kernel.Traversal;
import org.neo4j.kernel.Uniqueness;

import ac.memory.semantic.lattice.LatticeContextException;

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
abstract public class AbstractLatticeContextNode<ObjectType, RelatedObjectType, StoredObjectType>
    extends AbstractNode<ObjectType>
{
  protected String ID_FIELD;
  static final String MARK_FIELD = "mark";

  private static final Logger logger = Logger
      .getLogger(AbstractLatticeContextNode.class);

  static String OBJECT = "object";

  /**
   * @param node
   * @param id_field
   */
  protected AbstractLatticeContextNode(Node node, String id_field)
  {
    super(node);
    this.ID_FIELD = id_field;
  }

  /**
   * @return the id of the node
   */
  public Long getId()
  {
    return (Long) underlyingNode.getProperty(ID_FIELD);
  }

  /**
   * Return the object unserialized
   * 
   * @return the object of the node
   * @throws LatticeContextException
   *           error when unserialize object
   */
  @SuppressWarnings("unchecked")
  public StoredObjectType getObject() throws LatticeContextException
  {
    if (logger.isDebugEnabled())
      logger.debug("Getting the object");
    if (logger.isDebugEnabled())
      logger.debug("Un-serialize field");
    // / UNSERIALIZE
    ByteArrayInputStream bis = new ByteArrayInputStream(
        (byte[]) underlyingNode.getProperty(OBJECT));
    ObjectInput in;
    try
      {
        in = new ObjectInputStream(bis);
        return (StoredObjectType) in.readObject();
      }
    catch (IOException | ClassNotFoundException e)
      {
        logger.warn("An error occured while un-serialise object", e);
        throw new LatticeContextException(
            "An error occured while un-zerialize object", e);
      }
  }

  /**
   * @return Node related to this Node
   */
  public Iterable<RelatedObjectType> getRelatedObjects()
  {
    if (logger.isDebugEnabled())
      logger.debug("Getting the related objects");

    TraversalDescription travDesc = Traversal.description()
        .relationships(RelTypes.RELATED).breadthFirst()
        .uniqueness(Uniqueness.NODE_GLOBAL)
        .evaluator(EvaluatorUtil.lengthOfOne());

    return createObjectsFromPath(travDesc.traverse(underlyingNode));
  }

  /**
   * Associate new node to this node
   * 
   * @param object
   *          the object to associate
   */
  @SuppressWarnings("unchecked")
  public void addRelatedObject(RelatedObjectType object)
  {
    if (logger.isDebugEnabled())
      logger.debug("Relate new object to the node");
    if (logger.isDebugEnabled())
      logger.debug("Opening transaction");
    Transaction tx = underlyingNode.getGraphDatabase().beginTx();
    try
      {
        if (!this.equals(object))
          {
            Relationship related = getRelationshipTo(object);
            if (related == null)
              {
                underlyingNode
                    .createRelationshipTo(
                        ((AbstractLatticeContextNode<ObjectType, RelatedObjectType, StoredObjectType>) object)
                            .getUnderlyingNode(), RelTypes.RELATED);
              }
            else
              {
                logger.warn("Relationship already exists");
              }
            tx.success();
          }
      }
    finally
      {
        if (logger.isDebugEnabled())
          logger.debug("Transaction finished");
        tx.finish();
      }
  }

  /**
   * Remove a relationship between the tow objects
   * 
   * @param object
   *          the object
   */
  public void removeRelatedObject(RelatedObjectType object)
  {
    if (logger.isDebugEnabled())
      logger.debug("Removing relation between tow nodes");
    if (logger.isDebugEnabled())
      logger.debug("Opening transaction");
    Transaction tx = underlyingNode.getGraphDatabase().beginTx();
    try
      {
        if (!this.equals(object))
          {
            Relationship related = getRelationshipTo(object);
            if (related != null)
              {
                related.delete();
              }
            tx.success();
          }
      }
    finally
      {
        tx.finish();
        if (logger.isDebugEnabled())
          logger.debug("Transaction finished");
      }
  }

  /**
   * @param id
   *          id of the related object
   * @return true if current object is related with id, false otherwise
   */
  public boolean isRelatedWith(Long id)
  {
    for (Iterator<Relationship> iterator = this.underlyingNode
        .getRelationships(Direction.BOTH, RelTypes.RELATED).iterator(); iterator
        .hasNext();)
      {
        Relationship type = (Relationship) iterator.next();
        if (id == type.getEndNode().getProperty(ID_FIELD))
          return true;
      }
    return false;
  }

  /**
   * @return mark of the node
   * @throws NodeException
   *           if field not exists
   */
  public abstract double getMark() throws NodeException;

  /**
   * Set the mark of the node
   * 
   * @param mark
   *          the mark
   * @throws NodeException
   */
  public abstract void setMark(double mark) throws NodeException;;

  /**
   * Proceed to the calcul of the mark
   * 
   * @throws NodeException
   */
  public abstract void performMark() throws NodeException;

  @Override
  public int hashCode()
  {
    return underlyingNode.hashCode();
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(Object o)
  {
    return underlyingNode
        .equals(((AbstractLatticeContextNode<ObjectType, RelatedObjectType, StoredObjectType>) o)
            .getUnderlyingNode());
  }

  @Override
  public String toString()
  {
    String mark;
    try
      {
        mark = ((Double) getMark()).toString();
      }
    catch (NodeException e)
      {
        mark = "ERROR";
      }
    return "ALCNode[" + getId() + " mark: " + mark + "]";
  }

  protected abstract IterableWrapper<RelatedObjectType, Path> createObjectsFromPath(
      Traverser iterableToWrap);

  protected abstract Relationship getRelationshipTo(RelatedObjectType object);

}
