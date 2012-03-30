package ac.memory;

import java.util.List;

import book.Pair;

import ac.shared.structure.CompleteBoardState;
import ac.shared.structure.RelevantPartialBoardState;

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

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#getRelevantPartialBoardStates() */
  @Override
  public List<RelevantPartialBoardState> getRelevantPartialBoardStates()
      throws MemoryException
  {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * 
   * @see
   * ac.memory.Memory#putCompleteBoardState(ac.shared.structure.CompleteBoardState
   * , java.util.List) */
  @Override
  public void putCompleteBoardState(CompleteBoardState cbs,
      List<RelevantPartialBoardState> matching) throws MemoryException
  {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#getGradedCompleteBoardState() */
  @Override
  public List<Pair<CompleteBoardState, Double>> getGradedCompleteBoardState()
      throws MemoryException
  {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * 
   * @see ac.memory.Memory#CompleteBoardStateChosen(ac.shared.structure.
   * CompleteBoardState) */
  @Override
  public void CompleteBoardStateChosen(CompleteBoardState cbs)
      throws MemoryException
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
   * @see ac.memory.Memory#EndOfGame(ac.memory.Memory.FinalGameStatus, float) */
  @Override
  public void EndOfGame(FinalGameStatus status, float score)
      throws MemoryException
  {
    // TODO Auto-generated method stub

  }
}
