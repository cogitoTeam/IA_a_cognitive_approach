package ac.memory.persistance;

import org.neo4j.graphdb.RelationshipType;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 30 mars 2012
 * @version 0.1
 */
public enum RelTypes implements RelationshipType
{

  /**
   * Relation between main node and the master node attribute
   */
  REF_ATTRIBUTES,
  /**
   * Relation between the master node attribute and all the attribute nodes
   */
  ATTR,
  /**
   * Relation between main node and the master node objet
   */
  REF_OBJECTS,
  /**
   * Relation between master node object and all the object nodes
   */
  OBJ,
  /**
   * Relation between an attribute node and an object node
   */
  ATTR_TO_OBJ,
  /**
   * Relation between and object node and an attribute node
   */
  OBJ_TO_ATTR;
}
