package hanto.student_abenson_pasalem.common.RuleValidator;

import java.util.ArrayList;
import java.util.List;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPieceType;

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
		
		//Run all validators
		for(IRuleValidator validator : validators){
			validator.validate(game, pieceType, from, to);
		}
	}

}
