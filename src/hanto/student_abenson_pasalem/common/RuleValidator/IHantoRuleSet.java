/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.common.RuleValidator;

import common.HantoCoordinate;
import common.HantoPlayerColor;
import hanto.student_abenson_pasalem.common.Board.IHantoBoard;

/**
 * Interface for the Hanto rule set.
 */
public interface IHantoRuleSet {
	/**
	 * boolean for whether or not game is over.
	 * @return true or false depending if game is over
	 */
	boolean getIsGameOver();
	
	/**
	 * value for number of red turns.
	 * @return int of red turns
	 */
	int getRedTurns();
	
	/**
	 * value for number of blue turns.
	 * @return int of blue turns
	 */
	int getBlueTurns();
	
	/**
	 * @return true if the currentPlayer has played the butterfly.
	 */
	boolean getCurrentPlayerPlayedButterfly();
	
	/**
	 * @return the number of turns the current player has taken
	 */
	int getCurrentPlayerTurns();
	
	/**
	 * @return red butterfly position
	 */
	HantoCoordinate getRedButterflyPos();
	
	/**
	 * @return blue butterfly position
	 */
	HantoCoordinate getBlueButterflyPos();
	
	/**
	 * @return current player
	 */
	HantoPlayerColor getCurrentPlayer();
	
	/**
	 * @return hanto board
	 */
	IHantoBoard getBoard();
}
