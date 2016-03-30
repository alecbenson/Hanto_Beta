/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * Written by Alec Benson and Peter Salem
 *******************************************************************************/

package hanto.studentabensonpasalem.common;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPiece;


/**
 * Implementation of the Hanto Board for purpose of placing pieces on it.
 * @author Peter
 *
 */
public class HantoBoardImpl {
	
	private Map<HantoCoordinate, HantoPiece> board;
	
	/**
	 * Default constructor for a board in Hanto
	 */
	public HantoBoardImpl(){
		board = new HashMap<HantoCoordinate, HantoPiece>();
	}
	
	/**
	 * 
	 * @param coordinate
	 * @param piece
	 * @throws HantoException
	 */
	public void placePiece(HantoCoordinate coordinate, HantoPiece piece) throws HantoException{
		
		HantoCoordinateImpl key = new HantoCoordinateImpl(coordinate.getX(), coordinate.getY());
		if(spaceOccupied(key)){
			throw new HantoException("A piece already exists on this coordinate");
		}
		board.put(key, piece);
	}
	
	/**
	 * indicates whether or not the space at coordinate is occupied with a piece
	 * @param coordinate
	 * @return false if the space is unoccupied
	 */
	public boolean spaceOccupied(HantoCoordinate coordinate){
		if(board.containsKey(coordinate)){
			if(board.get(coordinate) != null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param piece
	 * @param from
	 * @param to
	 * 
	 * @throws HantoException
	 */
	public void movePiece(HantoPiece piece, HantoCoordinate from, HantoCoordinate to) throws HantoException{
		if(from != null){
			HantoCoordinateImpl fromKey = new HantoCoordinateImpl(from.getX(), from.getY());
			if(piece == null){
				piece = board.get(fromKey);
			}
		}
		HantoCoordinateImpl toKey = new HantoCoordinateImpl(to.getX(), to.getY());
		if(spaceOccupied(toKey)){
			throw new HantoException("Cannot move to space: already occupied");
		}
		board.put(toKey, piece);
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
	public static ArrayList<HantoCoordinate> getAdjacentSpaces(HantoCoordinate space){
		ArrayList<HantoCoordinate> adjacentSquares = new ArrayList<HantoCoordinate>();
		
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
