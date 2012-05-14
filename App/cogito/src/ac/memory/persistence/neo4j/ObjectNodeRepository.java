package ac.memory.persistence.neo4j;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import ac.memory.persistence.neo4j.RelTypes;
import ac.shared.CompleteBoardState;

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
public class ObjectNodeRepository extends
    AbstractLatticeContextNodeRepository<CompleteBoardState, ObjectNode>
{
  private static final Logger logger = Logger
      .getLogger(ObjectNodeRepository.class);

  static String ID_FIELD = "id_obj";

  /**
   * @param graphDb
   * @param id_index
   * @param mark_index
   */
  public ObjectNodeRepository(GraphDatabaseService graphDb,
      Index<Node> id_index, Index<Node> mark_index)
  {
    super(graphDb, id_index, mark_index, ID_FIELD);
    if (logger.isDebugEnabled())
      logger.debug("Building new ObjectNodeRepository");
  }

  /**
   * Create new node object
   * 
   * @param id
   *          ID of the node
   * @param object
   *          The object Object
   * @return the new NodeObject object
   * @throws Exception
   *           if the object already exists
   */
  @Override
  public ObjectNode createNode(CompleteBoardState object) throws Exception
  {
    if (logger.isDebugEnabled())
      logger.debug("Opening transaction for object node creation");
    Transaction tx = graphDb.beginTx();
    try
      {

        if (logger.isDebugEnabled())
          logger.debug("Node creation with " + ID_FIELD + " = "
              + object.getId());
        Node newNode = graphDb.createNode();
        newNode.setProperty(ID_FIELD, object.getId());

        if (logger.isDebugEnabled())
          logger.debug("Serializing object");
        // / SERIALIZE TO BYTE[]
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(object);
        byte[] bytes = bos.toByteArray();
        // ///////////////////////

        newNode.setProperty("object", bytes);
        newNode.setProperty(MARK_FIELD, (double) 0.5);
        logger.debug("Indexing new mark");
        Neo4jService.getObjMarkIndex().add(newNode, MARK_FIELD, (double) 0.5);

        if (logger.isDebugEnabled())
          logger.debug("Creating relationship to the root node");
        refNode.createRelationshipTo(newNode, RelTypes.OBJ);

        if (logger.isDebugEnabled())
          logger.debug("Searching for Objects with " + ObjectNode.ID_FIELD
              + " = " + object.getId());
        Node alreadyExist = id_index.get(ObjectNode.ID_FIELD, object.getId())
            .getSingle();
        if (alreadyExist != null)
          {
            logger
                .warn("Transaction exited because of duplicate ID for object node type");
            tx.failure();
            throw new Exception("Attribute with this ID already exists ");
          }

        if (logger.isDebugEnabled())
          logger.debug("Setting properties");
        newNode.setProperty(ObjectNode.ID_FIELD, object.getId());

        if (logger.isDebugEnabled())
          logger.debug("Indexing " + ID_FIELD);
        id_index.add(newNode, ObjectNode.ID_FIELD, object.getId());

        if (logger.isDebugEnabled())
          logger.debug("Indexing " + MARK_FIELD);
        mark_index.add(newNode, ObjectNode.MARK_FIELD, (double) 0.5);

        tx.success();
        if (logger.isDebugEnabled())
          logger.debug("Ok new Object created");
        return new ObjectNode(newNode);
      }
    finally
      {
        if (logger.isDebugEnabled())
          logger.debug("Finish transaction");
        tx.finish();
      }
  }

  /**
   * Get an object by its id
   * 
   * @param id
   *          the ID
   * @return the objectNode
   */
  @Override
  public ObjectNode getNodeById(long id)
  {
    if (logger.isDebugEnabled())
      logger.debug("Getting an object by ID " + id);
    Node object = id_index.get(ObjectNode.ID_FIELD, id).getSingle();
    if (object == null)
      {
        logger.warn("Object not found");
        return null;
      }
    if (logger.isDebugEnabled())
      logger.debug("Object found");
    return new ObjectNode(object);
  }

  @Override
  public List<ObjectNode> getBestValued() throws NodeRepositoryException
  {
    return getBestValued(null);
  }

  @Override
  public List<ObjectNode> getBestValued(Integer n)
      throws NodeRepositoryException
  {

    QueryContext query = new QueryContext(MARK_FIELD + ":*").sort(new Sort(
        new SortField(MARK_FIELD, SortField.STRING, true)));

    IndexHits<Node> results = mark_index.query(query);

    ArrayList<ObjectNode> objects = new ArrayList<>();

    int i = 0;

    for (Node node : results)
      {
        if (n == null || i < n)
          objects.add(new ObjectNode(node));
        else
          break;

        ++i;
      }
    return objects;
  }

  /**
   * Remove an object
   * 
   * @param node
   *          The object to remove
   */
  @Override
  public void deleteNode(ObjectNode node)
  {
    if (logger.isDebugEnabled())
      logger.debug("Opening transaction to delete object " + node.getId());
    Transaction tx = graphDb.beginTx();
    try
      {
        Node nodeObject = node.getUnderlyingNode();

        if (logger.isDebugEnabled())
          logger
              .debug("Removing from index " + ID_FIELD + " = " + node.getId());
        id_index.remove(nodeObject, ObjectNode.ID_FIELD, node.getId());

        if (logger.isDebugEnabled())
          logger.debug("Removing relationships to attributes");
        for (Relationship rel : nodeObject.getRelationships(RelTypes.RELATED,
            Direction.INCOMING))
          {
            rel.delete();
          }
        if (logger.isDebugEnabled())
          logger.debug("Removing main relation");
        nodeObject.getSingleRelationship(RelTypes.OBJ, Direction.INCOMING)
            .delete();

        if (logger.isDebugEnabled())
          logger.debug("Removing object node");
        nodeObject.delete();
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
  public Iterable<ObjectNode> getAllNodesWithoutLast()
  {
    if (logger.isDebugEnabled())
      logger.debug("Getting all the object nodes");

    return new IterableWrapper<ObjectNode, Relationship>(
        refNode.getRelationships(RelTypes.OBJ))
    {
      @Override
      protected ObjectNode underlyingObjectToObject(Relationship objectRel)
      {
        return new ObjectNode(objectRel.getEndNode());
      }
    };
  }

  @Override
  protected Node getRootNode(GraphDatabaseService graphDb)
  {
    return getRootNode(graphDb, RelTypes.REF_OBJECTS);
  }

}
