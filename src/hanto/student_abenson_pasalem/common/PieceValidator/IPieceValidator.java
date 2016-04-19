/**
 * Interface for Piece Validation.
 * 
 */

package hanto.student_abenson_pasalem.common.PieceValidator;

import common.HantoCoordinate;
import common.HantoException;
import hanto.student_abenson_pasalem.common.Board.HantoBoardImpl;


/**
 * Interface for piece validation
 * @author Peter
 *
 */
public interface IPieceValidator {
	
	/**
	 * boolean for whether or not a piece can move.
	 * @param from place moving from
	 * @param to place moving to
	 * @param board the gaem board
	 * @throws HantoException
	 */
	void validate(HantoBoardImpl board, HantoCoordinate from, HantoCoordinate to) throws HantoException;
}
