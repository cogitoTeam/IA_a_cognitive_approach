package ac.reasoning;

import java.util.List;

import org.apache.log4j.Logger;

import ac.memory.ActiveMemory;
import ac.shared.*;

/**
 * Class ChoiceEngine Cette classe implémente la prise de décision
 * 
 * @author Clément Sipieter <csipieter@gmail.com>
 * @date 26 mars 2012
 * @version 0.1
 */
class ChoiceEngine
{

  @SuppressWarnings("unused")
  private static final Logger logger = Logger.getLogger(ChoiceEngine.class);

  /* **************************************************************************
   * ATTRIBUTS
   * ************************************************************************ */

  private ActiveMemory        _memory;

  /* **************************************************************************
   * CONSTRUCTORS
   * ************************************************************************ */

  public ChoiceEngine(ActiveMemory memory)
  {
    this._memory = memory;
  }

  /* **************************************************************************
   * FRIENDLY METHODS
   * ************************************************************************ */

  void start()
  {
    List<GradedAdvancedBoardStateRelevantStructure> list_bs = this._memory
        .getGradedAdvancedBoardStateRelevantStructures();

    /* ————————————————————————————————————————————————————————————————————————
     * Search the BoardState with better grade
     * 
     * @TODO outsource this procedure
     * —————————————————————————————————————————————————————————————————————— */
    GradedAdvancedBoardStateRelevantStructure better_bs = list_bs.get(0);
    double max_grade = better_bs.getGrade();

    for (GradedAdvancedBoardStateRelevantStructure bs : list_bs)
      if (bs.getGrade() > max_grade)
        {
          better_bs = bs;
          max_grade = bs.getGrade();
        }
    /* —————————————————————————————————————————————————————————————————————— */

  }
}
