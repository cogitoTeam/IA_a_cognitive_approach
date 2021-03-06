package ac.memory.persistence.neo4j;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * Singleton to get an instance of the neo4j database
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 */
public class Neo4jService
{
  private static final Logger logger = Logger.getLogger(Neo4jService.class);

  private static final String DB_PATH = System.getProperty("user.dir")
      + "/neo4j/";
  private static GraphDatabaseService database;
  private static Index<Node> attr_index;
  private static Index<Node> attr_mark_index;
  private static Index<Node> obj_index;
  private static Index<Node> obj_mark_index;

  static
    {
      database = null;
      attr_index = null;
      obj_index = null;
      attr_mark_index = null;
      obj_mark_index = null;
    }

  /**
   * @return the unique instance of the database
   */
  public static GraphDatabaseService getInstance()
  {
    if (database == null)
      {
        if (logger.isDebugEnabled())
          {
            logger
                .debug("First call for Neo4jService, database initialization");
            logger.debug("Path of the database: " + DB_PATH);
          }
        try
          {
            database = new EmbeddedGraphDatabase(DB_PATH);
          }
        catch (Exception e)
          {
            logger.debug("Error when creating the embedded database.");
            throw e;
          }
        registerShutdownHook(database);
      }

    return database;
  }

  /**
   * @return the unique instance of obj index
   */
  public static Index<Node> getObjIndex()
  {
    if (obj_index == null)
      obj_index = database.index().forNodes("obj");

    return obj_index;
  }

  /**
   * @return the unique instance of attr index
   */
  public static Index<Node> getAttrIndex()
  {
    if (attr_index == null)
      attr_index = database.index().forNodes("attr");

    return attr_index;
  }

  /**
   * @return the unique instance of attr mark index
   */
  public static Index<Node> getAttrMarkIndex()
  {
    if (attr_mark_index == null)
      attr_mark_index = database.index().forNodes("mark_attr");

    return attr_mark_index;
  }

  /**
   * @return the unique instance of obj mark index
   */
  public static Index<Node> getObjMarkIndex()
  {
    if (obj_mark_index == null)
      obj_mark_index = database.index().forNodes("mark_obj");

    return obj_mark_index;
  }

  private static void registerShutdownHook(final GraphDatabaseService graphDb)
  {
    // Registers a shutdown hook for the Neo4j instance so that it
    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
    // running example before it's completed)
    Runtime.getRuntime().addShutdownHook(new Thread()
    {
      @Override
      public void run()
      {
        if (logger.isDebugEnabled())
          logger.debug("Thread down : shutdown the database");
        graphDb.shutdown();
        if (logger.isDebugEnabled())
          logger.debug("Thread down : OK !");
      }
    });
  }
}
