package ac.memory.persistence;

import org.neo4j.graphdb.GraphDatabaseService;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 * @param <ObjectType>
 *          Object type stored in node
 * @param <NodeType>
 *          Type of nodes wrapping class
 */
abstract public class AbstractEpisodicNodeRepository<NodeType> extends
    AbstractNodeRepository<NodeType>
{

  static String DATE_FIELD = "date";

  /**
   * Default constructor
   * 
   * @param graphDb
   *          the database
   */
  public AbstractEpisodicNodeRepository(GraphDatabaseService graphDb)
  {
    super(graphDb);
  }

  /**
   * Get an attribute by its id
   * 
   * @return the last nodeType node
   * @throws NodeRepositoryException
   */
  public abstract NodeType getLast() throws NodeRepositoryException;

  /**
   * @return all NodeTypes in the database without last
   */
  @Override
  public abstract Iterable<NodeType> getAllNodesWithoutLast();
}
