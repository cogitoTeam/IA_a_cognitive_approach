/* @author william */

package service;

import game.Board;
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
    /** ATTRIBUTES **/
    Game game;
    
    /** MAIN METHODS **/
    
    protected void processRequest(HttpServletRequest request, 
                                    HttpServletResponse response)
    throws ServletException, IOException 
    {
        // Get all parameters
        Map<String, String[]> parameters = request.getParameterMap();
        
        // Is the client requesting a new game ?
        if(parameters.containsKey("newgame"))
            processNewGameRequest();
        
        // Is the client requesting the current game be reset ?
        if(parameters.containsKey("restart"))
            processRestartGameRequest();
        
        // Is the client requesting a move ?
        if(parameters.containsKey("row") && parameters.containsKey("col")
        && parameters.containsKey("player"))
                processMoveRequest(parameters.get("row")[0],
                                    parameters.get("col")[0],
                                    parameters.get("player")[0]);
        
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
    
    protected void processNewGameRequest()
    {
        game = GameManager.getInstance().newGame();
    }
    
    protected void processRestartGameRequest()
    {
        game.restart();
    }
    
    protected void processMoveRequest(String s_row, String s_col, 
                                        String s_player)
    {
        // check that there is indeed something to parse
        if(s_row != null && s_col != null && s_player != null)
        {
            // parse position
            Board.Position p = new Board.Position(Integer.parseInt(s_row),
                                                    Integer.parseInt(s_col));
            // parse player
            Game.Player player = (Integer.parseInt(s_player) == 0) ?
                                    Game.Player.WHITE :
                                    Game.Player.BLACK;
            
            // try to perform the move
            game.tryMove(p, player);
        }
    }
    
    
    
    /** OVERRODES **/

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
