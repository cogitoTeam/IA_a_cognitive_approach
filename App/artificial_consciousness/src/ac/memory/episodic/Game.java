/**
 * 
 */
package ac.memory.episodic;

import java.util.Date;

import org.apache.log4j.Logger;

import ac.memory.Memory;

/**
 * Reprensents a game in the episodic memory
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public class Game
{
  private static final Logger logger = Logger.getLogger(Game.class);

  private Move last_move;
  private Memory.FinalGameStatus final_status;
  private float score;
  private Date date;
  private int quantity;

  /**
   * @return the last_move
   */
  public Move getLast_move()
  {
    return last_move;
  }

  /**
   * @return the final_status
   */
  public Memory.FinalGameStatus getFinal_status()
  {
    return final_status;
  }

  /**
   * @return the score
   */
  public float getScore()
  {
    return score;
  }

  /**
   * @return the date
   */
  public Date getDate()
  {
    return date;
  }

  /**
   * @return the quatity of moves
   */
  public int getQuantity()
  {
    return quantity;
  }

  /**
   * Default constructor
   */
  public Game()
  {
    if (logger.isDebugEnabled()) logger.debug("Creating new Game");
    this.last_move = null;
    this.final_status = Memory.FinalGameStatus.UNDEFINED;
    this.score = 0;
    this.date = new Date();
    this.quantity = 0;
  }

  /**
   * Add new move to the game
   * 
   * @param move
   *          the new move
   */
  public void addMove(Move move)
  {
    if (logger.isDebugEnabled()) logger.debug("Adding move " + move + " to the game");
    Move last = this.last_move;
    move.setPrev_move(last);
    this.last_move = move;
    this.quantity += 1;
  }

  @Override
  public String toString()
  {
    String ret = "[[ GAME | " + this.date + " | quantity = " + this.quantity
        + ":";
    Move m = last_move;
    while (m != null)
      {
        ret += "\n      " + m;
        m = m.prev_move;
      }
    ret += "\n   ]]";
    return ret;
  }
}
