/*****************
 * @author william
 * @date 30-Mar-2012
 *****************/


package frontier;


public class GameEndPercept extends Percept
{
    /* NESTING */
    
    static public enum Result
    {
        WIN, LOSE, DRAW
    }
    
    
    /* ATTRIBUTES */
    private final Result result;
    private final int score;
    
    
    /* METHODS */
    
    // creation
    
    public GameEndPercept(Result _result, int _score)
    {
        result = _result;
        score = _score;
    }
    
    // query
    public Result getResult()
    {
        return result;
    }
    
    public int getScore()
    {
        return score;
    }
   
}
