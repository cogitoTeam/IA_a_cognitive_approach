<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en"><!--
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
-->

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>index</title>
    <meta name="author" content="William">
    <!-- Date: 2012-03-13 -->
    <style>
        body { padding:10px; text-align:center; background-color:black; color:white; font-family: sans}
	h1 { position:absolute; left:20px; top:0 }
        #info_canvas { border:1px solid #C0C0C0; }
	#game_canvas { margin:40px; border: 2px solid #7E3000; border-width:2px 0 0 2px; }
 	#wood_game { background-image:url('data/wood.jpg'); width: 480px; height: 480px; display:inline-block; border:3px solid black;border-width:0 3px 3px 0 !important;}
	.active { box-shadow: 0px 0px 50px white; border:3px solid #7E3000 !important;}
	#pub { position:fixed; top:0px; right:0px; padding:10px;}
    </style>
</head>
	
<body>
    <h1>AC-cogito</h1>
    <div id="pub" >
	<a href="https://github.com/marminthibaut/artificial_consciousness"><img src="data/logo.png"></a>
    </div>
    <div id="wood_game" class="">
       <canvas id="game_canvas" width="400" height="400">
           Can't load HTML 5 canvas: is your browser up to date?
       </canvas>
    </div>
    </br>
    <canvas id="info_canvas" width="400" height="50">
        Can't load HTML 5 canvas: is your browser up to date?
    </canvas>
    
<%@page import="java.util.Map"%>
    <p> Game identifier: 
    <span id="game_id"><% 
        Map<String, String[]> parameters = request.getParameterMap();
        if(parameters.containsKey("game_id") && parameters.get("game_id")[0] != "")
            out.print(Integer.parseInt(parameters.get("game_id")[0]));
        %></span>
    </p>
    <p> Participation type:
    <span id="is_observer"><%
        if(parameters.containsKey("join_type") && parameters.get("join_type")[0] != "")
            out.print(parameters.get("join_type")[0]);
        %></span>
    </p>
    

    <p>
        Show options: <input type="checkbox" checked="checked" id="options_checkbox">
    </p>
    
    <script type="text/javascript">var DATA_LOCATION = "data/";</script>
    <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="js/init.js"></script>
    <script type="text/javascript" src="js/ajax.js"></script>
    <script type="text/javascript" src="js/Board.js"></script>
    <script type="text/javascript" src="js/Game.js"></script>
    <script type="text/javascript" src="js/main.js"></script>
   
    
</body>

</html>
