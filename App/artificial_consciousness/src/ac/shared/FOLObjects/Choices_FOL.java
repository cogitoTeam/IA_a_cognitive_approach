/**
 * 
 */
package ac.shared.FOLObjects;

import java.util.List;

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
  private List<Option> _options;

  // ***************************************************************************
  // METHODS
  // ***************************************************************************

  /**
   * @param option
   *          option to add
   * @param _options
   *          the options to set
   */
  public void addOption(Option option)
  {
    this._options.add(option);

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
  public List<Option> getOptions()
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
