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
import hanto.studentabensonpasalem.common.HantoCoordinateImpl;
import hanto.studentabensonpasalem.common.HantoPieceImpl;

import static common.MoveResult.*;

/**
 * <<Fill this in>>
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame
{

	private boolean firstMove = true;
	private int redTurns = 1;
	private int blueTurns = 1;
	private boolean redPlayedButterfly;
	private boolean bluePlayedButterfly;
	
	private HantoPlayerColor currentPlayer;
	private HantoBoardImpl board;
	
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
			if(piece.getType().getClass() == BUTTERFLY.getClass()){
				redPlayedButterfly = true;
			}
		} else{
			if(piece.getType().getClass() == BUTTERFLY.getClass()){
				bluePlayedButterfly = true;
			}
		}
		
		switchTurn();
		return OK;
	}
	
	/**
	 * Ensures that a move is valid, or throws a HantoException otherwise
	 * @param pieceType
	 * @param from
	 * @param to
	 * @return
	 * @throws HantoException
	 */
	public boolean validateMove(HantoPiece piece, HantoCoordinate from,
			HantoCoordinate to) throws HantoException{
		//Ensure that the player is playing a valid piece in the beta version of Hanto
		if (piece.getType().getClass() != BUTTERFLY.getClass() || piece.getType().getClass() != SPARROW.getClass()) {
			throw new HantoException("Only Butterflies and Sparrows are valid pieces in Beta Hanto");
		}
		
		//Check that it is actually the player's turn to move
		if(piece.getColor() != currentPlayer){
			throw new HantoException("It is not this player's turn to move");
		}
		
		//Check if the player must play the butterfly
		if(mustPlayButterfly()){
			throw new HantoException("This player must play the butterfly because it must be played by turn 4");
		}
		
		//Ensure that the player does not play a butterfly twice
		if(currentPlayer == RED){
			if(redPlayedButterfly && piece.getType().getClass() == BUTTERFLY.getClass()){
				throw new HantoException("RED has already played the butterfly");
			}
		} else{
			if(bluePlayedButterfly && piece.getType().getClass() == BUTTERFLY.getClass()){
				throw new HantoException("RED has already played the butterfly");
			}
		}
		
		//Ensure that the player moves to a valid space on the first turn
		if (firstMove){
			if(to.getX() != 0 && to.getY() !=0){
				throw new HantoException("The only valid space for the first move is (0,0)");
			}
		}		
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean mustPlayButterfly(){
		if(currentPlayer == RED){
			if(redTurns >= 3 && !redPlayedButterfly){
				return true;
			}
		} else{
			if(blueTurns >= 3 && !bluePlayedButterfly){
				return true;
			}
		}
		return false;
	}
	
	public boolean pieceInLegalSpot(){
		
		return true;
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
	
	public void switchTurn(){
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

}
