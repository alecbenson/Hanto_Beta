/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * Written by Alec Benson and Peter Salem
 *******************************************************************************/

package hanto.student_abenson_pasalem.common.Board;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPiece;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;


/**
 * Implementation of the Hanto Board for purpose of placing pieces on it.
 * @author Peter
 *
 */
public class HantoBoardImpl implements IHantoBoard {
	
	private Map<HantoCoordinate, HantoPiece> board;
	
	/**
	 * Default constructor for a board in Hanto
	 */
	public HantoBoardImpl(){
		board = new HashMap<HantoCoordinate, HantoPiece>();
	}
	
	/**
	 * indicates whether or not the space at coordinate is occupied with a piece
	 * @param coordinate
	 * @return false if the space is unoccupied
	 */
	public boolean spaceOccupied(HantoCoordinate coordinate){
		HantoCoordinateImpl key = new HantoCoordinateImpl(coordinate.getX(), coordinate.getY());
		if(board.containsKey(key)){
			if(getPieceAt(key) != null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the piece located at the specified coordinate, or null if no piece exists
	 * @param coordinate
	 * @return the board coordinate
	 */
	public HantoPiece getPieceAt(HantoCoordinate coordinate){
		HantoCoordinateImpl key = new HantoCoordinateImpl(coordinate.getX(), coordinate.getY());
		return board.get(key);
	}
	
	/**
	 * @param piece the piece to add to the board
	 * @param coordinate the place to put the piece at
	 */
	public void placePiece(HantoPiece piece, HantoCoordinate coordinate) throws HantoException {
		if(spaceOccupied(coordinate)){
			throw new HantoException("Cannot move to space: already occupied");
		}
		HantoCoordinateImpl key = new HantoCoordinateImpl(coordinate.getX(), coordinate.getY());
		board.put(key, piece);
	}
	
	/**
	 * Removes the piece at the specified location
	 * @param coordinate the coordinate at which to remove the piece from
	 */
	public void removePiece(HantoCoordinate coordinate){
		HantoCoordinateImpl key = new HantoCoordinateImpl(coordinate.getX(), coordinate.getY());
		board.remove(key);
	}
	
	/**
	 * @param piece
	 * @param from
	 * @param to
	 * 
	 * @throws HantoException
	 */
	public void movePiece(HantoCoordinate from, HantoCoordinate to) throws HantoException{		
		if(spaceOccupied(to)){
			throw new HantoException("Cannot move to space: already occupied");
		}
		
		HantoPiece piece = getPieceAt(from);
		removePiece(from);
		placePiece(piece, to);
	}
	
	/**
	 * Returns true if the space at coordinate A is adjacent to coordinate B
	 * @param a
	 * @param b
	 * @return euclidian distance between the coordinates
	 *
	 */
	public static boolean isAdjacentTo(HantoCoordinate a, HantoCoordinate b){
		int aX = a.getX();
		int aY = a.getY();
		int bX = b.getX();
		int bY = b.getY();
		
		//We use euclidian distance to verify that the pieces are adjacent
		//If the pieces are adjacent, they will have a eucl. distance <= sqrt(2)
		double dist = Math.sqrt(Math.pow(bY - aY, 2) + Math.pow(bX - aX, 2));
		return dist <= Math.sqrt(2);
	}
	
	/**
	 * 
	 * @param space
	 * @return array of adjacent spaces
	 */
	public static List<HantoCoordinate> getAdjacentSpaces(HantoCoordinate space){
		List<HantoCoordinate> adjacentSquares = new ArrayList<HantoCoordinate>();
		
		HantoCoordinate north = new HantoCoordinateImpl(space.getX(), space.getY() + 1);
		HantoCoordinate east = new HantoCoordinateImpl(space.getX() + 1, space.getY());
		HantoCoordinate southeast = new HantoCoordinateImpl(space.getX() + 1, space.getY() - 1);
		HantoCoordinate south = new HantoCoordinateImpl(space.getX(), space.getY() - 1);
		HantoCoordinate southwest = new HantoCoordinateImpl(space.getX() - 1, space.getY());
		HantoCoordinate west = new HantoCoordinateImpl(space.getX() - 1, space.getY() + 1);
		
		adjacentSquares.addAll(Arrays.asList(north, east, southeast, south, southwest, west));
		return adjacentSquares;
	}
}
