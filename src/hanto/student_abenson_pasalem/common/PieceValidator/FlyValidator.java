/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.common.PieceValidator;

import common.HantoCoordinate;
import common.HantoException;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;
import hanto.student_abenson_pasalem.common.Board.HantoBoardImpl;

/**
 * Validates movement of pieces that can fly
 * @author Alec
 *
 */
public class FlyValidator implements IPieceValidator{
	int maxDistance;
	
	/**
	 * Default constructor for the fly validator
	 * @param maxDistance the maximum distance the piece can fly
	 */
	public FlyValidator(int maxDistance){
		this.maxDistance = maxDistance;
	}

	@Override
	public void validate(HantoBoardImpl board, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		int distance = new HantoCoordinateImpl(from).distance(new HantoCoordinateImpl(to));
		// This functionality will be reenabled when a maxDistance parameter is needed by the game
		// Currently we have not been told what the max is, so we will add it in and cover it
		// when it is needed.
		/**
		if(distance > maxDistance){
			throw new HantoException("Cannot move from " + from.getX() + "," + from.getY() + " to " +
				to.getX() + "," + to.getY() + ": cannot fly more than " + maxDistance + " tiles." +
					"distance was " + distance);
		}	
		**/
	}

}
