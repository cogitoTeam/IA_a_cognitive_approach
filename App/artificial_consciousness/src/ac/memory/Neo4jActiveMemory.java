package ac.memory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import ac.util.Pair;

import ac.memory.episodic.EpisodicMemory;
import ac.memory.episodic.EpisodicMemoryException;
import ac.memory.episodic.Game;
import ac.memory.persistence.neo4j.AttributeNode;
import ac.memory.persistence.neo4j.AttributeNodeRepository;
import ac.memory.persistence.neo4j.Neo4jService;
import ac.memory.persistence.neo4j.NodeException;
import ac.memory.persistence.neo4j.NodeRepositoryException;
import ac.memory.persistence.neo4j.ObjectNode;
import ac.memory.persistence.neo4j.ObjectNodeRepository;
import ac.memory.semantic.SemanticMemory;
import ac.memory.semantic.lattice.LatticeContextException;
import ac.shared.CompleteBoardState;
import ac.shared.GameStatus;
import ac.shared.RelevantPartialBoardState;
import ac.shared.FOLObjects.Option_FOL;

/**
 * The class Active Memory is in French the "Mémoire primaire". It acts as a
 * buffer and as an interface between other modules and the semantic memory.
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */

public class Neo4jActiveMemory implements Memory
{

  private static final Logger logger = Logger
      .getLogger(Neo4jActiveMemory.class);

  EpisodicMemory episodic;
  SemanticMemory semantic;

  ObjectNodeRepository obj_repo;
  AttributeNodeRepository att_repo;

  List<Pair<Option_FOL, Double>> option_buffer;

