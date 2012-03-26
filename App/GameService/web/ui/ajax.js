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

function receive_board(data)
{
  game.update_from_xml(data.getElementsByTagName('game'));
}

var board_request =
{
  url: "../ws",
  dataType: "xml",
  success: receive_board,
  //complete: poll_board,
  timeout: 30000
}

function poll_board()
{
    $.ajax(board_request);
}

