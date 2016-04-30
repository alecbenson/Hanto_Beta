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
import hanto.common.HantoPlayerColor;
import hanto.student_abenson_pasalem.common.BaseHantoGame;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;

/**
 * Validator for piece adjacency.
 */
public class PieceAdjacentValidator implements IRuleValidator{

	@Override
	public void validate(BaseHantoGame game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		
		//This rule does not apply for the first turn
		
		if(game.getRedTurns() == 0 && game.getBlueTurns() == 0){
			return;
		}
		
		List<HantoCoordinateImpl> adjacentSpaces = new HantoCoordinateImpl(to).getAdjacentSpaces();
		for (HantoCoordinate space : adjacentSpaces) {
			//If moving, don't consider the space we are coming from
			if(from != null){
				if(space.getX() == from.getX() && space.getY() == from.getY()){
					continue;
				}
			}
			//If the piece is adjacent to any other pieces, move on
			HantoPiece piece = game.getBoard().getPieceAt(space);
			if (piece != null) {
				return;
			}
		}
		throw new HantoException("The piece is not adjacent to any other piece");
	}

}
