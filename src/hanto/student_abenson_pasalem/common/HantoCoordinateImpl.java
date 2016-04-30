/*******************************************************************************
 * This file was developed by Alec Benson and Peter Salem for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 ******************************************/
package hanto.student_abenson_pasalem.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;

/**
 * The implementation for my version of Hanto.
 * @version Mar 2, 2016
 */
public class HantoCoordinateImpl implements HantoCoordinate
{
	private int x, y;
	
	/**
	 * The only constructor.
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public HantoCoordinateImpl(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Copy constructor that creates an instance of HantoCoordinateImpl from an
	 * object that implements HantoCoordinate.
	 * @param coordinate an object that implements the HantoCoordinate interface.
	 */
	public HantoCoordinateImpl(HantoCoordinate coordinate)
	{
		this(coordinate.getX(), coordinate.getY());
	}
	
	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}
	
	/**
	 * Sets the value of the y coordinate
	 * @param y
	 */
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * Sets the value of the x coordinate
	 * @param x
	 */
	public void setX(int x){
		this.x = x;
	}
	
	/**
	 * Gets the euclidian distance between two hexes on the board
	 * @param other another HantoCoordinate to get the distance between
	 * @return the distance as an integer
	 * @throws HantoException
	 */
	public int distance(HantoCoordinate other) throws HantoException{
		if(other == null){
			throw new HantoException("Passed null argument when getting coordinate distance");
		}
		//We use euclidian distance to verify that the pieces are adjacent
		//If the pieces are adjacent, they will have a eucl. distance <= sqrt(2)
		double dist = Math.sqrt(Math.pow(other.getY() - this.getY(), 2) + 
				Math.pow(other.getX() - this.getX(), 2));
		return (int) dist;
	}
	
	public HantoDirection direction(HantoCoordinate other){
		if(other == null){
			return HantoDirection.NONE;
		}
		int yOffset = other.getY() - this.getY();
		int xOffset = other.getX() - this.getX();
		
		if(yOffset == 0){
			if(xOffset == 0){
				return HantoDirection.NONE;
			} else if (xOffset < 0){
				return HantoDirection.WEST;
			} else{
				return HantoDirection.EAST;
			}
		}
		
		if(xOffset == 0){
			if (yOffset > 0){
				return HantoDirection.NORTH;
			} else{
				return HantoDirection.SOUTH;
			}
		}
		
		if(xOffset < 0 && yOffset > 0){
			return xOffset + yOffset == 0 ? HantoDirection.NORTHWEST : HantoDirection.NONE;
		} else if(xOffset > 0 && yOffset < 0){
			return xOffset + yOffset == 0 ? HantoDirection.SOUTHEAST : HantoDirection.NONE;
		}
		return HantoDirection.NONE;	
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HantoCoordinateImpl)) {
			return false;
		}
		final HantoCoordinateImpl other = (HantoCoordinateImpl) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}
	
	/**
	 * @return array of adjacent spaces
	 */
	public List<HantoCoordinateImpl> getAdjacentSpaces(){
		List<HantoCoordinateImpl> adjacentSquares = new ArrayList<HantoCoordinateImpl>();
		
		HantoCoordinateImpl north = new HantoCoordinateImpl(this.getX(), this.getY() + 1);
		HantoCoordinateImpl east = new HantoCoordinateImpl(this.getX() + 1, this.getY());
		HantoCoordinateImpl southeast = new HantoCoordinateImpl(this.getX() + 1, this.getY() - 1);
		HantoCoordinateImpl south = new HantoCoordinateImpl(this.getX(), this.getY() - 1);
		HantoCoordinateImpl southwest = new HantoCoordinateImpl(this.getX() - 1, this.getY());
		HantoCoordinateImpl west = new HantoCoordinateImpl(this.getX() - 1, this.getY() + 1);
		
		adjacentSquares.addAll(Arrays.asList(north, east, southeast, south, southwest, west));
		return adjacentSquares;
	}
	
	/**
	 * Returns a list of all coordinates within a radius of this one
	 * @param radius
	 * @return
	 */
	public List<HantoCoordinateImpl> getCoordsInRadius(int radius){
		//Start with radius coords
		List<HantoCoordinateImpl> currentSearchSet = this.getAdjacentSpaces();
		List<HantoCoordinateImpl> toBeSearched = new ArrayList<HantoCoordinateImpl>();
		for(int i = 1; i < radius; i++){
			for(HantoCoordinateImpl adjacent : currentSearchSet){
				List<HantoCoordinateImpl> newSearchItems = adjacent.getAdjacentSpaces();
				for(HantoCoordinateImpl newSearchAdj : newSearchItems){
					if(!toBeSearched.contains(newSearchAdj)){
						toBeSearched.add(newSearchAdj);
					}
				}
			}
			currentSearchSet = new ArrayList<HantoCoordinateImpl>(toBeSearched);
		}
		
		for(HantoCoordinateImpl coord : currentSearchSet){
			System.out.println("new HantoCoordinateImpl(" + coord.getX() + "," + coord.getY() + "),");
		}
		return currentSearchSet;
	}
	
	/**
	 * Returns a list of adjacent HantoCoordinateImpls that are common between the two provided coords
	 * @param to the coordinate to find common adjacencies against
	 * @return a list of common adjacencies
	 */
	public List<HantoCoordinateImpl> getCommonNeighbors(HantoCoordinateImpl to){
		List<HantoCoordinateImpl> toAdj = to.getAdjacentSpaces();
		List<HantoCoordinateImpl> fromAdj = this.getAdjacentSpaces();
		fromAdj.retainAll(toAdj);
		return fromAdj;
	}


}
