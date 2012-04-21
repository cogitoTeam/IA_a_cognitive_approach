package ac;

import game.Game;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ac.analysis.Analysis;
import ac.analysis.structure.Atom;
import agent.Action;
import agent.Percept;
import ac.memory.MemoryException;
import ac.memory.Neo4jActiveMemory;
import ac.memory.episodic.Neo4jEpisodicMemory;
import ac.memory.semantic.Neo4jSemanticMemory;
import ac.reasoning.Reasoning;
import ac.shared.CompleteBoardState;
import ac.shared.GameStatus;
import ac.shared.RelevantPartialBoardState;
import agent.Agent;

/**
 * 
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public class AC extends Agent
{

  private static final Logger LOGGER = Logger.getLogger(AC.class);

  /* **************************************************************************
   * ATTRIBUTS
   * ************************************************************************ */

  private Analysis _analysis;
  private Neo4jActiveMemory _memory;
  private Reasoning _reasoning;

  private boolean existGame = false;

  /* **************************************************************************
   * CONSTRUCTOR
   * ************************************************************************ */

  /**
   * Default constructor
   */
  public AC()
  {
    if (LOGGER.isDebugEnabled())
      LOGGER.debug("AC constructor call");
    this._memory = new Neo4jActiveMemory(new Neo4jEpisodicMemory(),
        new Neo4jSemanticMemory());
    this._reasoning = new Reasoning(this._memory);
    this._analysis = new Analysis(this._memory, this._reasoning);

  }

  /* **************************************************************************
   * METHODS
   * ************************************************************************ */

  /**
   * bootstrap method for starting with some RelevantPartialBordState
   */
  @Override
  public void bootstrap()
  {
    if (LOGGER.isDebugEnabled())
      LOGGER.debug("AC bootstrap method call");
    ArrayList<Atom> atoms_list = new ArrayList<Atom>();

    if (LOGGER.isDebugEnabled())
      LOGGER.debug("Generating some Atoms");

    atoms_list.add(new Atom("isMine(x)"));

    try
      {
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Put new RPBS in memory");
        
        this._memory.putRelevantStructure(new RelevantPartialBoardState(
            atoms_list));
      }
    catch (MemoryException e)
      {
        LOGGER.error("An error occured when putting RPBS in memory", e);
      }
    
    atoms_list.clear();
    atoms_list.add(new Atom("isOpp(x)"));

    try
      {
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Put new RPBS in memory");
        
        this._memory.putRelevantStructure(new RelevantPartialBoardState(
            atoms_list));
      }
    catch (MemoryException e)
      {
        LOGGER.error("An error occured when putting RPBS in memory", e);
      }
    
    atoms_list.clear();
    atoms_list.add(new Atom("isMine(x)"));
    atoms_list.add(new Atom("isMine(y)"));
    atoms_list.add(new Atom("near(x,y)"));
    atoms_list.add(new Atom("near(y,x)"));


    try
      {
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Put new RPBS in memory");
        this._memory.putRelevantStructure(new RelevantPartialBoardState(
            atoms_list));
      }
    catch (MemoryException e)
      {
        LOGGER.error("An error occured when putting RPBS in memory", e);
      }
    
    atoms_list.clear();
    atoms_list.add(new Atom("isOpp(x)"));
    atoms_list.add(new Atom("isOpp(y)"));
    atoms_list.add(new Atom("near(x,y)"));
    atoms_list.add(new Atom("near(y,x)"));


    try
      {
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Put new RPBS in memory");
        this._memory.putRelevantStructure(new RelevantPartialBoardState(
            atoms_list));
      }
    catch (MemoryException e)
      {
        LOGGER.error("An error occured when putting RPBS in memory", e);
      }
    
    atoms_list.clear();
    atoms_list.add(new Atom("isOpp(x)"));
    atoms_list.add(new Atom("isMine(y)"));
    atoms_list.add(new Atom("near(x,y)"));
    atoms_list.add(new Atom("near(y,x)"));


    try
      {
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Put new RPBS in memory");
        this._memory.putRelevantStructure(new RelevantPartialBoardState(
            atoms_list));
      }
    catch (MemoryException e)
      {
        LOGGER.error("An error occured when putting RPBS in memory", e);
      }
    
    atoms_list.clear();
    atoms_list.add(new Atom("isMine(x)"));
    atoms_list.add(new Atom("isMine(y)"));
    atoms_list.add(new Atom("isMine(z)"));
    atoms_list.add(new Atom("near(x,y)"));
    atoms_list.add(new Atom("near(y,x)"));
    atoms_list.add(new Atom("near(y,z)"));
    atoms_list.add(new Atom("near(z,y)"));
    atoms_list.add(new Atom("aligned(x,y,z)"));
    atoms_list.add(new Atom("aligned(z,y,x)"));

    try
      {
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Put new RPBS in memory");
        this._memory.putRelevantStructure(new RelevantPartialBoardState(
            atoms_list));
      }
    catch (MemoryException e)
      {
        LOGGER.error("An error occured when putting RPBS in memory", e);
      }
    
    atoms_list.clear();
    atoms_list.add(new Atom("isOpp(x)"));
    atoms_list.add(new Atom("isOpp(y)"));
    atoms_list.add(new Atom("isOpp(z)"));
    atoms_list.add(new Atom("near(x,y)"));
    atoms_list.add(new Atom("near(y,x)"));
    atoms_list.add(new Atom("near(y,z)"));
    atoms_list.add(new Atom("near(z,y)"));
    atoms_list.add(new Atom("aligned(x,y,z)"));
    atoms_list.add(new Atom("aligned(z,y,x)"));

    try
      {
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Put new RPBS in memory");
        this._memory.putRelevantStructure(new RelevantPartialBoardState(
            atoms_list));
      }
    catch (MemoryException e)
      {
        LOGGER.error("An error occured when putting RPBS in memory", e);
      }
    
    atoms_list.clear();
    atoms_list.add(new Atom("isMine(x)"));
    atoms_list.add(new Atom("isOpp(y)"));
    atoms_list.add(new Atom("isOpp(z)"));
    atoms_list.add(new Atom("near(x,y)"));
    atoms_list.add(new Atom("near(y,x)"));
    atoms_list.add(new Atom("near(y,z)"));
    atoms_list.add(new Atom("near(z,y)"));
    atoms_list.add(new Atom("aligned(x,y,z)"));
    atoms_list.add(new Atom("aligned(z,y,x)"));

    try
      {
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Put new RPBS in memory");
        this._memory.putRelevantStructure(new RelevantPartialBoardState(
            atoms_list));
      }
    catch (MemoryException e)
      {
        LOGGER.error("An error occured when putting RPBS in memory", e);
      }
    
    atoms_list.clear();
    atoms_list.add(new Atom("isOpp(x)"));
    atoms_list.add(new Atom("isMine(y)"));
    atoms_list.add(new Atom("isMine(z)"));
    atoms_list.add(new Atom("near(x,y)"));
    atoms_list.add(new Atom("near(y,x)"));
    atoms_list.add(new Atom("near(y,z)"));
    atoms_list.add(new Atom("near(z,y)"));
    atoms_list.add(new Atom("aligned(x,y,z)"));
    atoms_list.add(new Atom("aligned(z,y,x)"));

    try
      {
        if (LOGGER.isDebugEnabled())
          LOGGER.debug("Put new RPBS in memory");
        this._memory.putRelevantStructure(new RelevantPartialBoardState(
            atoms_list));
      }
    catch (MemoryException e)
      {
        LOGGER.error("An error occured when putting RPBS in memory", e);
      }

  }

  @Override
  protected void think()
  {
    // nothing to do
  }

  @Override
  protected Action perceptReaction(Percept percept)
  {
    Action action = null;

    if (LOGGER.isDebugEnabled())
      LOGGER.debug("New percept received " + percept.getClass());

    switch (percept.getType())
      {
        case CHOICES:
          if (LOGGER.isDebugEnabled())
            LOGGER.debug("AC have to make a choice");
          if (!existGame)
            {
              if (LOGGER.isDebugEnabled())
                LOGGER.debug("No game started");
              try
                {
                  if (LOGGER.isDebugEnabled())
                    LOGGER.debug("Beginning new game");

                  this._memory.BeginOfGame();
                }
              catch (MemoryException e)
                {
                  LOGGER.error(
                      "An error occured when starting new game in memory", e);
                }
              this.existGame = true;
            }

          try
            {
              if (LOGGER.isDebugEnabled())
                LOGGER.debug("Call the analysis module to make a choice");
              action = this._analysis.analyse((Percept.Choices) percept, this.getPlayer());
            }
          catch (Exception e)
            {
              LOGGER.error("An error occured when analyzing moves", e);
            }
          break;

        case GAME_END:
          if (LOGGER.isDebugEnabled())
            LOGGER.debug("AC have to finish the current game");
          this.existGame = false;

          Percept.GameEnd game_end = (Percept.GameEnd) percept;

          if (LOGGER.isDebugEnabled())
            LOGGER.debug("Getting the game status");
          GameStatus status = GameStatus.UNDEFINED;

          if (game_end.getWinner() == this.getPlayer())
            status = GameStatus.VICTORY;
          else if (game_end.getWinner() == null)
            status = GameStatus.DRAW;
          else
            status = GameStatus.DEFEAT;

          if (LOGGER.isDebugEnabled())
            LOGGER.debug("GameStatus: " + status.toString());

          try
            {
              if (LOGGER.isDebugEnabled())
                LOGGER.debug("Ending game in memory");
              this._memory.EndOfGame(status, game_end.getScore());
            }
          catch (MemoryException e)
            {
              LOGGER.error(
                  "An error occured when saying to memory the end of game", e);
            }

          if (LOGGER.isDebugEnabled())
            LOGGER.debug("Restarting new Game :)");
          
        try
          {
            Thread.sleep(10_000);
          }
        catch (InterruptedException e)
          {}
        
          action = new Action.Restart();

          break;

      }

    return action;
  }

  @Override
  protected void actionResult(boolean success, Action action)
  {
    // TODO Auto-generated method stub

  }

  /* **************************************************************************
   * GETTERS
   * ************************************************************************ */

  /**
   * @return the memory module
   */
  public Neo4jActiveMemory getMemory()
  {
    return _memory;
  }

  /**
   * @return the reasoning module
   */
  public Reasoning getReasoning()
  {
    return _reasoning;
  }

  /**
   * @return the analysis module
   */
  public Analysis getAnalysis()
  {
    return _analysis;
  }

}
