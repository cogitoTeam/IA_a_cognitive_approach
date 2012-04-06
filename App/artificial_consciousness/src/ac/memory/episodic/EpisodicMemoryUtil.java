/**
 * 
 */
package ac.memory.episodic;

import ac.shared.GameStatus;

/**
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 6 avr. 2012
 * @version 0.1
 */
public class EpisodicMemoryUtil
{
  private EpisodicMemoryUtil()
  {
  }

  /**
   * Method that calculate the weight of a move for a game
   * 
   * @param position
   *          position of the move in the game
   * @param score
   *          score of the game
   * @param game_status
   *          status of the move's related game
   * @return the weight of the move
   */
  public static long DeacreaseMoveFormula(int position, int score,
      GameStatus game_status)
  {
    int coeff_status = 0;
    switch (game_status)
      {
        case VICTORY:
          coeff_status = 1;
          break;
        case DEFEAT:
          coeff_status = -1;
          break;
        default:
          return 0;
      }
    double coeff = 30.1029999;
    long influence = Math.round(coeff / Math.log10(position+1));

    return influence * score * coeff_status;

  }
}
