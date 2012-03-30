package ac.memory.persistance;

import ac.memory.persistance.RelTypes;
import ac.shared.structure.RelevantPartialBoardState;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 * @param <ObjectType>
 *          Object type stored in node
 * @param <NodeType>
 *          Type of nodes wrapping class
 */
abstract public class AbstractNodeRepository<ObjectType, NodeType>
{
  private static final Logger logger = Logger
      .getLogger(AbstractNodeRepository.class);

  protected GraphDatabaseService graphDb;
  protected Index<Node> index;
  protected Node attributeRefNode;

  /**
   * Default constructor
   * 
   * @param graphDb
   *          the database
   * @param index
   */
  public AbstractNodeRepository(GraphDatabaseService graphDb, Index<Node> index)
  {
    logger.debug("Building new NodeRepository");

    this.graphDb = graphDb;
    this.index = index;
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
  public abstract AttributeNode createAttribute(RelevantPartialBoardState object)
      throws Exception;

  /**
   * Get an attribute by its id
   * 
   * @param id
   *          the ID
   * @return the attribute
   */
  public abstract NodeType getNodeById(long id);

  /**
   * Remove an attribute
   * 
   * @param attribute
   *          The attribute to remove
   */
  public abstract void deleteAttribute(NodeType attribute);

  /**
   * @return all NodeAttributes in the database
   */
  public abstract Iterable<AttributeNode> getAllNodeAttributes();

  protected Node getRootNode(GraphDatabaseService graphDb, RelTypes rel_type)
  {
    Relationship rel = graphDb.getReferenceNode().getSingleRelationship(
        rel_type, Direction.OUTGOING);

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
                rel_type);
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
