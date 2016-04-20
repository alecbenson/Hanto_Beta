/**
 * Interface for Piece Validation.
 * 
 */

package hanto.student_abenson_pasalem.common.PieceValidator;

import hanto.common.HantoCoordinate;


/**
 * Interface for piece validation
 * @author Peter
 *
 */
public interface IPieceValidator {
	
	/**
	 * boolean for whether or not a piece can move.
	 * @param from
	 * @param to
	 * @return true if the piece can move
	 */
	boolean canMove(HantoCoordinate from, HantoCoordinate to);
}
