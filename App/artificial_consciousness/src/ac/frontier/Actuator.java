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
    void performMove(Position p)  throws IOException, SAXException
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
