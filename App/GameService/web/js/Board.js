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

function Board()
{
    /* ATTRIBUTES */
    
    // receiver
    var obj = this;
    var typ = Board;
    
    // attributes -- warning: should be initialised!
    var n_rows = 0;
    var n_cols = 0;
    var cell_w = 0; 
    var cell_h = 0;
    var cells = new Array();

    /* PRIVATE METHODS */
    
        
    var xml_update_cell = function(element)
    {
        // where ?
        var row = element.getAttribute('row');
        var col = element.getAttribute('col');
        
        // what ?
        var owner = element.getAttribute('owner');
        var value;
        if(owner == "WHITE")
            value = Board.CELL_WHITE;
        else if (owner == "BLACK")
            value = Board.CELL_BLACK;
        else // owner == "NOBODY"
            value = Board.CELL_EMPTY;
        
        // perform the update
        obj.setCell(row, col, value);
        
    }
    
    var resize = function(_n_rows, _n_cols)
    {
        // if size is the same don't bother reallocating memory
        if(n_rows == _n_rows && n_cols == _n_cols)
            return;
        
        // initialise board size
        n_rows = _n_rows;
        n_cols = _n_cols;
        cell_w = canvas.width/n_cols;
        cell_h = canvas.height/n_rows;
        
        // create new board matrix
        for(row = 0; row < n_rows; row++)
        {   
            // create cells
            cells[row] = new Array();
            for(col = 0; col < n_cols; col++)
                cells[row][col] = typ.CELL_EMPTY;
        }
    }

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

    /* PUBLIC METHODS */

    // update
    obj.redraw = function()
    {
        // redraw each individual cell
        for(row = 0; row < n_rows; row++)
            for(col = 0; col < n_cols; col++)
                draw_cell(row, col, cells[row][col]);
    }

    // modification
    
    obj.clear = function()
    {
        for(row = 0; row < n_rows; row++)
            for(col = 0; col < n_cols; col++)
                obj.setCell(row, col, Board.CELL_EMPTY);
    }

    obj.update_from_xml = function(element)
    {
        // read the size of the board from the element
        resize(element.getAttribute('n_rows'), element.getAttribute('n_cols'));
        
        // read the board positions
        var n_pieces = element.getAttribute('n_pieces')
        if(n_pieces > 0)
        {
            var pieces = element.childNodes;
            for(i = 0; i < pieces.length; i++)
                xml_update_cell(pieces.item(i));
        }
    }

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
        return (cell_h == 0) ? 0 : ~~(y/cell_h);
    }

    obj.x_to_col = function(x)
    {
        return (cell_w == 0) ? 0 : ~~(x/cell_w);
    }

    /* RETURN INSTANCE */
    return obj;
}
