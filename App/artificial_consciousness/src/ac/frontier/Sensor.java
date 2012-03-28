/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import game.BoardMatrix;
import game.BoardMatrix.Position;
import game.Game;
import game.Game.Player;
import game.Rules;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


public abstract class Sensor 
{
    /* ATTRIBUTES */
    private DocumentBuilder xml_builder;
    private final String base_url;
    
    /* METHODS */
    
    // creation
    public Sensor(String _base_url) throws ParserConfigurationException
    {
        // Create the XML document builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        xml_builder = factory.newDocumentBuilder();

        // Create the URL object
        base_url = _base_url;
    }
    
    // query
    public List<Option> getOptions(Player player) throws IOException, SAXException
    {
        // get an XML document from the server
        Document doc = xml_builder.parse(new URL(base_url).openStream());
        

        // parse the current board
        BoardMatrix board = parseBoard(doc.getDocumentElement()
                                .getElementsByTagName("board").item(0));
        
        // get legal moves
        getRules().getLegalMoves(board, player);
        
        // fixme
        return null;
    }
    
    /* SUBROUTINES / INTERFACE */
    
    private BoardMatrix parseBoard(Node board_node)
    {
        // get the cell nodes from the document
        NodeList cells = board_node.getChildNodes();
        
        // parse the current board
        BoardMatrix board = createBoard();
        
        // parse each cell
        for(int i = 0; i < cells.getLength(); i++)
        {
            // local variables
            NamedNodeMap attributes = cells.item(i).getAttributes();
            
            // parse position
            Position p = new Position(0, 0);
            p.row = Integer.parseInt(attributes.getNamedItem("row").getNodeValue());
            p.col = Integer.parseInt(attributes.getNamedItem("col").getNodeValue());
            
            // parse owner
            Player owner = null;
            Node n_owner = attributes.getNamedItem("owner");
            if(n_owner != null)
                owner = Game.parsePlayer(n_owner.getNodeValue());
            
            // finally set the cell's owner
            if(owner == null)
                board.setCell(p, BoardMatrix.Cell.EMPTY);
            else
                board.setCellOwner(p, owner);
        }
        
        // return the resulting parsed board
        return board;
    }
    
    protected abstract BoardMatrix createBoard();
    
    protected abstract Rules getRules();
}
