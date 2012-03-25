/*****************
 * @author Patrick Grimard
 * @date 28-July-2010
 * @url http://jpgmr.wordpress.com/
 *****************/

package service;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ResponseWrapper extends HttpServletResponseWrapper 
{
    /** ATTRIBUTES **/
    
    private ByteArrayOutputStream output;
    private int contentLength;
    private String contentType;

    
    /** METHODS **/
    
    public ResponseWrapper(HttpServletResponse response) 
    {
        super(response);
        output = new ByteArrayOutputStream();
    }

    public byte[] getData() 
    {
        return output.toByteArray();
    }

    @Override
    public ServletOutputStream getOutputStream()
    {
        return new FilterOStream(output);
    }

    @Override
    public PrintWriter getWriter() 
    {
        return new PrintWriter(getOutputStream(), true);
    }

    @Override
    public void setContentLength(int length)
    {
        this.contentLength = length;
        super.setContentLength(length);
    }

    public int getContentLength() 
    {
        return contentLength;
    }

    @Override
    public void setContentType(String type) 
    {
        this.contentType = type;
        super.setContentType(type);
    }

    @Override
    public String getContentType() 
    {
        return contentType;
    }
}

