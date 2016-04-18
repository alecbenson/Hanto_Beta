/**
 * Validator for each player needing to play
 * their butterfly by their turn 4.
 */

package hanto.student_abenson_pasalem.common.RuleValidator;

import static common.HantoPlayerColor.RED;
import static common.HantoPieceType.BUTTERFLY;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPieceType;

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
		if (!game.getCurrentPlayerPlayedButterfly()){
			if(game.getCurrentPlayerTurns() >= 3){
				throw new HantoException(game.getCurrentPlayer() + "  must play the butterfly!");
			}
		}
	}
}
