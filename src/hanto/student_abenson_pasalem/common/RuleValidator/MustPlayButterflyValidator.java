/**
 * Validator for each player needing to play
 * their butterfly by their turn 4.
 */

package hanto.student_abenson_pasalem.common.RuleValidator;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.RED;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;

/**
 * Validator for needing to played the butterfly.
 * @author Peter
 *
 */
public class MustPlayButterflyValidator implements IRuleValidator{

	/**
	 * Ensures that players play the butterfly by turn 4
	 */
	public void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		if (game.getCurrentPlayer() == RED) {
			if (game.getRedTurns() >= 3 && !game.getRedPlayedButterfly()) {
				if(pieceType != BUTTERFLY){
					throw new HantoException("Red must play the butterfly!");
				}
			}
		} else {
			if (game.getBlueTurns() >= 3 && !game.getBluePlayedButterfly()) {
				if(pieceType != BUTTERFLY){
					throw new HantoException("Blue must play the butterfly!");
				}
			}
		}		
	}
}
