package ac.memory.persistence.neo4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ac.memory.persistence.neo4j.RelTypes;
import ac.shared.RelevantPartialBoardState;

import org.apache.log4j.Logger;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.helpers.collection.IterableWrapper;
import org.neo4j.index.lucene.QueryContext;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 */
public class AttributeNodeRepository
    extends
    AbstractLatticeContextNodeRepository<RelevantPartialBoardState, AttributeNode>
{
  private static final Logger logger = Logger
      .getLogger(AttributeNodeRepository.class);

  static String ID_FIELD = "id_attr";

  /**
   * @param graphDb
   * @param id_index
   * @param mark_index
   */
  public AttributeNodeRepository(GraphDatabaseService graphDb,
      Index<Node> id_index, Index<Node> mark_index)
  {
    super(graphDb, id_index, mark_index, ID_FIELD);
    if (logger.isDebugEnabled())
      logger.debug("Building new AttributeNodeRepository");
  }

  /**
   * @return a free id
   */
  public long getFreeId()
  {
    Random r = new Random();
    long id = r.nextLong();
    while (getNodeById(id) != null)
      id = r.nextLong();
    return id;

  }

  /**
   * Create new node
   * 
   * @param id
   *          ID of the node
   * @param object
   *          The object attribute
   * @return the new NodeAttribute object
   * @throws NodeRepositoryException
   *           if the attribute already exists
   */
  @Override
  public AttributeNode createNode(RelevantPartialBoardState object)
      throws NodeRepositoryException
  {
    // to guard against duplications we use the lock grabbed on ref node
    // when
    // creating a relationship and are optimistic about person not existing
    if (logger.isDebugEnabled())
      logger.debug("Opening transaction for attribute node creation");
    Transaction tx = graphDb.beginTx();
    try
      {

        if (logger.isDebugEnabled())
          logger.debug("Node creation with " + ID_FIELD + " = "
              + object.getId());
        Node newNodeAttribute = graphDb.createNode();
        newNodeAttribute.setProperty(ID_FIELD, object.getId());

        if (logger.isDebugEnabled())
          logger.debug("Serializing object");
        // / SERIALIZE TO BYTE[]
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out;
        try
          {
            out = new ObjectOutputStream(bos);
            out.writeObject(object);
          }
        catch (IOException e)
          {
            logger.fatal("An error occured when serializing object");
            throw new NodeRepositoryException(
                "An error occured when serializing object", e);
          }
        byte[] bytes = bos.toByteArray();
        // ///////////////////////

        newNodeAttribute.setProperty("object", bytes);
        newNodeAttribute.setProperty(MARK_FIELD, (double) 0.5);

        if (logger.isDebugEnabled())
          logger.debug("Creating relationship to the root node");
        refNode.createRelationshipTo(newNodeAttribute, RelTypes.ATTR);

        // lock now taken, we can check if already exist in index
        if (logger.isDebugEnabled())
          logger.debug("Searching for Attribute with " + AttributeNode.ID_FIELD
              + " = " + object.getId());
        Node alreadyExist = id_index
            .get(AttributeNode.ID_FIELD, object.getId()).getSingle();
        if (alreadyExist != null)
          {
            logger
                .warn("Transaction exited because of duplicate ID for attribute node type");
            tx.failure();
            throw new NodeRepositoryException(
                "Attribute with this ID already exists ");
          }

        if (logger.isDebugEnabled())
          logger.debug("Setting properties");
        newNodeAttribute.setProperty(AttributeNode.ID_FIELD, object.getId());

        if (logger.isDebugEnabled())
          logger.debug("Indexing " + ID_FIELD);
        id_index.add(newNodeAttribute, AttributeNode.ID_FIELD, object.getId());

        if (logger.isDebugEnabled())
          logger.debug("Indexing " + MARK_FIELD);
        mark_index.add(newNodeAttribute, ObjectNode.MARK_FIELD, (double) 0.5);

        tx.success();
        if (logger.isDebugEnabled())
          logger.debug("Ok new Attribute created");
        return new AttributeNode(newNodeAttribute);
      }
    finally
      {
        if (logger.isDebugEnabled())
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
   * @throws NodeRepositoryException
   */
  @Override
  public AttributeNode getNodeById(long id)
  {
    if (logger.isDebugEnabled())
      logger.debug("Getting an attribute by ID " + id);
    Node attribute = id_index.get(AttributeNode.ID_FIELD, id).getSingle();
    if (attribute == null)
      {
        logger.warn("Attribute not found");
        return null;
      }
    if (logger.isDebugEnabled())
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

        if (logger.isDebugEnabled())
          logger.debug("Removing from index " + ID_FIELD + " = "
              + attribute.getId());
        id_index.remove(nodeAttribute);
        mark_index.remove(nodeAttribute);

        if (logger.isDebugEnabled())
          logger.debug("Removing relationships to objects");
        for (Relationship rel : nodeAttribute.getRelationships(
            RelTypes.RELATED, Direction.INCOMING))
          {
            rel.delete();
          }
        if (logger.isDebugEnabled())
          logger.debug("Removing main relation");
        nodeAttribute.getSingleRelationship(RelTypes.ATTR, Direction.INCOMING)
            .delete();

        if (logger.isDebugEnabled())
          logger.debug("Removing attribute node");
        nodeAttribute.delete();
        tx.success();
      }
    finally
      {
        if (logger.isDebugEnabled())
          logger.debug("Finish transaction");
        tx.finish();
      }
  }

  /**
   * @return all NodeAttributes in the database
   */
  @Override
  public Iterable<AttributeNode> getAllNodesWithoutLast()
  {
    if (logger.isDebugEnabled())
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

  @Override
  protected Node getRootNode(GraphDatabaseService graphDb)
  {
    return getRootNode(graphDb, RelTypes.REF_ATTRIBUTES);
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.persistence.neo4j.AbstractLatticeConceptNodeRepository#getBestValued
   * () */
  @Override
  public List<AttributeNode> getBestValued() throws NodeRepositoryException
  {
    return getBestValued(null);
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.persistence.neo4j.AbstractLatticeConceptNodeRepository#getBestValued
   * (java.lang.Integer) */
  @Override
  public List<AttributeNode> getBestValued(Integer n)
      throws NodeRepositoryException
  {

    QueryContext query = new QueryContext(MARK_FIELD + ":*").sort(new Sort(
        new SortField(MARK_FIELD, SortField.STRING, true))); // TODO Try to test
                                                             // with
                                                             // SortField.DOUBLE

    IndexHits<Node> results = mark_index.query(query);

    ArrayList<AttributeNode> attributes = new ArrayList<>();

    int i = 0;

    for (Node node : results)
      {
        if (n == null || i < n)
          attributes.add(new AttributeNode(node));
        else
          break;

        ++i;
      }
    return attributes;
  }

}
