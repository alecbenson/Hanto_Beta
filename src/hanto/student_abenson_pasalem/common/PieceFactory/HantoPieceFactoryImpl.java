package hanto.student_abenson_pasalem.common.PieceFactory;

import common.HantoException;
import common.HantoPieceType;
import common.HantoPlayerColor;
import hanto.student_abenson_pasalem.common.HantoPieceImpl;
import hanto.student_abenson_pasalem.common.MoveValidator.AdjacentSquareValidator;
import hanto.student_abenson_pasalem.common.MoveValidator.IMoveValidator;

public class HantoPieceFactoryImpl implements IHantoPieceFactory{

	/**
	 * Factory method for creating different types of hanto pieces
	 * @param color
	 * @param type
	 */
	public HantoPieceImpl createPiece(HantoPlayerColor color, HantoPieceType type) throws HantoException{
		IMoveValidator adjacentValidator = new AdjacentSquareValidator();
		
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
