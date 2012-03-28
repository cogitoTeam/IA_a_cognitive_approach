/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
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

        // fixme
        return null;
    }
}
