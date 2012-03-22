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

function MorpionRules()
{
	/** ATTRIBUTES **/
	var obj = new Rules();
	var typ = MorpionRules;
	
	/** SUBROUTINES **/
	var hasWon = function(turn, board)
	{
		// count the number which are my colour
		var mine = (turn == Game.WHITE) 
							? Board.CELL_WHITE
							: Board.CELL_BLACK;
		var count = 0;
		
		// check rows
		for(row = 0; row < MorpionBoard.N_ROWS; row++)
		{
			count = 0;
			for(col = 0; col < MorpionBoard.N_COLS; col++)
			{
				if(board.getCell(row, col) != mine)
					break;
				else
					count++;
			}
			if(count == MorpionBoard.N_COLS)
				return true;
		}
		
		// check columns
		for(col = 0; col < MorpionBoard.N_COLS; col++)
		{
			count = 0;
			for(row = 0; row < MorpionBoard.N_ROWS; row++)
			{
				if(board.getCell(row, col) != mine)
					break;
				else
					count++;
			}
			if(count == MorpionBoard.N_ROWS)
				return true;
		}
		
		// check diagonal 1 (downward left to right)
		count = 0;
		for(i = 0; i < MorpionBoard.N_ROWS; i++)
		{
			if(board.getCell(i, i) != mine)
				break;
			else
				count++;
		}
		if(count == MorpionBoard.N_ROWS)
			return true;
			
		// check diagonal 2 (upwards left to right)
		count = 0;
		for(i = 0; i < MorpionBoard.N_COLS; i++)
		{
			if(board.getCell(i, MorpionBoard.N_ROWS-1-i) != mine)
				break;
			else
				count++;
		}
		if(count == MorpionBoard.N_COLS)
			return true;
	}
	
	var isTie = function(board)
	{
		// it's a draw if none of the nine cells are empty
		for(row = 0; row < MorpionBoard.N_ROWS; row++)
			for(col = 0; col < MorpionBoard.N_COLS; col++)
				if(board.getCell(row, col) == Board.CELL_EMPTY)
					return false;
		// no cells are empty
		return true;
	}
	
	/** METHODS **/
	
	obj.firstTurn = function()
	{
		return Game.WHITE;
	}
	
	obj.reset = function(board)
	{
		for(row = 0; row < MorpionBoard.N_ROWS; row++)
			for(col = 0; col < MorpionBoard.N_COLS; col++)
				board.setCell(row, col, Board.CELL_EMPTY);
	}

	obj.isLegalMove = function(row, col, board, turn)
	{
		return (board.getCell(row, col) == Board.CELL_EMPTY);
	}

	obj.performMove = function(row, col, board, turn)
	{
		// set the cell to the new colour
		board.setCell(row, col, (turn == Game.WHITE) 
								? Board.CELL_WHITE 
							  	: Board.CELL_BLACK);
											  
		// report if success has occured
		if(hasWon(turn, board))
			return (turn == Game.WHITE) 
					? Game.VICTORY_WHITE 
				  	: Game.VICTORY_BLACK;
		// report if draw has occured
		else if(isTie(board))
			return Game.DRAW;
		// report next player to move (cycle between the two)
		else
			return (turn == Game.BLACK) 
					? Game.WHITE 
					: Game.BLACK;
	}
	
	/** RETURN INSTANCE **/
	return obj;
}
