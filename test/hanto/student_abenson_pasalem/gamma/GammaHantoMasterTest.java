/**
 * Test file for GammaHantoGame.
 *
 */


package hanto.student_abenson_pasalem.gamma;

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;
import hanto.student_abenson_pasalem.common.HantoGameFactory;
import hanto.student_abenson_pasalem.gamma.GammaHantoGame;

/**
 * Gamma Hanto Test.
 * @author Peter
 *
 */
public class GammaHantoMasterTest {
	
	class MoveData {
		final HantoPieceType type;
		final HantoCoordinate from, to;
		
		private MoveData(HantoPieceType type, HantoCoordinate from, HantoCoordinate to) 
		{
			this.type = type;
			this.from = from;
			this.to = to;
		}
	}
	
	/**
	 * Internal class for these test cases.
	 * @version Sep 13, 2014
	 */
	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		
		public TestHantoCoordinate(int x, int y)
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

	}
	
	private static HantoGameFactory factory;
	private GammaHantoGame game;
	
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
		game = (GammaHantoGame) factory.makeHantoGame(HantoGameID.GAMMA_HANTO, BLUE);
	}
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
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
	
	/**
	 * Ensures that blue can play a sparrow on their first turn,
	 * as long as it is at the origin.
	 * @throws HantoException
	 */
	@Test	// 2
	public void bluePlacesInitialSparrowAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	/**
	 * Ensures that if red tries to place a piece in a space that is occupied,
	 * an exception will be thrown.
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class)	// 3
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
	@Test // 4
	public void redMovesToAdjacentSquare() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		final MoveResult redmove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		assertEquals(OK, redmove1);
	}
	
	/**
	 * Ensures that red moves to an adjacent square to where blue's piece is.
	 * @throws HantoException
	 */
	@Test // 5
	public void testAdjacentSquares() throws HantoException
	{
		setup();
		final List<HantoCoordinateImpl> adjacencies = new HantoCoordinateImpl(-1,0).getAdjacentSpaces();
		HantoCoordinateImpl north = new HantoCoordinateImpl(-1,1);
		HantoCoordinateImpl east = new HantoCoordinateImpl(0,0);
		HantoCoordinateImpl southEast = new HantoCoordinateImpl(0,-1);
		HantoCoordinateImpl south = new HantoCoordinateImpl(-1,-1);
		HantoCoordinateImpl southWest = new HantoCoordinateImpl(-2,0);
		HantoCoordinateImpl west = new HantoCoordinateImpl(-2,1);
		List<HantoCoordinate> expectedCoords = new ArrayList<HantoCoordinate>();
		expectedCoords.add(north);
		expectedCoords.add(east);
		expectedCoords.add(southEast);
		expectedCoords.add(south);
		expectedCoords.add(southWest);
		expectedCoords.add(west);
		assertEquals(adjacencies,expectedCoords);
	}
	
	/**
	 * Ensures that if red tries to place their piece in a space not adjacent to
	 * another piece, an exception will be thrown
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class) // 6
	public void redPlacesPieceOnNonAdjacentSquare() throws HantoException
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
	@Test (expected = HantoException.class) // 7
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
	@Test (expected = HantoException.class) // 8
	public void blueAttemptsToPlayedAtInvalidLocationOnFirstTurn() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
	}
	
	/**
	 * Checking if first movement by blue is at the origin
	 * @throws HantoException
	 */
	@Test (expected = hanto.common.HantoException.class) // 12
	public void firstMoveIllegalSpot() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	}
	
	/**
	 * Throws an exception when blue tries to play a butterfly twice	
	 * @throws HantoException
	 */
	@Test (expected = hanto.common.HantoException.class) // 13
	public void bluebutterflyPlayedTwice() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		final MoveResult redmove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		assertEquals(OK, redmove1);
		final MoveResult bluemove2 = game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
	}
	
	/**
	 * Ensures that a player can't play the butterfly twice.
	 * @throws HantoException
	 */
	@Test (expected = hanto.common.HantoException.class) // 14
	public void redbutterflyPlayedTwice() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		final MoveResult redmove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		assertEquals(OK, redmove1);
		final MoveResult bluemove2 = game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		assertEquals(OK, redmove1);
		final MoveResult redmove2 = game.makeMove(BUTTERFLY, null, makeCoordinate(1,1));
	}
	
	/**
	 * Checking if a spot is moved to twice
	 * @throws HantoException
	 */
	@Test (expected = hanto.common.HantoException.class) // 15
	public void spotMovedToTwice() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult redmove1 = game.makeMove(SPARROW, null, makeCoordinate(1,0));
		final MoveResult bluemove2 = game.makeMove(SPARROW, null, makeCoordinate(1,0));
	}
	
	/**
	 * Simulates blue playing butterfly and moving it on the next turn
	 * @throws HantoException
	 */
	@Test // 16
	public void blueMovesButterflyToValidSpace() throws HantoException
	{
		setup();
		final MoveResult bluebutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult redbutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(1,0));
		final MoveResult bluemovebutterfly = game.makeMove(BUTTERFLY, makeCoordinate(0,0), makeCoordinate(1, -1));
		assertEquals(OK, bluemovebutterfly);
	}
	
	/**
	 * Simulates blue playing butterfly and moving in an invalid way
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class)  // 17
	public void blueMovesButterflyToInvalidSpace() throws HantoException
	{
		setup();
		final MoveResult bluebutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult redbutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(1,0));
		final MoveResult bluemovebutterfly = game.makeMove(BUTTERFLY, makeCoordinate(0,0), makeCoordinate(1, -10));
	}
	
	/**
	 * Blue isolates a sparrow when moving. 
	 * In this test, blue places a butterfly, followed by three sparrows in a line.
	 * The middle sparrow is moved, downward, so that it is still adjacent to a piece, 
	 * but it causes one of the sparrows in the line to be isolated. This should throw an exception
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class) //18
	public void blueIsolatesSparrow() throws HantoException
	{
		setup();
		final MoveResult bluebutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult redbutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		final MoveResult blueSparrow1 = game.makeMove(SPARROW, null, makeCoordinate(-1,0));
		final MoveResult redSparrow1 = game.makeMove(SPARROW, null, makeCoordinate(1,1));
		final MoveResult blueSparrow2 = game.makeMove(SPARROW, null, makeCoordinate(-2,1));
		final MoveResult redSparrow2 = game.makeMove(SPARROW, null, makeCoordinate(0,2));
		final MoveResult blueSparrow3 = game.makeMove(SPARROW, null, makeCoordinate(0,-1));
		final MoveResult redSparrow3 = game.makeMove(SPARROW, null, makeCoordinate(-1,2));
		final MoveResult isolatingMove = game.makeMove(SPARROW, makeCoordinate(-1,0), makeCoordinate(-1,-1));
	}
	
	
	/**
	 * Simulates blue playing butterfly and moving away from adjacent pieces
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class)  // 19
	public void blueMovesAwayFromAdjacentPieces() throws HantoException
	{
		setup();
		final MoveResult bluebutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult redbutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(1,0));
		final MoveResult bluemovebutterfly = game.makeMove(BUTTERFLY, makeCoordinate(0,0), makeCoordinate(-1, 0));
	}
	
	@Test (expected = HantoException.class)
	public void placeNextToOpposingPiece() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));	// Move 2
	}
	
	/**
	 * This should result in OK
	 * @throws HantoException
	 */
	@Test  // 20
	public void bluePlacesNexToRedFirstTwoTurns() throws HantoException
	{
		setup();
		final MoveResult bluebutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult redbutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(1,0));
		assertEquals(OK, redbutterfly);
	}
	
	/**
	 * This should result in an exception
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class) // 21
	public void bluePlacesNexToRedThirdTurn() throws HantoException
	{
		setup();
		final MoveResult bluebutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult redbutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(1,0));
		final MoveResult blueSparrow = game.makeMove(SPARROW, null, makeCoordinate(2,0));
	}
	
	/**
	 * This should be totally legal.
	 * @throws HantoException
	 */
	@Test
	public void blueButterflyMovesLegallyGroupStaysIntact() throws HantoException
	{
		setup();
		final MoveResult bluebutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult redbutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		//Blue
		final MoveResult sparrow1 = game.makeMove(SPARROW, null, makeCoordinate(0,-1));
		//Red
		final MoveResult sparrow2 = game.makeMove(SPARROW, null, makeCoordinate(1,1));
		//Blue
		final MoveResult sparrow3 = game.makeMove(SPARROW, null, makeCoordinate(-1,-1));
		//Red
		final MoveResult sparrow4 = game.makeMove(SPARROW, null, makeCoordinate(2,0));
		//Blue
		final MoveResult sparrow5 = game.makeMove(SPARROW, null, makeCoordinate(-2,0));
		//Red
		final MoveResult sparrow6 = game.makeMove(SPARROW, null, makeCoordinate(2,-1));
		//Blue
		final MoveResult sparrow7 = game.makeMove(SPARROW, null, makeCoordinate(-2,1));
		//Red
		final MoveResult sparrow8 = game.makeMove(SPARROW, null, makeCoordinate(2,-2));
		//Blue
		final MoveResult finalMove = game.makeMove( BUTTERFLY, makeCoordinate(0,0), makeCoordinate(1,-1));
		assertEquals(OK, finalMove);
	}
	
	/**
	 * This should cause an exception because it results in two isolated groups on the board
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void blueButterflyMovesIllegallyCausesIsolation() throws HantoException
	{
		setup();
		final MoveResult bluebutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult redbutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		//Blue
		final MoveResult sparrow1 = game.makeMove(SPARROW, null, makeCoordinate(0,-1));
		//Red
		final MoveResult sparrow2 = game.makeMove(SPARROW, null, makeCoordinate(1,1));
		//Blue
		final MoveResult sparrow3 = game.makeMove(SPARROW, null, makeCoordinate(-1,-1));
		//Red
		final MoveResult sparrow4 = game.makeMove(SPARROW, null, makeCoordinate(2,0));
		//Blue
		final MoveResult sparrow5 = game.makeMove(SPARROW, null, makeCoordinate(-2,0));
		//Red
		final MoveResult sparrow6 = game.makeMove(SPARROW, null, makeCoordinate(2,-1));
		//Blue
		final MoveResult sparrow7 = game.makeMove(SPARROW, null, makeCoordinate(-2,1));
		//Red
		final MoveResult sparrow8 = game.makeMove(SPARROW, null, makeCoordinate(2,-2));
		//Blue
		final MoveResult finalMove = game.makeMove( null, makeCoordinate(0,0), makeCoordinate(1,0));
	}
	
	/**
	 * This should result in an exception
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class)
	public void redPlacesNexToBlueFourthTurn() throws HantoException
	{
		setup();
		final MoveResult bluebutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult redbutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(1,0));
		final MoveResult blueSparrow = game.makeMove(SPARROW, null, makeCoordinate(-1,0));
		final MoveResult redSparrow = game.makeMove(SPARROW, null, makeCoordinate(-2,0));
	}
	
	/**
	 * Checks that the game ends when the blue butterfly is surrounded.
	 * @throws HantoException
	 */
	@Test // 24
	public void blueButterflySurrounded() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));	// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));	// Move 4
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(2, -1));	// Move 5
		game.makeMove(SPARROW, makeCoordinate(-1, 2), makeCoordinate(-1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0)); 	// Move 6
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));
		assertEquals(RED_WINS, game.makeMove(SPARROW, makeCoordinate(2, -1), makeCoordinate(1, 0)));
	}
	
	/**
	 * Checks that the game ends when the blue butterfly is surrounded.
	 * @throws HantoException
	 */
	@Test // 25
	public void redButterflySurrounded() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));	// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));	// Move 4
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(2, -1));	// Move 5
		game.makeMove(SPARROW, makeCoordinate(-1, 2), makeCoordinate(-1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0)); 	// Move 6
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(-1, -1)); // Move 7
		game.makeMove(SPARROW, null, makeCoordinate(2, 1));
		assertEquals(BLUE_WINS, game.makeMove(SPARROW, makeCoordinate(2, -1), makeCoordinate(1, 0)));	
	}
	
	/**
	 * Tests equality method of HantoCoordinateImpl
	 * @throws HantoException
	 */
	@Test
	public void coordinateImplEqualsChecks() throws HantoException
	{
		setup();
		HantoCoordinate a = new HantoCoordinateImpl(0,0);
		HantoCoordinate difX = new HantoCoordinateImpl(1,0);
		HantoCoordinate difY = new HantoCoordinateImpl(0,1);
		assertEquals(a,a);
		assertFalse(a.equals(null));
		assertEquals(a,a);
		assertFalse(a.equals("blah"));
		assertFalse(a.equals(difX));
		assertFalse(a.equals(difY));
	}
	
	@Test
	public void stateSwitchesAfterTurn() throws HantoException
	{
		setup();
		assertEquals(HantoPlayerColor.BLUE, game.getCurrentPlayerState().getColor());
		assertEquals(false, game.getCurrentPlayerState().getHasPlayedButterfly());
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));	// Move 1
		assertEquals(HantoPlayerColor.RED, game.getCurrentPlayerState().getColor());
		assertEquals(false, game.getCurrentPlayerState().getHasPlayedButterfly());
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		assertEquals(true, game.getCurrentPlayerState().getHasPlayedButterfly());
	}
	
	/**
	 * Tests copy constructor of HantoCoordinateImpl
	 * @throws HantoException
	 */
	@Test
	public void coordinateImplCopyConstructor() throws HantoException
	{
		setup();
		HantoCoordinate b = makeCoordinate(1,2);
		HantoCoordinateImpl a = new HantoCoordinateImpl(b);
		assertEquals(1,a.getX());
		assertEquals(2,a.getY());
	}
	
	
	@Test
	public void bluePlacesButterflyFirst() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(BUTTERFLY, piece.getType());
	}
	
	@Test
	public void redPlacesSparrowFirst() throws HantoException
	{
		setup();
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
	}
	
	@Test
	public void blueMovesSparrow() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, -1, 0));
		assertEquals(OK, mr);
		checkPieceAt(-1, 0, BLUE, SPARROW);
	}
	
	@Test(expected=HantoException.class)
	public void moveToDisconnectConfiguration() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 0, -1));
	}
	
	@Test(expected=HantoException.class)
	public void moveButterflyToSameHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 0, 0));
	}
	
	@Test(expected=HantoException.class)
	public void moveSparrowToOccupiedHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, 0, 0));
	}
	
	@Test(expected=HantoException.class)
	public void moveFromEmptyHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 1, 0, 1, -1));
	}
	
	@Test(expected=HantoException.class)
	public void tryToMoveTooFar() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, -1, 2));
	}
	
	@Test(expected=HantoException.class)
	public void tryToMoveWrongPieceType() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(BUTTERFLY, 0, -1, -1, 0));
	}
	
	@Test(expected=HantoException.class)
	public void tryToMoveWrongColorPiece() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, 2, 1, 1));
	}
	
	@Test(expected=HantoException.class)
	public void tryToMoveWhenNotEnoughSpace() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(SPARROW, -1, 0), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1), md(SPARROW, 0, 3),
				md(BUTTERFLY, 0, 0, 0, -1));
	}
	
	@Test(expected=HantoException.class)
	public void tryToUseTooManyButterflies() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, -1));
	}
	
	@Test(expected=HantoException.class)
	public void tryToUseTooManySparrows() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(SPARROW, 0, -1), md(SPARROW, 0, 2),
				md(SPARROW, 0, -2), md(SPARROW, 0, 3),
				md(SPARROW, 0, -3), md(SPARROW, 0, 4),
				md(SPARROW, 0, -4), md(SPARROW, 0, 5),
				md(SPARROW, 0, -5), md(SPARROW, 0, 6),
				md(SPARROW, 0, -6));
	}
	
	@Test(expected=HantoException.class)
	public void tryToUsePieceNotInGame() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(CRANE, 0, -1));
	}
	
	@Test
	public void blueWins() throws HantoException
	{
		MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(SPARROW, 1, 1),
				md(SPARROW, 1, -1), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1, 1, 0), md(SPARROW, -1, 2),
				md(SPARROW, -1, 0, -1, 1));
		assertEquals(BLUE_WINS, mr);
	}
	
	@Test
	public void redSelfLoses() throws HantoException
	{
		MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1), md(SPARROW, 1, 2),
				md(SPARROW, 1, -1, 1, 0), md(SPARROW, -1, 2),
				md(SPARROW, -1, 0, -1, 1), md(SPARROW, 1, 2, 1, 1));
		assertEquals(BLUE_WINS, mr);
	}
	
	@Test(expected=HantoException.class)
	public void tryToPlacePieceNextToOpponent() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(SPARROW, -2, 0));
	}
	
	@Test(expected=HantoException.class)
	public void attemptResign() throws HantoException
	{
		setup();
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		game.makeMove(null, null, null);
	}
	
	@Test
	public void newGameRedMovesFirst() throws HantoException
	{
		game = new GammaHantoGame(RED);
		assertEquals( game.getCurrentPlayerState().getColor(), RED);
		makeMoves(md(BUTTERFLY, 0, 0));
		assertEquals( game.getCurrentPlayerState().getColor(), BLUE);
		
	}
	
	@Test
	public void drawAfterTwentyTurns() throws HantoException
	{
		MoveResult mr = makeMoves(
				md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, 1, -1), md(SPARROW, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2),
				md(SPARROW, 1, -1, 0, -1), md(SPARROW, -1, 2, 0, 2),
				md(SPARROW, 0, -1, 1, -1), md(SPARROW, 0, 2, -1, 2));
		assertEquals(DRAW, mr);
	}
	
	
	@Test(expected=HantoException.class)
	public void butterflyNotPlacedByFourthMoveByFirstPlayer() throws HantoException
	{
		makeMoves(md(SPARROW, 0, 0), md(SPARROW, 0, 1),
				md(SPARROW, 0, -1), md(SPARROW, 0, 2),
				md(SPARROW, 0, -2), md(SPARROW, 0, 3),
				md(SPARROW, 0, -3));
	}
	
	@Test(expected=HantoException.class)
	public void butterflyNotPlacedByFourthMoveBySecondPlayer() throws HantoException
	{
		makeMoves(md(SPARROW, 0, 0), md(SPARROW, 0, 1),
				md(BUTTERFLY, 0, -1), md(SPARROW, 0, 2),
				md(SPARROW, 0, -2), md(SPARROW, 0, 3),
				md(SPARROW, 0, -3), md(SPARROW, 0, 4));
	}
	
	@Test(expected=HantoException.class)
	public void tryToMoveAfterGameIsOver() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(SPARROW, 1, 1),
				md(SPARROW, 1, -1), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1, 1, 0), md(SPARROW, -1, 2),
				md(SPARROW, -1, 0, -1, 1), md(SPARROW, 0, 3));
	}
	
	/**
	 * Make sure that the piece at the location is what you expect
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @param color piece color expected
	 * @param type piece type expected
	 */
	private void checkPieceAt(int x, int y, HantoPlayerColor color, HantoPieceType type)
	{
		final HantoPiece piece = game.getPieceAt(makeCoordinate(x, y));
		assertEquals(color, piece.getColor());
		assertEquals(type, piece.getType());
	}
	
	/**
	 * Make a MoveData object given the piece type and the x and y coordinates of the
	 * desstination. This creates a move data that will place a piece (source == null)
	 * @param type piece type
	 * @param toX destination x-coordinate
	 * @param toY destination y-coordinate
	 * @return the desitred MoveData object
	 */
	private MoveData md(HantoPieceType type, int toX, int toY) 
	{
		return new MoveData(type, null, makeCoordinate(toX, toY));
	}
	
	private MoveData md(HantoPieceType type, int fromX, int fromY, int toX, int toY)
	{
		return new MoveData(type, makeCoordinate(fromX, fromY), makeCoordinate(toX, toY));
	}
	
	/**
	 * Make the moves specified. If there is no exception, return the move result of
	 * the last move.
	 * @param moves
	 * @return the last move result
	 * @throws HantoException
	 */
	private MoveResult makeMoves(MoveData... moves) throws HantoException
	{
		MoveResult mr = null;
		for (MoveData md : moves) {
			mr = game.makeMove(md.type, md.from, md.to);
		}
		return mr;
	}
	
}
