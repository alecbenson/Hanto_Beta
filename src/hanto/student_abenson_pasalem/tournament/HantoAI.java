/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/

package hanto.student_abenson_pasalem.tournament;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.student_abenson_pasalem.Board.HantoBoardImpl;
import hanto.student_abenson_pasalem.common.BaseHantoGame;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;
import hanto.student_abenson_pasalem.common.HantoPieceImpl;
import hanto.tournament.HantoMoveRecord;

public class HantoAI {
	private BaseHantoGame game;
	
	/**
	 * Default constructor for the Hanto AI
	 * @param game - the game to use. 
	 */
	public HantoAI(BaseHantoGame game){
		this.game = game;
	}
	
	/**
	 * Gets a list of HantoMoveRecords that correspond to legal moves that a player can make
	 * @param color
	 * @return a list of HantoMoveRecords
	 */
	public List<HantoMoveRecord> getAllLegalMovesForPlayer(HantoPlayerColor color){
		List<HantoMoveRecord> moveList = new ArrayList<HantoMoveRecord>();
		Map<HantoCoordinate, HantoPiece> playerPieces = game.getBoard().getAllPlayerPieces(color);
		
		Iterator<Map.Entry<HantoCoordinate, HantoPiece>> it = playerPieces.entrySet().iterator();
		//Go through all pieces on the board
		while( it.hasNext() ){
			Map.Entry<HantoCoordinate, HantoPiece> pair = (Map.Entry<HantoCoordinate, HantoPiece>)it.next();
			HantoCoordinateImpl coord = new HantoCoordinateImpl(pair.getKey());
			HantoPiece piece = pair.getValue();
			List<HantoCoordinateImpl> legalHexList = ((HantoPieceImpl) piece).getAllLegalMoves
					((HantoBoardImpl) game.getBoard(), coord);
			for(HantoCoordinate legalHex : legalHexList){
				HantoMoveRecord legalMove = new HantoMoveRecord(piece.getType(), coord, legalHex);
				moveList.add(legalMove);
			}
		}
		return moveList;
	}
}
