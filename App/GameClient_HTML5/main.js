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
var game;

function main() 
{
	// poll till loaded
	if(!loading)
		game = new Game(new MorpionRules(), new MorpionBoard(), new MorpionAI());
	else
	{
		context.font = "20pt Arial";
		context.textAlign = "center";
		context.textBaseline = "middle";
		context.fillText("Loading " + loading + " resources...",
						 canvas.width/2, canvas.height/2);
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