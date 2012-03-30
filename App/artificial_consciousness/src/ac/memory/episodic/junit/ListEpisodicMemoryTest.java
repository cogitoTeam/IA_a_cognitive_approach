/**
 * 
 */
package ac.memory.episodic.junit;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import ac.memory.episodic.Game;
import ac.memory.episodic.ListEpisodicMemory;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 29 mars 2012
 * @version 0.1
 */
public class ListEpisodicMemoryTest
{

  /**
   * Test method for {@link ac.memory.episodic.ListEpisodicMemory}.
   */
  @Test
  public void testGame()
  {
    ListEpisodicMemory e = new ListEpisodicMemory();
    try
      {
        e.getLastGame();
        fail("Should be empty");
      }
    catch (Exception ex)
      {

      }
    assertEquals(0, e.getQuantity());

    Game g1 = new Game();
    e.addGame(g1);
    assertEquals(g1, e.getLastGame());
    assertEquals(1, e.getQuantity());

    Game g2 = new Game();
    e.addGame(g2);
    assertEquals(g2, e.getLastGame());
    assertEquals(2, e.getQuantity());
  }

  /**
   * Test method for
   * {@link ac.memory.episodic.ListEpisodicMemory#getLastGames(int)}.
   */
  @Test
  public void testGetLastGames()
  {
    ListEpisodicMemory e = new ListEpisodicMemory();
    try
      {
        e.getLastGame();
        fail("Should be empty");
      }
    catch (Exception ex)
      {

      }
    assertEquals(0, e.getQuantity());

    Game g1 = new Game();
    e.addGame(g1);
    assertEquals(g1, e.getLastGame());
    assertEquals(1, e.getQuantity());

    Game g2 = new Game();
    e.addGame(g2);
    assertEquals(g2, e.getLastGame());
    assertEquals(2, e.getQuantity());

    Game g3 = new Game();
    e.addGame(g3);
    assertEquals(g3, e.getLastGame());
    assertEquals(3, e.getQuantity());

    Game g4 = new Game();
    e.addGame(g4);
    assertEquals(g4, e.getLastGame());
    assertEquals(4, e.getQuantity());

    LinkedList<Game> ll = new LinkedList<Game>();
    ll.addFirst(g3);
    ll.addFirst(g4);
    assertEquals(ll, e.getLastGames(2));
  }

}
