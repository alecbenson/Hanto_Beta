/**
 * 
 * Interface for the Hanto rules.
 */

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
	 * @return true if red had played butterfly.
	 */
	boolean getRedPlayedButterfly();
	
	/**
	 * @return true if blue has played the butterfly
	 */
	boolean getBluePlayedButterfly();
	
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
