package ac.memory.persistance;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import ac.memory.persistance.RelTypes;
import ac.shared.structure.CompleteBoardState;
import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.helpers.collection.IterableWrapper;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 */
public class ObjectNodeRepository extends
    AbstractNodeRepository<CompleteBoardState, ObjectNode>
{
  /**
   * @param graphDb
   * @param index
   */
  public ObjectNodeRepository(GraphDatabaseService graphDb, Index<Node> index)
  {
    super(graphDb, index);
    refNode = getRootNode(graphDb);
  }

  private static final Logger logger = Logger
      .getLogger(ObjectNodeRepository.class);

  /**
   * Create new node
   * 
   * @param id
   *          ID of the node
   * @param object
   *          The object attribute
   * @return the new NodeAttribute object
   * @throws Exception
   *           if the attribute already exists
   */
  @Override
  public ObjectNode createNode(CompleteBoardState object) throws Exception
  {
    // to guard against duplications we use the lock grabbed on ref node
    // when
    // creating a relationship and are optimistic about person not existing
    logger.debug("Open transaction for attribute creation");
    Transaction tx = graphDb.beginTx();
    try
      {

        logger.debug("Node creation");
        Node newNode = graphDb.createNode();
        newNode.setProperty("id", object.getId());

        // / SERIALIZE TO BYTE[]
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(object);
        byte[] bytes = bos.toByteArray();
        // ///////////////////////

        newNode.setProperty("object", bytes);

        refNode.createRelationshipTo(newNode, RelTypes.ATTR);
        // lock now taken, we can check if already exist in index
        Node alreadyExist = index.get(AttributeNode.ID, object.getId())
            .getSingle();
        if (alreadyExist != null)
          {
            logger.warn("Transaction exited because of duplicate ID");
            tx.failure();
            throw new Exception("Attribute with this ID already exists ");
          }

        logger.debug("Setting properties");
        newNode.setProperty(AttributeNode.ID, object.getId());
        index.add(newNode, AttributeNode.ID, object.getId());
        tx.success();
        logger.debug("Ok new Attribute created");
        return new ObjectNode(newNode);
      }
    finally
      {
        logger.debug("Finish transaction");
        tx.finish();
      }
  }

  /**
   * Get an attribute by its id
   * 
   * @param id
   *          the ID
   * @return the attribute
   */
  @Override
  public ObjectNode getNodeById(long id)
  {
    logger.debug("Getting an attribute by ID " + id);
    Node object = index.get(ObjectNode.ID, id).getSingle();
    if (object == null)
      {
        logger.warn("Object not found");
        throw new IllegalArgumentException("[" + id + "] not found");
      }
    logger.debug("Object found");
    return new ObjectNode(object);
  }

  /**
   * Remove an attribute
   * 
   * @param node
   *          The attribute to remove
   */
  @Override
  public void deleteNode(ObjectNode node)
  {
    logger.debug("Opening transaction to delete attribute " + node.getId());
    Transaction tx = graphDb.beginTx();
    try
      {
        Node nodeObject = node.getUnderlyingNode();
        index.remove(nodeObject, ObjectNode.ID, node.getId());
        logger.debug("Removing related objects");
        for (AttributeNode object : node.getRelatedObjects())
          {
            node.removeRelatedObject(object);
          }
        logger.debug("Removing main relation");
        nodeObject.getSingleRelationship(RelTypes.ATTR, Direction.INCOMING)
            .delete();

        logger.debug("Removing object node");
        nodeObject.delete();
        tx.success();
      }
    finally
      {
        logger.debug("Finish transaction");
        tx.finish();
      }
  }

  /**
   * @return all NodeAttributes in the database
   */
  @Override
  public Iterable<ObjectNode> getAllNodes()
  {
    logger.debug("Getting all the attribute nodes");
    return new IterableWrapper<ObjectNode, Relationship>(
        refNode.getRelationships(RelTypes.ATTR))
    {
      @Override
      protected ObjectNode underlyingObjectToObject(Relationship objectRel)
      {
        return new ObjectNode(objectRel.getEndNode());
      }
    };
  }

  private Node getRootNode(GraphDatabaseService graphDb)
  {
    return getRootNode(graphDb, RelTypes.REF_OBJECTS);
  }

}
