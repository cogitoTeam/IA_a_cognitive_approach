/**
 * 
 */
package ac.memory.persistance;

import java.util.Date;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.IterableWrapper;

import ac.memory.Memory;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 2 avr. 2012
 * @version 0.1
 */
public class GameNodeRepository extends
    AbstractEpisodicNodeRepository<GameNode>
{
  private static final Logger logger = Logger
      .getLogger(GameNodeRepository.class);

  private static String STATUS_FIELD = "status";

  /**
   * Default constructor
   * 
   * @param graphDb
   *          the database
   */
  public GameNodeRepository(GraphDatabaseService graphDb)
  {
    super(graphDb);
  }

  /**
   * @param status
   *          status of the game
   * @return the new GameNode
   */
  public GameNode createGame(Memory.FinalGameStatus status)
  {
    // to guard against duplications we use the lock grabbed on ref node
    // when
    // creating a relationship and are optimistic about person not existing
    if (logger.isDebugEnabled())
      logger.debug("Opening transaction for game node creation");
    Transaction tx = graphDb.beginTx();
    try
      {

        if (logger.isDebugEnabled())
          logger.debug("Game node creation");
        Node newGameNode = graphDb.createNode();

        long date = (new Date()).getTime();
        if (logger.isDebugEnabled())
          logger.debug("Game node creation at date = " + date);
        newGameNode.setProperty(DATE_FIELD, date);
        newGameNode.setProperty(STATUS_FIELD, status);

        if (logger.isDebugEnabled())
          logger.debug("Creating relationship to the root node");
        refNode.createRelationshipTo(newGameNode, RelTypes.GAME);

        if (logger.isDebugEnabled())
          logger.debug("Moving LAST_GAME relationship to this new game");
        refNode.getSingleRelationship(RelTypes.LAST_GAME, Direction.BOTH)
            .delete();
        refNode.createRelationshipTo(newGameNode, RelTypes.LAST_GAME);

        tx.success();
        if (logger.isDebugEnabled())
          logger.debug("New Game added successfuly");
        return new GameNode(newGameNode);
      }
    finally
      {
        if (logger.isDebugEnabled())
          logger.debug("Finish transaction");
        tx.finish();
      }
  }

  /**
   * Remove oldest games
   * 
   * @param nb
   *          The number of oldest games to remove
   */
  public void deleteOldestGames(int nb)
  {
    // TODO the method
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistance.AbstractEpisodicNodeRepository#getLast() */
  @Override
  public GameNode getLast() throws NodeRepositoryException
  {
    Relationship rel = refNode.getSingleRelationship(RelTypes.LAST_GAME,
        Direction.OUTGOING);
    if (rel != null)
      return new GameNode(rel.getEndNode());
    else
      {
        throw new NodeRepositoryException("Error : no LAST_GAME node found");
      }
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.persistance.AbstractEpisodicNodeRepository#getAllNodes() */
  @Override
  public Iterable<GameNode> getAllNodes()
  {
    if (logger.isDebugEnabled())
      logger.debug("Getting all the games nodes");
    return new IterableWrapper<GameNode, Relationship>(
        refNode.getRelationships(RelTypes.GAME))
    {
      @Override
      protected GameNode underlyingObjectToObject(Relationship gameRel)
      {
        return new GameNode(gameRel.getEndNode());
      }
    };
  }

}
