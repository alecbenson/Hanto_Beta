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
	 * Gets a list of HantoMoveRecords that correspond to legal moves that a player can make
	 * @param color the color of the player
	 * @param game the game
	 * @return a list of HantoMoveRecords
	 */
	private final int earlyGameMoves = 10;
	private final int midGameMoves = 20;
	
	public List<HantoMoveRecord> getAllLegalMovementsForPlayer(BaseHantoGame game, HantoPlayerColor color){
		List<HantoMoveRecord> moveList = new ArrayList<HantoMoveRecord>();
		Map<HantoCoordinate, HantoPiece> playerPieces = game.getBoard().getAllPlayerPieces(color);
		
		Iterator<Map.Entry<HantoCoordinate, HantoPiece>> it = playerPieces.entrySet().iterator();
		//Go through all pieces on the board
		while( it.hasNext() ){
			Map.Entry<HantoCoordinate, HantoPiece> pair = it.next();
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
	
	/**
	 * Gets a list of HantoMoveRecords that correspond to legal moves that a player can make
	 * @param color the color of the player
	 * @param game the game
	 * @return a list of HantoMoveRecords
	 */
	public List<HantoMoveRecord> getAllLegalPlacementsForPlayer(BaseHantoGame game, HantoPlayerColor color){
		List<HantoMoveRecord> validPlacementOptions = new ArrayList<HantoMoveRecord>();
		List<HantoPieceType> pieceList = game.getCurrentPlayerState().piecesInInventory();
		List<HantoCoordinateImpl> validHexes = game.getBoard().getAllPlaceableHexes(color);
		int turnCount = game.getCurrentPlayerTurns();
		
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
		boolean playedButterfly = game.getCurrentPlayerPlayedButterfly();
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
	
	/**
	 * Decides a move for the tournament player to issue
	 * @param game
	 * @param opponentMove
	 * @return a HantoMoveRecord with the decided move
	 */
	public HantoMoveRecord decideMove(BaseHantoGame game, HantoMoveRecord opponentMove){
		//Get pieces left in inventory, make sure we can place pieces
		int piecesLeftInInventory = game.getCurrentPlayerState().numPiecesLeftInInventory();
		boolean canPlace = game.canPlacePiece();
		HantoPlayerColor currentPlayer = game.getCurrentPlayer();
		int moveCount = game.getCurrentPlayerTurns();
		
		//Try to place pieces early if possible
		if( canPlace && piecesLeftInInventory > 0 && moveCount < earlyGameMoves){
			List<HantoMoveRecord> placeList = this.getAllLegalPlacementsForPlayer(game, currentPlayer);
			return placeList.get(new Random().nextInt(placeList.size()));
		} else{
			HantoMoveRecord offensiveMove = lookForMoveToButterfly(game, opponentMove);
			if(offensiveMove != null){
				return offensiveMove;
			}
			HantoMoveRecord defensiveMove = moveButterflyToSafety(game, opponentMove);
			if(defensiveMove != null){
				return defensiveMove;
			}
			//Otherwise move a piece on the board
			List<HantoMoveRecord> moveList = this.getAllLegalMovementsForPlayer(game, currentPlayer);
			return moveList.get(new Random().nextInt(moveList.size()));
		}
	}
	
	/**
	 * Attempts to find a move that places a piece adjacent to the opponent butterfly if possible.
	 * If the piece is already adjacent, skip it.
	 * @param game
	 * @param opponentMove
	 * @return
	 */
	public HantoMoveRecord lookForMoveToButterfly(BaseHantoGame game, HantoMoveRecord opponentMove){
		HantoPlayerColor currentPlayer = game.getCurrentPlayer();
		HantoCoordinate opponentButterflyPos = currentPlayer == HantoPlayerColor.RED 
				? game.getBlueButterflyPos() : game.getRedButterflyPos();
		if(opponentButterflyPos == null){
			return null;
		}
		List<HantoMoveRecord> moveList = this.getAllLegalMovementsForPlayer(game, currentPlayer);
		for(HantoMoveRecord move : moveList){
			HantoCoordinateImpl fromPos = new HantoCoordinateImpl(move.getFrom());
			if(fromPos.isAdjacent(opponentButterflyPos)){
				continue;
			}
			HantoCoordinateImpl toPos = new HantoCoordinateImpl(move.getTo());
			if(toPos.isAdjacent(opponentButterflyPos)){
				return move;
			}
		}
		return null;
	}
	
	public HantoMoveRecord moveButterflyToSafety(BaseHantoGame game, HantoMoveRecord opponentMove){
		HantoMoveRecord bestMove = null;
		HantoPlayerColor currentPlayer = game.getCurrentPlayer();
		HantoCoordinate myButterflyPos = currentPlayer == HantoPlayerColor.RED 
				? game.getRedButterflyPos() : game.getBlueButterflyPos();
		
		if(myButterflyPos == null){
			return null;
		}
		int currentDanger = this.butterflyDanger(game, myButterflyPos);
		
		//Find the move that puts the butterfly in the least danger
		List<HantoMoveRecord> moveList = this.getAllLegalMovementsForPlayer(game, currentPlayer);
		for(HantoMoveRecord move : moveList){
			if(move.getPiece() != HantoPieceType.BUTTERFLY){
				continue;
			}
			int tentativeDanger = this.butterflyDanger(game, move.getTo());
			//If it's more dangerous or just as dangerous, don't bother
			if(tentativeDanger >= currentDanger){
				continue;
			}
			//If we haven't found any good move yet, use this one
			if(bestMove == null){
				bestMove = move;
			//If this move is better than one we previously found, use this one
			} else if(tentativeDanger < this.butterflyDanger(game, bestMove.getTo())){
				bestMove = move;
			}
		}
		return bestMove;
	}
	
	/**
	 * Return a danger heuristic for the given piece based on number of occupied adjacent hexes
	 * @param game
	 * @param hex
	 * @return adjacent count
	 */
	public int butterflyDanger(BaseHantoGame game, HantoCoordinate hex){
		HantoPlayerColor currentColor = game.getCurrentPlayer();
		List<HantoCoordinateImpl> adjacentSquares = new HantoCoordinateImpl(hex).getAdjacentSpaces();
		int adjCount = 0;
		for(HantoCoordinateImpl adjHex : adjacentSquares){
			HantoPiece pieceAt = game.getBoard().getPieceAt(adjHex);
			if(pieceAt == null){
				continue;
			}
			//2 danger is against enemy hexes, one for our own
			if(pieceAt.getColor() != currentColor){
				adjCount += 2;
			} else{
				adjCount += 1;
			}
		}
		return adjCount;
	}
}
