/**
 * 
 */
package ac.memory.episodic;

import java.util.Date;

import org.apache.log4j.Logger;

import ac.shared.CompleteBoardState;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public class Move
{
  @SuppressWarnings("unused")
  private static final Logger logger = Logger.getLogger(Move.class);

  CompleteBoardState board_state;

  Move next_move;
  Move prev_move;
  Date date;

  /**
   * Default constructor
   * 
   * @param board_state
   */
  public Move(CompleteBoardState board_state)
  {
    this.board_state = board_state;
    this.date = new Date();
  }

  /**
   * @return the next_move
   */
  public Move getNext_move()
  {
    return next_move;
  }

  /**
   * @param next_move
   *          the next_move to set
   */
  public void setNext_move(Move next_move)
  {
    this.next_move = next_move;
  }

  /**
   * @return the prev_move
   */
  public Move getPrev_move()
  {
    return prev_move;
  }

  /**
   * @param prev_move
   *          the prev_move to set
   */
  public void setPrev_move(Move prev_move)
  {
    this.prev_move = prev_move;
  }

  /**
   * @return the board_state
   */
  public CompleteBoardState getBoard_state()
  {
    return board_state;
  }

  /**
   * @return the date
   */
  public Date getDate()
  {
    return date;
  }

  @Override
  public String toString()
  {
    return "[[ MOVE | " + date.toString() + "]]";
  }

}
