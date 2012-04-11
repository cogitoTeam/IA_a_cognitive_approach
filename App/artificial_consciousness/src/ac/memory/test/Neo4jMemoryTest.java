/**
 * 
 */
package ac.memory.test;

import game.BoardMatrix.Position;

import java.util.List;

import ac.memory.MemoryException;
import ac.memory.Neo4jActiveMemory;
import ac.memory.episodic.Neo4jEpisodicMemory;
import ac.memory.persistence.neo4j.AttributeNodeRepository;
import ac.memory.persistence.neo4j.Neo4jService;
import ac.memory.persistence.neo4j.ObjectNodeRepository;
import ac.memory.semantic.Neo4jSemanticMemory;
import ac.shared.CompleteBoardState;
import ac.shared.GameStatus;
import ac.shared.RelevantPartialBoardState;
import ac.shared.FOLObjects.Option;
import ac.util.Pair;
import agent.Action;
import agent.Action.Type;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 11 avr. 2012
 * @version 0.1
 */
public class Neo4jMemoryTest
{

  static Neo4jActiveMemory memory = new Neo4jActiveMemory(
      new Neo4jEpisodicMemory(), new Neo4jSemanticMemory());

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    boolean add = true;
    boolean init = false;

    if (add)
      {
        System.out
            .println("/////////////////// Testing Memory ///////////////////////");
        System.out.println("Init memory");
        if (init)
          init();

        playGame(GameStatus.DEFEAT, 10);
        playGame(GameStatus.DEFEAT, 20);
        playGame(GameStatus.VICTORY, 10);
        playGame(GameStatus.DRAW, 0);
        playGame(GameStatus.DEFEAT, 10);
        playGame(GameStatus.DEFEAT, 20);
        playGame(GameStatus.VICTORY, 10);
        playGame(GameStatus.DRAW, 0);
        playGame(GameStatus.DEFEAT, 10);
        playGame(GameStatus.DEFEAT, 20);
        playGame(GameStatus.VICTORY, 10);
        playGame(GameStatus.DRAW, 0);
        playGame(GameStatus.DEFEAT, 10);
        playGame(GameStatus.DEFEAT, 20);
        playGame(GameStatus.VICTORY, 10);
        playGame(GameStatus.DRAW, 0);
        playGame(GameStatus.DEFEAT, 10);
        playGame(GameStatus.DEFEAT, 20);
        playGame(GameStatus.VICTORY, 10);
        playGame(GameStatus.DRAW, 0);
        playGame(GameStatus.DEFEAT, 10);
        playGame(GameStatus.DEFEAT, 20);
        playGame(GameStatus.VICTORY, 10);
        playGame(GameStatus.DRAW, 0);
        playGame(GameStatus.DEFEAT, 10);
        playGame(GameStatus.DEFEAT, 20);
        playGame(GameStatus.VICTORY, 10);
        playGame(GameStatus.DRAW, 0);
        playGame(GameStatus.DEFEAT, 10);
        playGame(GameStatus.DEFEAT, 20);
        playGame(GameStatus.VICTORY, 10);
        playGame(GameStatus.DRAW, 0);
        playGame(GameStatus.DEFEAT, 10);
        playGame(GameStatus.DEFEAT, 20);
        playGame(GameStatus.VICTORY, 10);
        playGame(GameStatus.DRAW, 0);
        playGame(GameStatus.DEFEAT, 10);
        playGame(GameStatus.DEFEAT, 20);
        playGame(GameStatus.VICTORY, 10);
        playGame(GameStatus.DRAW, 0);

        System.out.println("PRINTING MEMORY");
      }
    System.out.println(memory);
  }

  private static void init()
  {
    ObjectNodeRepository obj_repo = new ObjectNodeRepository(
        Neo4jService.getInstance(), Neo4jService.getObjIndex(),
        Neo4jService.getObjMarkIndex());
    AttributeNodeRepository att_repo = new AttributeNodeRepository(
        Neo4jService.getInstance(), Neo4jService.getAttrIndex(),
        Neo4jService.getAttrMarkIndex());

    for (int i = 0; i < 10; ++i)
      {
        try
          {
            obj_repo.createNode(new CompleteBoardState(i));
          }
        catch (Exception e)
          {
            System.err.println("Error: " + e.getMessage());
          }
      }

    for (int i = 0; i < 10; ++i)
      {
        try
          {
            long id = att_repo.getFreeId();
            att_repo.createNode(new RelevantPartialBoardState(id));
            att_repo.getNodeById(id).addRelatedObject(obj_repo.getNodeById(i));

          }
        catch (Exception e)
          {
            System.err.println("Error: " + e.getMessage());
          }
      }
  }

  private static void playGame(GameStatus status, int score)
  {
    System.out.println("==> PLAYING NEW GAME");
    int nb_moves = (int) Math.round((Math.random() * (double) 10) + 20);
    System.out.println(" 1   Playing new game with " + nb_moves + " moves");

    boolean first = true;

    // Chaque move
    for (int i = 0; i < nb_moves; ++i)
      {
        System.out.println("  >> New Move");

        // ANALYSIS PUT LES OPTIONS
        System.out.println("     Analysis putting options");
        try
          {
            List<RelevantPartialBoardState> list = memory
                .getRelevantPartialBoardStates();

            // On put des options
            for (int j = 0; j < 10; ++j)
              {
                Option option = new Option(new Action.Move(new Position(0, 0)),
                    new CompleteBoardState(Math.round(Math.random()
                        * (double) 40.0)));
                memory.putOption(option);
              }

          }
        catch (MemoryException e)
          {
            System.err.println("Error: " + e.getMessage());
          }

        // REASONING BEGIN NEW GAME
        System.out.println("      Reasoning begin new game");
        if (first)
          try
            {
              memory.BeginOfGame();
            }
          catch (MemoryException e)
            {
              System.err.println("Error: " + e.getMessage());
            }

        // REASONING MAKE A CHOICE
        System.out.println("      Reasoning make a choice");
        try
          {
            List<Pair<Option, Double>> list = memory.getGradedOptions();
            int nb = list.size();
            nb = (int) Math.round(Math.random() * (double) (nb - 1));
            memory.OptionChosen(list.get(nb).first);
          }
        catch (MemoryException e)
          {
            System.err.println("Error: " + e.getMessage());
          }
        first = false;
        System.out.println("");
      }

    // REASONING END OF GAME
    System.out.println("  >> Reasoning finishes game");
    try
      {
        memory.EndOfGame(status, score);
      }
    catch (MemoryException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

  }
}
