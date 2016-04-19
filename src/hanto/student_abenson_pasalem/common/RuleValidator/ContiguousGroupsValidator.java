/**
 * 
 * Validator for whether or not an adjacent piece is left 
 * isolated after a piece is moved
 * 
 */

package hanto.student_abenson_pasalem.common.RuleValidator;

import java.util.ArrayList;
import java.util.List;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPieceType;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;
import hanto.student_abenson_pasalem.common.Board.HantoBoardImpl;
import hanto.student_abenson_pasalem.common.Board.IHantoBoard;

/**
 * Validator for isolated pieces.
 * @author Peter
 *
 */
public class ContiguousGroupsValidator implements IRuleValidator{

	@Override
	public void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {

		List<HantoCoordinate> visited = new ArrayList<HantoCoordinate>();
		int totalPieces = game.getBoard().pieceCount();
		List<HantoCoordinate> contiguousGraph = getContiguousGraph(game.getBoard(), from, to, visited); 
		if(totalPieces != contiguousGraph.size()){
			throw new HantoException("Moving this piece would cause isolated groups to form on the board.");
		}
	}
	
	/**
	 * Retrieves a list of all pieces reachable at a specific piece
	 * @param board the board of pieces
	 * @param from the space the piece is moving from
	 * @param to the space the piece is moving to
	 * @param visited the list of visited coordinates
	 * @return a list of coordinates visited by the traversal
	 */
	public List<HantoCoordinate> getContiguousGraph(IHantoBoard board, HantoCoordinate from, HantoCoordinate to, List<HantoCoordinate> visited){
		visited.add(to);
		
		for(HantoCoordinate adjacent : new HantoCoordinateImpl(to).getAdjacentSpaces()){
			if(adjacent.getX() == from.getX() && adjacent.getY() == from.getY()){
				continue;
			}
			if(board.getPieceAt(adjacent) != null){
				if(!visited.contains(adjacent)){
					List<HantoCoordinate> results = getContiguousGraph(board, from, adjacent, visited);
					for(HantoCoordinate result : results){
						if(!visited.contains(result)){
							visited.add(result);
						}
					}
				}
			}
		}
		return visited;
	}
}
