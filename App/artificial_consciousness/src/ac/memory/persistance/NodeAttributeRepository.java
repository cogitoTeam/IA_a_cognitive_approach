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
public class NodeAttributeRepository
{
  private static final Logger logger = Logger
      .getLogger(NodeAttributeRepository.class);

  private final GraphDatabaseService graphDb;
  private final Index<Node> index;
  private final Node attributeRefNode;

  /**
   * Default constructor
   * 
   * @param graphDb
   *          the database
   * @param index
   */
  public NodeAttributeRepository(GraphDatabaseService graphDb, Index<Node> index)
  {
    logger.debug("Building new NodeAttributeRepository");

    this.graphDb = graphDb;
    this.index = index;

    attributeRefNode = getAttributesRootNode(graphDb);
  }

  /**
   * Create new attribute
   * 
   * @param id
   *          ID of the attribtue
   * @param object
   *          The RelevantePartialBoardState
   * @return the new NodeAttribute object
   * @throws Exception
   *           if the attribute already exists
   */
  public NodeAttribute createAttribute(RelevantPartialBoardState object)
      throws Exception
  {
    // to guard against duplications we use the lock grabbed on ref node
    // when
    // creating a relationship and are optimistic about person not existing
    logger.debug("Open transaction for attribute creation");
    Transaction tx = graphDb.beginTx();
    try
      {

        logger.debug("Node creation");
        Node newNodeAttribute = graphDb.createNode();
        newNodeAttribute.setProperty("id", object.getId());
        newNodeAttribute.setProperty(NodeAttribute.TYPE_FIELD,
            NodeAttribute.TYPE_VALUE);

        // / SERIALIZE TO BYTE[]
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(object);
        byte[] bytes = bos.toByteArray();
        // ///////////////////////

        newNodeAttribute.setProperty("object", bytes);

        attributeRefNode.createRelationshipTo(newNodeAttribute, RelTypes.ATTR);
        // lock now taken, we can check if already exist in index
        Node alreadyExist = index.get(NodeAttribute.ID, object.getId())
            .getSingle();
        if (alreadyExist != null)
          {
            logger.warn("Transaction exited because of duplicate ID");
            tx.failure();
            throw new Exception("Attribute with this ID already exists ");
          }

        logger.debug("Setting properties");
        newNodeAttribute.setProperty(NodeAttribute.ID, object.getId());
        newNodeAttribute.setProperty(NodeAttribute.TYPE_FIELD,
            NodeAttribute.TYPE_VALUE);
        index.add(newNodeAttribute, NodeAttribute.ID, object.getId());
        tx.success();
        logger.debug("Ok new Attribute created");
        return new NodeAttribute(newNodeAttribute);
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
  public NodeAttribute getAttributeById(long id)
  {
    logger.debug("Getting an attribute by ID " + id);
    Node attribute = index.get(NodeAttribute.ID, id).getSingle();
    if (attribute == null)
      {
        logger.warn("Attribute not found");
        throw new IllegalArgumentException(NodeAttribute.TYPE_VALUE + "[" + id
            + "] not found");
      }
    logger.debug("Attribute found");
    return new NodeAttribute(attribute);
  }

  /**
   * Remove an attribute
   * 
   * @param attribute
   *          The attribute to remove
   */
  public void deleteAttribute(NodeAttribute attribute)
  {
    logger
        .debug("Opening transaction to delete attribute " + attribute.getId());
    Transaction tx = graphDb.beginTx();
    try
      {
        Node nodeAttribute = attribute.getUnderlyingNode();
        index.remove(nodeAttribute, NodeAttribute.ID, attribute.getId());
        logger.debug("Removing related objects");
        for (NodeObject object : attribute.getRelatedObjects())
          {
            attribute.removeRelatedObject(object);
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
  public Iterable<NodeAttribute> getAllNodeAttributes()
  {
    logger.debug("Getting all the attribute nodes");
    return new IterableWrapper<NodeAttribute, Relationship>(
        attributeRefNode.getRelationships(RelTypes.ATTR))
    {
      @Override
      protected NodeAttribute underlyingObjectToObject(Relationship attributeRel)
      {
        return new NodeAttribute(attributeRel.getEndNode());
      }
    };
  }

  private Node getAttributesRootNode(GraphDatabaseService graphDb)
  {
    Relationship rel = graphDb.getReferenceNode().getSingleRelationship(
        RelTypes.REF_ATTRIBUTES, Direction.OUTGOING);

    if (rel != null)
      {
        return rel.getEndNode();
      }
    else
      {
        Transaction tx = this.graphDb.beginTx();
        try
          {
            Node refNode = this.graphDb.createNode();
            this.graphDb.getReferenceNode().createRelationshipTo(refNode,
                RelTypes.REF_ATTRIBUTES);
            tx.success();
            return refNode;
          }
        finally
          {
            tx.finish();
          }
      }
  }
}
