/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.common.RuleValidator;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPieceType;

/**
 * Ensures that when a piece is being moved, that the piece actually exists on the specified "from" coordinate
 */
public class MovingPieceExistsValidator  implements IRuleValidator{

	@Override
	public void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		if(game.getBoard().getPieceAt(from) == null){
			throw new HantoException("There is no piece at the space you are trying to move from");
		}
	}
	

}
