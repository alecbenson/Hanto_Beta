package hanto.student_abenson_pasalem.common.RuleValidator;

import static common.HantoPieceType.BUTTERFLY;
import static common.HantoPlayerColor.RED;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPieceType;

public class AlreadyPlayedButterflyValidator implements IRuleValidator {

	/**
	 * Ensures the butterfly has not already been played
	 */
	public void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		
		if (game.getCurrentPlayer() == RED) {
			if (game.getRedPlayedButterfly() && pieceType == BUTTERFLY) {
				throw new HantoException("RED has already played the butterfly");
			}
		} else {
			if (game.getBluePlayedButterfly() && pieceType == BUTTERFLY) {
				throw new HantoException("BLUE has already played the butterfly");
			}
		}
	}
}