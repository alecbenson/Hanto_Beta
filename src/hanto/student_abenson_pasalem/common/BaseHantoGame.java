/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.common;

import static common.HantoPieceType.BUTTERFLY;
import static common.HantoPlayerColor.BLUE;
import static common.HantoPlayerColor.RED;
import static common.MoveResult.BLUE_WINS;
import static common.MoveResult.DRAW;
import static common.MoveResult.OK;
import static common.MoveResult.RED_WINS;

import java.util.List;

import common.*;
import hanto.student_abenson_pasalem.common.Board.HantoBoardImpl;
import hanto.student_abenson_pasalem.common.Board.IHantoBoard;
import hanto.student_abenson_pasalem.common.PieceFactory.HantoPieceFactory;
import hanto.student_abenson_pasalem.common.RuleValidator.AdjacentOpposingPieceValidator;
import hanto.student_abenson_pasalem.common.RuleValidator.IRuleValidator;
import hanto.student_abenson_pasalem.common.RuleValidator.MovingValidPieceValidator;
import hanto.student_abenson_pasalem.common.RuleValidator.GameRuleSets.PreTurnValidator;
import hanto.student_abenson_pasalem.comon.PlayerState.HantoPlayerState;

/**
 * An abstract class used for defining common characteristics of Hanto games
 * @author alec
 */
public abstract class BaseHantoGame implements HantoGame{
	protected int redTurns = 0, blueTurns = 0;
	protected boolean canResign, isGameOver;
	protected int maxTurns = 0;
	protected HantoCoordinateImpl redButterflyPos = null, blueButterflyPos = null;
	protected HantoPlayerColor currentPlayer;
	protected HantoBoardImpl board;
	protected HantoPlayerState redPlayerState, bluePlayerState, currentPlayerState;
	
	/**
	 * Default constructor for the hanto template
	 * @param movesFirst - the player color that moves first
	 */
	public BaseHantoGame(HantoPlayerColor movesFirst){
		currentPlayer = movesFirst;
		board = new HantoBoardImpl();
	}
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType,
	 * hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		//If there is a resignation, end the game
		if(checkPlayerResigned(pieceType, from, to)){
			isGameOver = true;
			return currentPlayerState.getColor() == BLUE ? RED_WINS : BLUE_WINS;
		}
		
		HantoPieceImpl piece;
		IRuleValidator preturnValidator = new PreTurnValidator();
		preturnValidator.validate(this, pieceType, from, to);
		
		//Moving a piece
		if(from != null){
			IRuleValidator checkMovingValidPiece = new MovingValidPieceValidator();
			checkMovingValidPiece.validate(this, pieceType, from, to);
			piece = (HantoPieceImpl) board.getPieceAt(from);
			checkPieceCanMove(piece, from, to);
			board.movePiece(from, to);
		//Placing a piece
		} else{
			IRuleValidator opposingAdjacentValidator = new AdjacentOpposingPieceValidator();
			opposingAdjacentValidator.validate(this, pieceType, from, to);
			piece = HantoPieceFactory.createPiece(currentPlayer, pieceType);
			board.placePiece(piece, to);
			currentPlayerState.getPieceFromInventory(pieceType);
		}
		updateButterflyIfMoved(pieceType, to);
		switchTurn();
		return gameState();
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
		if (totalTurnsTaken() >= maxTurns) {
			isGameOver = true;
			return DRAW;
		}
		return OK;
	}
	
	/**
	 * Checks if a piece can move.
	 * @param piece
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	public void checkPieceCanMove(HantoPieceImpl piece, HantoCoordinate from, HantoCoordinate to) throws HantoException {

		if(piece == null){
			throw new HantoException("Cannot move: provided piece was null.");
		}
		piece.checkCanMove(board, from, to);
	}

	/**
	 * Checks if a butterfly is surrounded by another player's pieces
	 * 
	 * @param butterflyPos
	 * 
	 * @return true if surrounded, false otherwise
	 * @throws HantoException
	 */
	public boolean checkButterflySurrounded(HantoCoordinateImpl butterflyPos) throws HantoException {
		if (butterflyPos == null) {
			return false;
		}

		HantoPiece butterfly = board.getPieceAt(butterflyPos);
		if (butterfly.getType().getClass() != BUTTERFLY.getClass()) {
			throw new HantoException("provided coordinates to a non-butterfly piece in checkButterflySurrounded");
		}
		List<HantoCoordinateImpl> adjacentSpaces = butterflyPos.getAdjacentSpaces();
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
		// If the blue player moved, it is now red's turn
		if (currentPlayer == BLUE) {
			currentPlayer = RED;
			currentPlayerState = redPlayerState;
			blueTurns++;
			// Otherwise it is red's turn, make it blue's
		} else {
			currentPlayer = BLUE;
			currentPlayerState = bluePlayerState;
			redTurns++;
		}
	}
	
	/**
	 * Update the butterfly position if it is moved
	 * @param pieceType the type of the piece being moved
	 * @param to the location the piece is being moved to
	 */
	public void updateButterflyIfMoved(HantoPieceType pieceType, HantoCoordinate to){
		if(pieceType == BUTTERFLY){
			switch(currentPlayerState.getColor()){
			case RED:
				redButterflyPos = new HantoCoordinateImpl(to);
				break;
			case BLUE:
				blueButterflyPos = new HantoCoordinateImpl(to);
				break;
			default:
				break;		
			}
		}
	}
	
	/**
	 * Returns true if a resignation is received
	 * @param pieceType the piecetype being played
	 * @param from the coordinate the player is moving from
	 * @param to the coordinate the player is moving to
	 * @return true if resignation received, false otherwise
	 * @throws HantoException if game does not support resignation
	 */
	public boolean checkPlayerResigned(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException{
		if(pieceType == null && from == null && to == null){
			if(!canResign){
				throw new HantoException("You cannot resign in this version of hanto");
			}
			return true;
		}
		return false;
	}

	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
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

	/**
	 * Returns whether or not the game is over
	 */
	public boolean getIsGameOver() {
		return isGameOver;
	}

	/**
	 * Returns whether or not the red player has played the butterfly
	 */
	public boolean getCurrentPlayerPlayedButterfly() {
		return currentPlayerState.getHasPlayedButterfly();
	}

	/**
	 * Returns the red butterfly position
	 */
	public HantoCoordinate getRedButterflyPos() {
		return redButterflyPos;
	}

	/**
	 * Returns the blue butterfly position
	 */
	public HantoCoordinate getBlueButterflyPos() {
		return blueButterflyPos;
	}

	/**
	 * Returns the current player
	 */
	public HantoPlayerColor getCurrentPlayer() {
		return currentPlayer;
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
	public int getRedTurns() {
		return redTurns;
	}

	/**
	 * @return number of blue turns taken
	 */
	public int getBlueTurns() {
		return blueTurns;
	}
	
	/**
	 * returns the number of turns the current player has made
	 * @return
	 */
	public int getCurrentPlayerTurns(){
		return currentPlayerState.getColor() == HantoPlayerColor.BLUE ? blueTurns : redTurns;
	};

	/**
	 * Gets the game board
	 */
	public IHantoBoard getBoard() {
		return board;
	}
	
	/**
	 * Gets the current player state
	 */
	public HantoPlayerState getCurrentPlayerState(){
		return currentPlayerState;
	}
}
