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

function MorpionAI()
{
	/** ATTRIBUTES **/
	var typ = MorpionAI;
	var obj = this;

	/** SUBROUTINES **/
	var get_max = function(board, turn, me)
	{
		beta = Infinity;
	}
	
	var get_min = function(board, turn, me)
	{
		alpha = -Infinity;
	}

	var minimax = function(board, turn, me)
	{
		switch(turn)
		{
			case Game.WHITE:
				return (turn == me) ? get_max(board, turn, me) 
									: get_min(board, turn, me);

			case Game.BLACK:
				return (turn == me) ? get_max(board, turn, me) 
									: get_min(board, turn, me);
			
			case Game.VICTORY_WHITE:
				return (Game.WHITE == me) ? 1 : -1;

			case Game.VICTORY_BLACK:
				return (Game.BLACK == me) ? 1 : -1;

			case Game.DRAW:
				return 0;
		}
	}
	
	/** METHODS **/
	obj.bestMove = function(board, turn)
	{
		minimax(board, turn, turn);
		return [0, 0];
	}
	
	/** RETURN INSTANCE **/
	return obj;
}