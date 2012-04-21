/** @author William J.D. **/

/*
Naughts and Crosses HTML 5 game example
Copyright (C) 2012 William James Dyce

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

Game.DRAW_LEGAL_MOVES = false;

Game.WHITE = 0;
Game.BLACK = 1;
Game.VICTORY_WHITE = 2;
Game.VICTORY_BLACK = 3;
Game.DRAW = 4;

Game.IMAGE_WHITE = load_image("white.png");
Game.IMAGE_BLACK = load_image("black.png");
Game.IMAGE_WHITE_ALPHA = load_image("white_alpha.png");
Game.IMAGE_BLACK_ALPHA = load_image("black_alpha.png");

Game.C_BACKGROUND = 'rgb(0,113,35)';
Game.C_BACKGROUNDINFO = 'rgb(0,0,0)';
Game.C_TEXT = 'rgb(200,200,200)';

function Game()
{
    /* ATTRIBUTES */
    const WATCH_ID = parseInt(DIV_GAME_ID.innerHTML.toString());
    
    // receiver 
    var obj = this;
    var typ = Game;
    
    // true attributes
    var board = null;
    var current_turn;
    var n_players = 2;
    var id = null;
    var is_observer = false;
    var is_local = [false, false];
    var waiting_for_player = true;

    /* SUBROUTINES */
    var xml_parse_state = function(s_state, s_colour)
    {
        // parse the colour
        var i_colour = (s_colour == "WHITE") ? Game.WHITE : Game.BLACK;
        var i_other = (i_colour+1)%2;
        
        // parse the state
        switch(s_state)
        {
            // first player to join plays first
            case "WAITING_FOR_PLAYER":
                // only the host sees the game start
                if(!is_observer && !is_local[i_colour] && !is_local[i_other])
                {
                    is_local[i_colour] = true;
                    is_local[i_other] = false;
                }
                return i_colour;
                
            case "PLAYER_JOINED":
                // if I'm not the host then I must be the client
                if(!is_observer && !is_local[i_colour] && !is_local[i_other])
                {
                    console.log("I am player " + i_other)
                    is_local[i_colour] = false;
                    is_local[i_other] = true;
                }
                waiting_for_player = false;
                return i_colour;
            
            // don't change current player
            case "MOVE_FAILURE":
                console.log("move failed!");
            case "NO_CHANGE":
            case "MOVE_SUCCESS":
                return i_colour;
                
            // victory
            case "VICTORY":
                return (i_colour == Game.WHITE) ? Game.VICTORY_WHITE
                                                : Game.VICTORY_BLACK;
            case "DRAW":
                return Game.DRAW;
                
            default:
                console.log("Unknown game state"+s_state);
                return -1;
        }
    }
    
    var redraw_ui = function()
    {
        // redraw the state information
        context_info.fillStyle = Game.C_BACKGROUNDINFO;
        context_info.fillRect(0,0,canvas_info.width, canvas_info.height);

        var image = null;
        var text = null;
        if(!waiting_for_player)
        {
	    var text_player;
            
            
	    if(is_local[current_turn])
            {
		text_player = "Your ";
		$("#wood_game").addClass("active");
	    }
            else if (is_local[(current_turn+1) % 2])
            {
		text_player = "Enemy ";
		$("#wood_game").removeClass("active");
	    }
            else
                text_player = "Observing " + 
                    (current_turn == Game.WHITE ? "white " : "black ");

            switch(current_turn)
            {
                case typ.WHITE:
                    text = text_player + "turn";
                    image = typ.IMAGE_WHITE;
                    break;

                case typ.BLACK:
                    text = text_player + "turn";
                    image = typ.IMAGE_BLACK;
                    break;

                case typ.VICTORY_WHITE:
                    text = (is_local[Game.WHITE]) ? "Victory!" : "Defeat!";
                    image = typ.IMAGE_WHITE;
                    break;

                case typ.VICTORY_BLACK:
                    text = (is_local[Game.BLACK]) ? "Victory!" : "Defeat!";
                    image = typ.IMAGE_BLACK;
                    break;

                case typ.DRAW:
                    text = "Draw...";
                    break;

                default:
                    break;
            }
        }
        else
            text = "Awaiting other player...";
        
        // draw icon of winning/current name
        if(image != null)
            context_info.drawImage(image, canvas_info.width-canvas_info.height, 
                                    0, canvas_info.height, canvas_info.height);

        // draw player name
        if(text != null)
        {
            // always the same font, alignment and so on
            context_info.fillStyle = typ.C_TEXT;
            context_info.font = "16pt Arial";
            context_info.textAlign = "right";
            context_info.textBaseline = "middle";

            context_info.fillText(text, canvas_info.width-canvas_info.height,
                                                    canvas_info.height/2);
        }
    }

    /* METHODS */
    
    obj.request_update = function()
    {
        ajax_request_refresh(id);
    }
    
    obj.update_from_xml = function(data)
    {
        // get the game identifier (for future queries)
        id = Number(data[0].getAttribute('id'));
        if(is_observer && game_id != id)
        {
            is_observer = false;
            if(data[0].getAttribute('state') == "WAITING_FOR_PLAYER")
                waiting_for_player = true;
        }
        DIV_GAME_ID.innerHTML = id.toString();
        
        /* Parse new game state */
        current_turn = xml_parse_state(data[0].getAttribute('state'),
                                data[0].childNodes[1].getAttribute('colour'));
                         
        // redraw indicators to reflect changed game state
        redraw_ui();
        
        /* Parse new board state */
        // create the board if it doesn't already exist
        if(board == null)
            board = new Board();
        // update board using the 'board' element of the XML document
        board.update_from_xml(data[0].childNodes[0]);
        
        /* Update the view to take changes into account */
        board.redraw();
        
        // also draw legal moves
        if(options_checkbox.checked)
        {
            turn_options = [];
            var nl_options = data[0].childNodes[1].childNodes;
            for(i = 0; i < nl_options.length; i++)
            {
                var r = nl_options[i].getAttribute('row');
                var c = nl_options[i].getAttribute('col');
                board.draw_option(r, c, current_turn);
            }
        }
    }
    
    obj.clickEvent = function(x, y)
    {
        // observers can't make moves
        if(is_observer)
        {
            console.log("you are observing this match...")
            return;
        }
        
        // restart the game if it's a draw or somebody has won
        if(current_turn >= n_players)
        {
            ajax_request_restart(id);
            return;	// consume the event
        }
        
        // check if two players are indeed in the game
        if(waiting_for_player)
        {
            console.log("waiting for opponent...");
            return;
        }

        // check if it's the local player's turn
        if (!is_local[current_turn])
        {
            console.log("wait your turn...")
            return;
        }

        // get the board position pointed to
        var row = board.y_to_row(y);
        var col = board.x_to_col(x);

        // the server will check if the move is legal
        ajax_request_move(id, row, col, current_turn);
    }

    /* INITIALISE */
    if(!isNaN(WATCH_ID))
    {
        is_observer = true;
        waiting_for_player = false;
        game_id = WATCH_ID;
        ajax_request_refresh(game_id);
    }
    else
        ajax_request_id();

    /* RETURN INSTANCE */
    return obj;
}
