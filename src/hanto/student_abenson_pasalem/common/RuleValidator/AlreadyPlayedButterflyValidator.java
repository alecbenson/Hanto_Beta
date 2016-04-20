/**
 * Validator for checking if a butterfly has already
 * been played by the player attempting to play it.
 * 
 */


package hanto.student_abenson_pasalem.common.RuleValidator;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.RED;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;

/**
 * Validator for if the butterfly has already been played.
 * @author Peter
 *
 */
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
