/** @author William J.D. **/

package service;

import game.BoardMatrix.Position;
import game.Game;
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
    Game game = null;
    
    /* MAIN METHODS */
    
    protected void processRequest(HttpServletRequest request, 
                                    HttpServletResponse response)
    throws ServletException, IOException 
    {
        // Get all parameters
        Map<String, String[]> parameters = request.getParameterMap();
        
        // Is the client requesting a specific game or a new one ?
        GameManager gm = GameManager.getInstance();
        game = null;
        if(parameters.containsKey("game_id"))
            game = gm.getGame(Integer.parseInt(parameters.get("game_id")[0]));
        if(game == null)
            game = gm.findGame();
        
        // If a new game has been created ignore other requests
        else
        {
            // Is the client requesting the current game be reset ?
            if(parameters.containsKey("restart"))
                game.restart();
            else
            {
                // Is the client requesting a move ?
                if(parameters.containsKey("row") && parameters.containsKey("col")
                && parameters.containsKey("player"))
                        processMoveRequest(parameters.get("row")[0],
                                            parameters.get("col")[0],
                                            parameters.get("player")[0]);
                else
                    game.failMove();
            }
        }
        
        // Send the response
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try 
        {
            out.print("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + game);
        }
        finally 
        {
            out.close();
        }
    }
    
    protected void processMoveRequest(String s_row, String s_col, 
                                        String s_player)
    {
        // parse position
        Position p = new Position(Integer.parseInt(s_row),
                                                Integer.parseInt(s_col));
        // try to perform the move
        game.tryMove(p, Game.parsePlayer(s_player));
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
