/**
 * Validator for adjacency checks.
 * 
 */

package hanto.student_abenson_pasalem.common.PieceValidator;


import common.HantoCoordinate;
import hanto.student_abenson_pasalem.common.Board.HantoBoardImpl;

/**
 * Validator for adjacent squares after a piece has moved.
 * @author Peter
 *
 */
public class AdjacentSquareValidator implements IPieceValidator{

	/**
	 * returns true if the space is adjacent to another space
	 * @param from the space the piece is moving from
	 * @param to the space the piece is moving to
	 */
	public boolean canMove(HantoCoordinate from, HantoCoordinate to) {
		if(from == null || to == null){
			return false;
		}
		return HantoBoardImpl.isAdjacentTo(to,from);
	}
}
