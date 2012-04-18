/**
 * 
 */
package ac.shared.FOLObjects;

import java.io.IOException;
import java.util.List;
import java.util.LinkedList;


import ac.analysis.inferenceEngine.Homomorphisms;
import ac.analysis.inferenceEngine.KnowledgeBase;
import ac.analysis.structure.Query;
import ac.shared.CompleteBoardState;
import ac.shared.RelevantPartialBoardState;

/**
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @author namratapatel
 * 
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
    this._options = new LinkedList<Option_FOL>();
  }
  
  // ***************************************************************************
  // METHODS
  // ***************************************************************************

  /**
   * This method represents the Advanced Conceptual Analyzer : it uses an inference
   * engine to analyze complete board states in order to find relevant structures
   * within them. These then become relevant partial board states which are passed
   * on to the memory.    
   * @param rpbsList
   *          the list of {@link RelevantPartialBoardState}s from the active
   *          memory
   * @throws IOException
   */
  public void linkRelevant(List<RelevantPartialBoardState> rpbsList)
      throws IOException
  {
    KnowledgeBase kb = new KnowledgeBase("RuleBase");

    // can be omitted if clement adds the rule directly to the RuleBase file
    for (RelevantPartialBoardState rpbs : rpbsList)
      kb.addNewRule(rpbs.getRule());
    // till here

    Homomorphisms h;
    Query q;
    for (Option_FOL o : this.getOptions())
      {
        kb.setBF(o.getResult().getBoardStateFacts());
        kb.optimizedSaturation_FOL();
        for (RelevantPartialBoardState rpbs : rpbsList)
          {
            q = new Query(rpbs.getRule().getConclusion());
            h = new Homomorphisms(q, kb.getFB());
            if (h.existsHomomorphismTest())
              o.addPartialStates(rpbs);
          }
      }
  }
  
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
