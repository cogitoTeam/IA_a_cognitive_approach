package ac.memory.persistance;

import org.neo4j.graphdb.RelationshipType;

/**
 * List of relationship types in the graph database
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 */
public enum RelTypes implements RelationshipType
{

  /**
   * Relationship between main node and the master node attribute (should be
   * unique)
   */
  REF_ATTRIBUTES,
  /**
   * Relationship between the master node attribute and all the attribute nodes
   */
  ATTR,
  /**
   * Relationship between main node and the master node objet (should be unique)
   */
  REF_OBJECTS,
  /**
   * Relationship between master node object and all the object nodes
   */
  OBJ,
  /**
   * Relationship between two nodes
   */
  RELATED,
  /**
   * Relationship between main node and all the master node game (should be
   * unique)
   */
  REF_GAME,
  /**
   * Relationship between master node game and all the game nodes
   */
  GAME,
  /**
   * Relationship between the master node game and the last game node (should be
   * unique)
   */
  LAST_GAME,
  /**
   * Relationship between a game node and his previous game node
   */
  PREV_GAME,
  /**
   * Relationship between a game node and its each move
   */
  HAS_MOVE,
  /**
   * Relationship between main node an the master node move (should be unique)
   */
  REF_MOVE,
  /**
   * Relationship between the master node move and all the move node
   */
  MOVE,
  /**
   * Relationship between a game node and the last move node for this game
   * (should
   * be unique for a game)
   */
  LAST_MOVE,
  /**
   * Relationship between a move node and his previous move node
   */
  PREV_MOVE;
}
