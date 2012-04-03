package ac.shared;

/**
 * Status at the end of a game
 * 
 * @author Thibaut Marmin <marminthibaut@gmail.com>
 * @date 3 avr. 2012
 * @version 0.1
 */
public enum GameEndStatus
{

  /**
   * When you won the game
   */
  VICTORY,
  /**
   * When you lost the game
   */
  DEFEAT,
  /**
   * For a draw game
   */
  DRAW,
  /**
   * When the game did not finished
   */
  INTERRUPTED,
  /**
   * Undefined game type is used when the game is in progress.
   */
  UNDEFINED,
  /**
   * Unrecognized game status is mainly used by memory, when a status game
   * stored in database can't be convert to this enum
   */
  UNRECOGNIZED;

}
