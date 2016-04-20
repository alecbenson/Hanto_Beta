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
 * Validator for moving to an occupied space.
 * @author Alec
 */
public class SpaceOccupiedValidator implements IRuleValidator{

	@Override
	public void validate(BaseHantoGame game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		if(game.getBoard().spaceOccupied(to)){
			throw new HantoException("Piece cannot go here: space already occupied");
		}
	}

}
