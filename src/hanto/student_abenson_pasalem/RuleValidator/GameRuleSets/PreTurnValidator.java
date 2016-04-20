/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.RuleValidator.GameRuleSets;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.student_abenson_pasalem.BaseHantoGame;
import hanto.student_abenson_pasalem.RuleValidator.FirstTurnValidator;
import hanto.student_abenson_pasalem.RuleValidator.IRuleValidator;
import hanto.student_abenson_pasalem.RuleValidator.IsGameOverValidator;
import hanto.student_abenson_pasalem.RuleValidator.MustPlayButterflyValidator;
import hanto.student_abenson_pasalem.RuleValidator.PieceAdjacentValidator;
import hanto.student_abenson_pasalem.RuleValidator.SpaceOccupiedValidator;

/**
 * Validator that gets run before a turn.
 * @author Peter
 *
 */
public class PreTurnValidator implements IRuleValidator{

	/**
	 * Runs all validators that need to be checked before a turn is taken
	 */
	public void validate(BaseHantoGame game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		
		List<IRuleValidator> validators = new ArrayList<IRuleValidator>();
		
		IRuleValidator gameoverValidator = new IsGameOverValidator();
		validators.add(gameoverValidator);
		IRuleValidator firstMoveValidator = new FirstTurnValidator();
		validators.add(firstMoveValidator);
		IRuleValidator butterflyValidator = new MustPlayButterflyValidator();
		validators.add(butterflyValidator);
		IRuleValidator adjacentValidator = new PieceAdjacentValidator();
		validators.add(adjacentValidator);
		IRuleValidator spaceoccupied = new SpaceOccupiedValidator();
		validators.add(spaceoccupied);
		
		//Run all validators
		for(IRuleValidator validator : validators){
			validator.validate(game, pieceType, from, to);
		}
		
	}

}
