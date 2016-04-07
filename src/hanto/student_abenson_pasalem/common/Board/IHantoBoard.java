package hanto.student_abenson_pasalem.common.Board;

import java.util.List;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPiece;

public interface IHantoBoard {
	public boolean spaceOccupied(HantoCoordinate coordinate);
	public HantoPiece getPieceAt(HantoCoordinate coordinate);
	public void movePiece(HantoCoordinate from, HantoCoordinate to) throws HantoException;
	public void placePiece(HantoPiece piece, HantoCoordinate coordinate) throws HantoException;
	public void removePiece(HantoCoordinate coordinate);
	public boolean isBoardIsEmpty();
}
