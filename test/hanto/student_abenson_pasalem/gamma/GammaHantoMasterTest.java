package hanto.student_abenson_pasalem.gamma;

import static common.HantoPieceType.BUTTERFLY;
import static common.HantoPieceType.CRAB;
import static common.HantoPieceType.SPARROW;
import static common.HantoPlayerColor.BLUE;
import static common.MoveResult.DRAW;
import static common.MoveResult.OK;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import common.HantoCoordinate;
import common.HantoException;
import common.HantoGameID;
import common.HantoPiece;
import common.MoveResult;
import hanto.student_abenson_pasalem.common.HantoGameFactory;
import student_abenson_pasalem.gamma.GammaHantoGame;

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
	 * Ensures that if red tries to place their piece in a space not adjacent to
	 * another piece, an exception will be thrown
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class) // 5
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
	@Test (expected = HantoException.class) // 6
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
	@Test (expected = HantoException.class) // 7
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
	@Test (expected = HantoException.class)  // 8
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
		
		final MoveResult bluemove2 = game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		assertEquals(OK, bluemove2);
		assertEquals(2, game.getBlueTurns());
		
		final MoveResult redmove2 = game.makeMove(SPARROW, null, makeCoordinate(1,-1));
		assertEquals(OK, redmove2);
		assertEquals(2, game.getRedTurns());

		final MoveResult bluemove3 = game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		assertEquals(OK, bluemove3);
		assertEquals(3, game.getBlueTurns());
		
		final MoveResult redmove3 = game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		assertEquals(OK, redmove3);
		
		final MoveResult bluemove4 = game.makeMove(SPARROW, null, makeCoordinate(2, -1));
	}
	
	/**
	 * Ensures that red will play their butterfly by turn 4,
	 * or an exception will be thrown
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class) // 9

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
		
		final MoveResult bluemove2 = game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		assertEquals(OK, bluemove2);
		assertEquals(2, game.getBlueTurns());
		
		final MoveResult redmove2 = game.makeMove(SPARROW, null, makeCoordinate(1,-1));
		assertEquals(OK, redmove2);
		assertEquals(2, game.getRedTurns());
		
		final MoveResult bluemove3 = game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		assertEquals(OK, bluemove3);
		assertEquals(3, game.getBlueTurns());

		final MoveResult redmove3 = game.makeMove(SPARROW, null, makeCoordinate(2, 0));
		assertEquals(OK, redmove3);
		
		final MoveResult bluemove4 = game.makeMove(BUTTERFLY, null, makeCoordinate(2, -1));
		assertEquals(OK, bluemove4);

		final MoveResult redmove4 = game.makeMove(SPARROW, null, makeCoordinate(3, 0));
		assertEquals(OK, redmove4);
	}
	
	/**
	 * Ensures that if 40 total turns are played and the butterflies aren't surrounded,
	 * the game ends in a draw
	 * @throws HantoException
	 */
	@Test // 12
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
	@Test (expected = common.HantoException.class) // 13
	public void firstMoveIllegalSpot() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	}
	
	/**
	 * Throws an exception when blue tries to play a butterfly twice	
	 * @throws HantoException
	 */
	@Test (expected = common.HantoException.class) // 14
	public void bluebutterflyPlayedTwice() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		final MoveResult redmove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		assertEquals(OK, redmove1);
		final MoveResult bluemove2 = game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	}
	
	@Test (expected = common.HantoException.class) // 15
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
	 * Checking if a spot is moved to twice
	 * @throws HantoException
	 */
	@Test (expected = common.HantoException.class) // 16
	public void spotMovedToTwice() throws HantoException
	{
		setup();
		final MoveResult bluemove1 = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, bluemove1);
		final MoveResult redmove1 = game.makeMove(SPARROW, null, makeCoordinate(0,0));
	}
	
	/**
	 * Simulates blue playing butterfly and moving it on the next turn
	 * @throws HantoException
	 */
	@Test
	public void blueMovesButterflyToValidSpace() throws HantoException
	{
		setup();
		final MoveResult bluebutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult redbutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(1,0));
		final MoveResult bluemovebutterfly = game.makeMove(null, makeCoordinate(0,0), makeCoordinate(1, -1));
		assertEquals(OK, bluemovebutterfly);
	}
	
	/**
	 * Simulates blue playing butterfly and moving it to a non adjacent space
	 * @throws HantoException
	 */
	@Test (expected = HantoException.class)  // 8
	public void blueMovesButterflyToInvalidSpace() throws HantoException
	{
		setup();
		final MoveResult bluebutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult redbutterfly = game.makeMove(BUTTERFLY, null, makeCoordinate(1,0));
		final MoveResult bluemovebutterfly = game.makeMove(null, makeCoordinate(0,0), makeCoordinate(1, -10));
	}
}
