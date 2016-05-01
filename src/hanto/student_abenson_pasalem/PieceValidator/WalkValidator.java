/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.PieceValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.student_abenson_pasalem.Board.HantoBoardImpl;
import hanto.student_abenson_pasalem.Board.IHantoBoard;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;

/**
 * Ensures that pieces walk in accordance to the rules
 * @author Alec
 *
 */
public class WalkValidator implements IPieceValidator{
	int maxDistance; 
	
	/**
	 * Validates a walk movement that can move up to maxDistance hexes
	 * @param maxDistance
	 */
	public WalkValidator(int maxDistance){
		this.maxDistance = maxDistance;	
	}

	public void validate(IHantoBoard board, HantoCoordinate from, HantoCoordinate to) throws HantoException{
		if(from.getX() == to.getX() && from.getY() == to.getY()){
			throw new HantoException("Cannot walk from " + from.getX() + "," + from.getY() + " to " +
					to.getX() + "," + to.getY() + ": cannot walk in place.");
		}
		
		checkWalkablePathExists(board, from, to);
	}
	
	/**
	 * Returns true if there is enough space to walk to the adjacent piece
	 * @param board
	 * @param from
	 * @param to
	 * @return true if there is enough space to walk, false otherwise
	 * @throws HantoException
	 */
	public boolean enoughSpaceToWalk(IHantoBoard board, 
			HantoCoordinate from, HantoCoordinate to) throws HantoException{
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
	
	/**
	 * Ensures that a valid path exists for the piece to walk
	 * @param board the game board
	 * @param from the place the piece is moving from
	 * @param to the place the piece is moving to 
	 * @throws HantoException
	 */
	public void checkWalkablePathExists(IHantoBoard board, 
			HantoCoordinate from, HantoCoordinate to) throws HantoException{	
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
				IPieceValidator contiguousChecker = new ContiguousMovementValidator();
				//Skip if we can't walk to this neighbor
				if(!this.enoughSpaceToWalk(board, current, neighbor)){
					continue;
				}
				//Skip if this movement causes isolation
				try{
					contiguousChecker.validate(board, from, to);
				} catch(HantoException e){
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
					return;
				}
			}
		}
		throw new HantoException("There is no walkable path from " + 
				from.getX() + "," + from.getY() + " to " + 
				to.getX() + "," + to.getY());
	}

	@Override
	public List<HantoCoordinateImpl> getValidMoves(HantoBoardImpl board, HantoCoordinate source) {
		HantoCoordinateImpl sourceCoord = new HantoCoordinateImpl(source);
		List<HantoCoordinateImpl> searchSet = new ArrayList<HantoCoordinateImpl>();
		searchSet.add(sourceCoord);
		List<HantoCoordinateImpl> addedSet = new ArrayList<HantoCoordinateImpl>();
		List<HantoCoordinateImpl> resultSet = new ArrayList<HantoCoordinateImpl>();
		
		for(int i = 0; i < maxDistance; i++){
			addedSet.clear();
			for(HantoCoordinateImpl adjacent : searchSet){
				for(HantoCoordinateImpl adjacentSearchItem : adjacent.getAdjacentSpaces()){
					try{
						this.validate(board, sourceCoord, adjacentSearchItem);
						if(!resultSet.contains(adjacentSearchItem)){
							resultSet.add(adjacentSearchItem);
							addedSet.add(adjacentSearchItem);
						}
					} catch(HantoException e){
						continue;
					}
				}
			}
			//Stop early if we get nothing past here
			if(addedSet.isEmpty()){
				break;
			}
			searchSet = new ArrayList<HantoCoordinateImpl>(addedSet);
		}
		return resultSet;
	}
}
