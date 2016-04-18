/**
 * Master validator for all placing piece validators called in Gamma Hanto.
 */


package hanto.student_abenson_pasalem.common.RuleValidator;

import java.util.ArrayList;
import java.util.List;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPieceType;

/**
 * Validator for Gamma Hanto placements.
 * @author Peter
 *
 */
public class GammaPlaceValidator implements IRuleValidator{

	/**
	 * Runs all validators for Gamma hanto when a piece is being placed
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
		IRuleValidator firstMoveValidator = new FirstTurnValidator();
		validators.add(firstMoveValidator);
		IRuleValidator butterflySparrowValidator = new ButterflyOrSparrowValidator();
		validators.add(butterflySparrowValidator);
		IRuleValidator adjacentOpposingPiece = new AdjacentOpposingPieceValidator();
		validators.add(adjacentOpposingPiece);
		IRuleValidator spaceoccupied = new SpaceOccupiedValidator();
		validators.add(spaceoccupied);
		
		//Run all validators
		for(IRuleValidator validator : validators){
			validator.validate(game, pieceType, from, to);
		}
		
	}

}
