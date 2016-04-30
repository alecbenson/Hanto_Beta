/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.delta;

import static hanto.common.HantoGameID.DELTA_HANTO;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.student_abenson_pasalem.PieceValidator.FlyValidator;
import hanto.student_abenson_pasalem.PieceValidator.IPieceValidator;
import hanto.student_abenson_pasalem.PieceValidator.WalkValidator;
import hanto.student_abenson_pasalem.PlayerState.HantoPlayerStateFactory;
import hanto.student_abenson_pasalem.common.BaseHantoGame;
import hanto.student_abenson_pasalem.common.HantoPieceBuilder;

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
	
	/**
	 * Set rules for each piece
	 */
	@Override
	public void definePieceRules(){
		pieceBuilder = new HantoPieceBuilder();
		IPieceValidator butterflyValidator = new WalkValidator(1);
		IPieceValidator sparrowValidator = new FlyValidator(Integer.MAX_VALUE);
		IPieceValidator crabValidator = new WalkValidator(3);
		pieceBuilder.setButterflyValidators(butterflyValidator);
		pieceBuilder.setSparrowValidators(sparrowValidator);
		pieceBuilder.setCrabValidators(crabValidator);
	}
}
