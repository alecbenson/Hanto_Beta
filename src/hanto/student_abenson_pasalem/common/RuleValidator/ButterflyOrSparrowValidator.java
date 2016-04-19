/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.common.RuleValidator;

import static common.HantoPieceType.BUTTERFLY;
import static common.HantoPieceType.SPARROW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPieceType;

/**
 * Validator for the butterfly or sparrow being played.
 * @author Peter
 *
 */
public class ButterflyOrSparrowValidator implements IRuleValidator{

	/**
	 * Ensures the piece played is a butterfly or sparrow
	 */
	public void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		
		List<HantoPieceType> validTypes = new ArrayList<HantoPieceType>(Arrays.asList(BUTTERFLY, SPARROW));
		if (!validTypes.contains(pieceType)) {
			throw new HantoException("You may only play butterflies or sparrows in this version of Hanto");
		}
	}
}
