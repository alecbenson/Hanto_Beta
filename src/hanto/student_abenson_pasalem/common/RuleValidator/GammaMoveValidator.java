/**
 * 
 * Master validator for all move validators called in Gamma Hanto.
 */

package hanto.student_abenson_pasalem.common.RuleValidator;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;

/**
 * Validator for moves in Gamma Hanto.
 * @author Peter
 *
 */
public class GammaMoveValidator implements IRuleValidator{

	/**
	 * Runs all validators for Gamma hanto when a piece is being moved
	 */
	public void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		List<IRuleValidator> validators = new ArrayList<IRuleValidator>();
		
		IRuleValidator gameoverValidator = new IsGameOverValidator();
		validators.add(gameoverValidator);
		IRuleValidator adjacentValidator = new PieceAdjacentValidator();
		validators.add(adjacentValidator);
		IRuleValidator butterflyValidator = new MustPlayButterflyValidator();
		validators.add(butterflyValidator);
		IRuleValidator spaceoccupied = new SpaceOccupiedValidator();
		validators.add(spaceoccupied);
		IRuleValidator adjacentIsolation = new ContiguousGroupsValidator();
		validators.add(adjacentIsolation);
		
		//Run all validators
		for(IRuleValidator validator : validators){
			validator.validate(game, pieceType, from, to);
		}
	}

}
