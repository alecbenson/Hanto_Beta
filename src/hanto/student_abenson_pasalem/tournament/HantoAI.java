/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/

package hanto.student_abenson_pasalem.tournament;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.student_abenson_pasalem.Board.HantoBoardImpl;
import hanto.student_abenson_pasalem.common.BaseHantoGame;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;
import hanto.student_abenson_pasalem.common.HantoPieceImpl;
import hanto.tournament.HantoMoveRecord;

/**
 *  A constructor for the class responsible for making move decisions
 * @author Alec
 *
 */
public class HantoAI {
	
	/**
	 * Default constructor for the Hanto AI
	 * @param game - the game to use. 
	 */
	public HantoAI(){
	}
	
	/**
	 * Gets a list of HantoMoveRecords that correspond to legal moves that a player can make
	 * @param color
	 * @return a list of HantoMoveRecords
	 */
	public List<HantoMoveRecord> getAllLegalMovementsForPlayer(BaseHantoGame game, HantoPlayerColor color){
		List<HantoMoveRecord> moveList = new ArrayList<HantoMoveRecord>();
		Map<HantoCoordinate, HantoPiece> playerPieces = ((BaseHantoGame) game).getBoard().getAllPlayerPieces(color);
		
		Iterator<Map.Entry<HantoCoordinate, HantoPiece>> it = playerPieces.entrySet().iterator();
		//Go through all pieces on the board
		while( it.hasNext() ){
			Map.Entry<HantoCoordinate, HantoPiece> pair = it.next();
			HantoCoordinateImpl coord = new HantoCoordinateImpl(pair.getKey());
			HantoPiece piece = pair.getValue();
			List<HantoCoordinateImpl> legalHexList = ((HantoPieceImpl) piece).getAllLegalMoves
					((HantoBoardImpl) ((BaseHantoGame) game).getBoard(), coord);
			for(HantoCoordinate legalHex : legalHexList){
				HantoMoveRecord legalMove = new HantoMoveRecord(piece.getType(), coord, legalHex);
				moveList.add(legalMove);
			}
		}
		return moveList;
	}
	
	/**
	 * Gets a list of HantoMoveRecords that correspond to legal moves that a player can make
	 * @param color
	 * @return a list of HantoMoveRecords
	 */
	public List<HantoMoveRecord> getAllLegalPlacementsForPlayer(BaseHantoGame game, HantoPlayerColor color){
		List<HantoMoveRecord> validPlacementOptions = new ArrayList<HantoMoveRecord>();
		List<HantoPieceType> pieceList = ((BaseHantoGame) game).getCurrentPlayerState().piecesInInventory();
		List<HantoCoordinateImpl> validHexes = ((BaseHantoGame) game).getBoard().getAllPlaceableHexes(color);
		int turnCount = ((BaseHantoGame) game).getCurrentPlayerTurns();
		
		//Special cases for first turns
		if(turnCount == 0){
			if(color == HantoPlayerColor.BLUE){
				validPlacementOptions.add(new HantoMoveRecord(
						HantoPieceType.BUTTERFLY,null, new HantoCoordinateImpl(0,0)));
			} else{
				validHexes = new HantoCoordinateImpl(0,0).getAdjacentSpaces();
			}
		}
		
		//Force butterfly if necessary
		boolean playedButterfly = ((BaseHantoGame) game).getCurrentPlayerPlayedButterfly();
		if(!playedButterfly && turnCount >= 3){
			pieceList.clear();
			pieceList.add(HantoPieceType.BUTTERFLY);
		}
		
		//Otherwise generate full list of options
		for(HantoPieceType type : pieceList){
			for(HantoCoordinateImpl coord : validHexes){
				HantoMoveRecord placementRecord = new HantoMoveRecord(type, null, coord);
				validPlacementOptions.add(placementRecord);
			}
		}
		return validPlacementOptions;
	}
	
	public HantoMoveRecord decideMove(BaseHantoGame game, HantoMoveRecord opponentMove){
		//Get pieces left in inventory, make sure we can place pieces
		int piecesLeftInInventory = game.getCurrentPlayerState().numPiecesLeftInInventory();
		boolean canPlace = game.canPlacePiece();
		HantoPlayerColor currentPlayer = game.getCurrentPlayer();
		
		//Try to place a piece if possible
		if( canPlace && piecesLeftInInventory > 0 ){
			List<HantoMoveRecord> placeList = this.getAllLegalPlacementsForPlayer(game, currentPlayer);
			return placeList.get(new Random().nextInt(placeList.size()));
		} else{
			//Otherwise move a piece on the board
			List<HantoMoveRecord> moveList = this.getAllLegalMovementsForPlayer(game, currentPlayer);
			return moveList.get(new Random().nextInt(moveList.size()));
		}
	}
}
