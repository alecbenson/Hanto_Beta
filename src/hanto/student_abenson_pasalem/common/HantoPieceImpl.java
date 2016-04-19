/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.student_abenson_pasalem.common;


import common.*;
import hanto.student_abenson_pasalem.common.Board.HantoBoardImpl;
import hanto.student_abenson_pasalem.common.PieceValidator.IPieceValidator;

/**
 * Implementation of the HantoPiece.
 * @version Mar 2,2016
 */
public class HantoPieceImpl implements HantoPiece
{
	private final HantoPlayerColor color;
	private final HantoPieceType type;
	private final IPieceValidator[] validators;
	
	/**
	 * Default constructor
	 * @param color the piece color
	 * @param type the piece type
	 * @param validators the validators
	 */
	public HantoPieceImpl(HantoPlayerColor color, HantoPieceType type, IPieceValidator... validators)
	{
		this.color = color;
		this.type = type;
		this.validators = validators;
	}
	/*
	 * @see hanto.common.HantoPiece#getColor()
	 */
	@Override
	public HantoPlayerColor getColor()
	{
		return color;
	}

	/*
	 * @see hanto.common.HantoPiece#getType()
	 */
	@Override
	public HantoPieceType getType()
	{
		return type;
	}
	
	/**
	 * Returns true if the piece can move from point A to point B, false otherwise. 
	 * @param from the place the piece is being moved from
	 * @param to the place the piece is being moved to
	 * @param board the game board
	 * @throws HantoException
	 */
	public void checkCanMove(HantoBoardImpl board, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		for(IPieceValidator validator : validators){
			validator.validate(board, from, to);
		}
	}
}
