package ac.shared.FOLObjects;

import java.util.LinkedList;
import java.util.List;

import ac.shared.CompleteBoardState;
import agent.Action;
import ac.shared.RelevantPartialBoardState;

/**
 * 
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 4 avr. 2012
 * @version 0.1
 */
public class Option
{
  // ***************************************************************************
  // ATTRIBUTES
  // ***************************************************************************

  private Action _move;
  private CompleteBoardState _result;
  private List<RelevantPartialBoardState> _partial_states;

  // ***************************************************************************
  // CONSTRUCTOR
  // ***************************************************************************

  public Option(Action move, CompleteBoardState result)
  {
    this._move = move;
    this._result = result;
    this._partial_states = new LinkedList<RelevantPartialBoardState>();
  }

  // ***************************************************************************
  // METHODS
  // ***************************************************************************

  /**
   * @param partial_states
   *          the partial_states to set
   */
  public void setPartialStates(List<RelevantPartialBoardState> partial_states)
  {
    this._partial_states = partial_states;
  }

  // ***************************************************************************
  // GETTERS
  // ***************************************************************************

  /**
   * @return the move
   */
  public Action getMove()
  {
    return _move;
  }

  /**
   * @return the result
   */
  public CompleteBoardState getResult()
  {
    return _result;
  }

  /**
   * Add a relevant partial board state to this option
   * @param partial_state the relevant partial board state to add
   */
  public void addPartialStates(RelevantPartialBoardState partial_state)
  {
    this._partial_states.add(partial_state);
  }

  // ***************************************************************************
  // SETTERS
  // ***************************************************************************

  /**
   * @param move
   *          the move to set
   */
  public void setMove(Action move)
  {
    this._move = move;
  }

  /**
   * @param result
   *          the result to set
   */
  public void setResult(CompleteBoardState result)
  {
    this._result = result;
  }

}
