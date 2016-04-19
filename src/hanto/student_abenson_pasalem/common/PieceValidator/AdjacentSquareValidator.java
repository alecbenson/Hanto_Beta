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
	 * @param from the space the piece is moving from
	 * @param to the space the piece is moving to
	 * @throws HantoException 
	 */
	public void validate(HantoBoardImpl board, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		if(from == null || to == null){
			throw new HantoException("Can't move with null to/from parameter");
		}
		if(!HantoBoardImpl.isAdjacentTo(to,from)){
			throw new HantoException("Cannot move to adjacent hex: provided hexes are not adjacent");
		};
	}
}
