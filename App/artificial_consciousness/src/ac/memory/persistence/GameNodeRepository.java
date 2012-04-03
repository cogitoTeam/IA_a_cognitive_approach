/**
 * 
 */
package ac.memory.persistence;

import java.util.Date;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.IterableWrapper;

import ac.shared.GameStatus;

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
  public GameNode createGame(GameStatus status)
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
        newGameNode.setProperty(STATUS_FIELD, status.toString());

        if (logger.isDebugEnabled())
          logger.debug("Moving LAST_GAME relationship to this new game");
        Relationship last_rel = refNode.getSingleRelationship(
            RelTypes.LAST_GAME, Direction.BOTH);

        if (last_rel != null)
          {
            Node old_last_game = last_rel.getEndNode();
            newGameNode.createRelationshipTo(old_last_game, RelTypes.PREV_GAME);
            refNode.createRelationshipTo(old_last_game, RelTypes.GAME);
            last_rel.delete();
          }
        else
          logger
              .warn("No LAST_GAME relationship found. This is maybe due to a first game creation");

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
  public Iterable<GameNode> getAllNodesWithoutLast()
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

  @Override
  protected Node getRootNode(GraphDatabaseService graphDb)
  {
    return getRootNode(graphDb, RelTypes.REF_GAME);
  }

}
