/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.common.PlayerState;

import common.HantoException;
import common.HantoGameID;
import common.HantoPieceType;
import common.HantoPlayerColor;

/**
 * A factory for creating player states
 */
public class HantoPlayerStateFactory {
	/**
	 * A factory method for creating the player states for the different types of hanto
	 * @param version the HantoGameID
	 * @param player the playerColor to create the state for
	 * @return the HantoPlayerState that was created
	 */
	public static HantoPlayerState makePlayerState(HantoGameID version, HantoPlayerColor player){
		HantoPlayerState state = new HantoPlayerState(player);
		try{
			if(version == null){
				throw new HantoException("Player states not defined for this game type");
			}
			switch(version){
				case BETA_HANTO:
					state.setStartPieceCount(HantoPieceType.SPARROW, 5);
					break;
				case GAMMA_HANTO:
					state.setStartPieceCount(HantoPieceType.SPARROW, 5);
					break;
				case DELTA_HANTO:
					state.setStartPieceCount(HantoPieceType.SPARROW, 4);
					state.setStartPieceCount(HantoPieceType.CRAB, 4);
					break;
				default:
					break;
			}
		}catch(HantoException e){
			state = null;
			
		}
		return state;
	}
}
