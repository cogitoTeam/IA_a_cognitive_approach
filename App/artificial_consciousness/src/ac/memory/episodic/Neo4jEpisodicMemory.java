/**
 * 
 */
package ac.memory.episodic;

import java.util.List;

import ac.memory.persistence.GameNode;
import ac.memory.persistence.GameNodeRepository;
import ac.memory.persistence.MoveNodeRepository;
import ac.memory.persistence.Neo4jService;
import ac.memory.persistence.NodeRepositoryException;
import ac.memory.persistence.ObjectNodeRepository;
import ac.shared.CompleteBoardState;
import ac.shared.GameStatus;

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
        Neo4jService.getObjIndex());
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
  public void newMove(CompleteBoardState board_state)
      throws EpisodicMemoryException
  {
    try
      {
        move_repo.addMove(game_repo.getLast(),
            obj_repo.getNodeById(board_state.getId()));
      }
    catch (NodeRepositoryException e)
      {
        throw new EpisodicMemoryException("Error occured when adding new move",
            e);
      }

  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.episodic.EpisodicMemory#finishGame(ac.shared.GameStatus) */
  @Override
  public void finishGame(GameStatus status)
  {
    getLastGame().setStatus(status);
  }
}
