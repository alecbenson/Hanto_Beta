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
import java.util.IdentityHashMap;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPiece;

public class HantoBoardImpl {
	
	private HashMap<HantoCoordinate, HantoPiece> board;
	
	/**
	 * Default constructor for a board in Hanto
	 */
	public HantoBoardImpl(){
		this.board = new HashMap<HantoCoordinate, HantoPiece>();
	}
	
	/**
	 * 
	 * @param coordinate
	 * @param piece
	 * @throws HantoException
	 */
	public void placePiece(HantoCoordinate coordinate, HantoPiece piece) throws HantoException{
		
		if(spaceOccupied(coordinate)){
			throw new HantoException("A piece already exists on this coordinate");
		}
		board.put(coordinate, piece);
	}
	
	/**
	 * indicates whether or not the space at coordinate is occupied with a piece
	 * @param coordinate
	 * @return
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
	 * 
	 * @param from
	 * @param to
	 */
	public void movePiece(HantoPiece piece, HantoCoordinate from, HantoCoordinate to) throws HantoException{
		if(spaceOccupied(to)){
			throw new HantoException("Cannot move to space: already occupied");
		}
		
		if(piece == null){
			piece = board.get(from);
		}
		this.board.put(to, piece);
	}
	
	/**
	 * Returns the piece located at the specified coordinate, or null if no piece exists
	 * @param coordinate
	 * @return
	 */
	public HantoPiece getPieceAt(HantoCoordinate coordinate){
		return this.board.get(coordinate);
	}
	
	/**
	 * Returns true if the space at coordinate A is adjacent to coordinate B
	 * @param a
	 * @param b
	 * @return
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
	 * @return
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
