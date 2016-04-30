/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.student_abenson_pasalem.tournament;

import java.util.List;
import java.util.Random;

import hanto.common.*;
import hanto.student_abenson_pasalem.common.BaseHantoGame;
import hanto.student_abenson_pasalem.common.HantoGameFactory;
import hanto.tournament.*;

/**
 * Defines how a hanto tournament game is played
 * @author Alec
 *
 */
public class HantoPlayer implements HantoGamePlayer
{
	protected boolean doIMoveFirst;
	protected BaseHantoGame game;

	/*
	 * @see hanto.tournament.HantoGamePlayer#startGame(hanto.common.HantoGameID, hanto.common.HantoPlayerColor, boolean)
	 */
	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor,
			boolean doIMoveFirst)
	{
        this.doIMoveFirst = doIMoveFirst;
        //Determine color of starting player
        HantoPlayerColor startColor = doIMoveFirst ? myColor
                : myColor.equals(HantoPlayerColor.BLUE) ? 
                		HantoPlayerColor.RED : HantoPlayerColor.BLUE;
        
        game = (BaseHantoGame) HantoGameFactory.getInstance().makeHantoGame(version,
                startColor);
	}

	/*
	 * @see hanto.tournament.HantoGamePlayer#makeMove(hanto.tournament.HantoMoveRecord)
	 */
	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove)
	{
		HantoMoveRecord myMove;
		HantoAI ai = new HantoAI(game);
		try {
			//Record the opponent move
			if(opponentsMove != null){
				game.makeMove(opponentsMove.getPiece(), opponentsMove.getFrom(), opponentsMove.getTo());
			}
			
			System.out.println("YOUR TURN: MOVE # " + game.getCurrentPlayerTurns());
			
			//Get pieces left in inventory, make sure we can place pieces
			int piecesLeftInInventory = game.getCurrentPlayerState().numPiecesLeftInInventory();
			boolean canPlace = game.canPlacePiece();
			
			//Try to place a piece if possible
			System.out.println("Can place : " + canPlace);
			if( canPlace && piecesLeftInInventory > 0 ){
				List<HantoMoveRecord> placeList = ai.getAllLegalPlacementsForPlayer(game.getCurrentPlayer());
				myMove = placeList.get(new Random().nextInt(placeList.size()));
				game.makeMove(myMove.getPiece(), myMove.getFrom(), myMove.getTo());
				return myMove;
			} else{
				//Otherwise move a piece on the board
				List<HantoMoveRecord> moveList = ai.getAllLegalMovementsForPlayer(game.getCurrentPlayer());
				myMove = moveList.get(new Random().nextInt(moveList.size()));
				game.makeMove(myMove.getPiece(), myMove.getFrom(), myMove.getTo());
				return myMove;
			}
		} catch (HantoException e) {
			e.printStackTrace();
		}
		//Something went horribly wrong
		return new HantoMoveRecord(null, null, null);
	}

}
