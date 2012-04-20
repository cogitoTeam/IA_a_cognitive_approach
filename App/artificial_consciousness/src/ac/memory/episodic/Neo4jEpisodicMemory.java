/**
 * 
 */
package ac.memory.episodic;

import java.util.List;

import ac.memory.persistence.neo4j.GameNode;
import ac.memory.persistence.neo4j.GameNodeRepository;
import ac.memory.persistence.neo4j.MoveNodeRepository;
import ac.memory.persistence.neo4j.Neo4jService;
import ac.memory.persistence.neo4j.NodeRepositoryException;
import ac.memory.persistence.neo4j.ObjectNode;
import ac.memory.persistence.neo4j.ObjectNodeRepository;
import ac.shared.CompleteBoardState;
import ac.shared.GameStatus;
import ac.shared.RelevantPartialBoardState;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 3 avr. 2012
 * @version 0.1
 */
public class Neo4jEpisodicMemory implements EpisodicMemory
{
  GameNodeRepository game_repo;
  MoveNodeRepository move_repo;
  ObjectNodeRepository obj_repo;

  /**
   * Default constructor
   */
  public Neo4jEpisodicMemory()
  {
    game_repo = new GameNodeRepository(Neo4jService.getInstance());
    move_repo = new MoveNodeRepository(Neo4jService.getInstance());
    obj_repo = new ObjectNodeRepository(Neo4jService.getInstance(),
        Neo4jService.getObjIndex(), Neo4jService.getObjMarkIndex());
  }

  @Override
  public Game getLastGame()
  {
    GameNode last_game = null;
    try
      {
        last_game = game_repo.getLast();
        return new Neo4jGame(last_game);
      }
    catch (NodeRepositoryException e)
      {
        return null;
      }
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.EpisodicMemory#getLastGames(int) */
  @Override
  public List<Game> getLastGames(int number)
  {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.EpisodicMemory#addGame(ac.memory.episodic.Game) */
  @Override
  public void newGame()
  {
    game_repo.createGame(GameStatus.UNDEFINED);
  }

  @Override
  public String toString()
  {
    String ret = "Neo4jEpisodicMemory[";

    Game game = getLastGame();
    while (game != null)
      {
        ret += "\n   " + game;
        game = game.getPreviousGame();
      }

    ret += "]";
    return ret;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.EpisodicMemory#newMove() */
  @Override
  public void newMove(CompleteBoardState board_state, List<Long> rpbs_ids)
      throws EpisodicMemoryException
  {
    try
      {
        ObjectNode object = obj_repo.getNodeById(board_state.getId());
        if (object == null)
          obj_repo.createNode(board_state);

        move_repo.addMove(game_repo.getLast(),
            obj_repo.getNodeById(board_state.getId()), rpbs_ids);
      }
    catch (Exception e)
      {
        throw new EpisodicMemoryException("Error occured when adding new move",
            e);
      }

  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.EpisodicMemory#finishGame(ac.shared.GameStatus) */
  @Override
  public void finishGame(GameStatus status, int score)
  {
    getLastGame().setStatus(status);
    getLastGame().setScore(Math.abs(score));
  }
}
