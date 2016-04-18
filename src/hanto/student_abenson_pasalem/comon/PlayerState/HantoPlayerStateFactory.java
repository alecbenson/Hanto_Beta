package hanto.student_abenson_pasalem.comon.PlayerState;

import common.HantoException;
import common.HantoGameID;
import common.HantoPieceType;
import common.HantoPlayerColor;

public class HantoPlayerStateFactory {
	public static HantoPlayerState makePlayerState(HantoGameID version, HantoPlayerColor player){
		HantoPlayerState state = new HantoPlayerState(player);
		try{
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
					throw new HantoException("Player states not defined for this game type");
			}
		}catch(HantoException e){
			state = null;
		}
		return state;
	}
}
