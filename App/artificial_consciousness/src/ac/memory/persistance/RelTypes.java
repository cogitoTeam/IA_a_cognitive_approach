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
   * Relation between main node and the master node attribute (should be unique)
   */
  REF_ATTRIBUTES,
  /**
   * Relation between the master node attribute and all the attribute nodes
   */
  ATTR,
  /**
   * Relation between main node and the master node objet (should be unique)
   */
  REF_OBJECTS,
  /**
   * Relation between master node object and all the object nodes
   */
  OBJ,
  /**
   * Relation between two nodes
   */
  RELATED,
  /**
   * Relation between main node and all the master node game (should be unique)
   */
  REF_GAME,
  /**
   * Relation between master node game and all the game nodes
   */
  GAME,
  /**
   * Relation between the master node game and the last game node (should be
   * unique)
   */
  LAST_GAME,
  /**
   * Relation between a game node and his previous game node
   */
  PREV_GAME,
  /**
   * Relation between main node an the master node move (should be unique)
   */
  REF_MOVE,
  /**
   * Relation between the master node move and all the move node
   */
  MOVE,
  /**
   * Relation between a game node and the last move node for this game (should
   * be unique for a game)
   */
  LAST_MOVE,
  /**
   * Relation between a move node and his previous move node
   */
  PREV_MOVE;
}
