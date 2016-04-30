/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.PieceValidator;

import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.student_abenson_pasalem.Board.HantoBoardImpl;
import hanto.student_abenson_pasalem.Board.IHantoBoard;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;


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
	void validate(IHantoBoard board, HantoCoordinate from, HantoCoordinate to) throws HantoException;
	
	
	/**
	 * Returns a list of any valid moves that the piece can make with this validator
	 * @param board
	 * @param source
	 * @return a list of valid moves
	 */
	List<HantoCoordinateImpl> getValidMoves(HantoBoardImpl board, HantoCoordinate source);
}
