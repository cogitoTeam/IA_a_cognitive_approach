/**
 * 
 */
package ac.memory.episodic;

import static org.junit.Assert.*;

import org.junit.Test;

import ac.shared.GameStatus;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 6 avr. 2012
 * @version 0.1
 */
public class EpisodicMemoryUtilTest
{

  /**
   * Test method for
   * {@link ac.memory.episodic.EpisodicMemoryUtil#DeacreaseMoveFormula(int, int, ac.shared.GameStatus)}
   * .
   */
  @Test
  public void testDeacreaseMoveFormula()
  {
    // CHECK FIRST MOVE IS AT 100 (WON)
    assertEquals(100,
        EpisodicMemoryUtil.DeacreaseMoveFormula(1, 1, GameStatus.VICTORY));

    // CHECK THE FUNCTION IS DECREASING (WON)
    long before = 100;
    long next;
    for (int i = 2; i < 100; ++i)
      {
        next = EpisodicMemoryUtil
            .DeacreaseMoveFormula(i, 1, GameStatus.VICTORY);
        assertTrue(next < before);
        next = before;
      }

    // CHECK FIRST MOVE IS AT 100 (LOST)
    assertEquals(-100,
        EpisodicMemoryUtil.DeacreaseMoveFormula(1, 1, GameStatus.DEFEAT));

    // CHECK THE FUNCTION IS INSCREASING (LOST)
    before = -100;
    for (int i = 2; i < 100; ++i)
      {
        next = EpisodicMemoryUtil
            .DeacreaseMoveFormula(i, 1, GameStatus.DEFEAT);
        assertTrue(next > before);
        next = before;
      }

    // CHECK IGNORE OTHER STATUS
    assertEquals(0,
        EpisodicMemoryUtil.DeacreaseMoveFormula(1, 1, GameStatus.DRAW));
    assertEquals(0,
        EpisodicMemoryUtil.DeacreaseMoveFormula(1, 1, GameStatus.UNDEFINED));
    assertEquals(0,
        EpisodicMemoryUtil.DeacreaseMoveFormula(1, 1, GameStatus.UNRECOGNIZED));

    // CHECK SCORE OK
    assertEquals(1000,
        EpisodicMemoryUtil.DeacreaseMoveFormula(1, 10, GameStatus.VICTORY));
    assertEquals(-1000,
        EpisodicMemoryUtil.DeacreaseMoveFormula(1, 10, GameStatus.DEFEAT));
  }

}