  /**
   * Default constructor for the active memory
   * 
   * @param episodic
   *          the episodic memory
   * @param semantic
   *          the semantic memory
   */
  public Neo4jActiveMemory(EpisodicMemory episodic, SemanticMemory semantic)
  {
    this.episodic = episodic;
    this.semantic = semantic;
    this.obj_repo = new ObjectNodeRepository(Neo4jService.getInstance(),
        Neo4jService.getObjIndex(), Neo4jService.getObjMarkIndex());
    this.att_repo = new AttributeNodeRepository(Neo4jService.getInstance(),
        Neo4jService.getAttrIndex(), Neo4jService.getAttrMarkIndex());

    this.option_buffer = new LinkedList<>();
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#getRelevantPartialBoardStates() */
  @Override
  public List<RelevantPartialBoardState> getRelevantPartialBoardStates()
      throws MemoryException
  {
    if (logger.isDebugEnabled())
      logger.debug("getting best valued RPBS");

    ArrayList<RelevantPartialBoardState> ret = new ArrayList<>();
    try
      {
        List<AttributeNode> list = att_repo.getBestValued();
        for (AttributeNode node : list)
          {
            ret.add(node.getObject());
          }
      }
    catch (NodeRepositoryException | LatticeContextException e)
      {
        logger.warn("An error occured when constructing the RPBS list", e);
        throw new MemoryException(
            "An error occured when constructing the RPBS list", e);
      }

    return ret;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#putOption(ac.shared.FOLObjects.Option) */
  @Override
  public void putOption(Option_FOL option) throws MemoryException
  {
    if (logger.isDebugEnabled())
      logger.debug("Put new option in the Active Memory buffer");
    double total = 0.5;
    int nb = 0;

    if (logger.isDebugEnabled())
      logger.debug("Grade calculation for the new option");
    for (RelevantPartialBoardState rpbs : option.getPartialStates())
      {
        try
          {
            total += att_repo.getNodeById(rpbs.getId()).getMark();
            ++nb;
          }
        catch (NodeException | NullPointerException e)
          {
            logger
                .warn("Not fatal error occured when put new option in active memory");
          }
      }

    double mark = total / (double) nb;
    if (logger.isDebugEnabled())
      logger.debug("grade = " + mark);
    option_buffer.add(new Pair<Option_FOL, Double>(option, mark));
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#getGradedOptions() */
  @Override
  public List<Pair<Option_FOL, Double>> getGradedOptions() throws MemoryException
  {
    return option_buffer;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#OptionChosen(ac.shared.FOLObjects.Option) */
  @Override
  public void OptionChosen(Option_FOL option) throws MemoryException
  {
    // TODO verify that the option chosen is in the buffer list.
    try
      {
        episodic.newMove(option.getResult());
      }
    catch (EpisodicMemoryException e)
      {
        logger.error("Error occured when active memory get the choice", e);
        throw new MemoryException(
            "Error occured when active memory get the choice", e);
      }

    // Buffer is reseted when a choice is done
    option_buffer.clear();
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#BeginOfGame() */
  @Override
  public void BeginOfGame() throws MemoryException
  {
    episodic.newGame();
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#EndOfGame(ac.shared.GameStatus, float) */
  @Override
  public void EndOfGame(GameStatus status, int score) throws MemoryException
  {
    episodic.finishGame(status, score);
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#getWonGames(int) */
  @Override
  public List<Game> getLastWonGames(int n)
  {
    return getLastGamesByStatus(n, GameStatus.VICTORY);
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#getLostGames(int) */
  @Override
  public List<Game> getLastLostGames(int n)
  {
    return getLastGamesByStatus(n, GameStatus.DEFEAT);
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#getMostActiveRPBS(int) */
  @Override
  public List<Pair<RelevantPartialBoardState, Double>> getMostActiveRPBS(int n)
      throws MemoryException
  {
    LinkedList<Pair<RelevantPartialBoardState, Double>> ret = new LinkedList<>();
    try
      {
        List<AttributeNode> list = att_repo.getBestValued(n);

        for (AttributeNode node : list)
          {
            ret.add(new Pair<RelevantPartialBoardState, Double>(node
                .getObject(), node.getMark()));
          }
      }
    catch (NodeRepositoryException | LatticeContextException | NodeException e)
      {
        logger.error("Error when getting most active RPBS", e);
        throw new MemoryException("Error when getting most active RPBS", e);
      }

    return ret;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#getMostActiveCBS(int) */
  @Override
  public List<Pair<CompleteBoardState, Double>> getMostActiveCBS(int n)
      throws MemoryException
  {
    LinkedList<Pair<CompleteBoardState, Double>> ret = new LinkedList<>();
    try
      {
        List<ObjectNode> list = obj_repo.getBestValued(n);

        for (ObjectNode node : list)
          {
            ret.add(new Pair<CompleteBoardState, Double>(node.getObject(), node
                .getMark()));
          }
      }
    catch (NodeRepositoryException | LatticeContextException | NodeException e)
      {
        logger.error("Error when getting most active CBS", e);
        throw new MemoryException("Error when getting most active CBS", e);
      }

    return ret;
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.Memory#putRelevantStructure(ac.shared.RelevantPartialBoardState) */
  @Override
  public long putRelevantStructure(RelevantPartialBoardState rpbs)
      throws MemoryException
  {
    rpbs.setId(att_repo.getFreeId());
    try
      {
        att_repo.createNode(rpbs);
      }
    catch (NodeRepositoryException e)
      {
        logger.error("Error occured when trying to add new RPBS", e);
        throw new MemoryException("Error occured when trying to add new RPBS",
            e);
      }
    return rpbs.getId();
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#addAssociation(ac.shared.CompleteBoardState,
   * ac.shared.RelevantPartialBoardState) */
  @Override
  public void addAssociation(CompleteBoardState cbs,
      RelevantPartialBoardState rpbs) throws MemoryException
  {
    try
      {
        semantic.getLatticeContext().setStatus(cbs, rpbs, true);
      }
    catch (LatticeContextException e)
      {
        logger.error("Error occured when trying to add association in memory",
            e);
        throw new MemoryException(
            "Error occured when trying to add association in memory", e);
      }
  }

  @Override
  public String toString()
  {
    String ret = "Neo4jMEMORY: \n";
    ret += episodic.toString();
    ret += "\n" + semantic.toString();

    return ret;
  }

  /**
   * @param n
   * @param victory
   * @return
   */
  private List<Game> getLastGamesByStatus(int n, GameStatus status)
  {
    Game g = episodic.getLastGame();
    LinkedList<Game> list = new LinkedList<>();

    int i = 0;
    while (i < n && g != null)
      {
        if (g.getStatus().equals(status))
          {
            list.add(g);
            ++i;
          }
        g = g.getPreviousGame();
      }
    return list;
  }
}
