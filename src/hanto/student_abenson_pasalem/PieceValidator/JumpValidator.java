/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.PieceValidator;

import java.util.ArrayList;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.student_abenson_pasalem.Board.HantoBoardImpl;
import hanto.student_abenson_pasalem.Board.IHantoBoard;
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
	public void validate(IHantoBoard board, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		int distance = new HantoCoordinateImpl(from).distance(new HantoCoordinateImpl(to));
		if(from.getX() == to.getX() && from.getY() == to.getY()){
			throw new HantoException("Cannot jump from " + from.getX() + "," + from.getY() + " to " +
					to.getX() + "," + to.getY() + ": cannot jump in place.");
		}
		if(distance > maxDistance){
			throw new HantoException("Cannot jump from " + from.getX() + "," + from.getY() + " to " +
				to.getX() + "," + to.getY() + ": cannot jump more than " + maxDistance + " tiles." +
					"distance was " + distance);
		}
		
		HantoCoordinateImpl fromKey = new HantoCoordinateImpl(from);
		HantoCoordinateImpl toKey = new HantoCoordinateImpl(to);
		if(fromKey.isAdjacent(toKey)){
			throw new HantoException("Must jump over at least one piece!");
		}
		HantoDirection dir = fromKey.direction(toKey);
		if(dir == HantoDirection.NONE){
			throw new HantoException("Cannot move from " + from.getX() + "," + from.getY() + " to " +
				to.getX() + "," + to.getY() + ": must only jump in straight lines");
		}
		checkJumpSpacesOccupied(from, to, board);
	}
	
	/**
	 * A helper method used to determine if a jump contains is over occupied spaces or not
	 * @param from the location jumping from
	 * @param to location jumping to
	 * @param board 
	 * @throws HantoException
	 */
	private void checkJumpSpacesOccupied(HantoCoordinate from, HantoCoordinate to,
			IHantoBoard board) throws HantoException{
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
						+ to.getY() + ": intermediate space not occupied at "
						+ checkedLoc.getX() + "," + checkedLoc.getY());
			}
		}
	}
	
	@Override
	public List<HantoCoordinateImpl> getValidMoves(HantoBoardImpl board, HantoCoordinate source) {
		List<HantoCoordinateImpl> validMoves = new ArrayList<HantoCoordinateImpl>();
		validMoves.addAll(getValidMovesInDirection(board, source, HantoDirection.NORTH));
		validMoves.addAll(getValidMovesInDirection(board, source, HantoDirection.NORTHWEST));
		validMoves.addAll(getValidMovesInDirection(board, source, HantoDirection.WEST));
		validMoves.addAll(getValidMovesInDirection(board, source, HantoDirection.SOUTH));
		validMoves.addAll(getValidMovesInDirection(board, source, HantoDirection.SOUTHEAST));
		validMoves.addAll(getValidMovesInDirection(board, source, HantoDirection.EAST));
		return validMoves;
	}
	
	private List<HantoCoordinateImpl> getValidMovesInDirection(HantoBoardImpl board, 
			HantoCoordinate source, HantoDirection dir) {
		
		List<HantoCoordinateImpl> validMoves = new ArrayList<HantoCoordinateImpl>();
		try{
			for(int i = 1; i < maxDistance; i++){
				HantoCoordinateImpl dest = new HantoCoordinateImpl(0,0);
				switch(dir){
				case NORTH:
					dest = new HantoCoordinateImpl(source.getX(), source.getY() + i);
					break;
				case NORTHWEST:
					dest = new HantoCoordinateImpl(source.getX() - i, source.getY() + i);
					break;
				case WEST:
					dest = new HantoCoordinateImpl(source.getX() - i, source.getY());
					break;
				case SOUTH:
					dest = new HantoCoordinateImpl(source.getX(), source.getY() - i);
					break;
				case SOUTHEAST:
					dest = new HantoCoordinateImpl(source.getX() + i, source.getY() - i);
					break;
				case EAST:
					dest = new HantoCoordinateImpl(source.getX() + i, source.getY());
					break;
				default:
					break;	
				}
				if(board.spaceOccupied(dest)){
					continue;
				}
				IPieceValidator contiguousMoveValidator = new ContiguousMovementValidator();
				contiguousMoveValidator.validate(board, source, dest);
				this.validate(board, source, dest);
				validMoves.add(dest);
			}
		}catch(HantoException e){
			return validMoves;
		}
		return validMoves;
	}
	
}
