/**
 * 
 */
package ac.memory.semantic;

import ac.memory.semantic.lattice.LatticeContext;
import ac.shared.CompleteBoardState;
import ac.shared.RelevantPartialBoardState;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 5 avr. 2012
 * @version 0.1
 */
public interface SemanticMemory
{
  /**
   * @return the context
   */
  LatticeContext getLatticeContext();

  /**
   * @param cbs
   *          the cbs
   * @return mark of the cbs
   */
  public double getMark(CompleteBoardState cbs);

  /**
   * @param rpbs
   *          the rpbs
   * @return mark of the cbs
   */
  public double getMark(RelevantPartialBoardState rpbs);
}
