/**
 * 
 * Interface for the rule validation.
 */

package hanto.student_abenson_pasalem.common.RuleValidator;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;

/**
 * Interface for validator.
 * @author Peter
 *
 */
public interface IRuleValidator {
	
	/**
	 * Rule validation.
	 * @param game
	 * @param pieceType
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException;
}
