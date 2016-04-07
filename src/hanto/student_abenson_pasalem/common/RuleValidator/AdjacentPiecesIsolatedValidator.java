/**
 * 
 * Validator for whether or not an adjacent piece is left 
 * isolated after a piece is moved
 * 
 */

package hanto.student_abenson_pasalem.common.RuleValidator;

import java.util.List;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoPiece;
import common.HantoPieceType;
import hanto.student_abenson_pasalem.common.Board.HantoBoardImpl;

/**
 * Validator for isolated pieces.
 * @author Peter
 *
 */
public class AdjacentPiecesIsolatedValidator implements IRuleValidator{

	@Override
	public void validate(IHantoRuleSet game, HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {

		
		List<HantoCoordinate> adjacentSpaces = HantoBoardImpl.getAdjacentSpaces(from);
		for (HantoCoordinate space : adjacentSpaces) {
			HantoPiece piece = game.getBoard().getPieceAt(space);
			if (piece != null) {
				//Ensure that pieces adjacent to this piece do not become isolated when this move is made
				List<HantoCoordinate> adjacentPieceAdjacencies = HantoBoardImpl.getAdjacentSpaces(space);
				//If the adjacent piece is adjacent to another piece, return
				boolean foundValidAdjacency = false;
				for(HantoCoordinate adjacency : adjacentPieceAdjacencies){
					//Ignore the spot the piece we are moving is currently in
					if(adjacency.getX() == from.getX() && adjacency.getY() == from.getY()){
						continue;
					}
					//We are moving the piece to another square that is adjacent to this one
					if(adjacency.getX() == to.getX() && adjacency.getY() == to.getY()){
						foundValidAdjacency = true;
						break;
					}
					//Another piece on the board is still adjacent to this one
					if(game.getBoard().getPieceAt(adjacency) != null){
						foundValidAdjacency = true;
						break;
					}
				}
				//No pieces are adjacent to this one
				if(!foundValidAdjacency){
					throw new HantoException("Moving this piece causes another piece to become isolated");
				}
			}
		}
	}

}
