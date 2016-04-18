package hanto.student_abenson_pasalem.common.RuleValidator;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPiece;
import common.HantoPieceType;

public class MovePieceTypeMatchesValidator implements IRuleValidator{
	@Override
	public void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		HantoPiece piece = game.getBoard().getPieceAt(from);
		if(piece == null || (piece.getType() != pieceType)){
			throw new HantoException("The piece being moved does not match the piece type provided");
		}
	}
}
