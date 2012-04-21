/*****************
 * @author william
 * @date 26-Mar-2012
 *****************/

package service;

import game.Game;
import game.ReversiRules;
import game.Rules;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class GameManager 
{
    /* SINGLETON */
    public static GameManager instance = null;
    public static synchronized GameManager getInstance()
    {
        if(instance == null)
            instance = new GameManager();
        return instance;
    }
    
    /* CLASS NAMESPACE ATTRIBUTES */
    private static int next_id = 0;
    
    /* ATTRIBUTES */
    
    private Rules rules;
    private Map<Integer, Game> games;
    private Queue<Game> waiting;
    
    /* METHODS */
    
    // singleton
    private GameManager()
    {
        //rules = MorpionRules.getInstance();
        rules = ReversiRules.getInstance();
        games = new HashMap<Integer, Game>();
        waiting = new LinkedList<Game>();
    }
    
    // query
    public synchronized Game getGame(int id)
    {
        return games.get(id);
    }
    
    // modification
    public synchronized void setRules(Rules _rules)
    {
        rules = _rules;
    }
    
    public synchronized Game findGame()
    {
        // always join a game if possible
        if(waiting.isEmpty())
            return newGame();
        else
            return openGame();
    }
    
    /* SUBROUTINES */
    
    private synchronized Game newGame()
    {
        // create and save the new game
        Game game = new Game(next_id, rules);
        games.put(next_id, game);
        
        // remember to increment the id generator!
        next_id++;
        
        // add the game to the waiting queue
        waiting.add(game);
        
        // return the game we created
        return game;
    }
    
    private synchronized Game openGame()
    {
        // join the game at the head of the queue
        Game open_game = waiting.poll();
        open_game.join();
        return open_game;
    }
    
}
