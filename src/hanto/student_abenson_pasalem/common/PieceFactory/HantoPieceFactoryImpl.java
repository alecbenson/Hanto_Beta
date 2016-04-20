/**
 * Implementation of a Hanto piece factory,
 * to handle all of the functionality for pieces.
 */

package hanto.student_abenson_pasalem.common.PieceFactory;

import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.student_abenson_pasalem.common.HantoPieceImpl;
import hanto.student_abenson_pasalem.common.PieceValidator.AdjacentSquareValidator;
import hanto.student_abenson_pasalem.common.PieceValidator.IPieceValidator;

/**
 * Implementation of the hanto piece factory.
 */
public class HantoPieceFactoryImpl implements IHantoPieceFactory{

	/**
	 * Factory method for creating different types of hanto pieces
	 * @param color
	 * @param type
	 */
	public HantoPieceImpl createPiece(HantoPlayerColor color, HantoPieceType type) throws HantoException{
		IPieceValidator adjacentValidator = new AdjacentSquareValidator();
		
		switch(type){
		case BUTTERFLY:
			return new HantoPieceImpl(color, type, adjacentValidator);
		case SPARROW:
			return new HantoPieceImpl(color, type, adjacentValidator);
		default:
			throw new HantoException("Move validators not defined for this type of piece");
		}
	}

}
