package ac.frontier;

/**
 * Classe ReversiMatrix
 * 
 * @author wilbefast
 * @date 26 mars 2012
 * @version 0.1
 */

public class ReversiMatrix extends BoardMatrix {
    /** METHODS **/

    public ReversiMatrix() {
        // Reversi game uses an 8 by 8 board
        super(new Size(8, 8));
    }
}
