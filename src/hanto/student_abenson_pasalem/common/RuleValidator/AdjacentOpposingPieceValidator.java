/**
 * Validator for not placing a piece adjacent 
 * to an opponent's piece.
 */


package hanto.student_abenson_pasalem.common.RuleValidator;

import java.util.List;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPiece;
import common.HantoPieceType;
import hanto.student_abenson_pasalem.common.Board.HantoBoardImpl;

/**
 * Validator for not placing a piece next to an opposing piece.
 * @author Peter
 *
 */
public class AdjacentOpposingPieceValidator implements IRuleValidator{

	@Override
	public void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		
		//Does not apply for first turns
		if(game.getRedTurns() == 0 || game.getBlueTurns() == 0){
			return;
		}
		
		List<HantoCoordinate> adjacentSpaces = HantoBoardImpl.getAdjacentSpaces(to);
		for (HantoCoordinate space : adjacentSpaces) {
			HantoPiece piece = game.getBoard().getPieceAt(space);
			if (piece != null) {
				if(piece.getColor() != game.getCurrentPlayer()){
					throw new HantoException("You cannot place your piece next to an opposing piece");
				}
			}
		}
	}
}
