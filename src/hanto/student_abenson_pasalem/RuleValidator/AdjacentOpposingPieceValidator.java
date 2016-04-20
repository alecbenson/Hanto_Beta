/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.RuleValidator;

import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.student_abenson_pasalem.common.BaseHantoGame;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;

/**
 * Validator for not placing a piece next to an opposing piece.
 * @author Peter
 *
 */
public class AdjacentOpposingPieceValidator implements IRuleValidator{

	@Override
	public void validate(BaseHantoGame game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		
		//Does not apply for first turns
		if(game.getRedTurns() == 0 || game.getBlueTurns() == 0){
			return;
		}
		
		List<HantoCoordinateImpl> adjacentSpaces = new HantoCoordinateImpl(to).getAdjacentSpaces();
		for (HantoCoordinate space : adjacentSpaces) {
			HantoPiece piece = game.getBoard().getPieceAt(space);
			if (piece != null) {
				if(piece.getColor() != game.getCurrentPlayer()){
					throw new HantoException("Cannot place a piece next to an opposing piece");
				}
			}
		}
	}
}
