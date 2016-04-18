package hanto.student_abenson_pasalem.common.RuleValidator;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPiece;
import common.HantoPieceType;

public class PlayerMovesOwnPieceValidator implements IRuleValidator{

	@Override
	public void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		
		HantoPiece movingPiece = game.getBoard().getPieceAt(from);	
		if(movingPiece == null){
			throw new HantoException("Tried to move a piece that did not exist");
		}
		if(movingPiece.getColor() != game.getCurrentPlayer()){
			throw new HantoException("Cannot move a piece that is not yours");
		}
	}
}
