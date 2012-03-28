/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import game.BoardMatrix.Position;
import game.Game;
import game.Game.Player;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


class Sensor 
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
    public List<Option> getOptions() throws IOException, SAXException
    {
        // get an XML document from the server
        Document doc = xml_builder.parse(new URL(base_url).openStream());

        // get the cell nodes from the document
        NodeList cells = doc.getDocumentElement().getElementsByTagName("board")
                            .item(0).getChildNodes();
        
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
            Player owner;
            Node n_owner = attributes.getNamedItem("owner");
            if(n_owner != null)
                owner = Game.parsePlayer(n_owner.getNodeValue());
        }
        
        // fixme
        return null;
    }
}
