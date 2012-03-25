/*****************
 * @author Patrick Grimard
 * @date 28-July-2010
 * @url http://jpgmr.wordpress.com/
 *****************/


package service;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;

public class FilterOStream extends ServletOutputStream 
{
    /** ATTRIBUTES **/
    
    private DataOutputStream stream;

    
    
    /** METHODS **/
    
    // creation
    public FilterOStream(OutputStream output) 
    {
        stream = new DataOutputStream(output);
    }

    // overrides
    @Override
    public void write(int b) throws IOException 
    {
        stream.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException 
    {
        stream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException 
    {
        stream.write(b, off, len);
    }

}

