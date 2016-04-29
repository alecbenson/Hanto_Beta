/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.PieceValidator;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.student_abenson_pasalem.Board.HantoBoardImpl;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;

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
		if(distance > maxDistance){
			throw new HantoException("Cannot move from " + from.getX() + "," + from.getY() + " to " +
				to.getX() + "," + to.getY() + ": cannot fly more than " + maxDistance + " tiles." +
					"distance was " + distance);
		}	
	}

}
