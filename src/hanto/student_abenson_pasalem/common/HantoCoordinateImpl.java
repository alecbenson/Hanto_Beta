/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2015 Gary F. Pollice
 *******************************************************************************/

package hanto.student_abenson_pasalem.common;

import common.HantoCoordinate;
import common.HantoException;

/**
 * The implementation for my version of Hanto.
 * @version Mar 2, 2016
 */
public class HantoCoordinateImpl implements HantoCoordinate
{
	final private int x, y;
	
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


}
