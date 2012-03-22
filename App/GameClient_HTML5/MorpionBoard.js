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

MorpionBoard.N_ROWS = 4;
MorpionBoard.N_COLS = 4;

function MorpionBoard()
{
	/** ATTRIBUTES **/
	var typ = MorpionBoard;
	var obj = new Board(typ.N_ROWS, typ.N_COLS);
	
	
	/** METHODS **/
	
	/** RETURN INSTANCE **/
	return obj;
}
