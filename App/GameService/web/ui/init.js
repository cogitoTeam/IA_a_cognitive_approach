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

/** GLOBAL VARIABLES **/

// html5 objects
var canvas = document.getElementById('game_canvas');
var canvas_info = document.getElementById('info_canvas');
var context = canvas.getContext('2d');
var context_info  = canvas_info.getContext('2d');

// the main application holder
var game;

/** RESOURCE MANAGEMENT **/
var loading = 0;
var resourceLoaded = function()
{
    // one less to wait for
    if(loading > 0)
            loading--;
}

var DATA_LOCATION = "../data/";

// simple image-loading API
function load_image(file_name)
{
  // grab the file
  var img = new Image();
  img.src = DATA_LOCATION + file_name;
  
  // make sure we wait till its loaded
  img.onload = resourceLoaded;
  loading++;
  
  // return the handler
  return img;
}