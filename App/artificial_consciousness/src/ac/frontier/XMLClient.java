/*****************
 * @author william
 * @date 28-Mar-2012
 *****************/


package ac.frontier;

import java.io.IOException;
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
    protected final String s_server_url;
    
    /* METHODS */
    
    // creation
    public XMLClient(String _s_server_url)
    {
        // Create the XML document builder
        try 
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            xml_builder = factory.newDocumentBuilder();
        } 
        catch (ParserConfigurationException ex) 
        {
            Logger.getLogger(XMLClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Create the URL object
        s_server_url = _s_server_url;
    }
    
    // query
    
    public Document getXML()
    {
        return getXML("");
    }
    
    
    public Document getXML(String s_query)
    {
        try 
        {
            // get the document from url and parse
            return xml_builder.parse(new URL(s_server_url+"?"+s_query).openStream());
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
