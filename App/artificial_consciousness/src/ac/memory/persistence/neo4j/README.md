# Persistence module via Neo4j

To ensure data persistence, this module incorporates an embedded version of Neo4j. Neo4j is an high-performance NOSQL graph database (see here : http://neo4j.org).

## This package is composed of this entities :

* The neo4j util : witch provides the unique database object, and the repositories,
* The repositories : for ObjectNodes and AttributeNodes. It provide access to the nodes, allows to get node by id, etc.,
* The nodes : ObjectNode and Attribute node. These classes wrap generic node classes.

## Graph schema is as following for Lattice Context Storing:

* **MAIN NODE**
  * ---- REF_ATTRIBUTE ----> **MAIN ATTRIBUTE NODE**

        ---- ATTR ----> **ATTRIBUTE 1**

        ---- ATTR ----> **ATTRIBUTE 2**

        ---- ATTR ----> **ATTRIBUTE 3**

        ---- ATTR ----> **ATTRIBUTE 4**

        ---- ATTR ----> ***ATTRIBUTE 5**

        ...

  * ---- REF_OBJECT ----> **MAIN OBJECT NODE**

        ---- OBJ ----> **OBJECT 1**

        ---- OBJ ----> **OBJECT 2**

        ---- OBJ ----> **OBJECT 3**

        ---- OBJ ----> **OBJECT 4**

        ---- OBJ ----> **OBJECT 5**

        ...

Relationship between an object and an attribute :

**OBJECT** ---- RELATED ----> **ATTRIBUTE**

*Note: these relationships can be crossed in both directions.*

For the AC application, attributes store RelevantPartialBoardState and objects store CompleteBoardState.

## Graph schema is as following for Episodic Memory Storing:

* **MAIN NODE**
  * ---- REF_GAME ----> **MAIN GAME NODE**

        -- LAST_GAME --> **GAME 1 (the last)**
        
                   |
                PREV_GAME
                   |
                   ▼ 

        ---- GAME ----> **GAME 2**
        
                   |
                PREV_GAME
                   |
                   ▼ 
                                      
        ---- GAME ----> **GAME 3**
        
                   |
                PREV_GAME
                   |
                   ▼ 
                                      
        ---- GAME ----> **GAME 4**
        
                   |
                PREV_GAME
                   |
                   ▼ 
                                      
        ---- GAME ----> ***GAME 5**

        ...
        
Each game has a "LAST_MOVE" relationship to a move node (the last move of the game).
        
* **MAIN NODE**
  * ---- REF_MOVE ----> **MAIN MOVE NODE**

        -- LAST_MOVE --> **MOVE 1 (the last)**
        
                   |
                PREV_MOVE
                   |
                   ▼ 

        ---- MOVE ----> **MOVE 2**
        
                   |
                PREV_MOVE
                   |
                   ▼ 
                                      
        ---- MOVE ----> **MOVE 3**
        
                   |
                PREV_MOVE
                   |
                   ▼ 
                                      
        ---- MOVE ----> **MOVE 4**
        
                   |
                PREV_MOVE
                   |
                   ▼ 
                                      
        ---- MOVE ----> ***MOVE 5**

        ...

Each Move has a "BOARD_STATE" realtionship to an Object node (object of the lattice context).
