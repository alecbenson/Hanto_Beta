/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.RuleValidator;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.student_abenson_pasalem.BaseHantoGame;

/**
 * Validator for game over condition.
 * @author Peter
 *
 */
public class IsGameOverValidator implements IRuleValidator{

	/**
	 * Throws a HantoException if the game is already over
	 */
	public void validate(BaseHantoGame game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		if(game.getIsGameOver()){
			throw new HantoException("The game is over already");
		}
	}

}
