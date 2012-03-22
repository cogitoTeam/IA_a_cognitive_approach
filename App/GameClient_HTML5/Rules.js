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

function Rules()
{
	/** ATTRIBUTES **/
	var obj = this;
	var typ = Rules;
	
	/** PUBLIC METHODS **/

	obj.getLegalMoves = function(board, turn)
	{
		result = new Array();

		/*for(row = 0; row < board.get_n_rows())
			for(col = 0; col < board.get_n_cols())
				if(obj.isLegalMove(row, col, board, turn))
					alert("legal move");*/
	}
	
	obj.isLegalMove = function(row, col, board, turn)
	{
		// override this !
		return false;		// no legal moves
	}
	
	obj.performMove = function(row, col, board, turn)
	{
		// override this !
		return turn;		// same player to move again
	}
	
	/** RETURN INSTANCE **/
	return obj;
}
