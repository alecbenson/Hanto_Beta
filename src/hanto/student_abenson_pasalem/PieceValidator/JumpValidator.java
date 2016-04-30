/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.PieceValidator;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.student_abenson_pasalem.Board.HantoBoardImpl;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;
import hanto.student_abenson_pasalem.common.HantoDirection;

/**
 * Validates movement of pieces that can jump
 * @author Alec
 *
 */
public class JumpValidator implements IPieceValidator{
	int maxDistance;
	
	/**
	 * Default constructor for the fly validator
	 * @param maxDistance the maximum distance the piece can fly
	 */
	public JumpValidator(int maxDistance){
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
		
		HantoCoordinateImpl fromKey = new HantoCoordinateImpl(from);
		HantoCoordinateImpl toKey = new HantoCoordinateImpl(to);
		HantoDirection dir = fromKey.direction(toKey);
		if(dir == HantoDirection.NONE){
			throw new HantoException("Cannot move from " + from.getX() + "," + from.getY() + " to " +
				to.getX() + "," + to.getY() + ": must only jump in straight lines");
		}
		checkJumpSpacesOccupied(from, to, board);
	}
	
	public void checkJumpSpacesOccupied(HantoCoordinate from, HantoCoordinate to,
			HantoBoardImpl board) throws HantoException{
		int yOffset = to.getY() - from.getY();
		int xOffset = to.getX() - from.getX();
		int yIncrement = Integer.signum(yOffset);
		int xIncrement = Integer.signum(xOffset);
		
		HantoCoordinateImpl checkedLoc = new HantoCoordinateImpl(from);
		while((checkedLoc.getX() + xIncrement) != to.getX() || (checkedLoc.getY() + yIncrement) != to.getY()){
			checkedLoc.setX(checkedLoc.getX() + xIncrement);
			checkedLoc.setY(checkedLoc.getY() + yIncrement);
			if(!board.spaceOccupied(checkedLoc)){
				throw new HantoException("Cannot move to " + to.getX() + ","
						+ to.getY() + ": space not occupied at " + checkedLoc.getX() + "," + checkedLoc.getY());
			}
		}
	}
	
	

}
