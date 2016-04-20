/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package student_abenson_pasalem.delta;

import static common.HantoGameID.DELTA_HANTO;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoGame;
import common.HantoGameID;
import common.HantoPieceType;
import common.HantoPlayerColor;
import common.MoveResult;
import hanto.student_abenson_pasalem.common.BaseHantoGame;
import hanto.student_abenson_pasalem.common.PlayerState.HantoPlayerStateFactory;

/**
 * represents a game of delta hanto
 * @author root
 *
 */
public class DeltaHantoGame extends BaseHantoGame implements HantoGame {

	public DeltaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		maxTurns = Integer.MAX_VALUE;
		canResign = true;
		HantoGameID version = DELTA_HANTO;
		bluePlayerState = HantoPlayerStateFactory.makePlayerState(
				version, HantoPlayerColor.BLUE);
		redPlayerState = HantoPlayerStateFactory.makePlayerState(
				version, HantoPlayerColor.RED);	
		currentPlayerState = movesFirst == HantoPlayerColor.BLUE ? bluePlayerState : redPlayerState;
	}
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType,
	 * hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		return super.makeMove(pieceType, from, to);
	}
}
