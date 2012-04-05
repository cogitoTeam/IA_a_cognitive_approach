package ac.memory;

import java.util.List;

import ac.util.Pair;

import ac.memory.episodic.EpisodicMemory;
import ac.memory.episodic.Game;
import ac.memory.semantic.SemanticMemory;
import ac.shared.CompleteBoardState;
import ac.shared.GameStatus;
import ac.shared.RelevantPartialBoardState;
import ac.shared.FOLObjects.Option;

/**
 * The class Active Memory is in French the "Mémoire primaire". It acts as a
 * buffer and as an interface between other modules and the semantic memory.
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */

public class ActiveMemory implements Memory
{

  EpisodicMemory episodic;
  SemanticMemory semantic;

  List<Pair<Option, Double>> option_buffer;

  /**
   * Default constructor for the active memory
   * 
   * @param episodic
   *          the episodic memory
   * @param semantic
   *          the semantic memory
   */
  public ActiveMemory(EpisodicMemory episodic, SemanticMemory semantic)
  {
    this.episodic = episodic;
    this.semantic = semantic;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#getRelevantPartialBoardStates() */
  @Override
  public List<RelevantPartialBoardState> getRelevantPartialBoardStates()
      throws MemoryException
  {
    // TODO !!
    return null;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#putOption(ac.shared.FOLObjects.Option) */
  @Override
  public void putOption(Option option) throws MemoryException
  {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#getGradedOptions() */
  @Override
  public List<Pair<Option, Double>> getGradedOptions() throws MemoryException
  {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#OptionChosen(ac.shared.FOLObjects.Option) */
  @Override
  public void OptionChosen(Option option) throws MemoryException
  {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#BeginOfGame() */
  @Override
  public void BeginOfGame() throws MemoryException
  {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#EndOfGame(ac.shared.GameStatus, float) */
  @Override
  public void EndOfGame(GameStatus status, float score) throws MemoryException
  {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#getWonGames(int) */
  @Override
  public List<Game> getWonGames(int n)
  {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#getLostGames(int) */
  @Override
  public List<Game> getLostGames(int n)
  {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#getMostActiveBoardStates(int) */
  @Override
  public List<CompleteBoardState> getMostActiveBoardStates(int n)
  {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.Memory#putRelevantStructure(ac.shared.RelevantPartialBoardState) */
  @Override
  public long putRelevantStructure(RelevantPartialBoardState rpbs)
  {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see ac.memory.Memory#addAssociation(ac.shared.CompleteBoardState, ac.shared.RelevantPartialBoardState)
   */
  @Override
  public void addAssociation(CompleteBoardState cbs,
      RelevantPartialBoardState rpbs)
  {
    // TODO Auto-generated method stub
    
  }
}
