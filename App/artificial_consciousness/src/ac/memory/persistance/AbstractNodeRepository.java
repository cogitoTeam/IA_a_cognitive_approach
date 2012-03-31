package ac.memory.persistance;

import ac.memory.persistance.RelTypes;
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

  protected GraphDatabaseService graphDb;
  protected Index<Node> index;
  protected Node refNode;
  protected String ID_FIELD;

  /**
   * Default constructor
   * 
   * @param graphDb
   *          the database
   * @param index
   *          index id
   * @param id_field
   *          field identifier
   */
  public AbstractNodeRepository(GraphDatabaseService graphDb,
      Index<Node> index, String id_field)
  {
    this.graphDb = graphDb;
    this.index = index;
    this.ID_FIELD = id_field;
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
  public abstract NodeType createNode(ObjectType object) throws Exception;

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
   * @param node
   *          The attribute to remove
   */
  public abstract void deleteNode(NodeType node);

  /**
   * @return all NodeAttributes in the database
   */
  public abstract Iterable<NodeType> getAllNodes();

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
