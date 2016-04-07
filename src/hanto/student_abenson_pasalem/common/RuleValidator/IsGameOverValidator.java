package hanto.student_abenson_pasalem.common.RuleValidator;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPieceType;

public class IsGameOverValidator implements IRuleValidator{

	/**
	 * Throws a HantoException if the game is already over
	 */
	public void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		if(game.getIsGameOver()){
			throw new HantoException("The game is over already");
		}
	}

}
