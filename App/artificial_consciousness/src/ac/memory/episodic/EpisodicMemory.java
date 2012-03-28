package ac.memory.episodic;

import java.util.List;

/**
 * Interface for an episodic memory
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public interface EpisodicMemory {
    /**
     * @return the more recent game
     */
    Game getLastGame();

    /**
     * @param number
     *            The number of last games wanted
     * @return A list of game, ordered from the more recent to the less recent
     */
    List<Game> getLastGames(int number);

    /**
     * Add a game in the memory.
     * 
     * @param game
     */
    void addGame(Game game);
}
