/**
 * 
 */
package ac.shared;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public class CompleteBoardState extends AbstractBoardState
{
  private static final long serialVersionUID = -600890485679051029L;
  
  // ***************************************************************************
  // ATTRIBUTES
  // ***************************************************************************

  private List<RelevantPartialBoardState> _partial_states;

  // ***************************************************************************
  // CONSTRUCTOR
  // ***************************************************************************

  /**
   * @param id
   */
  public CompleteBoardState()
  {
    super();
    init();
  }

  /**
   * /!\ This constructor will be removed later !
   * 
   * @param id
   */
  public CompleteBoardState(long id)
  {
    super(id);
    init();
	}
  
  private void init()
  {
    this._partial_states = new LinkedList<RelevantPartialBoardState>();
  }
  
  // ***************************************************************************
  // METHODS
  // ***************************************************************************

  /**
   * Add a relevant partial board state to this option
   * 
   * @param partial_state
   *          the relevant partial board state to add
   */
  public void addPartialStates(RelevantPartialBoardState partial_state)
  {
    this._partial_states.add(partial_state);
  }
  
  /* **************************************************************************
   * GETTERS
   * ************************************************************************ */

  /**
   * @return the _partial_states
   */
  public List<RelevantPartialBoardState> getPartialStates()
  {
    return _partial_states;
  }
}
