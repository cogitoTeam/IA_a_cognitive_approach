/**
 * 
 */
package ac.shared;

import java.io.Serializable;

import ac.analysis.structure.Atom;
import ac.analysis.structure.FactBase;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 28 mars 2012
 * @version 0.1
 */
public abstract class AbstractBoardState implements Serializable
{
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "AbstractBoardState [boardStateFacts=" + boardStateFacts + "]";
  }

  private static final long serialVersionUID = 6794929662400698592L;

  protected FactBase boardStateFacts;

  // TODO list of defining facts

  /**
   * Default constructor of an advanced boardstate
   * 
   * @param id
   *          the id's advanced boardstate
   */
  public AbstractBoardState()
  {
    boardStateFacts = new FactBase();
  }

  /**
   * @return the id
   */
  public long getId()
  {
    return boardStateFacts.hashCode();
  }


  /**
   * @return the boardStateFacts ({@link FactBase})
   */
  public FactBase getBoardStateFacts()
  {
    return boardStateFacts;
  }

  /**
   * @param boardStateFacts
   *          the boardStateFacts ({@link FactBase}) to set
   */
  public void setBoardStateFacts(FactBase boardStateFacts)
  {
    this.boardStateFacts = boardStateFacts;
  }

}
