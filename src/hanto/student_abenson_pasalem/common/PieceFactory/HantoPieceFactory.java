/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.common.PieceFactory;

import common.HantoException;
import common.HantoPieceType;
import common.HantoPlayerColor;
import hanto.student_abenson_pasalem.common.HantoPieceImpl;
import hanto.student_abenson_pasalem.common.PieceValidator.ContiguousMovementValidator;
import hanto.student_abenson_pasalem.common.PieceValidator.FlyValidator;
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
	 * @return the HantoPieceImpl game piece created by the factory
	 * @throws HantoException
	 */
	public static HantoPieceImpl createPiece(HantoPlayerColor color, HantoPieceType type) throws HantoException{
		IPieceValidator contiguousMoveValidator = new ContiguousMovementValidator();
		
		switch(type){
		case BUTTERFLY:
			IPieceValidator butterflyValidator = new WalkValidator(1);
			return new HantoPieceImpl(color, type, contiguousMoveValidator, butterflyValidator);
		case SPARROW:
			IPieceValidator sparrowValidator = new FlyValidator(Integer.MAX_VALUE);
			return new HantoPieceImpl(color, type, contiguousMoveValidator, sparrowValidator);
		case CRAB:
			IPieceValidator crabValidator = new WalkValidator(3);
			return new HantoPieceImpl(color, type, contiguousMoveValidator, crabValidator);
		default:
			throw new HantoException("Move validators not defined for this type of piece");
		}
	}

}
