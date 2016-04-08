/**
 * Interface for the purpose of creating the Hanto board
 * with checking occupied spaces, getting piece locations,
 * moving pieces, placing pieces, and remove pieces. It 
 * also checks if the board is empty. 
 * 
 */

package hanto.student_abenson_pasalem.common.Board;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPiece;

/**
 */
public interface IHantoBoard {
<<<<<<< HEAD
	
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
=======
	public boolean spaceOccupied(HantoCoordinate coordinate);
	public HantoPiece getPieceAt(HantoCoordinate coordinate);
	public void movePiece(HantoCoordinate from, HantoCoordinate to) throws HantoException;
	public void placePiece(HantoPiece piece, HantoCoordinate coordinate) throws HantoException;
	public void removePiece(HantoCoordinate coordinate);
	public boolean isBoardIsEmpty();
	public int pieceCount();
>>>>>>> d82c3064d0f697e9e89e278eec24997a1ec02f51
}
