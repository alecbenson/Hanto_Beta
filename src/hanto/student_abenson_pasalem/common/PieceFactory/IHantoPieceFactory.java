package hanto.student_abenson_pasalem.common.PieceFactory;

import common.HantoException;
import common.HantoPieceType;
import common.HantoPlayerColor;
import hanto.student_abenson_pasalem.common.HantoPieceImpl;

public interface IHantoPieceFactory {
	public HantoPieceImpl createPiece(HantoPlayerColor color, HantoPieceType type) throws HantoException;
}
