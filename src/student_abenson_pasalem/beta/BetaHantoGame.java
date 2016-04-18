/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package student_abenson_pasalem.beta;

import static common.HantoGameID.BETA_HANTO;
import static common.HantoPieceType.BUTTERFLY;
import static common.HantoPlayerColor.RED;

import common.*;
import hanto.student_abenson_pasalem.common.BaseHantoGame;
import hanto.student_abenson_pasalem.common.PieceFactory.HantoPieceFactoryImpl;
import hanto.student_abenson_pasalem.common.PieceFactory.IHantoPieceFactory;
import hanto.student_abenson_pasalem.common.RuleValidator.BetaPlaceValidator;
import hanto.student_abenson_pasalem.common.RuleValidator.IRuleValidator;
import hanto.student_abenson_pasalem.comon.PlayerState.HantoPlayerState;
import hanto.student_abenson_pasalem.comon.PlayerState.HantoPlayerStateFactory;

/**
 * Constructor for the beta version of the hanto game
 */
public class BetaHantoGame extends BaseHantoGame implements HantoGame {
	/**
	 * Constructor for BetaHantoGame.
	 * 
	 * @param startPlayer
	 *            HantoPlayerColor
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		maxTurns = 12;
		HantoGameID version = BETA_HANTO;
		HantoPlayerState bluePlayerState = HantoPlayerStateFactory.makePlayerState(
				version, HantoPlayerColor.BLUE);
		HantoPlayerState redPlayerState = HantoPlayerStateFactory.makePlayerState(
				version, HantoPlayerColor.RED);	
	}
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType,
	 * hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
		throws HantoException {
		IHantoPieceFactory pieceFactory = new HantoPieceFactoryImpl();
		HantoPiece piece = pieceFactory.createPiece(currentPlayer, pieceType);
		IRuleValidator placeValidator = new BetaPlaceValidator();
		placeValidator.validate(this, pieceType, from, to);
		board.placePiece(piece, to);

		// Indicate that the player has actually played the butterfly
		if (currentPlayer == RED) {
			if (piece.getType() == BUTTERFLY) {
				redButterflyPos = to;
				redPlayedButterfly = true;
			}
		} else {
			if (piece.getType() == BUTTERFLY) {
				blueButterflyPos = to;
				bluePlayedButterfly = true;
			}
		}
		switchTurn();
		return gameState();
	}
	
}