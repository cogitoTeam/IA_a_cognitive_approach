/**
 * 
 */
package ac.shared;

import java.util.ArrayList;
import java.util.TreeSet;

import ac.analysis.structure.Atom;
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


  private void init()
  {
  }
  
  /**
   * Remplace les constants du cbs par des variables
   */
  public void generalise(String s)
  {
    ArrayList<Term> terms = this.boardStateFacts.getTermSet();
    
    for(Term t : terms)
      {
        t.setConstant(false);
        t.setLabel(s + t.getLabel().substring(1));
      }
  }

  /**
   * Get a part of this board state that contains all and only atoms that 
   * fully covered by vars.
   * @param vars
   * @return an RelevantPartialBoardState
   */
  public RelevantPartialBoardState getPart(ArrayList<Term> vars)
  {
    ArrayList<Atom> atoms_list = this.getBoardStateFacts().getAtomList();
    ArrayList<Atom> fully_covered_atoms_list = new ArrayList<Atom>();

    for (Atom a : atoms_list)
      {
        boolean is_fully_covered = true;
        for (Term t : a.getTerms())
          if (!vars.contains(t))
            is_fully_covered = false;
        
        if(is_fully_covered)
          fully_covered_atoms_list.add(a);
            
      }

    return new RelevantPartialBoardState(fully_covered_atoms_list);
  }
}
