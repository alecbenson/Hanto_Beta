/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.PieceFactory;

import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.student_abenson_pasalem.PieceValidator.ContiguousMovementValidator;
import hanto.student_abenson_pasalem.PieceValidator.IPieceValidator;
import hanto.student_abenson_pasalem.common.HantoPieceImpl;

/**
 * Implementation of the hanto piece builder.
 */
public class HantoPieceBuilder{
	private IPieceValidator[] butterflyValidators, sparrowValidators, crabValidators, horseValidators;
	
	/**
	 * Factory method for creating different types of hanto pieces
	 * @param color
	 * @param type
	 * @return the HantoPieceImpl game piece created by the factory
	 * @throws HantoException
	 */
	public HantoPieceImpl getResult(HantoPlayerColor color, HantoPieceType type) throws HantoException{
		
		IPieceValidator contiguousMoveValidator = new ContiguousMovementValidator();
		HantoPieceImpl piece = new HantoPieceImpl(color, type, contiguousMoveValidator);
		
		switch(type){
		case BUTTERFLY:
			piece.addValidators(butterflyValidators);
			return piece;
		case SPARROW:
			piece.addValidators(sparrowValidators);
			return piece;
		case CRAB:
			piece.addValidators(crabValidators);
			return piece;
		case HORSE:
			piece.addValidators(horseValidators);
			return piece;
		default:
			throw new HantoException("Move validators not defined for this type of piece");
		}
	}
	
	/**
	 * Sets the validators for making new butterflies
	 * @param validators
	 */
	public void setButterflyValidators(IPieceValidator... validators){
		butterflyValidators = validators;
	}
	
	/**
	 * Sets the validators for making new sparrows
	 * @param validators
	 */
	public void setSparrowValidators(IPieceValidator... validators){
		sparrowValidators = validators;
	}
	
	/**
	 * Sets the validators for making new crabs
	 * @param validators
	 */
	public void setCrabValidators(IPieceValidator... validators){
		crabValidators = validators;
	}
	
	/**
	 * Sets the validators for making new horses
	 * @param validators
	 */
	public void setHorseValidators(IPieceValidator... validators){
		horseValidators = validators;
	}
}
