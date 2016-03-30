/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentabensonpasalem.beta;

import static common.HantoPieceType.*;
import static common.HantoPlayerColor.*;
import static common.MoveResult.*;
import static org.junit.Assert.*;

import org.junit.*;

import common.*;
import studentabensonpasalem.HantoGameFactory;
import studentabensonpasalem.beta.BetaHantoGame;

/**
 * Test cases for Beta Hanto.
 * @version Sep 14, 2014
 */
public class BetaHantoMasterTest
{
	/**
	 * Internal class for these test cases.
	 * @version Sep 13, 2014
	 */
	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		
		/**
		 * Constructor for values of TestHantoCoordinate
		 * @param x
		 * @param y
		 */
		TestHantoCoordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		/*
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX()
		{
			return x;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY()
		{
			return y;
		}
		
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
			if (!(obj instanceof TestHantoCoordinate)) {
				return false;
			}
			final TestHantoCoordinate other = (TestHantoCoordinate) obj;
			if (x != other.x) {
				return false;
			}
			if (y != other.y) {
				return false;
			}
			return true;
		}

	}
	
	private static HantoGameFactory factory;
	private BetaHantoGame game;
	
	/**
	 * Initializing Factory Class
	 *
	 */
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	/**
	 * Setting up the game to test.
	 */
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = (BetaHantoGame) factory.makeHantoGame(HantoGameID.BETA_HANTO, BLUE);
	}
	
	/**
	 * Ensures that blue places the butterfly at the origin for their first move.
	 * @throws HantoException
	 */
	@Test	// 1
	public void bluePlacesInitialButterflyAtOrigin() throws HantoException
	{
		setup();
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
	
	/**
	 * Ensures that if red tries to place a piece in a space that is occupied,
	 * an exception will be thrown.
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class)	// 2
	public void redPlacesButterflyAtBlueButterfly() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		final MoveResult redmove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0,0));
	}
	
	/**
	 * Ensures that red moves to an adjacent square to where blue's piece is.
	 * @throws HantoException
	 */
	@Test //3
	public void redMovesToAdjacentSquare() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		final MoveResult redmove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		assertEquals(OK, redmove1);
	}
	
	/**
	 * Ensures that if red tries to place their piece in a space not adjacent to
	 * another piece, an exception will be thrown
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class) //4
	public void redMovesToNonAdjacentSquare() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		final MoveResult redmove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0,2));
		assertEquals(OK, redmove1);
	}
	
	/**
	 * Ensures that if blue attempts to play a CRAB piece,
	 * an exception will be thrown
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class) // 5
	public void blueAttemptsToPlayInvalidCrabPiece() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(CRAB, null, makeCoordinate(0, 0));
	}
	
	/**
	 * Ensures that blue plays their first piece at the origin,
	 *  or an exception is thrown
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class) // 6
	public void blueAttemptsToPlayedAtInvalidLocationOnFirstTurn() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
	}
	
	/**
	 * Ensures that blue will play their butterfly by turn 4,
	 * or an exception will be thrown
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class)  // 7
	public void blueMustPlayButterflyByFourthTurn() throws HantoException
	{
		setup();
		assertEquals(0, game.redTurnsTaken());
		assertEquals(0, game.blueTurnsTaken());
		
		final MoveResult bluemove1 = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		assertEquals(1, game.blueTurnsTaken());
		
		final MoveResult redmove1 = game.makeMove(SPARROW, null, makeCoordinate(0,1));
		assertEquals(OK, redmove1);
		assertEquals(1, game.redTurnsTaken());
		
		final MoveResult bluemove2 = game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		assertEquals(OK, bluemove2);
		assertEquals(2, game.blueTurnsTaken());
		
		final MoveResult redmove2 = game.makeMove(SPARROW, null, makeCoordinate(1,-1));
		assertEquals(OK, redmove2);
		assertEquals(2, game.redTurnsTaken());

		final MoveResult bluemove3 = game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		assertEquals(OK, bluemove3);
		assertEquals(3, game.blueTurnsTaken());
		
		final MoveResult redmove3 = game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		assertEquals(OK, redmove3);
		
		final MoveResult bluemove4 = game.makeMove(SPARROW, null, makeCoordinate(2, -1));
	}
	
	/**
	 * Ensures that red will play their butterfly by turn 4,
	 * or an exception will be thrown
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class) // 8

	public void redMustPlayButterflyByFourthTurn() throws HantoException
	{
		setup();
		assertEquals(0, game.redTurnsTaken());
		assertEquals(0, game.blueTurnsTaken());

		final MoveResult bluemove1 = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		assertEquals(1, game.blueTurnsTaken());
		
		final MoveResult redmove1 = game.makeMove(SPARROW, null, makeCoordinate(0,1));
		assertEquals(OK, redmove1);
		assertEquals(1, game.redTurnsTaken());
		
		final MoveResult bluemove2 = game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		assertEquals(OK, bluemove2);
		assertEquals(2, game.blueTurnsTaken());
		
		final MoveResult redmove2 = game.makeMove(SPARROW, null, makeCoordinate(1,-1));
		assertEquals(OK, redmove2);
		assertEquals(2, game.redTurnsTaken());
		
		final MoveResult bluemove3 = game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		assertEquals(OK, bluemove3);
		assertEquals(3, game.blueTurnsTaken());

		final MoveResult redmove3 = game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		assertEquals(OK, redmove3);
		
		final MoveResult bluemove4 = game.makeMove(BUTTERFLY, null, makeCoordinate(2, -1));
		assertEquals(OK, bluemove4);

		final MoveResult redmove4 = game.makeMove(SPARROW, null, makeCoordinate(3, 0));
		assertEquals(OK, redmove4);
	}
	
	/**
	 * Tests that blue can't play on red's turn
	 * or an exception is thrown
	 * @throws HantoException
	 */
	@Test //9
	public void blueTriesToPlayOnRedsTurn() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
	}
	
	/**
	 * Tests that red can't play on blue's turn
	 * or an exception is thrown
	 * @throws HantoException
	 */
	@Test // 10
	public void redTriesToPlayOnBluesTurn() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
	}
	
	/**
	 * Ensures that if 12 total turns are played and the butterflies aren't surrounded,
	 * the game ends in a draw
	 * @throws HantoException
	 */
	@Test // 11
	public void gameEndsInDrawAfter12Turns() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		final MoveResult redmove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		assertEquals(OK, redmove1);
		final MoveResult bluemove2 = game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		assertEquals(OK, bluemove2);
		final MoveResult redmove2 = game.makeMove(SPARROW, null, makeCoordinate(1,-1));
		assertEquals(OK, redmove2);
		final MoveResult bluemove3 = game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		assertEquals(OK, bluemove3);
		final MoveResult redmove3 = game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		assertEquals(OK, redmove3);
		final MoveResult bluemove4 = game.makeMove(SPARROW, null, makeCoordinate(2, -1));
		assertEquals(OK, bluemove4);
		final MoveResult redmove4 = game.makeMove(SPARROW, null, makeCoordinate(3, 0));
		assertEquals(OK, redmove4);
		final MoveResult bluemove5 = game.makeMove(SPARROW, null, makeCoordinate(2, 1));
		assertEquals(OK, bluemove5);
		final MoveResult redmove5 = game.makeMove(SPARROW, null, makeCoordinate(1, 2));
		assertEquals(OK, redmove5);
		final MoveResult bluemove6 = game.makeMove(SPARROW, null, makeCoordinate(0, 3));
		assertEquals(OK, bluemove6);
		final MoveResult redmove6 = game.makeMove(SPARROW, null, makeCoordinate(0, 4));
		assertEquals(DRAW, redmove6);
	}
	
	/**
	 * Simple test to test the IsAdjacent function
	 * @throws HantoException
	 */
