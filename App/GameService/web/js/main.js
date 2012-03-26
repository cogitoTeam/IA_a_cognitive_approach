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

function main() 
{
    // keep checking till loaded
    if(!loading)
    {
        // clear canvas
        context.fillStyle = Game.C_BACKGROUND;
        context.fillRect(0,0,canvas.width, canvas.height);
        
        // create the game object
        game = new Game();
    }
    else
    {
        // clear canvases
        context.fillStyle = Game.C_BACKGROUND;
        context.fillRect(0,0,canvas.width, canvas.height);
        context_info.fillStyle = Game.C_BACKGROUND;
        context_info.fillRect(0,0,canvas_info.width, canvas_info.height);
        
        // draw "loading" text
        context.fillStyle = Game.C_TEXT;
        context.font = "20pt Arial";
        context.textAlign = "center";
        context.textBaseline = "middle";
        context.fillText("Loading " + loading + " resources...",
                                        canvas.width/2, canvas.height/2);
                                        
        // check again in 0.5 seconds
        setTimeout("main()", 500);
    }
}

canvas.onmousedown = function(event)
{
  game.clickEvent(event.layerX - canvas.offsetLeft,
                event.layerY - canvas.offsetTop);
}

// launch the program
main();