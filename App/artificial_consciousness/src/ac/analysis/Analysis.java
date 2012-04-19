package ac.analysis;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import game.BoardMatrix;
import game.Game;
import game.ReversiRules;
import game.BoardMatrix.Cell;
import game.BoardMatrix.Position;
import game.Game.Player;
import ac.AC;
import ac.analysis.inferenceEngine.Homomorphisms;
import ac.analysis.inferenceEngine.KnowledgeBase;
import ac.analysis.structure.Atom;
import ac.analysis.structure.Query;
import ac.analysis.structure.Rule;
import ac.memory.Neo4jActiveMemory;
import ac.memory.MemoryException;
import ac.reasoning.Reasoning;
import ac.shared.CompleteBoardState;
import ac.shared.RelevantPartialBoardState;
import ac.shared.FOLObjects.Choices_FOL;
import ac.shared.FOLObjects.Option_FOL;
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
   * This is the input method from the environment in case of a choice.
   * 
   * @param input
   * @return an Action to stimulate the reasoning
   * @throws MemoryException
   * @throws IOException
   */
  public Action analyse(Choices input, Game.Player player) throws MemoryException, IOException
  {
    Choices_FOL output = basicAnalysisEngine(input, player);
    
    advancedAnalysisEngine(output, this._memory.getRelevantPartialBoardStates());

    for (Option_FOL o : output.getOptions())
      this._memory.putOption(o);

    return this._reasoning.stimulate();
  }

  /* **************************************************************************
   * METHODS
   * ************************************************************************* */

  /**
   * This method runs the basic conceptual analyzer. It takes choices in the
   * form of matrices from the game and converts them into first order logic
   * formulas
   * 
   * @param input
   *          The matrices representing the choices provided by the game engine
   * @return an instance of {@link Choices_FOL} which represents the same
   *         choices in FOL
   */
  public static Choices_FOL basicAnalysisEngine(Choices input, Game.Player player)
  {
    BoardMatrix board = input.getCurrentBoard();
    CompleteBoardState current_board = convertMatrixtoCBS(board, player);
    Choices_FOL output = new Choices_FOL();

    output.setCurrent_board(current_board);

    CompleteBoardState result;
    Option_FOL tmp;
    for (agent.Action.Option o : input.getOptions())
      {
        result = convertMatrixtoCBS(o.getResult(), player);
        tmp = new Option_FOL(o.getAction(), result);
        output.addOption(tmp);
      }
    return output;
  }

  /**
   * @param matrix
   *          a BoardMatrix
   * @return an instance of a {@link CompleteBoardState} class which represents
   *         the converted BoardMatrix
   */
  private static CompleteBoardState convertMatrixtoCBS(BoardMatrix matrix, Game.Player player)
  {
    BoardMatrix.Position p = new Position(0, 0);
    Cell c;
    String s;
    Atom a;
    CompleteBoardState cbs = new CompleteBoardState();

    // add case type
    for (p.row = 0; p.row < matrix.getSize().n_rows; p.row++)
      for (p.col = 0; p.col < matrix.getSize().n_cols; p.col++)
        {
          c = matrix.getCell(p);
          s = c.toRule(player) + "('c_" + p.row + '_' + p.col + "')";
          a = new Atom(s);
          cbs.getBoardStateFacts().addNewFact(a);
        }

    // Add edges
    p.col = 0;
    p.row = 0;
    for (p.row = 0; p.row < matrix.getSize().n_rows; p.row++)
      {
        s = "isEdge('c_" + p.row + '_' + 0 + "')";
        a = new Atom(s);
        cbs.getBoardStateFacts().addNewFact(a);

        s = "isEdge('c_" + p.row + '_' + (matrix.getSize().n_cols - 1) + "')";
        a = new Atom(s);
        cbs.getBoardStateFacts().addNewFact(a);
      }

    for (p.col = 0; p.col < matrix.getSize().n_cols; p.col++)
      {
        s = "isEdge('c_" + 0 + '_' + p.col + "')";
        a = new Atom(s);
        cbs.getBoardStateFacts().addNewFact(a);

        s = "isEdge('c_" + (matrix.getSize().n_rows - 1) + '_' + p.col + "')";
        a = new Atom(s);
        cbs.getBoardStateFacts().addNewFact(a);
      }

    // Add corners
    s = "isCorner('c_" + 0 + '_' + 0 + "')";
    a = new Atom(s);
    cbs.getBoardStateFacts().addNewFact(a);

    s = "isCorner('c_" + (matrix.getSize().n_rows - 1) + '_' + 0 + "')";
    a = new Atom(s);
    cbs.getBoardStateFacts().addNewFact(a);

    s = "isCorner('c_" + 0 + '_' + (matrix.getSize().n_cols - 1) + "')";
    a = new Atom(s);
    cbs.getBoardStateFacts().addNewFact(a);

    s = "isCorner('c_" + (matrix.getSize().n_rows - 1) + '_'
        + (matrix.getSize().n_cols - 1) + "')";
    a = new Atom(s);
    cbs.getBoardStateFacts().addNewFact(a);

    // Add nears
    for (p.row = 0; p.row < matrix.getSize().n_rows - 1; p.row++)
      for (p.col = 0; p.col < matrix.getSize().n_cols - 1; p.col++)
        {
          s = "near('c_" + p.row + '_' + p.col + "','c_" + (p.row + 1) + '_'
              + p.col + "')";
          a = new Atom(s);
          cbs.getBoardStateFacts().addNewFact(a);

          s = "near('c_" + p.row + '_' + p.col + "','c_" + p.row + '_'
              + (p.col + 1) + "')";
          a = new Atom(s);
          cbs.getBoardStateFacts().addNewFact(a);

          s = "near('c_" + p.row + '_' + p.col + "','c_" + (p.row + 1) + '_'
              + (p.col + 1) + "')";
          a = new Atom(s);
          cbs.getBoardStateFacts().addNewFact(a);

          // near(x,y) --> near(y,x)
          s = "near('c_" + (p.row + 1) + '_' + p.col + "','c_" + p.row + '_'
              + p.col + "')";
          a = new Atom(s);
          cbs.getBoardStateFacts().addNewFact(a);

          s = "near('c_" + p.row + '_' + (p.col + 1) + "','c_" + p.row + '_'
              + p.col + "')";
          a = new Atom(s);
          cbs.getBoardStateFacts().addNewFact(a);

          s = "near('c_" + (p.row + 1) + '_' + (p.col + 1) + "','c_" + p.row
              + '_' + p.col + "')";
          a = new Atom(s);
          cbs.getBoardStateFacts().addNewFact(a);
        }

    // Add aligns
    int shift_p2 = 0;
    int shift_p3 = 0;
    for (p.row = 0; p.row < matrix.getSize().n_rows; p.row++)
      for (p.col = 0; p.col < matrix.getSize().n_cols - 2; p.col++)
        {
          for (shift_p2 = 1; shift_p2 + p.col < matrix.getSize().n_cols - 1; ++shift_p2)
            for (shift_p3 = shift_p2 + 1; shift_p3 + p.col < matrix.getSize().n_rows; ++shift_p3)
              {
                s = "aligned('c_" + p.row + '_' + p.col + "','c_" + p.row + '_'
                    + (p.col + shift_p2) + "','c_" + p.row + '_'
                    + (p.col + shift_p3) + "')";
                a = new Atom(s);
                cbs.getBoardStateFacts().addNewFact(a);

                // aligned(x,y,z) --> aligned(z,y,x)
                s = "aligned('c_" + p.row + '_' + (p.col + shift_p3) + "','c_"
                    + p.row + '_' + (p.col + shift_p2) + "','c_" + p.row + '_'
                    + p.col + "')";
                a = new Atom(s);
                cbs.getBoardStateFacts().addNewFact(a);
              }
        }

    for (p.row = 0; p.row < matrix.getSize().n_rows - 2; p.row++)
      for (p.col = 0; p.col < matrix.getSize().n_cols; p.col++)
        {
          for (shift_p2 = 1; shift_p2 + p.row < matrix.getSize().n_rows - 1; ++shift_p2)
            for (shift_p3 = shift_p2 + 1; shift_p3 + p.row < matrix.getSize().n_rows; ++shift_p3)
              {
                s = "aligned('c_" + p.row + '_' + p.col + "','c_"
                    + (p.row + shift_p2) + '_' + p.col + "','c_"
                    + (p.row + shift_p3) + '_' + p.col + "')";
                a = new Atom(s);
                cbs.getBoardStateFacts().addNewFact(a);

                // aligned(x,y,z) --> aligned(z,y,x)
                s = "aligned('c_" + (p.row + shift_p3) + '_' + p.col + "','c_"
                    + (p.row + shift_p2) + '_' + p.col + "','c_" + p.row + '_'
                    + p.col + "')";
                a = new Atom(s);
                cbs.getBoardStateFacts().addNewFact(a);
              }
        }

    for (p.row = 0; p.row < matrix.getSize().n_rows - 2; p.row++)
      for (p.col = 0; p.col < matrix.getSize().n_cols - 2; p.col++)
        {
          for (shift_p2 = 1; shift_p2 + p.row < matrix.getSize().n_rows - 1; ++shift_p2)
            for (shift_p3 = shift_p2 + 1; shift_p3 + p.row < matrix.getSize().n_rows; ++shift_p3)
              {
                s = "aligned('c_" + p.row + '_' + p.col + "','c_"
                    + (p.row + shift_p2) + '_' + (p.col + shift_p2) + "','c_"
                    + (p.row + shift_p3) + '_' + (p.col + shift_p3) + "')";
                a = new Atom(s);
                cbs.getBoardStateFacts().addNewFact(a);

                // aligned(x,y,z) --> aligned(z,y,x)
                s = "aligned('c_" + (p.row + shift_p3) + '_'
                    + (p.col + shift_p3) + "','c_" + (p.row + shift_p2) + '_'
                    + (p.col + shift_p2) + "','c_" + p.row + '_' + p.col + "')";
                a = new Atom(s);
                cbs.getBoardStateFacts().addNewFact(a);
              }
        }

    return cbs;

  }

  /**
   * This method represents the Advanced Conceptual Analyzer : it uses an
   * inference
   * engine to analyze complete board states in order to find relevant
   * structures
   * within them. These then become relevant partial board states which are
   * passed
   * on to the memory.
   * 
   * @param rpbsList
   *          the list of {@link RelevantPartialBoardState}s from the active
   *          memory
   * @throws IOException
   */
  public static void advancedAnalysisEngine(Choices_FOL input,
      List<RelevantPartialBoardState> rpbsList) throws IOException
  {
    KnowledgeBase kb = new KnowledgeBase("RuleBase");

    // can be omitted if clement adds the rule directly to the RuleBase file
    int cpt = -1;
    Rule r;
    for (RelevantPartialBoardState rpbs : rpbsList)
      {
        r = rpbs.getRule();
        r.setName("R" + ++cpt);
        kb.addNewRule(r);
      }
    // till here

    Homomorphisms h;
    Query q;
    for (Option_FOL o : input.getOptions())
      {
        kb.setBF(o.getResult().getBoardStateFacts());
        LinkedList<Long> list_rpbs = kb.optimizedSaturation_FOL();
        
        for(Long id_rpbs : list_rpbs)
          {
            o.addPartialStates(id_rpbs);
          }
        
        /*for (RelevantPartialBoardState rpbs : rpbsList)
          {
            q = new Query(rpbs.getRule().getConclusion());
            h = new Homomorphisms(q, kb.getFB());
            if (h.existsHomomorphismTest())
          }*/
      }
  }

  // just for tests
  /**
   * @param args
   * @throws IOException
   * @throws MemoryException
   */
  public static void main(String[] args) throws IOException, MemoryException
  {
    BoardMatrix b = ReversiRules.getInstance().createBoard();
    System.out.println(convertMatrixtoCBS(b, Player.WHITE));
    
   /* Choices test = new Choices(b);
    test.getCurrentBoard();
    AC new_AC = new AC();
    Analysis test_analysis = new Analysis(new_AC.getMemory(),
        new_AC.getReasoning());
    test_analysis.analyse(test);
    
    System.out.println(test.getCurrentBoard());*/
  }
}
