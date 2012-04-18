package ac.analysis;

import java.io.IOException;
import java.util.List;

import ac.analysis.inferenceEngine.Homomorphisms;
import ac.analysis.inferenceEngine.KnowledgeBase;
import ac.analysis.structure.Query;
import ac.shared.RelevantPartialBoardState;
import ac.shared.FOLObjects.Choices_FOL;
import ac.shared.FOLObjects.Option;

/**
 * This class represents the Advanced Conceptual Analyzer : it uses an inference
 * engine to analyze complete board states in order to find relevant structures
 * within them. These then become relevant partial board states which are passed
 * on to the memory.
 * 
 * @author namratapatel
 * 
 */
public class AdvancedAnalysisEngine
{
  /* **************************************************************************
   * FIELD
   * ************************************************************************* */

  /**
   * The input : {@link Choices_FOL} where the options have yet to be analyzed
   * for relevant structures
   */
  private Choices_FOL input;

  /**
   * The output : {@link Choices_FOL} where the options have been analyzed such
   * that each option contains, along with a move and its result, the list of
   * relevant partial board states that can be found in the result.
   */
  private Choices_FOL output;

  /* **************************************************************************
   * CONSTRUCTOR
   * ************************************************************************* */

  /**
   * @param input
   *          ({@code Choices_FOL})
   */
  public AdvancedAnalysisEngine(Choices_FOL input)
  {
    super();
    this.input = input;
    output = new Choices_FOL();
  }

  /* **************************************************************************
   * GETTERS & SETTERS
   * ************************************************************************* */

  /**
   * @return the input
   */
  public Choices_FOL getInput()
  {
    return input;
  }

  /**
   * @param input
   *          the input to set
   */
  public void setInput(Choices_FOL input)
  {
    this.input = input;
  }

  /**
   * @return the output
   */
  public Choices_FOL getOutput()
  {
    return output;
  }

  /* **************************************************************************
   * METHODS
   * ************************************************************************* */
  /**
   * the method that runs the advanced analyzer
   * 
   * @param rpbsList
   *          the list of {@link RelevantPartialBoardState}s from the active
   *          memory
   * @throws IOException
   */
  public void runEngine(List<RelevantPartialBoardState> rpbsList)
      throws IOException
  {
    KnowledgeBase kb = new KnowledgeBase("RuleBase");

    // can be omitted if clement adds the rule directly to the RuleBase file
    for (RelevantPartialBoardState rpbs : rpbsList)
      kb.addNewRule(rpbs.getRule());
    // till here

    Homomorphisms h;
    Query q;
    for (Option o : output.getOptions())
      {
        kb.setBF(o.getResult().getBoardStateFacts());
        kb.optimizedSaturation_FOL();
        for (RelevantPartialBoardState rpbs : rpbsList)
          {
            q = new Query(rpbs.getRule().getConclusion());
            h = new Homomorphisms(q, kb.getFB());
            if (h.existsHomomorphismTest())
              o.addPartialStates(rpbs);
          }
      }

  }
}
