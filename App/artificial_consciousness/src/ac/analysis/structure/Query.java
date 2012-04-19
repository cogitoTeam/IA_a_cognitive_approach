package ac.analysis.structure;

/**
 * Represents a conjunctive query : an atom list.
 * This class extends {@code FactBase} in order to reuse the latter's
 * functionality. It redefines merely the toString method
 */
@SuppressWarnings("serial")
public class Query extends FactBase
{
  /**
   * Empty constructor
   */
  public Query()
  {
  }

  /**
   * Constructor with atom as parameter
   * 
   * @param a
   */
  public Query(Atom a)
  {
    super.addNewFact(a);
  }

  /**
   * Constructor with a fact base as parameter
   * 
   * @param BF
   */
  public Query(FactBase BF)
  {
    super(BF);
  }

  /**
   * Constructor with a string as parameter
   * 
   * @param baseFaits
   */
  public Query(String baseFaits)
  {
    super(baseFaits);
  }

  public String toString()
  {
    String s = "Nombre d'atomes : " + atomList.size() + "\n";
    s += "Liste des atomes : \n";
    for (int i = 0; i < atomList.size(); i++)
      {
        s += "\tAtome " + (i + 1) + " : " + atomList.get(i) + "\n";
      }
    return s;
  }

}
