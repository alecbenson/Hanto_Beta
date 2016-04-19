package hanto.student_abenson_pasalem.common.PieceValidator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import common.HantoCoordinate;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;
import hanto.student_abenson_pasalem.common.Board.HantoBoardImpl;

public class WalkValidator implements IPieceValidator{
	int maxDistance; 
	
	public WalkValidator(int maxDistance){
		this.maxDistance = maxDistance;	
	}

	public boolean canMove(HantoBoardImpl board, HantoCoordinate from, HantoCoordinate to) {
		return walkablePathExists(board, from, to);
	}
	
	public boolean walkablePathExists(HantoBoardImpl board, 
			HantoCoordinate from, HantoCoordinate to){
		Map<HantoCoordinate, Integer> distMap = new HashMap<HantoCoordinate, Integer>();
		Queue<HantoCoordinate> visitQueue = new LinkedList<HantoCoordinate>();
		visitQueue.add(from);
		
		while(!visitQueue.isEmpty()){
			HantoCoordinate current = visitQueue.poll();
			List<HantoCoordinate> neighbors = HantoBoardImpl.getAdjacentSpaces(current);
			
			//Go through list of neighboring pieces
			for(HantoCoordinate neighbor : neighbors){
				if(board.spaceOccupied(neighbor)){
					//Also ignore spaces that cannot be walked to
					//Or spaces that would cause isolation if moved to
					continue;
				}
				
				HantoCoordinate neighborKey = new HantoCoordinateImpl(neighbor);
				if(!distMap.containsKey(neighborKey)){
					int tentativeCost = distMap.get(neighborKey) + 1;
					
					if(tentativeCost < maxDistance){
						if(neighbor == to){
							return true;
						}
						visitQueue.add(neighborKey);
					}
					distMap.put(neighborKey, tentativeCost);
				}
			}
		}
		return false;
	}

}
