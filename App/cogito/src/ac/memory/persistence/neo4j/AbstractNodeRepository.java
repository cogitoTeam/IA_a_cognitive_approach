package ac.memory.persistence.neo4j;

import ac.memory.persistence.neo4j.RelTypes;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 * @param <ObjectType>
 *          Object type stored in node
 * @param <NodeType>
 *          Type of nodes wrapping class
 */
abstract public class AbstractNodeRepository<NodeType>
{

  protected GraphDatabaseService graphDb;
  protected Node refNode;

  /**
   * Default constructor
   * 
   * @param graphDb
   *          the database
   */
  public AbstractNodeRepository(GraphDatabaseService graphDb)
  {
    this.graphDb = graphDb;
    refNode = getRootNode(graphDb);
  }

  /**
   * @return all NodeAttributes in the database
   */
  public abstract Iterable<NodeType> getAllNodesWithoutLast();

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

  protected abstract Node getRootNode(GraphDatabaseService graphDb);
}
