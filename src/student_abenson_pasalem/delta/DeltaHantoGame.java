package student_abenson_pasalem.delta;

import static common.HantoGameID.DELTA_HANTO;
import static common.HantoPieceType.BUTTERFLY;
import static common.HantoPlayerColor.RED;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoGame;
import common.HantoGameID;
import common.HantoPieceType;
import common.HantoPlayerColor;
import common.MoveResult;
import hanto.student_abenson_pasalem.common.BaseHantoGame;
import hanto.student_abenson_pasalem.common.HantoPieceImpl;
import hanto.student_abenson_pasalem.common.PieceFactory.HantoPieceFactory;
import hanto.student_abenson_pasalem.common.RuleValidator.GammaMoveValidator;
import hanto.student_abenson_pasalem.common.RuleValidator.GammaPlaceValidator;
import hanto.student_abenson_pasalem.common.RuleValidator.IRuleValidator;
import hanto.student_abenson_pasalem.comon.PlayerState.HantoPlayerStateFactory;

public class DeltaHantoGame extends BaseHantoGame implements HantoGame {

	public DeltaHantoGame(HantoPlayerColor movesFirst) {
		super(movesFirst);
		maxTurns = 40;
		HantoGameID version = DELTA_HANTO;
		bluePlayerState = HantoPlayerStateFactory.makePlayerState(
				version, HantoPlayerColor.BLUE);
		redPlayerState = HantoPlayerStateFactory.makePlayerState(
				version, HantoPlayerColor.RED);	
		currentPlayerState = movesFirst == HantoPlayerColor.BLUE ? bluePlayerState : redPlayerState;
	}
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType,
	 * hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
			throws HantoException {
		HantoPieceImpl piece;
		
		//If no piece is provided, then we are moving a piece rather than placing one
		if(from != null && to != null){
			piece = (HantoPieceImpl) board.getPieceAt(from);
			checkPieceCanMove(piece, from, to);
			IRuleValidator moveValidator = new GammaMoveValidator();
			moveValidator.validate(this, pieceType, from, to);
			board.movePiece(from, to);
		} else{
			piece = HantoPieceFactory.createPiece(currentPlayer, pieceType);
			IRuleValidator placeValidator = new GammaPlaceValidator();
			placeValidator.validate(this, pieceType, from, to);
			board.placePiece(piece, to);
			currentPlayerState.getPieceFromInventory(pieceType);
		}

		// Indicate that the player has actually played the butterfly
		if (currentPlayer == RED) {
			if (piece.getType() == BUTTERFLY) {
				redButterflyPos = to;
			}
		} else {
			if (piece.getType() == BUTTERFLY) {
				blueButterflyPos = to;
			}
		}
		switchTurn();
		return gameState();
	}

}
