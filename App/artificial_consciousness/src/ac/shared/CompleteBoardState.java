/**
 * 
 */
package ac.shared;

import java.util.ArrayList;

import ac.analysis.structure.Term;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public class CompleteBoardState extends AbstractBoardState
{
  private static final long serialVersionUID = -600890485679051029L;

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
  }

  public RelevantPartialBoardState getPart(ArrayList<Term> vars)
  {
    // TODO Auto-generated method stub
    return null;
  }

}
