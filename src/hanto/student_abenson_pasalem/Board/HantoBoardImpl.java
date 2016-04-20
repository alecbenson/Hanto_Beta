/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.Board;

import java.util.HashMap;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
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
		HantoPiece piece = getPieceAt(from);
		removePiece(from);
		placePiece(piece, to);
	}

	/**
	 * True if board is empty, false otherwise
	 */
	public boolean isBoardIsEmpty() {
		return board.isEmpty();
	}

	/**
	 * Returns the total number of pieces on the board
	 */
	public int pieceCount() {
		int total = 0;
		for(HantoPiece pieces : board.values()){
			total++;
		}
		return total;
	}
}
