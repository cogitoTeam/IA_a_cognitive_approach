package ac.analysis;

import java.io.IOException;

import game.BoardMatrix;
import game.ReversiRules;
import ac.AC;
import ac.memory.Neo4jActiveMemory;
import ac.memory.MemoryException;
import ac.reasoning.Reasoning;
import ac.shared.FOLObjects.Option;
import agent.Action;
import agent.Percept.Choices;

/**
 * This class handles the basic and advanced conceptual analysis of the AI using
 * inputs from the game and the memory and providing outputs for the reasoning
 * 
 * @author namratapatel
 * 
 */
public class Analysis
{

  private Neo4jActiveMemory _memory;
  private Reasoning _reasoning;

  /**
   * @param memory
   * @param reasoning
   */
  public Analysis(Neo4jActiveMemory memory, Reasoning reasoning)
  {
    this._memory = memory;
    this._reasoning = reasoning;
  }

  /**
   * This is the input method from environment in case of a choice.
   * 
   * @param percept
   * @return an Action to stimulate the reasoning
   * @throws MemoryException
   * @throws IOException
   */
  public Action analyse(Choices percept) throws MemoryException, IOException
  {
    BasicAnalysisEngine folConversion = new BasicAnalysisEngine(percept);
    folConversion.runEngine();

    AdvancedAnalysisEngine findHomomorphisms = new AdvancedAnalysisEngine(
        folConversion.getOutput());
    findHomomorphisms.runEngine(this._memory.getRelevantPartialBoardStates());

    for (Option o : findHomomorphisms.getOutput().getOptions())
      this._memory.putOption(o);

    return this._reasoning.stimulate();
  }

  
  //just for tests
  /**
   * @param args
   * @throws IOException
   * @throws MemoryException 
   */
  public static void main(String[] args) throws IOException, MemoryException
  {
    BoardMatrix b = ReversiRules.getInstance().createBoard();

    Choices test = new Choices(b);
    test.getCurrentBoard();
    AC new_AC = new AC();
    Analysis test_analysis = new Analysis(new_AC.getMemory(),new_AC.getReasoning());
    test_analysis.analyse(test);
    
    System.out.println(test.getCurrentBoard());
  }
}
