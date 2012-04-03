/*****************
 * @author william
 * @date 29-Mar-2012
 *****************/

package agent;

import game.BoardMatrix;
import java.util.LinkedList;
import java.util.List;

import ac.shared.GameStatus;

public abstract class Percept
{
  /* NESTING */

  public static enum Type
  {
    WAITING_FOR_PLAYER, OPPONENT_TURN, CHOICES, GAME_END
  }

  /* ATTRIBUTES */

  private final Type type;
  private final BoardMatrix current_board;

  /* METHODS */

  // creation
  protected Percept(Type _type, BoardMatrix _current_board)
  {
    type = _type;
    current_board = _current_board;
  }

  // query
  public BoardMatrix getCurrentBoard()
  {
    return current_board;
  }

  public Type getType()
  {
    return type;
  }

  /* THE OTHER PLAYER HASN'T YET JOINED THE GAME */

  public static class WaitingForPlayer extends Percept
  {
    // creation
    public WaitingForPlayer(BoardMatrix _current_board)
    {
      super(Type.WAITING_FOR_PLAYER, _current_board);
    }
  }

  /* IT'S NOT OUR TURN TO PLAY */

  public static class OpponentTurn extends Percept
  {
    // creation
    public OpponentTurn(BoardMatrix _current_board)
    {
      super(Type.OPPONENT_TURN, _current_board);
    }
  }

  /* IT'S OUR TURN TO PLAY */

  public static class Choices extends Percept
  {
    // attributes
    private final List<Action.Option> options;

    // creation
    public Choices(BoardMatrix _current_board)
    {
      super(Type.CHOICES, _current_board);
      options = new LinkedList<Action.Option>();
    }

    // query
    public List<Action.Option> getOptions()
    {
      return options;
    }
  }

  /* THE GAME HAS ENDED */

  public static class GameEnd extends Percept
  {
    // attributes
    private final int score;

    // creation
    protected GameEnd(GameStatus _game_end_status,
        BoardMatrix _current_board, int _score)
    {
      super(Type.GAME_END, _current_board);
      score = _score;
    }

    // query
    public int getScore()
    {
      return score;
    }
  }

}
