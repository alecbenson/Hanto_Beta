package hanto.student_abenson_pasalem.common.PieceValidator;

import common.HantoCoordinate;
import common.HantoException;
import hanto.student_abenson_pasalem.common.Board.HantoBoardImpl;

public class SameToFromValidator implements IPieceValidator {

	@Override
	public void validate(HantoBoardImpl board, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		if(from.getX() == to.getX() && from.getY() == to.getY()){
			throw new HantoException("Cannot make a move from " + from.getX() + "," + from.getY() + " to "
					+ to.getX() + "," + to.getY() + ": positions are the same.");
		}
	}

}