/**
	@Test // 12
	public boolean originIsAdjacentToX0Y1() throws HantoException
	{ 
		isAdjacentTo(makeCoordinate(0,0), makeCoordinate(0,1));
		return true;
	}
*/	

	/**
	 * Ensures that if the blue butterfly is surrounded,
	 * the game ends and red wins.
	 * @throws HantoException
	 */
	@Test // 13
	public void gameEndsWhenBlueButterflyCantMove() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		final MoveResult redmove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		assertEquals(OK, redmove1);
		final MoveResult bluemove2 = game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		assertEquals(OK, bluemove2);
		final MoveResult redmove2 = game.makeMove(SPARROW, null, makeCoordinate(1,-1));
		assertEquals(OK, redmove2);
		final MoveResult bluemove3 = game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		assertEquals(OK, bluemove3);
		final MoveResult redmove3 = game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		assertEquals(OK, redmove3);
		final MoveResult bluemove4 = game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
		assertEquals(RED_WINS, bluemove4);
	}
	
	/**
	 * Ensures if the red butterfly is surrounded,
	 * the game ends and blue wins.
	 * @throws HantoException
	 */
	@Test // 14
	public void gameEndsWhenRedButterflyCantMove() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		final MoveResult redmove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		assertEquals(OK, redmove1);
		final MoveResult bluemove2 = game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		assertEquals(OK, bluemove2);
		final MoveResult redmove2 = game.makeMove(SPARROW, null, makeCoordinate(1,-1));
		assertEquals(OK, redmove2);
		final MoveResult bluemove3 = game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		assertEquals(OK, bluemove3);
		final MoveResult redmove3 = game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		assertEquals(OK, redmove3);
		final MoveResult bluemove4 = game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
		assertEquals(BLUE_WINS, bluemove4);
	}
	
	/**
	 * Checking if first movement by blue is at the origin
	 * @throws HantoException
	 */
	@Test (expected = common.HantoException.class) // 11
	public void firstMoveIllegalSpot() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	}
	
	/**
	 * Throws an exception when blue tries to play a butterfly twice	
	 * @throws HantoException
	 */
	@Test (expected = common.HantoException.class) //12
	public void bluebutterflyPlayedTwice() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		final MoveResult redmove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		assertEquals(OK, redmove1);
		final MoveResult bluemove2 = game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	}
	
	/**
	 * Throws an exception when red tries to play a butterfly 
	 * @throws HantoException
	 */
	@Test (expected = common.HantoException.class) //13

	public void redbutterflyPlayedTwice() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		final MoveResult redmove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		assertEquals(OK, redmove1);
		final MoveResult bluemove2 = game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		assertEquals(OK, redmove1);
		final MoveResult redmove2 = game.makeMove(BUTTERFLY, null, makeCoordinate(1,1));
	}
	
	/**
	 * Checking if a spot to is moved to twice
	 * @throws HantoException
	 */
	@Test (expected = common.HantoException.class) //13
	public void spotMovedToTwice() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		final MoveResult redmove1 = game.makeMove(SPARROW, null, makeCoordinate(0,0));
	}
	
}
