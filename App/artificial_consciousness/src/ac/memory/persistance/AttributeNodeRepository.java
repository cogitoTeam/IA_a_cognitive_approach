package ac.memory.persistance;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import ac.memory.persistance.RelTypes;
import ac.shared.structure.RelevantPartialBoardState;

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
public class AttributeNodeRepository extends
    AbstractNodeRepository<RelevantPartialBoardState, AttributeNode>
{
  private static final Logger logger = Logger
      .getLogger(AttributeNodeRepository.class);

  static String ID_FIELD = "id_attr";

  /**
   * @param graphDb
   * @param index
   */
  public AttributeNodeRepository(GraphDatabaseService graphDb, Index<Node> index)
  {
    super(graphDb, index, ID_FIELD);
    refNode = getRootNode(graphDb);
    logger.debug("Building new AttributeNodeRepository");
  }

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
  public AttributeNode createNode(RelevantPartialBoardState object)
      throws Exception
  {
    // to guard against duplications we use the lock grabbed on ref node
    // when
    // creating a relationship and are optimistic about person not existing
    logger.debug("Opening transaction for attribute node creation");
    Transaction tx = graphDb.beginTx();
    try
      {

        logger.debug("Node creation with " + ID_FIELD + " = " + object.getId());
        Node newNodeAttribute = graphDb.createNode();
        newNodeAttribute.setProperty(ID_FIELD, object.getId());

        logger.debug("Serializing object");
        // / SERIALIZE TO BYTE[]
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(object);
        byte[] bytes = bos.toByteArray();
        // ///////////////////////

        newNodeAttribute.setProperty("object", bytes);

        logger.debug("Creating relationship to the root node");
        refNode.createRelationshipTo(newNodeAttribute, RelTypes.ATTR);

        // lock now taken, we can check if already exist in index
        logger.debug("Searching for Attribute with " + AttributeNode.ID_FIELD
            + " = " + object.getId());
        Node alreadyExist = index.get(AttributeNode.ID_FIELD, object.getId())
            .getSingle();
        if (alreadyExist != null)
          {
            logger.warn("Transaction exited because of duplicate ID for attribute node type");
            tx.failure();
            throw new Exception("Attribute with this ID already exists ");
          }

        logger.debug("Setting properties");
        newNodeAttribute.setProperty(AttributeNode.ID_FIELD, object.getId());

        logger.debug("Indexing " + ID_FIELD);
        index.add(newNodeAttribute, AttributeNode.ID_FIELD, object.getId());
        tx.success();
        logger.debug("Ok new Attribute created");
        return new AttributeNode(newNodeAttribute);
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
  public AttributeNode getNodeById(long id)
  {
    logger.debug("Getting an attribute by ID " + id);
    Node attribute = index.get(AttributeNode.ID_FIELD, id).getSingle();
    if (attribute == null)
      {
        logger.warn("Attribute not found");
        throw new IllegalArgumentException("[" + id + "] not found");
      }
    logger.debug("Attribute found");
    return new AttributeNode(attribute);
  }

  /**
   * Remove an attribute
   * 
   * @param attribute
   *          The attribute to remove
   */
  @Override
  public void deleteNode(AttributeNode attribute)
  {
    logger
        .debug("Opening transaction to delete attribute " + attribute.getId());
    Transaction tx = graphDb.beginTx();
    try
      {
        Node nodeAttribute = attribute.getUnderlyingNode();

        logger.debug("Removing from index " + ID_FIELD + " = "
            + attribute.getId());
        index.remove(nodeAttribute, AttributeNode.ID_FIELD, attribute.getId());

        logger.debug("Removing relationships to objects");
        for (Relationship rel : nodeAttribute.getRelationships(
            RelTypes.RELATED, Direction.INCOMING))
          {
            rel.delete();
          }
        logger.debug("Removing main relation");
        nodeAttribute.getSingleRelationship(RelTypes.ATTR, Direction.INCOMING)
            .delete();

        logger.debug("Removing attribute node");
        nodeAttribute.delete();
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
  public Iterable<AttributeNode> getAllNodes()
  {
    logger.debug("Getting all the attribute nodes");
    return new IterableWrapper<AttributeNode, Relationship>(
        refNode.getRelationships(RelTypes.ATTR))
    {
      @Override
      protected AttributeNode underlyingObjectToObject(Relationship attributeRel)
      {
        return new AttributeNode(attributeRel.getEndNode());
      }
    };
  }

  private Node getRootNode(GraphDatabaseService graphDb)
  {
    return getRootNode(graphDb, RelTypes.REF_ATTRIBUTES);
  }

}
