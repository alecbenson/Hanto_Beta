/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.epsilon;

import static hanto.common.HantoGameID.EPSILON_HANTO;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.student_abenson_pasalem.PlayerState.HantoPlayerStateFactory;
import hanto.student_abenson_pasalem.common.BaseHantoGame;

/**
 * represents a game of delta hanto
 * @author root
 *
 */
public class EpsilonHantoGame extends BaseHantoGame implements HantoGame {

	public EpsilonHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		maxTurns = Integer.MAX_VALUE;
		canResign = true;
		HantoGameID version = EPSILON_HANTO;
		bluePlayerState = HantoPlayerStateFactory.makePlayerState(
				version, HantoPlayerColor.BLUE);
		redPlayerState = HantoPlayerStateFactory.makePlayerState(
				version, HantoPlayerColor.RED);	
		currentPlayerState = movesFirst == HantoPlayerColor.BLUE ? bluePlayerState : redPlayerState;
	}
}