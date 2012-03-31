# Persistence module via Neo4j

To ensure data persistence, this module incorporates an embedded version of Neo4j. Neo4j is an high-performance NOSQL graph database (see here : http://neo4j.org).

## This package is composed of this entities :

* The neo4j util : witch provides the unique database object, and the repositories,
* The repositories : for ObjectNodes and AttributeNodes. It provide access to the nodes, allows to get node by id, etc.,
* The nodes : ObjectNode and Attribute node. These classes wrap generic node classes.

## Graph schema is as following :

* **MAIN NODE**
  * ---- REF_ATTRIBUTE ----> **MAIN ATTRIBUTE NODE**
    * ---- ATTR ----> **ATTRIBUTE 1**
    * ---- ATTR ----> **ATTRIBUTE 2**
    * ---- ATTR ----> **ATTRIBUTE 3**
    * ---- ATTR ----> **ATTRIBUTE 4**
    * ---- ATTR ----> ***ATTRIBUTE 5**
    * ...
  * ---- REF_OBJECT ----> **MAIN OBJECT NODE**
    * ---- OBJ ----> **OBJECT 1**
    * ---- OBJ ----> **OBJECT 2**
    * ---- OBJ ----> **OBJECT 3**
    * ---- OBJ ----> **OBJECT 4**
    * ---- OBJ ----> **OBJECT 5**
    * ...

Relationship between an object and an attribute :

**OBJECT** ---- RELATED ----> **ATTRIBUTE**

*Note: these relationships can be crossed in both directions.*

For the AC application, attributes store RelevantPartialBoardState and objects store CompleteBoardState/
