/**
 * 
 */
package ac.shared.FOLObjects;

import java.util.List;
import java.util.LinkedList;


import ac.shared.CompleteBoardState;

/**
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 4 avr. 2012
 * @version 0.1
 */
public class Choices_FOL
{
  // ***************************************************************************
  // ATTRIBUTES
  // ***************************************************************************

  private CompleteBoardState _current_board;
  private List<Option_FOL> _options;

  // ***************************************************************************
  // CONSTRUCTORS
  // ***************************************************************************

  public Choices_FOL()
  {
    this._options = new LinkedList<Option>();
  }
  
  // ***************************************************************************
  // METHODS
  // ***************************************************************************

  /**
   * @param option
   *          option to add
   * @param _options
   *          the options to set
   */
  public void addOption(Option_FOL option)
  {
    this._options.add(option);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "Choices_FOL [_current_board=" + _current_board + ", _options="
        + _options + "]";
  }

  // ***************************************************************************
  // GETTERS
  // ***************************************************************************

  /**
   * @return the current_board
   */
  public CompleteBoardState getCurrent_board()
  {
    return _current_board;
  }

  /**
   * @return the options
   */
  public List<Option_FOL> getOptions()
  {
    return _options;
  }

  // ***************************************************************************
  // SETTERS
  // ***************************************************************************

  /**
   * @param current_board
   *          the current_board to set
   */
  public void setCurrent_board(CompleteBoardState current_board)
  {
    this._current_board = current_board;
  }

}
