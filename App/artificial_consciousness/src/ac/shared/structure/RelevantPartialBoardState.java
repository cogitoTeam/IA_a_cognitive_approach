package ac.shared.structure;

/**
 * Class which represents a Relevant structure (attribute of the lattice)
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
public class RelevantPartialBoardState extends AbstractBoardState implements
    Comparable<RelevantPartialBoardState>
{
  private static final long serialVersionUID = -8844815904283187121L;

  /**
   * @param id
   */
  public RelevantPartialBoardState(long id)
  {
    super(id);
  }

  private double weight;

  public int compareTo(RelevantPartialBoardState rs)
  {
    if (getWeight() < rs.getWeight())
      return -1;
    else if (getWeight() > rs.getWeight())
      return 1;
    else
      return 0;
  }

  /**
   * @return the weight
   */
  public double getWeight()
  {
    return weight;
  }

  /**
   * @param weight
   *          the weight to set
   */
  public void setWeight(double weight)
  {
    this.weight = weight;
  }

  /**
   * @return the id
   */
  public long getId()
  {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(long id)
  {
    this.id = id;
  }

}
