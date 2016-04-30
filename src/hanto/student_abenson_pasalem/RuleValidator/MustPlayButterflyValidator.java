/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.RuleValidator;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.student_abenson_pasalem.common.BaseHantoGame;

/**
 * Validator for needing to played the butterfly.
 * @author Peter
 */
public class MustPlayButterflyValidator implements IRuleValidator{

	/**
	 * Ensures that players play the butterfly by turn 4
	 */
	public void validate(BaseHantoGame game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		if (!game.getCurrentPlayerPlayedButterfly() && pieceType != HantoPieceType.BUTTERFLY){
			if(game.getCurrentPlayerTurns() >= 3){
				throw new HantoException(game.getCurrentPlayer() + "  must play the butterfly!");
			}
		}
	}
}
