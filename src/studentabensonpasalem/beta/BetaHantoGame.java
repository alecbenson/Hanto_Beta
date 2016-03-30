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

package studentabensonpasalem.beta;

import static common.HantoPieceType.BUTTERFLY;
import static common.HantoPieceType.SPARROW;
import static common.HantoPlayerColor.BLUE;
import static common.HantoPlayerColor.RED;

import common.*;
import hanto.studentabensonpasalem.common.HantoBoardImpl;
import hanto.studentabensonpasalem.common.HantoPieceImpl;

import static common.MoveResult.*;

import java.util.ArrayList;

/**
 * <<Fill this in>>
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame
{

	private boolean firstMove = true;
	private int redTurns = 0;
	private int blueTurns = 0;
	private boolean redPlayedButterfly;
	private boolean bluePlayedButterfly;
	
	private HantoCoordinate redButterflyPos = null;
	private HantoCoordinate blueButterflyPos = null;
	
	private HantoPlayerColor currentPlayer;
	private HantoBoardImpl board;
	
	/*
	 * 
	 */
	/**
	 * Constructor for BetaHantoGame.
	 * @param startPlayer HantoPlayerColor
	 */
	public BetaHantoGame(HantoPlayerColor startPlayer){
		this.currentPlayer = startPlayer;
		this.board = new HantoBoardImpl();
	}
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		HantoPieceImpl piece = new HantoPieceImpl(currentPlayer, pieceType);
		validateMove(piece, from, to);
		board.movePiece(piece, from, to);
		
		//Indicate that the player has actually played the butterfly
		if(currentPlayer == RED){
			if(piece.getType() == BUTTERFLY){
				redButterflyPos = to;
				redPlayedButterfly = true;
			}
		} else{
			if(piece.getType() == BUTTERFLY){
				blueButterflyPos = to;
				bluePlayedButterfly = true;
			}
		}
		
		switchTurn();
		return gameState();
	}
	
	/**
	 * Ensures that a move is valid, or throws a HantoException otherwise
	 * @param piece
	 * @param from
	 * @param to
	
	
	 * @return true if move is valid 
	 * @throws HantoException
	 */
	public boolean validateMove(HantoPiece piece, HantoCoordinate from,
			HantoCoordinate to) throws HantoException{
		
		checkIsValidPieceType(piece);
		checkIsPlayersTurn(piece);
		checkMustPlayButterfly();
		checkAlreadyPlayedButterfly(piece);
		checkPieceInLegalSpot(to);
		return true;
	}
	
	/**
	 * 
	
	
	 * @return game state after completed move
	 * 
	 * @throws HantoException
	 */
	public MoveResult gameState() throws HantoException{
		if(totalTurnsTaken() >= 12){
			return DRAW;
		}
		if(checkButterflySurrounded(blueButterflyPos)){
			return BLUE_WINS;
		}
		if(checkButterflySurrounded(redButterflyPos)){
			return RED_WINS;
		}
		return OK;
	}
	
	/**
	 * 
	 * @throws HantoException 
	 * 
	 */
	public void checkMustPlayButterfly() throws HantoException{
		if(currentPlayer == RED){
			if(redTurns >= 3 && !redPlayedButterfly){
				throw new HantoException("Red must play the butterfly!");
			}
		} else{
			if(blueTurns >= 3 && !bluePlayedButterfly){
				throw new HantoException("Blue must play the butterfly!");
			}
		}
	}
	
	/**
	 * Ensures that it is the moving player's turn
	 * @param piece the piece the player is considering moving
	
	 * @throws HantoException */
	public void checkIsPlayersTurn(HantoPiece piece) throws HantoException{
		//Check that it is actually the player's turn to move
		if(piece.getColor() != currentPlayer){
			throw new HantoException("It is not this player's turn to move");
		}
	}
	
	/**
	 * Ensures that the piece being played is a butterfly or sparrow
	 * @param piece the piece being played
	
	 * @throws HantoException */
	public void checkIsValidPieceType(HantoPiece piece) throws HantoException{
		if(piece.getType().getClass() != BUTTERFLY.getClass() || piece.getType().getClass() != SPARROW.getClass()){
			throw new HantoException("You may only play butterflies or sparrows in Hanto Beta");
		}
	}
	
	/**
	 * 
	
	 * @param piece HantoPiece
	 * @throws HantoException */
	public void checkAlreadyPlayedButterfly(HantoPiece piece) throws HantoException{
		if(currentPlayer == RED){
			if(redPlayedButterfly && piece.getType().getClass() == BUTTERFLY.getClass()){
				throw new HantoException("RED has already played the butterfly");
			}
		} else{
			if(bluePlayedButterfly && piece.getType().getClass() == BUTTERFLY.getClass()){
				throw new HantoException("BLUE has already played the butterfly");
			}
		}
	}
	
	/**
	 * Ensures that a valid space is being moved to
	 * @param coordinate the space the player is considering moving to
	
	 * @throws HantoException */
	public void checkPieceInLegalSpot(HantoCoordinate coordinate) throws HantoException{
		if(firstMove){
			if(coordinate.getX() != 0 && coordinate.getY() != 0){
				throw new HantoException("The only valid space for the first move is (0,0)");
			}
			return;
		}
		ArrayList<HantoCoordinate> adjacentSpaces = HantoBoardImpl.getAdjacentSpaces(coordinate);
		for(HantoCoordinate space : adjacentSpaces){
			HantoPiece piece = board.getPieceAt(space);
			if(piece != null){
				return;
			}
		}
		throw new HantoException("The piece is not adjacent to any other piece");
	}
	
	/**
	 * Checks if a butterfly is surrounded by another player's pieces
	 * @param butterflyPos
	 * 
	 * @return true if surrounded, false otherwise
	 * @throws HantoException
	 */
	public boolean checkButterflySurrounded(HantoCoordinate butterflyPos) throws HantoException{
		if(butterflyPos == null){
			return false;
		}
		
		HantoPiece butterfly = board.getPieceAt(butterflyPos);
		if(butterfly.getType().getClass() != BUTTERFLY.getClass()){
			throw new HantoException("provided coordinates to a non-butterfly piece in checkButterflySurrounded");
		}
		ArrayList<HantoCoordinate> adjacentSpaces = HantoBoardImpl.getAdjacentSpaces(butterflyPos);
		HantoPlayerColor butterflyOwner = butterfly.getColor();
		for(HantoCoordinate space : adjacentSpaces){
			HantoPiece adjacentPiece = board.getPieceAt(space);
			if(adjacentPiece == null || adjacentPiece.getColor() == butterflyOwner){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Switches turns from RED to BLUE and vice versa
	 */
	public void switchTurn(){
		if(firstMove){
			firstMove = false;
		}
		//If the blue player moved, it is now red's turn
		if(currentPlayer == BLUE){
			currentPlayer = RED;
			blueTurns++;
		//Otherwise it is red's turn, make it blue's
		} else{
			currentPlayer = BLUE;
			redTurns++;
		}
	}
	
	/**
	
	 * @return the total number of turns taken in the game */
	public int totalTurnsTaken(){
		return redTurns + blueTurns;
	}
	
	/**
	 * 
	
	 * @return number of red turns taken */
	public int redTurnsTaken(){
		return redTurns;
	}
	
	/**
	 * 
	
	 * @return number of blue turns taken */
	public int blueTurnsTaken(){
		return blueTurns;
	}
	
	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where)
	{
		// TODO Auto-generated method stub	private HantoCoordinateImpl blueButterflyHex = null, redButterflyHex = null;
		return board.getPieceAt(where);
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
