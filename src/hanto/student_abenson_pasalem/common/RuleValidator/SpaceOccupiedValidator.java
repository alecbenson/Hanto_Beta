/**
 * Validator for moving to an occupied space.
 */

package hanto.student_abenson_pasalem.common.RuleValidator;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;

/**
 * Validator for if a space is occupied.
 * @author Peter
 *
 */
public class SpaceOccupiedValidator implements IRuleValidator{

	@Override
	public void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		if(game.getBoard().spaceOccupied(to)){
			throw new HantoException("Piece cannot go here: space already occupied");
		}
	}

}
