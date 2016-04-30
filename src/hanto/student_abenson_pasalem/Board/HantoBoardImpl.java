/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.Board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;
import hanto.student_abenson_pasalem.common.HantoPieceImpl;


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
	
	public boolean canPlacePieces(HantoPlayerColor color){
		Iterator<Map.Entry<HantoCoordinate, HantoPiece>> it = board.entrySet().iterator();
		//Go through all pieces on the board
		while( it.hasNext() ){
			Map.Entry<HantoCoordinate, HantoPiece> pair = (Map.Entry<HantoCoordinate, HantoPiece>)it.next();
			HantoCoordinateImpl coord = new HantoCoordinateImpl(pair.getKey());
			HantoPiece piece = pair.getValue();
			//Skip if not our color
			if(piece.getColor() != color){
				continue;
			}
			//Go through all of the spots adjacent to the piece we are looking at
			List<HantoCoordinateImpl> adjacencies = coord.getAdjacentSpaces();
			for(HantoCoordinateImpl adj : adjacencies){
				//If we found an empty spot next to the empty spot, skip
				if(this.spaceOccupied(adj)){
					continue;
				}
				//Look for neighboring hexes to the empty spot that are not adjacenet to
				//pieces of the opposing color
				if(!hexAdjacentOpposingPiece(color, adj)){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean hexAdjacentOpposingPiece(HantoPlayerColor color, HantoCoordinateImpl coord){
		List<HantoCoordinateImpl> hexesAdjacentToOpenSpot = coord.getAdjacentSpaces();
		for(HantoCoordinateImpl neighbor: hexesAdjacentToOpenSpot){
			//If there is an occupied space next to the open space and it's not our color, stop looking here
			if(this.spaceOccupied(neighbor)){
				if(this.getPieceAt(neighbor).getColor() != color){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean canMovePieces(HantoPlayerColor color){
		Iterator<Map.Entry<HantoCoordinate, HantoPiece>> it = board.entrySet().iterator();
		//Go through all pieces on the board
		while( it.hasNext() ){
			Map.Entry<HantoCoordinate, HantoPiece> pair = (Map.Entry<HantoCoordinate, HantoPiece>)it.next();
			HantoCoordinateImpl coord = new HantoCoordinateImpl(pair.getKey());
			HantoPiece piece = pair.getValue();
			//Skip if not our color
			if(piece.getColor() != color){
				continue;
			}
			if(((HantoPieceImpl) piece).hasLegalMoves(this, coord)){
				return true;
			}
		}
		return false;
	}
	
	public HashMap<HantoCoordinate, HantoPiece> getAllPlayerPieces(HantoPlayerColor color){
		HashMap<HantoCoordinate, HantoPiece> playerPieces = new HashMap<HantoCoordinate, HantoPiece>();
		Iterator<Map.Entry<HantoCoordinate, HantoPiece>> it = board.entrySet().iterator();
		//Go through all pieces on the board
		while( it.hasNext() ){
			Map.Entry<HantoCoordinate, HantoPiece> pair = (Map.Entry<HantoCoordinate, HantoPiece>)it.next();
			HantoCoordinateImpl coord = new HantoCoordinateImpl(pair.getKey());
			HantoPiece piece = pair.getValue();
			//Skip if not our color
			if(piece.getColor() == color){
				((Map<HantoCoordinate, HantoPiece>) playerPieces).put(coord, piece);
			}
		}
		return playerPieces;
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
