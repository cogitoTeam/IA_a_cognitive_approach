/**
 * 
 */
package ac.memory.episodic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ac.helloworldlog4j.HelloWorldLog4j;

/**
 * Episodic Memory implemented with a LinkedList. New recent game is added as
 * the first element of the list
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public class ListEpisodicMemory implements EpisodicMemory {
    private static final Logger logger = Logger
            .getLogger(ListEpisodicMemory.class);

    private LinkedList<Game> games;

    private long quantity;

    /**
     * Default constructor
     */
    public ListEpisodicMemory() {
        this.games = new LinkedList<>();
        this.quantity = 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ac.memory.episodic.EpisodicMemory#getLastGame()
     */
    @Override
    public Game getLastGame() {
        return games.getFirst();
    }

    /*
     * (non-Javadoc)
     * 
     * @see ac.memory.episodic.EpisodicMemory#getLastGames(int)
     */
    @Override
    public List<Game> getLastGames(int number) {
        LinkedList<Game> list = new LinkedList<Game>();

        for (int i = 0; i < number; ++i) {
            list.addLast(games.get(i));
        }

        return list;
    }

    @Override
    public String toString() {
        String ret = "[[ EPISODIC MEMORY | quantity = " + this.quantity + " :";
        for (Iterator<Game> iterator = games.iterator(); iterator.hasNext();) {
            Game game = (Game) iterator.next();
            ret += "\n   " + game;
        }
        ret += "\n]]";
        return ret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ac.memory.episodic.EpisodicMemory#addGame(ac.memory.episodic.Game)
     */
    @Override
    public void addGame(Game game) {
        this.games.addFirst(game);
        this.quantity += 1;
    }

    /**
     * @return the quantity
     */
    public long getQuantity() {
        return quantity;
    }

}
