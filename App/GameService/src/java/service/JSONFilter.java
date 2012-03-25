/*****************
 * @author Patrick Grimard
 * @date 28-July-2010
 * @url http://jpgmr.wordpress.com/
 *****************/

package service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JSONFilter implements Filter 
{
    /** ATTRIBUTES **/
    
    private FilterConfig filterConfig = null;
    
    
    /** MAIN METHODS **/
    
    // creation
    public JSONFilter() 
    {
    }    

    // filter requests
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException 
    {
        // local variables
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Map<String, String[]> parms = httpRequest.getParameterMap();

        // check if the client is requesting a JSON callback
        if(parms.containsKey("callback")) 
        {
            // create a response wrapper object
            OutputStream out = httpResponse.getOutputStream();
            ResponseWrapper wrapper = new ResponseWrapper(httpResponse);

            // pass to the next filter
            chain.doFilter(request, wrapper);

            // wrap the response as JSONP
            out.write(new String(parms.get("callback")[0] + "(").getBytes());
            out.write(wrapper.getData());
            out.write(new String(");").getBytes());

            // specific that the context type is now javascript, not xml/html
            wrapper.setContentType("text/javascript;charset=UTF-8");

            out.close();
        } 
        else 
            // pass to the next filter
            chain.doFilter(request, response);
    }

    /** IMPLEMENTATIONS **/
    
    public FilterConfig getFilterConfig() 
    {
        return filterConfig;
    }

    public void setFilterConfig(FilterConfig _filterConfig) 
    {
        filterConfig = _filterConfig;
    }

    @Override
    public void destroy() 
    {        
    }

    @Override
    public void init(FilterConfig _filterConfig) 
    {        
        filterConfig = _filterConfig;
    }

}
