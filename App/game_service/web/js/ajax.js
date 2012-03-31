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


/** GENERIC MESSAGE TEMPLATE */

const WEBSERVICE = "./ws";

var msg =
{
  type: 'GET',
  url: WEBSERVICE,
  dataType: "xml",
  success: receive_board,
  complete: request_complete,
  timeout: 30000
}


/** TREAT REPLY CALLBACKS */

function receive_board(data)
{
  game.update_from_xml(data.getElementsByTagName('game'));
}

function request_complete()
{
    // send a new request after one second
    setTimeout("get_new_request()", 1000);
}

function get_new_request()
{
    // send a new request after a certain delay
    game.request_update();
}


/** REQUEST NEW GAME STATE */

function ajax_request_move(id, row, col, player)
{
    msg.url = WEBSERVICE + "?game_id=" + id + "&row=" + row + "&col=" + col 
                    + "&player=" + ((player == Game.WHITE) ? "WHITE" : "BLACK");
    $.ajax(msg);
}

function ajax_request_refresh(id)
{
    msg.url = WEBSERVICE + "?game_id=" + id;
    $.ajax(msg);
}

function ajax_request_id()
{
    msg.url = WEBSERVICE;
    $.ajax(msg);
}

function ajax_request_restart(id)
{
    msg.url = WEBSERVICE + "?game_id=" + id + "&restart";
    $.ajax(msg);
}
