package ac.memory.persistence.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
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
abstract public class AbstractLatticeConceptNodeRepository<ObjectType, NodeType>
    extends AbstractNodeRepository<NodeType>
{
  protected Index<Node> id_index;
  protected Index<Node> mark_index; // TODO Index data
  protected String ID_FIELD;
  protected String MARK_FIELD = "mark";

  /**
   * Default constructor
   * 
   * @param graphDb
   *          the database
   * @param id_index
   *          index id
   * @param mark_index
   *          index mark
   * @param id_field
   *          field identifier
   */
  public AbstractLatticeConceptNodeRepository(GraphDatabaseService graphDb,
      Index<Node> id_index, Index<Node> mark_index, String id_field)
  {
    super(graphDb);
    this.id_index = id_index;
    this.mark_index = mark_index;
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
   * @throws NodeRepositoryException
   */
  public abstract NodeType getNodeById(long id) throws NodeRepositoryException;

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
  @Override
  public abstract Iterable<NodeType> getAllNodesWithoutLast();
}
