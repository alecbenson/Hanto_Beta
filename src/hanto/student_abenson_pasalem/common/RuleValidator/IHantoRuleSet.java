package hanto.student_abenson_pasalem.common.RuleValidator;

import common.HantoCoordinate;
import common.HantoPlayerColor;
import hanto.student_abenson_pasalem.common.Board.IHantoBoard;

public interface IHantoRuleSet {
	public boolean getIsGameOver();
	public int getRedTurns();
	public int getBlueTurns();
	public boolean getRedPlayedButterfly();
	public boolean getBluePlayedButterfly();
	public HantoCoordinate getRedButterflyPos();
	public HantoCoordinate getBlueButterflyPos();
	public HantoPlayerColor getCurrentPlayer();
	public IHantoBoard getBoard();
}
