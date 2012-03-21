/*****************
 * @author william
 * @date 21-Mar-2012
 *****************/


package game;


public class ReversiGame extends Game
{
    /** METHODS **/
    
    public ReversiGame()
    {
        super(ReversiRules.getInstance(), new ReversiBoard());
    }
}
