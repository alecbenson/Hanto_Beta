/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.Board;

import java.util.Map;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;

/**
 */
public interface IHantoBoard {
	/**
	 * Method spaceOccupied.
	 * @param coordinate HantoCoordinate
	 * @return boolean
	 */
	boolean spaceOccupied(HantoCoordinate coordinate);
	
	/**
	 * Method getPieceAt.
	 * @param coordinate HantoCoordinate
	 * @return HantoPiece
	 */
	HantoPiece getPieceAt(HantoCoordinate coordinate);
	
	/**
	 * Method movePiece.
	 * @param from HantoCoordinate
	 * @param to HantoCoordinate
	 * @throws HantoException
	 */
	void movePiece(HantoCoordinate from, HantoCoordinate to) throws HantoException;
	
	/**
	 * Method placePiece.
	 * @param piece HantoPiece
	 * @param coordinate HantoCoordinate
	 * @throws HantoException
	 */
	void placePiece(HantoPiece piece, HantoCoordinate coordinate) throws HantoException;
	
	/**
	 * Method removePiece.
	 * @param coordinate HantoCoordinate
	 */
	void removePiece(HantoCoordinate coordinate);
	
	/**
	 * @return true if board is empty
	 */
	boolean isBoardIsEmpty();
	
	/**
	 * @return the number of pieces on the board
	 */
	int pieceCount();

	/**
	 * Determines if the player has any choices for placing pieces
	 * @param color
	 * @return true if can place, false otherwise
	 */
	boolean canPlacePieces(HantoPlayerColor color);
	
	/**
	 * Determines if the player has any choices for moving pieces
	 * @param color
	 * @return true if the player still has legal moves, false otherwise
	 */
	boolean canMovePieces(HantoPlayerColor color);
	
	/**
	 * Retrieves a map of pieces owned by the specified player
	 * @param color
	 * @return a map of pieces owned by the player
	 */
	Map<HantoCoordinate, HantoPiece> getAllPlayerPieces(HantoPlayerColor color);
}
