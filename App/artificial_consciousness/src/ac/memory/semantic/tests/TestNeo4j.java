package ac.memory.semantic.tests;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;

import ac.memory.persistence.AttributeNode;
import ac.memory.persistence.AttributeNodeRepository;
import ac.memory.persistence.Neo4jService;
import ac.memory.persistence.ObjectNode;
import ac.memory.persistence.ObjectNodeRepository;
import ac.shared.CompleteBoardState;
import ac.shared.RelevantPartialBoardState;

@SuppressWarnings("javadoc")
public class TestNeo4j
{
  private static final Logger logger = Logger.getLogger(TestNeo4j.class);
  private static GraphDatabaseService graphDb;

  // END SNIPPET: createRelTypes

  @SuppressWarnings("unused")
  public static void main(final String[] args) throws Exception
  {

    graphDb = Neo4jService.getInstance();

    AttributeNodeRepository attr_repo = new AttributeNodeRepository(graphDb,
        Neo4jService.getAttrIndex());

    ObjectNodeRepository obj_repo = new ObjectNodeRepository(graphDb,
        Neo4jService.getObjIndex());

    // createNewDatabase(attr_repo, obj_repo, 1000, 100, 10000);
    // printNodes(attr_repo, obj_repo);

    /* ObjectNode ob_node2938 = obj_repo.getNodeById(2938);
     * 
     * for (Iterator<AttributeNode> iterator = ob_node2938.getRelatedObjects()
     * .iterator(); iterator.hasNext();)
     * {
     * AttributeNode related_attribute = (AttributeNode) iterator.next();
     * System.out.println(related_attribute);
     * 
     * } */

    System.out.println("OK !");
  }

  /**
   * @param attr_repo
   * @param obj_repo
   * @throws Exception
   */
  @SuppressWarnings("unused")
  private static void createNewDatabase(AttributeNodeRepository attr_repo,
      ObjectNodeRepository obj_repo, int nb_obj, int nb_attr, int nb_rel)
      throws Exception
  {

    if (nb_obj + nb_attr + nb_rel > 1000 && logger.isDebugEnabled())
      {
        throw new Exception("You should not use a debug level in log4j");
      }

    int nb_obj_error = 0;
    int nb_attr_error = 0;

    System.out.println("Generating attributes");
    for (int i = 0; i < nb_attr; ++i)
      {
        try
          {
            attr_repo.createNode(new RelevantPartialBoardState(i));
          }
        catch (Exception e)
          {
            System.err.println("Error during creation of node " + i + " : "
                + e.getMessage());
            nb_attr_error -= 1;
          }
      }
    System.out.println((nb_attr - nb_attr_error) + " attributes insered");

    System.out.println("Generating objects and relationships");
    for (int i = 0; i < nb_obj; ++i)
      {
        ObjectNode obj = null;
        try
          {
            obj = obj_repo.createNode(new CompleteBoardState(i));
          }
        catch (Exception e)
          {
            System.err.println("Error during creation of node " + i + " : "
                + e.getMessage());
            nb_obj_error -= 1;
          }
        try
          {
            // On ajoute au moins une relation au hasard
            if (obj != null)
              {
                obj.addRelatedObject(attr_repo.getNodeById(Math.round(Math
                    .random() * nb_attr)));
                nb_rel--;
              }
          }
        catch (Exception e)
          {
            // TODO: handle exception
          }
      }
    System.out.println((nb_obj - nb_obj_error) + " objects insered");

    while (nb_rel > 0)
      {
        try
          {
            obj_repo.getNodeById(
                Math.round(Math.random() * (nb_obj - nb_obj_error)))
                .addRelatedObject(
                    attr_repo.getNodeById(Math.round(Math.random()
                        * (nb_attr - nb_attr_error))));
            nb_rel--;
          }
        catch (Exception e)
          {
          }
      }

  }

  @SuppressWarnings("unused")
  private static void printNodes(AttributeNodeRepository attr_repo,
      ObjectNodeRepository obj_repo)
  {
    System.out.println("Objects:");

    Iterable<ObjectNode> liste_o = obj_repo.getAllNodesWithoutLast();
    ObjectNode obj;
    for (Iterator<ObjectNode> iterator = liste_o.iterator(); iterator.hasNext();)
      {
        obj = (ObjectNode) iterator.next();
        System.out.println(obj);
      }

    System.out.println("Attributes:");
    Iterable<AttributeNode> liste_a = attr_repo.getAllNodesWithoutLast();
    AttributeNode att;
    for (Iterator<AttributeNode> iterator = liste_a.iterator(); iterator
        .hasNext();)
      {
        att = (AttributeNode) iterator.next();
        System.out.println(att);
      }
  }
}
