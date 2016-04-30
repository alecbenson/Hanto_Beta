/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/

package hanto.student_abenson_pasalem.gamma;

import static hanto.common.HantoGameID.GAMMA_HANTO;

import hanto.common.*;
import hanto.student_abenson_pasalem.PieceValidator.IPieceValidator;
import hanto.student_abenson_pasalem.PieceValidator.WalkValidator;
import hanto.student_abenson_pasalem.PlayerState.HantoPlayerStateFactory;
import hanto.student_abenson_pasalem.common.BaseHantoGame;
import hanto.student_abenson_pasalem.common.HantoPieceBuilder;

/**
 * Gamma Hanto Game.
 * @author Peter
 *
 */
public class GammaHantoGame extends BaseHantoGame implements HantoGame {
	public GammaHantoGame(HantoPlayerColor movesFirst){
		super(movesFirst);
		maxTurns = 40;
		canResign = false;
		HantoGameID version = GAMMA_HANTO;
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
		IPieceValidator sparrowValidator = new WalkValidator(1);
		pieceBuilder.setButterflyValidators(butterflyValidator);
		pieceBuilder.setSparrowValidators(sparrowValidator);
	}
}
