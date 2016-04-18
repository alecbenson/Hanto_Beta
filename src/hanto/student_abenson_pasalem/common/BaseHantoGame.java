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
import hanto.student_abenson_pasalem.common.PieceFactory.HantoPieceFactoryImpl;
import hanto.student_abenson_pasalem.common.PieceFactory.IHantoPieceFactory;
import hanto.student_abenson_pasalem.common.RuleValidator.GammaMoveValidator;
import hanto.student_abenson_pasalem.common.RuleValidator.GammaPlaceValidator;
import hanto.student_abenson_pasalem.common.RuleValidator.IHantoRuleSet;
import hanto.student_abenson_pasalem.common.RuleValidator.IRuleValidator;

public abstract class BaseHantoGame implements HantoGame, IHantoRuleSet{
	protected int redTurns = 0;
	protected int blueTurns = 0;
	protected boolean redPlayedButterfly;
	protected boolean bluePlayedButterfly;
	protected boolean isGameOver;
	protected int maxTurns = 0;

	protected HantoCoordinate redButterflyPos = null;
	protected HantoCoordinate blueButterflyPos = null;

	protected HantoPlayerColor currentPlayer;
	protected HantoBoardImpl board;
	
	public BaseHantoGame(HantoPlayerColor movesFirst){
		this.currentPlayer = movesFirst;
		board = new HantoBoardImpl();
	}
	
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType,
	 * hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		IHantoPieceFactory pieceFactory = new HantoPieceFactoryImpl();
		HantoPieceImpl piece;
		
		//If no piece is provided, then we are moving a piece rather than placing one
		if(from != null && to != null){
			piece = (HantoPieceImpl) board.getPieceAt(from);
			checkPieceCanMove(piece, from, to);
			IRuleValidator moveValidator = new GammaMoveValidator();
			moveValidator.validate(this, pieceType, from, to);
			board.movePiece(from, to);
		} else{
			piece = pieceFactory.createPiece(currentPlayer, pieceType);
			IRuleValidator placeValidator = new GammaPlaceValidator();
			placeValidator.validate(this, pieceType, from, to);
			board.placePiece(piece, to);
		}

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

		if(piece == null || !piece.canMove(from, to)){
			throw new HantoException("The piece cannot move in this way.");
		}
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
			blueTurns++;
			// Otherwise it is red's turn, make it blue's
		} else {
			currentPlayer = BLUE;
			redTurns++;
		}
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
	 * REturns whether or not the red player has played the butterfly
	 */
	public boolean getRedPlayedButterfly() {
		return redPlayedButterfly;
	}

	/**
	 * Returns whether or not the blue player has played the butterfly
	 */
	public boolean getBluePlayedButterfly() {
		return bluePlayedButterfly;
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
	 * Gets the game board
	 */
	public IHantoBoard getBoard() {
		return board;
	}
}
