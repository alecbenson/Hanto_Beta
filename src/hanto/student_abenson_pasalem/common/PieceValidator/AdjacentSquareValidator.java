/**
 * Validator for adjacency checks.
 * 
 */

package hanto.student_abenson_pasalem.common.PieceValidator;


import common.HantoCoordinate;
import common.HantoException;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;
import hanto.student_abenson_pasalem.common.Board.HantoBoardImpl;

/**
 * Validator for adjacent squares after a piece has moved.
 * @author Peter
 *
 */
public class AdjacentSquareValidator implements IPieceValidator{
	int maxDistance;
	
	public AdjacentSquareValidator(int maxDistance){
		this.maxDistance = maxDistance;
	}

	/**
	 * returns true if the space is adjacent to another space
	 * @param from the space the piece is moving from
	 * @param to the space the piece is moving to
	 * @throws HantoException 
	 */
	public boolean canMove(HantoBoardImpl board, HantoCoordinate from, HantoCoordinate to) {
		if(from == null || to == null){
			return false;
		}
		try {
			int dist = new HantoCoordinateImpl(from).distance(new HantoCoordinateImpl(to));
			if(dist > maxDistance){
				return false;
			}
			return HantoBoardImpl.isAdjacentTo(to,from);
		} catch (HantoException e) {
			return false;
		}
	}
}
