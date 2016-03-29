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

package studentpasalem.beta;

import static common.HantoPieceType.BUTTERFLY;
import static common.HantoPieceType.SPARROW;
import static common.HantoPlayerColor.BLUE;
import static common.HantoPlayerColor.RED;

import common.*;
import hanto.studentpasalem.common.HantoCoordinateImpl;
import hanto.studentpasalem.common.HantoPieceImpl;
import static common.MoveResult.*;

/**
 * <<Fill this in>>
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame
{

	private boolean firstMove = true;
	private HantoCoordinateImpl blueButterflyHex = null, redButterflyHex = null;
	private final HantoPiece blueButterfly = new HantoPieceImpl(BLUE, BUTTERFLY);
	private final HantoPiece redButterfly = new HantoPieceImpl(RED, BUTTERFLY);
	private boolean gameOver= false;
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		if (gameOver) {
			throw new HantoException("You cannot move after the game is finished");
		}
		if (pieceType != BUTTERFLY || pieceType !=SPARROW) {
			throw new HantoException("Only Butterflies and Sparrows are valid in Beta Hanto");
		}
		
		final HantoCoordinateImpl dest = new HantoCoordinateImpl(to);
		
		if (firstMove) {
			if (dest.getX() != 0 || dest.getY() != 0) {
				throw new HantoException("Blue did not move Butterfly to origin");
			}
			blueButterflyHex = dest;
		} else {
			if (!hexIsValidForRed(dest)) {
				throw new HantoException("Red cannot place a piece in that hex");
			}
			redButterflyHex = dest;
			gameOver = true;
		}
	}

	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where)
	{
		// TODO Auto-generated method stub
		return null;
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
