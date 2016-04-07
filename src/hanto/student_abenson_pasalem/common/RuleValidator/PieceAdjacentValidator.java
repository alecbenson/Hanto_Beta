package hanto.student_abenson_pasalem.common.RuleValidator;

import java.util.List;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPiece;
import common.HantoPieceType;
import hanto.student_abenson_pasalem.common.Board.HantoBoardImpl;

public class PieceAdjacentValidator implements IRuleValidator{

	@Override
	public void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		
		//This rule does not apply for the first turn
		if(game.getRedTurns() == 0 && game.getBlueTurns() == 0){
			return;
		}
		
		List<HantoCoordinate> adjacentSpaces = HantoBoardImpl.getAdjacentSpaces(to);
		for (HantoCoordinate space : adjacentSpaces) {
			HantoPiece piece = game.getBoard().getPieceAt(space);
			if (piece != null) {
				return;
			}
		}
		throw new HantoException("The piece is not adjacent to any other piece");
	}

}
