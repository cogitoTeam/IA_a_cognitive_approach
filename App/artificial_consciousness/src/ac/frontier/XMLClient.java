/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


class XMLClient 
{
    /* ATTRIBUTES */
    
    private DocumentBuilder xml_builder;
    protected final String base_url;
    
    /* METHODS */
    
    // creation
    public XMLClient(String _base_url) throws ParserConfigurationException
    {
        // Create the XML document builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        xml_builder = factory.newDocumentBuilder();

        // Create the URL object
        base_url = _base_url;
    }
    
    // query
    
    public Document getDocument(String s_url)
    {
        try 
        {
            // get the document from url and parse
            return xml_builder.parse(new URL(s_url).openStream());
        } 
        catch (SAXException ex) 
        {
            Logger.getLogger(XMLClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(XMLClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
