package ac.shared;

import java.io.Serializable;
import java.util.ArrayList;

import ac.analysis.inferenceEngine.Homomorphisms;
import ac.analysis.structure.Atom;
import ac.analysis.structure.Rule;
import ac.analysis.structure.Substitution;
import ac.analysis.structure.Term;
import ac.analysis.structure.TermPair;

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
   * 
   * @param atoms_list
   */
  public RelevantPartialBoardState(ArrayList<Atom> atoms_list)
  {
    this._id = 0;
    ArrayList<Term> terms = new ArrayList<Term>();

    for (Atom a : atoms_list)
      for (Term t : a.getTerms())
        if (!terms.contains(t))
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
            if(this._rule.getPremise().size() != rpbs._rule.getPremise().size())
              return false;
            
            if(this._rule.getTerms().size() != rpbs._rule.getTerms().size())
              return false;
            
            this.renameVars("x");
            rpbs.renameVars("y");
            Homomorphisms homo = new Homomorphisms(this._rule.getPremise(), rpbs._rule.getPremise());
            if(homo.existsHomomorphismTest(new Substitution()))
              {
                homo = new Homomorphisms(rpbs._rule.getPremise(), this._rule.getPremise());
                if(homo.existsHomomorphismTest(new Substitution()))
                  return true;
              }
              
            
          }
      }
   
    return false;
  }

  @Override
  public RelevantPartialBoardState clone()
  {
    return new RelevantPartialBoardState(this._id,
        this._rule.toStringForConstructor());
  }

  public void renameVars(Substitution sub1)
  {
    for (Term t : this._rule.getTerms())
      for (TermPair p : sub1.getPairList())
        if (t.equals(p.getY()))
          t.copy(p.getX());

  }

  public void renameVars(String x)
  {
    int i = 0;
    for (Term t : this._rule.getTerms())
      t.setLabel(x + i++);

  }
  
  public void renameVars()
  {
    this.renameVars("x");
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
  
  public static void main(String[] args)
  {
    RelevantPartialBoardState rs1, rs2, rs3;
    
    rs1 = new RelevantPartialBoardState("a(x);b(y);c(y,z);r(x,y,z);");
    rs2 = new RelevantPartialBoardState("b(z);c(z,x);a(y);r(x,y,z);");
    rs3 = new RelevantPartialBoardState("b(z);c(y,x);a(y);r(x,y,z);");
    
    if(rs1.equals(rs2))
      System.out.println("ok");
    else
      System.out.println("nok");
    
    if(rs1.equals(rs3))
      System.out.println("nok");
    else
      System.out.println("ok");
    
    


  }

}
