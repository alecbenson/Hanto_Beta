package hanto.student_abenson_pasalem.common.RuleValidator;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPieceType;

public class MovingPieceExistsValidator  implements IRuleValidator{

	@Override
	public void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		if(game.getBoard().getPieceAt(from) == null){
			throw new HantoException("There is no piece at the space you are trying to move from");
		}
	}
	

}
