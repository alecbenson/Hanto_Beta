/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/

package student_abenson_pasalem.gamma;

import common.*;
import static common.HantoGameID.GAMMA_HANTO;
import static common.HantoPieceType.BUTTERFLY;
import static common.HantoPlayerColor.RED;

import hanto.student_abenson_pasalem.common.BaseHantoGame;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;
import hanto.student_abenson_pasalem.common.HantoPieceImpl;
import hanto.student_abenson_pasalem.common.PieceFactory.HantoPieceFactory;
import hanto.student_abenson_pasalem.common.RuleValidator.GammaMoveValidator;
import hanto.student_abenson_pasalem.common.RuleValidator.GammaPlaceValidator;
import hanto.student_abenson_pasalem.common.RuleValidator.IRuleValidator;
import hanto.student_abenson_pasalem.comon.PlayerState.HantoPlayerStateFactory;

/**
 * Gamma Hanto Game.
 * @author Peter
 *
 */
public class GammaHantoGame extends BaseHantoGame implements HantoGame {
	public GammaHantoGame(HantoPlayerColor movesFirst){
		super(movesFirst);
		maxTurns = 40;
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
		HantoPieceImpl piece;
		
		//If no piece is provided, then we are moving a piece rather than placing one
		if(from != null && to != null){
			piece = (HantoPieceImpl) board.getPieceAt(from);
			checkPieceCanMove(piece, from, to);
			IRuleValidator moveValidator = new GammaMoveValidator();
			moveValidator.validate(this, pieceType, from, to);
			board.movePiece(from, to);
		} else{
			piece = HantoPieceFactory.createPiece(currentPlayer, pieceType);
			IRuleValidator placeValidator = new GammaPlaceValidator();
			placeValidator.validate(this, pieceType, from, to);
			board.placePiece(piece, to);
			currentPlayerState.getPieceFromInventory(pieceType);
		}

		// Indicate that the player has actually played the butterfly
		if (currentPlayer == RED) {
			if (piece.getType() == BUTTERFLY) {
				redButterflyPos = new HantoCoordinateImpl(to);
			}
		} else {
			if (piece.getType() == BUTTERFLY) {
				blueButterflyPos = new HantoCoordinateImpl(to);
			}
		}
		switchTurn();
		return gameState();
	}
}
