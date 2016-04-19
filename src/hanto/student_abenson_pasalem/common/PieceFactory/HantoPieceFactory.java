/**
 * Implementation of a Hanto piece factory,
 * to handle all of the functionality for pieces.
 */

package hanto.student_abenson_pasalem.common.PieceFactory;

import common.HantoException;
import common.HantoPieceType;
import common.HantoPlayerColor;
import hanto.student_abenson_pasalem.common.HantoPieceImpl;
import hanto.student_abenson_pasalem.common.PieceValidator.AdjacentSquareValidator;
import hanto.student_abenson_pasalem.common.PieceValidator.ContiguousMovementValidator;
import hanto.student_abenson_pasalem.common.PieceValidator.IPieceValidator;
import hanto.student_abenson_pasalem.common.PieceValidator.WalkValidator;

/**
 * Implementation of the hanto piece factory.
 */
public class HantoPieceFactory{

	/**
	 * Factory method for creating different types of hanto pieces
	 * @param color
	 * @param type
	 */
	public static HantoPieceImpl createPiece(HantoPlayerColor color, HantoPieceType type) throws HantoException{
		IPieceValidator contiguousMoveValidator = new ContiguousMovementValidator();
		
		switch(type){
		case BUTTERFLY:
			IPieceValidator butterflyValidator = new WalkValidator(1);
			return new HantoPieceImpl(color, type, contiguousMoveValidator, butterflyValidator);
		case SPARROW:
			IPieceValidator sparrowValidator = new WalkValidator(1);
			return new HantoPieceImpl(color, type, contiguousMoveValidator, sparrowValidator);
		case CRAB:
			IPieceValidator crabValidator = new WalkValidator(3);
			return new HantoPieceImpl(color, type, contiguousMoveValidator, crabValidator);
		default:
			throw new HantoException("Move validators not defined for this type of piece");
		}
	}

}
