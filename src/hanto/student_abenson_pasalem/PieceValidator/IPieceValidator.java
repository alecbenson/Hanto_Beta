/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.PieceValidator;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.student_abenson_pasalem.Board.HantoBoardImpl;


/**
 * Interface for piece validation
 * @author Peter
 *
 */
public interface IPieceValidator {
	
	/**
	 * boolean for whether or not a piece can move.
	 * @param from place moving from
	 * @param to place moving to
	 * @param board the gaem board
	 * @throws HantoException
	 */
	void validate(HantoBoardImpl board, HantoCoordinate from, HantoCoordinate to) throws HantoException;
}
