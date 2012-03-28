/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import game.BoardMatrix.Position;
import java.io.IOException;
import org.xml.sax.SAXException;


public abstract class Actuator 
{
    /* METHODS */
    
    // creation
    public Actuator(String s_url)
    {
        
    }
    
    // command
    boolean tryMove(Position p)  throws IOException, SAXException
    {
        // return true if the move succeed, false otherwise
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
