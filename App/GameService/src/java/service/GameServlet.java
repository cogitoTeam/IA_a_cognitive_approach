/* @author william */

package service;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameServlet extends HttpServlet 
{
    protected void processRequest(HttpServletRequest request, 
                                    HttpServletResponse response)
    throws ServletException, IOException 
    {
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try 
        {
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
            out.println("<game id=\"123\" turn=\"456\">");
            out.println("<board>");
            out.println("</board>");
            out.println("</game>");
        }
        finally 
        {            
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, 
                        HttpServletResponse response)
    throws ServletException, IOException 
    {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, 
                        HttpServletResponse response)
    throws ServletException, IOException 
    {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() 
    {
        return "Game-master (referee) service";
    }
}
