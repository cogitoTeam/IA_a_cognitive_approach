/** @author William J.D. **/

package service;

import game.BoardMatrix.Position;
import game.Game;
import game.Game.Player;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameServlet extends HttpServlet 
{
    /* ATTRIBUTES */
    private Game game = null;
    
    /* MAIN METHODS */
    
    protected void processRequest(HttpServletRequest request, 
                                    HttpServletResponse response)
    throws ServletException, IOException 
    {
        String xml_reply = 
            GameManager.getInstance().performRequest(new GameRequest(request));
        
        // Send the response
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try 
        {
            out.print("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + xml_reply);
        }
        finally 
        {
            out.close();
        }
    }
   
    /* OVERRODES */

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
        return "Game-master (referee) service.";
    }
}
