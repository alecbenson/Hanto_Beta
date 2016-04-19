/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.common.RuleValidator;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPieceType;
import hanto.student_abenson_pasalem.common.BaseHantoGame;

/**
 * Interface for validator.
 * @author Peter
 *
 */
public interface IRuleValidator {
	
	/**
	 * Rule validation.
	 * @param game
	 * @param pieceType
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	void validate(BaseHantoGame game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException;
}
