package ac.shared;

import java.io.Serializable;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import ac.analysis.structure.Rule;

/**
 * Class which represents a Relevant structure (attribute of the lattice)
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 26 mars 2012
 * @version 0.2
 */
public class RelevantPartialBoardState implements Serializable
{

  // ***************************************************************************
  // ATTRIBUTES
  // ***************************************************************************

  private static final long serialVersionUID = -8844815904283187121L;
  private static final String PREFIX_LABEL = "_rpbs";

  private long _id;
  private Rule _rule;

  // ***************************************************************************
  // CONSTRUCTOR
  // ***************************************************************************

  /**
   * Constructor with id and rule in parameter
   * 
   * @param id
   *          the id
   * @param rule
   *          the rule
   */
  public RelevantPartialBoardState(long id, String rule)
  {
    this.init(id, rule);
  }

  /**
   * Constructor with rule in parameter
   * 
   * @param rule
   *          the rule
   */
  public RelevantPartialBoardState(String rule)
  {
    this.init(0, rule);
  }

  private void init(long id, String rule)
  {
    this._id = id;
    this._rule = new Rule(rule, "");
  }

  // ***************************************************************************
  // METHODS
  // ***************************************************************************

  @Override
  public boolean equals(Object o)
  {
    if(o instanceof RelevantPartialBoardState) 
      {
        RelevantPartialBoardState rpbs = (RelevantPartialBoardState)o;
        
        if(this._id != 0 && rpbs._id != 0)
          return (this._id == rpbs._id);
        else
          {
            //@todo test avec une comparaison logique
            return false;
          }
      }
    else
      return false;
  }
  
  // ***************************************************************************
  // GETTERS
  // ***************************************************************************

  /**
   * @return the _id
   */
  public long getId()
  {
    return _id;
  }

  /**
   * @return the _rule
   */
  public Rule getRule()
  {
    return _rule;
  }

  // ***************************************************************************
  // SETTERS
  // ***************************************************************************

  /**
   * @param id
   *          the id to set
   */
  public void setId(long id)
  {
    this._id = id;
    this._rule.getConclusion().setLabel(PREFIX_LABEL + id);
  }

}
