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

import static common.HantoPieceType.BUTTERFLY;
import static common.HantoPieceType.SPARROW;
import static common.HantoPlayerColor.BLUE;
import static common.HantoPlayerColor.RED;

import common.*;
import hanto.student_abenson_pasalem.common.HantoBoardImpl;
import hanto.student_abenson_pasalem.common.HantoPieceImpl;

import static common.MoveResult.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <<Fill this in>>
 * 
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame {

	private boolean firstMove = true;
	private int redTurns = 0;
	private int blueTurns = 0;
	private boolean redPlayedButterfly;
	private boolean bluePlayedButterfly;
	private boolean isGameOver;

	private HantoCoordinate redButterflyPos = null;
	private HantoCoordinate blueButterflyPos = null;

	private HantoPlayerColor currentPlayer;
	private HantoBoardImpl board;

	/*
	 * 
	 */
	/**
	 * Constructor for BetaHantoGame.
	 * 
	 * @param startPlayer
	 *            HantoPlayerColor
	 */
	public BetaHantoGame(HantoPlayerColor startPlayer) {
		currentPlayer = startPlayer;
		board = new HantoBoardImpl();
	}

	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType,
	 * hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		HantoPieceImpl piece = new HantoPieceImpl(currentPlayer, pieceType);
		validateMove(piece, from, to);
		board.movePiece(piece, from, to);

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

	/**
	 * Ensures that a move is valid, or throws a HantoException otherwise
	 * 
	 * @param piece
	 * @param from
	 * @param to
	 * 
	 * 
	 * @return true if move is valid
	 * @throws HantoException
	 */
	public boolean validateMove(HantoPiece piece, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		checkGameAlreadyOver();
		checkIsValidPieceType(piece);
		checkMustPlayButterfly();
		checkAlreadyPlayedButterfly(piece);
		checkPieceInLegalSpot(to);
		return true;
	}

	/**
	 * @return game state after completed move
	 * @throws HantoException
	 */
	public MoveResult gameState() throws HantoException {
		if (checkButterflySurrounded(blueButterflyPos) && checkButterflySurrounded(redButterflyPos)) {
			isGameOver = true;
			return DRAW;
		}
		if (checkButterflySurrounded(blueButterflyPos)) {
			isGameOver = true;
			return RED_WINS;
		}
		if (checkButterflySurrounded(redButterflyPos)) {
			isGameOver = true;
			return BLUE_WINS;
		}
		if (totalTurnsTaken() >= 12) {
			isGameOver = true;
			return DRAW;
		}
		return OK;
	}

	/**
	 * 
	 * @throws HantoException
	 * 
	 */
	public void checkMustPlayButterfly() throws HantoException {
		if (currentPlayer == RED) {
			if (redTurns >= 3 && !redPlayedButterfly) {
				throw new HantoException("Red must play the butterfly!");
			}
		} else {
			if (blueTurns >= 3 && !bluePlayedButterfly) {
				throw new HantoException("Blue must play the butterfly!");
			}
		}
	}

	/**
	 * Ensures that the piece being played is a butterfly or sparrow
	 * 
	 * @param piece
	 *            the piece being played
	 * 
	 * @throws HantoException
	 */
	public void checkIsValidPieceType(HantoPiece piece) throws HantoException {
		ArrayList<HantoPieceType> validTypes = new ArrayList<HantoPieceType>(Arrays.asList(BUTTERFLY, SPARROW));
		if (!validTypes.contains(piece.getType())) {
			throw new HantoException("You may only play butterflies or sparrows in Hanto Beta");
		}
	}

	/**
	 * 
	 * 
	 * @param piece
	 *            HantoPiece
	 * @throws HantoException
	 */
	public void checkAlreadyPlayedButterfly(HantoPiece piece) throws HantoException {
		if (currentPlayer == RED) {
			if (redPlayedButterfly && piece.getType() == BUTTERFLY) {
				throw new HantoException("RED has already played the butterfly");
			}
		} else {
			if (bluePlayedButterfly && piece.getType() == BUTTERFLY) {
				throw new HantoException("BLUE has already played the butterfly");
			}
		}
	}
	
	/**
	 * throws an exception if the game is already over
	 * @throws HantoException
	 */
	public void checkGameAlreadyOver() throws HantoException {
		if (isGameOver) {
			throw new HantoException("The game is over already.");
		}
	}

	/**
	 * Ensures that a valid space is being moved to
	 * 
	 * @param coordinate
	 *            the space the player is considering moving to
	 * 
	 * @throws HantoException
	 */
	public void checkPieceInLegalSpot(HantoCoordinate coordinate) throws HantoException {
		if (firstMove) {
			if (coordinate.getX() != 0 || coordinate.getY() != 0) {
				throw new HantoException("The only valid space for the first move is (0,0)");
			}
			return;
		}
		List<HantoCoordinate> adjacentSpaces = HantoBoardImpl.getAdjacentSpaces(coordinate);
		for (HantoCoordinate space : adjacentSpaces) {
			HantoPiece piece = board.getPieceAt(space);
			if (piece != null) {
				return;
			}
		}
		throw new HantoException("The piece is not adjacent to any other piece");
	}

	/**
	 * Checks if a butterfly is surrounded by another player's pieces
	 * 
	 * @param butterflyPos
	 * 
	 * @return true if surrounded, false otherwise
	 * @throws HantoException
	 */
	public boolean checkButterflySurrounded(HantoCoordinate butterflyPos) throws HantoException {
		if (butterflyPos == null) {
			return false;
		}

		HantoPiece butterfly = board.getPieceAt(butterflyPos);
		if (butterfly.getType().getClass() != BUTTERFLY.getClass()) {
			throw new HantoException("provided coordinates to a non-butterfly piece in checkButterflySurrounded");
		}
		List<HantoCoordinate> adjacentSpaces = HantoBoardImpl.getAdjacentSpaces(butterflyPos);
		HantoPlayerColor butterflyOwner = butterfly.getColor();
		for (HantoCoordinate space : adjacentSpaces) {
			HantoPiece adjacentPiece = board.getPieceAt(space);
			if (adjacentPiece == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Switches turns from RED to BLUE and vice versa
	 */
	public void switchTurn() {
		if (firstMove) {
			firstMove = false;
		}
		// If the blue player moved, it is now red's turn
		if (currentPlayer == BLUE) {
			currentPlayer = RED;
			blueTurns++;
			// Otherwise it is red's turn, make it blue's
		} else {
			currentPlayer = BLUE;
			redTurns++;
		}
	}

	/**
	 * 
	 * @return the total number of turns taken in the game
	 */
	public int totalTurnsTaken() {
		return redTurns + blueTurns;
	}

	/**
	 * 
	 * 
	 * @return number of red turns taken
	 */
	public int redTurnsTaken() {
		return redTurns;
	}

	/**
	 * 
	 * 
	 * @return number of blue turns taken
	 */
	public int blueTurnsTaken() {
		return blueTurns;
	}

	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		// TODO Auto-generated method stub private HantoCoordinateImpl
		// blueButterflyHex = null, redButterflyHex = null;
		return board.getPieceAt(where);
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}

}
