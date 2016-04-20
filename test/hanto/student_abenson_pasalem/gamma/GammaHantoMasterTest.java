/**
 * Test file for GammaHantoGame.
 *
 */


package hanto.student_abenson_pasalem.gamma;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.CRAB;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
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
import hanto.common.MoveResult;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;
import hanto.student_abenson_pasalem.common.HantoGameFactory;
import hanto.student_abenson_pasalem.common.Board.HantoBoardImpl;
import hanto.student_abenson_pasalem.gamma.GammaHantoGame;

/**
 * Gamma Hanto Test.
 * @author Peter
 *
 */
public class GammaHantoMasterTest {
	
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
		final List<HantoCoordinate> adjacencies = HantoBoardImpl.getAdjacentSpaces(makeCoordinate(-1,0));
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
	 * Ensures that blue will play their butterfly by turn 4,
	 * or an exception will be thrown
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class)  // 9
	public void blueMustPlayButterflyByFourthTurn() throws HantoException
	{
		setup();
		assertEquals(0, game.getRedTurns());
		assertEquals(0, game.getBlueTurns());

		final MoveResult bluemove1 = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		assertEquals(1, game.getBlueTurns());
		
		final MoveResult redmove1 = game.makeMove(SPARROW, null, makeCoordinate(0,1));
		assertEquals(OK, redmove1);
		assertEquals(1, game.getRedTurns());
		
		final MoveResult bluemove2 = game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		assertEquals(OK, bluemove2);
		assertEquals(2, game.getBlueTurns());
		
		final MoveResult redmove2 = game.makeMove(SPARROW, null, makeCoordinate(1,1));
		assertEquals(OK, redmove2);
		assertEquals(2, game.getRedTurns());
		
		final MoveResult bluemove3 = game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		assertEquals(OK, bluemove3);
		assertEquals(3, game.getBlueTurns());

		final MoveResult redmove3 = game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		assertEquals(OK, redmove3);
		
		final MoveResult bluemove4 = game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
	}
	
	/**
	 * Ensures that red will play their butterfly by turn 4,
	 * or an exception will be thrown
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class) // 10
	public void redMustPlayButterflyByFourthTurn() throws HantoException
	{
		setup();
		assertEquals(0, game.getRedTurns());
		assertEquals(0, game.getBlueTurns());

		final MoveResult bluemove1 = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		assertEquals(1, game.getBlueTurns());
		
		final MoveResult redmove1 = game.makeMove(SPARROW, null, makeCoordinate(0,1));
		assertEquals(OK, redmove1);
		assertEquals(1, game.getRedTurns());
		
		final MoveResult bluemove2 = game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		assertEquals(OK, bluemove2);
		assertEquals(2, game.getBlueTurns());
		
		final MoveResult redmove2 = game.makeMove(SPARROW, null, makeCoordinate(1,1));
		assertEquals(OK, redmove2);
		assertEquals(2, game.getRedTurns());
		
		final MoveResult bluemove3 = game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		assertEquals(OK, bluemove3);
		assertEquals(3, game.getBlueTurns());

		final MoveResult redmove3 = game.makeMove(SPARROW, null, makeCoordinate(0, 2));
		assertEquals(OK, redmove3);
		
		final MoveResult bluemove4 = game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
		assertEquals(OK, bluemove4);

		final MoveResult redmove4 = game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		assertEquals(OK, redmove4);
	}
	
	/**
	 * Ensures that if 40 total turns are played and the butterflies aren't surrounded,
	 * the game ends in a draw
	 * @throws HantoException
	 */
	@Test // 11
	public void gameEndsInDrawAfter40Turns() throws HantoException
	{
		setup();
		final MoveResult bluebutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult redbutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		
		for(int i = 1; i< 19; i++){
			final MoveResult blueMove = game.makeMove(SPARROW, null, makeCoordinate((i * -1), 0));
			final MoveResult redMove = game.makeMove(SPARROW, null, makeCoordinate(i, 1));
		}
		
		final MoveResult blueFinal = game.makeMove(SPARROW, null, makeCoordinate(-18, 1));
		final MoveResult redFinal = game.makeMove(SPARROW, null, makeCoordinate(19, 1));
		assertEquals(DRAW, redFinal);
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
		final MoveResult bluemovebutterfly = game.makeMove(null, makeCoordinate(0,0), makeCoordinate(1, -1));
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
		final MoveResult bluemovebutterfly = game.makeMove(null, makeCoordinate(0,0), makeCoordinate(1, -10));
	}
	
	/**
	 * Blue isolates a sparrow when moving. 
	 * In this test, blue places a butterfly, followed by three sparrows in a line.
	 * The middle sparrow is moved, downward, so that it is still adjacent to a piece, 
	 * but it causes one of the sparrows in the line to be isolated. This should throw an exception
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class)  // 18
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
		final MoveResult isolatingMove = game.makeMove(null, makeCoordinate(-1,0), makeCoordinate(-1,-1));
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
		final MoveResult bluemovebutterfly = game.makeMove(null, makeCoordinate(0,0), makeCoordinate(-1, 0));
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
<<<<<<< HEAD
=======
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
		final MoveResult finalMove = game.makeMove( null, makeCoordinate(0,0), makeCoordinate(1,-1));
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
	 * 
	 * Checks that a draw occurs when a move results in both the
	 * red and blue butterflies to be surrounded.
	 * @throws HantoException
	 */
	@Test	// 23
	public void drawnGame() throws HantoException
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
		game.makeMove(null, makeCoordinate(-1, 2), makeCoordinate(-1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0)); 	// Move 6
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		assertEquals(DRAW, game.makeMove(null, makeCoordinate(2, -1), makeCoordinate(1, 0)));
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
		game.makeMove(null, makeCoordinate(-1, 2), makeCoordinate(-1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0)); 	// Move 6
		game.makeMove(SPARROW, null, makeCoordinate(0, 3));
		assertEquals(RED_WINS, game.makeMove(null, makeCoordinate(2, -1), makeCoordinate(1, 0)));
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
		game.makeMove(null, makeCoordinate(-1, 2), makeCoordinate(-1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0)); 	// Move 6
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
		game.makeMove(null, makeCoordinate(0, -1), makeCoordinate(-1, -1)); // Move 7
		game.makeMove(SPARROW, null, makeCoordinate(2, 1));
		assertEquals(BLUE_WINS, game.makeMove(null, makeCoordinate(2, -1), makeCoordinate(1, 0)));	
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
	
	
}
