package hanto.student_abenson_pasalem.common.MoveValidator;

import java.util.List;

import common.HantoCoordinate;
import hanto.student_abenson_pasalem.common.Board.HantoBoardImpl;

public class AdjacentSquareValidator implements IMoveValidator{

	/**
	 * returns true if the space is adjacent to another space
	 * @param from the space the piece is moving from
	 * @param to the space the piece is moving to
	 */
	public boolean canMove(HantoCoordinate from, HantoCoordinate to) {
		return HantoBoardImpl.isAdjacentTo(to,from);
	}
}
