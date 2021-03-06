/**
 * 
 */
package ac.memory.semantic.lattice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import ac.util.Pair;

import ac.memory.persistence.neo4j.AttributeNode;
import ac.memory.persistence.neo4j.AttributeNodeRepository;
import ac.memory.persistence.neo4j.Neo4jService;
import ac.memory.persistence.neo4j.NodeException;
import ac.memory.persistence.neo4j.ObjectNode;
import ac.memory.persistence.neo4j.ObjectNodeRepository;
import ac.shared.CompleteBoardState;
import ac.shared.RelevantPartialBoardState;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 1 avr. 2012
 * @version 0.1
 */
public class Neo4jLatticeContext implements LatticeContext
{
  private static final Logger logger = Logger
      .getLogger(Neo4jLatticeContext.class);

  private static AttributeNodeRepository attr_repo;
  private static ObjectNodeRepository obj_repo;

  static
    {
      attr_repo = new AttributeNodeRepository(Neo4jService.getInstance(),
          Neo4jService.getAttrIndex(), Neo4jService.getAttrMarkIndex());
      obj_repo = new ObjectNodeRepository(Neo4jService.getInstance(),
          Neo4jService.getObjIndex(), Neo4jService.getObjMarkIndex());
    }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.semantic.graph.lattice.LatticeContext#addAttribute(ac.shared
   * .structure.RelevantPartialBoardState) */
  @Override
  public void addAttribute(RelevantPartialBoardState attribute)
      throws LatticeContextException
  {
    try
      {
        attr_repo.createNode(attribute);
      }
    catch (Exception e)
      {
        throw new LatticeContextException(
            "An error occured when adding an attribute", e);
      }
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.semantic.graph.lattice.LatticeContext#addObject(ac.shared.structure
   * .CompleteBoardState) */
  @Override
  public void addObject(CompleteBoardState objet)
      throws LatticeContextException
  {
    try
      {
        obj_repo.createNode(objet);
      }
    catch (Exception e)
      {
        throw new LatticeContextException(
            "An error occured when adding an object", e);
      }

  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.semantic.graph.lattice.LatticeContext#getStatus(ac.shared.structure
   * .CompleteBoardState, ac.shared.structure.RelevantPartialBoardState) */
  @Override
  public boolean getStatus(CompleteBoardState object,
      RelevantPartialBoardState attribute) throws LatticeContextException
  {
    return getStatus(object.getId(), attribute.getId());
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.semantic.graph.lattice.LatticeContext#setStatus(ac.shared.structure
   * .CompleteBoardState, ac.shared.structure.RelevantPartialBoardState,
   * boolean) */
  @Override
  public void setStatus(CompleteBoardState object,
      RelevantPartialBoardState attribute, boolean value)
      throws LatticeContextException
  {
    setStatus(object.getId(), attribute.getId(), value);
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.semantic.graph.lattice.LatticeContext#getStatus(long, long) */
  @Override
  public boolean getStatus(long id_object, long id_attribute)
      throws LatticeContextException
  {
    try
      {
        return obj_repo.getNodeById(id_object).isRelatedWith(id_attribute);
      }
    catch (Exception e)
      {
        throw new LatticeContextException(
            "An error occured when getting status", e);
      }
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.semantic.graph.lattice.LatticeContext#setStatus(long, long,
   * boolean) */
  @Override
  public void setStatus(long id_object, long id_attribute, boolean value)
      throws LatticeContextException
  {
    try
      {
        if (value)
          {
            obj_repo.getNodeById(id_object).addRelatedObject(
                attr_repo.getNodeById(id_attribute));
          }
        else
          {
            obj_repo.getNodeById(id_object).removeRelatedObject(
                attr_repo.getNodeById(id_attribute));
          }

      }
    catch (Exception e)
      {
        throw new LatticeContextException(
            "An error occured when setting status", e);
      }
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.semantic.graph.lattice.LatticeContext#getAttributesByObject(
   * ac.shared.structure.CompleteBoardState) */
  @Override
  public Map<Long, RelevantPartialBoardState> getAttributesByObject(
      CompleteBoardState object) throws LatticeContextException
  {
    return getAttributesByObject(object.getId());
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.semantic.graph.lattice.LatticeContext#getObjectsByAttribute(
   * ac.shared.structure.RelevantPartialBoardState) */
  @Override
  public Map<Long, CompleteBoardState> getObjectsByAttribute(
      RelevantPartialBoardState attribute) throws LatticeContextException
  {
    return getObjectsByAttribute(attribute.getId());
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.semantic.graph.lattice.LatticeContext#getAttributesByObject(long) */
  @Override
  public Map<Long, RelevantPartialBoardState> getAttributesByObject(
      long id_object) throws LatticeContextException
  {
    try
      {
        HashMap<Long, RelevantPartialBoardState> ret = new HashMap<Long, RelevantPartialBoardState>();
        for (Iterator<AttributeNode> iterator = obj_repo.getNodeById(id_object)
            .getRelatedObjects().iterator(); iterator.hasNext();)
          {
            AttributeNode att = (AttributeNode) iterator.next();
            RelevantPartialBoardState rpbs = att.getObject();
            ret.put(att.getId(), rpbs);

          }

        return ret;
      }
    catch (Exception e)
      {
        throw new LatticeContextException(
            "An error occured when getting attributes by object", e);
      }
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.semantic.graph.lattice.LatticeContext#getObjectsByAttribute(long) */
  @Override
  public Map<Long, CompleteBoardState> getObjectsByAttribute(long id_attribute)
      throws LatticeContextException
  {
    try
      {
        HashMap<Long, CompleteBoardState> ret = new HashMap<Long, CompleteBoardState>();
        for (Iterator<ObjectNode> iterator = attr_repo
            .getNodeById(id_attribute).getRelatedObjects().iterator(); iterator
            .hasNext();)
          {
            ObjectNode obj = (ObjectNode) iterator.next();
            CompleteBoardState cbs = obj.getObject();
            ret.put(cbs.getId(), cbs);

          }

        return ret;
      }
    catch (Exception e)
      {
        throw new LatticeContextException(
            "An error occured when getting object by attribute", e);
      }
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.semantic.graph.lattice.LatticeContext#getObjects() */
  @Override
  public Map<Long, CompleteBoardState> getObjects()
      throws LatticeContextException
  {
    try
      {
        logger.debug("Generation of the HashMap");
        HashMap<Long, CompleteBoardState> ret = new HashMap<Long, CompleteBoardState>();
        for (Iterator<ObjectNode> iterator = obj_repo.getAllNodesWithoutLast()
            .iterator(); iterator.hasNext();)
          {
            ObjectNode obj = (ObjectNode) iterator.next();
            CompleteBoardState cbs = obj.getObject();
            ret.put(cbs.getId(), cbs);
          }
        logger.debug("End of generation");
        return ret;
      }
    catch (Exception e)
      {
        throw new LatticeContextException(
            "An error occured when getting all the objects", e);
      }
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.semantic.graph.lattice.LatticeContext#getObjectsWithAttributes() */
  @Override
  public Map<Long, Pair<CompleteBoardState, HashSet<RelevantPartialBoardState>>> getObjectsWithAttributes()
      throws LatticeContextException
  {
    try
      {
        logger.debug("Generation of the HashMap");
        HashMap<Long, Pair<CompleteBoardState, HashSet<RelevantPartialBoardState>>> ret = new HashMap<Long, Pair<CompleteBoardState, HashSet<RelevantPartialBoardState>>>();
        for (Iterator<ObjectNode> iterator = obj_repo.getAllNodesWithoutLast()
            .iterator(); iterator.hasNext();)
          {
            ObjectNode obj = (ObjectNode) iterator.next();
            CompleteBoardState cbs = obj.getObject();

            HashSet<RelevantPartialBoardState> attr = new HashSet<RelevantPartialBoardState>();

            for (Iterator<AttributeNode> iterator2 = obj.getRelatedObjects()
                .iterator(); iterator2.hasNext();)
              {
                AttributeNode a = (AttributeNode) iterator2.next();
                attr.add(a.getObject());
              }

            ret.put(
                cbs.getId(),
                new Pair<CompleteBoardState, HashSet<RelevantPartialBoardState>>(
                    cbs, attr));

          }
        logger.debug("End of generation");
        return ret;
      }
    catch (Exception e)
      {
        throw new LatticeContextException(
            "An error occured when getting all the objects", e);
      }
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.semantic.graph.lattice.LatticeContext#getAttributes() */
  @Override
  public Map<Long, RelevantPartialBoardState> getAttributes()
      throws LatticeContextException
  {
    try
      {
        HashMap<Long, RelevantPartialBoardState> ret = new HashMap<Long, RelevantPartialBoardState>();
        for (Iterator<AttributeNode> iterator = attr_repo
            .getAllNodesWithoutLast().iterator(); iterator.hasNext();)
          {
            AttributeNode att = (AttributeNode) iterator.next();
            RelevantPartialBoardState rpbs = att.getObject();
            ret.put(rpbs.getId(), rpbs);
          }

        return ret;
      }
    catch (Exception e)
      {
        throw new LatticeContextException(
            "An error occured when getting all the objects", e);
      }
  }

  public String toString()
  {
    String ret = "[[ LATTICE CONTEXT";
    for (Iterator<ObjectNode> iterator = obj_repo.getAllNodesWithoutLast()
        .iterator(); iterator.hasNext();)
      {
        ObjectNode object = (ObjectNode) iterator.next();
        ret += "\n  " + object.getId() + " | ";

        for (Iterator<AttributeNode> iterator2 = object.getRelatedObjects()
            .iterator(); iterator2.hasNext();)
          {
            AttributeNode attribute = (AttributeNode) iterator2.next();
            try
              {
                ret += "(" + attribute.getId() + "|" + attribute.getMark()
                    + ", ";
              }
            catch (NodeException e)
              {
                System.err.println(e.getLocalizedMessage());
              }
          }
      }
    ret += "\n]]";
    return ret;
  }
}
