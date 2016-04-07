/**
 * Interface for Hanto Piece Factory
 */

package hanto.student_abenson_pasalem.common.PieceFactory;

import common.HantoException;
import common.HantoPieceType;
import common.HantoPlayerColor;
import hanto.student_abenson_pasalem.common.HantoPieceImpl;

/**
 * Interface for Hanto Piece Factory.
 * @author Peter
 *
 */
public interface IHantoPieceFactory {
	/**
	 * Creates a piece
	 * @param color
	 * @param type
	 * @return piece
	 * @throws HantoException
	 */
	HantoPieceImpl createPiece(HantoPlayerColor color, HantoPieceType type) throws HantoException;
}
