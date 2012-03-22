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

Board.CELL_EMPTY = 0;
Board.CELL_BLACK = 1;
Board.CELL_WHITE = 2;
Board.OUT_OF_BOUNDS = 3;

function Board(_n_rows, _n_cols)
{
	/** ATTRIBUTES **/
	var obj = this;
	var typ = Board;
	var n_rows = _n_rows;
	var n_cols = _n_cols;
	var cell_w = canvas.width/n_cols;
	var cell_h = canvas.height/n_rows;
	var cells = new Array();
	for(row = 0; row < n_rows; row++)
	{
		// create cells
		cells[row] = new Array();
		for(col = 0; col < n_cols; col++)
			cells[row][col] = typ.CELL_EMPTY;
	}
	
	/** PRIVATE METHODS **/
	
	var draw_cell = function(row, col, cell_value)
	{
		var image = null;
		
		// choose image
		switch(cell_value)
		{
			case typ.CELL_WHITE:
				image = Game.IMAGE_WHITE;
				break;
			case typ.CELL_BLACK:
				image = Game.IMAGE_BLACK;
				break;
			default:
			case typ.CELL_EMPTY:
				break;
		}
		
		// draw image
		if(image != null)
			context.drawImage(image, col*cell_w, row*cell_h, cell_w, cell_h);
		else
		{
			context.fillStyle = Game.C_BACKGROUND;
			context.fillRect(col*cell_w, row*cell_h, cell_w, cell_h);
		}
			
	}
	
	/** PUBLIC METHODS **/
	
	// update
	obj.redraw = function()
	{
		for(row = 0; row < n_rows; row++)
			for(col = 0; col < n_cols; col++)
				draw_cell(row, col, cells[row][col]);
	}
	
	// modification
	obj.setCell = function(row, col, new_value)
	{
		if(row < n_rows && col < n_cols)
		{
			var previous_value = cells[row][col];
			cells[row][col] = new_value;
			return previous_value;
		}
		else
			return typ.OUT_OF_BOUNDS;
	}
	
	// access
	obj.get_n_rows = function()
	{
		return n_rows;
	}

	obj.get_n_cols = function()
	{
		return n_cols;
	}

	obj.getCell = function(row, col)
	{
		if(row >= n_rows || col >= n_cols)
			return typ.OUT_OF_BOUNDS;
		else
			return cells[row][col];
	}
	
	// utility
	obj.y_to_row = function(y)
	{
		return ~~(y/cell_h);
	}
	
	obj.x_to_col = function(x)
	{
		return ~~(x/cell_w);
	}
	
	/** RETURN INSTANCE **/
	return obj;
}
