package ac.shared;

import java.io.Serializable;
import java.util.ArrayList;

import ac.analysis.structure.Atom;
import ac.analysis.structure.Rule;
import ac.analysis.structure.Term;

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
  // CONSTRUCTORS
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
   * Construit un RPBS à partir d'une liste d'atomes.
   * @param atoms_list
   */
  public RelevantPartialBoardState(ArrayList<Atom> atoms_list)
  {
    this._id = 0;
    ArrayList<Term> terms = new ArrayList<Term>();
    
    for(Atom a : atoms_list)
      for(Term t : a.getTerms())
        if(!terms.contains(t))
          terms.add(t);
    
    this._rule = new Rule(atoms_list, new Atom(PREFIX_LABEL, terms));
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

  /**
   * DON'T SUPRESS THIS METHOD. THX
   * 
   * @param id
   */
  public RelevantPartialBoardState(long id)
  {
    this._id = id;
    this._rule = null;
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
    if (o instanceof RelevantPartialBoardState)
      {
        RelevantPartialBoardState rpbs = (RelevantPartialBoardState) o;

        if (this._id != 0 && rpbs._id != 0)
          return (this._id == rpbs._id);
        else
          {
            // @todo test avec une comparaison logique
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
