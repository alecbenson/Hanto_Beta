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
	
	/**
	 * Returns true if there is enough space to walk to the adjacent piece
	 * @param board
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean enoughSpaceToWalk(HantoBoardImpl board, HantoCoordinate from, HantoCoordinate to){
		List<HantoCoordinateImpl> commonAdjacencies = new HantoCoordinateImpl(from).getCommonNeighbors(
				new HantoCoordinateImpl(to));
		
		//Can't walk to occupied space
		if(board.spaceOccupied(to)){
			return false;
		}
		
		//To walk to a space, at least one common adjacency must be unoccupied
		for(HantoCoordinateImpl neighbor : commonAdjacencies){
			if(!board.spaceOccupied(neighbor)){
				return true;
			}
		}
		return false;
	}
	
	public boolean walkablePathExists(HantoBoardImpl board, 
			HantoCoordinate from, HantoCoordinate to){	
		Map<HantoCoordinateImpl, Integer> distMap = new HashMap<HantoCoordinateImpl, Integer>();
		Queue<HantoCoordinateImpl> visitQueue = new LinkedList<HantoCoordinateImpl>();
		HantoCoordinateImpl fromKey = new HantoCoordinateImpl(from);
		visitQueue.add(fromKey);
		distMap.put(fromKey, 0);

		while(!visitQueue.isEmpty()){
			HantoCoordinateImpl current = visitQueue.poll();
			List<HantoCoordinateImpl> neighbors = current.getAdjacentSpaces();
			
			HantoCoordinateImpl currentKey = new HantoCoordinateImpl(current);
			int tentativeCost = distMap.get(currentKey) + 1;
			if(tentativeCost > maxDistance){
				break;
			}
			
			//Go through list of neighboring pieces
			for(HantoCoordinate neighbor : neighbors){
				HantoCoordinateImpl neighborKey = new HantoCoordinateImpl(neighbor);
				//Skip if we can't walk to this neighbor
				if(!this.enoughSpaceToWalk(board, current, neighbor)){
					//Or spaces that would cause isolation if moved to
					continue;
				}
				if(!distMap.containsKey(neighborKey)){
						visitQueue.add(neighborKey);
						distMap.put(neighborKey, tentativeCost);
				} else{
					int currentNeighborCost = distMap.get(neighborKey);
					if(tentativeCost < currentNeighborCost){
						distMap.put(neighborKey, tentativeCost);
					}
				}
				
				if(neighbor.getX() == to.getX() && neighbor.getY() == to.getY()){
					System.out.println("Got to " + to.getX() + "," + to.getY() + 
							" in " + tentativeCost + " from " + current.getX() + "," + current.getY());
					return true;
				}
			}
		}
		return false;
	}

}
