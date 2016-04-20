/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.common.RuleValidator;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPieceType;
import hanto.student_abenson_pasalem.common.BaseHantoGame;
import hanto.student_abenson_pasalem.common.PlayerState.HantoPlayerState;

/**
 * Ensures that a player does not move until their butterfly is down
 * @author Alec
 *
 */
public class MovementBeforeButterflyValidator implements IRuleValidator{

	@Override
	public void validate(BaseHantoGame game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		HantoPlayerState currentState = game.getCurrentPlayerState();
		if(!currentState.getHasPlayedButterfly()){
			if(from != null){
				throw new HantoException("You cannot move until the butterfly has been placed");
			}
		}
	}

}
