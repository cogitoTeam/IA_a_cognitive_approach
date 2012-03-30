/**
 * 
 */
package ac.memory.episodic.junit;

import static org.junit.Assert.*;

import org.junit.Test;

import ac.memory.episodic.Game;
import ac.memory.episodic.Move;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 29 mars 2012
 * @version 0.1
 */
public class GameTest
{

  /**
   * Test method for {@link ac.memory.episodic.Game}.
   */
  @Test
  public void testGame()
  {

    Game g = new Game();
    assertEquals(null, g.getLast_move());
    assertEquals(0, g.getQuantity());

    Move m1 = new Move(null);
    g.addMove(m1);
    assertEquals(m1, g.getLast_move());
    assertEquals(1, g.getQuantity());

    Move m2 = new Move(null);
    g.addMove(m2);
    assertEquals(m2, g.getLast_move());
    assertEquals(m1, g.getLast_move().getPrev_move());
    assertEquals(2, g.getQuantity());

  }

}
