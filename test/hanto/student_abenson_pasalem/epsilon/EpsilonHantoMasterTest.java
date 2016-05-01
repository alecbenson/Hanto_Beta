package hanto.student_abenson_pasalem.epsilon;

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.student_abenson_pasalem.Board.HantoBoardImpl;
import hanto.student_abenson_pasalem.PieceValidator.IPieceValidator;
import hanto.student_abenson_pasalem.PieceValidator.JumpValidator;
import hanto.student_abenson_pasalem.PieceValidator.WalkValidator;
import hanto.student_abenson_pasalem.common.BaseHantoGame;
import hanto.student_abenson_pasalem.common.HantoCoordinateImpl;
import hanto.student_abenson_pasalem.common.HantoDirection;
import hanto.student_abenson_pasalem.common.HantoGameFactory;

public class EpsilonHantoMasterTest {
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
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
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
	
	private static HantoGameFactory factory;
	private HantoGame game;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO, BLUE);
	}
	
	//1
	@Test(expected=HantoPrematureResignationException.class)
	public void cannotResignStillValidMoves() throws HantoException
	{
		setup();
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		boolean canPlacePieces = ((BaseHantoGame) game).getBoard().canPlacePieces(BLUE);
		boolean canMovePieces =  ((BaseHantoGame) game).getBoard().canMovePieces(BLUE);
		assertEquals(true, canPlacePieces);
		assertEquals(true, canMovePieces);
		game.makeMove(null, null, null);
	}
	
	//2
	@Test(expected=HantoException.class)
	public void flySparrowMoreThan4Spaces() throws HantoException
	{
		setup();
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(SPARROW, 1, 1),
				md(CRAB, 0, -1), md(CRAB, 0, 2),
				md(CRAB, 0, -2), md(CRAB, 0, 3),
				md(SPARROW, 1, -2), md(SPARROW, -1, 3),
				md(SPARROW, 1, -2, 0, 4));
	}
	
	//3
	//This should throw an exception because you cannot jump over an empty square. 
	@Test(expected=HantoException.class)
	public void jumpHorseOverEmptySpaces() throws HantoException
	{
		setup();
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(CRAB, 0, -1), md(CRAB, 0, 2),
				md(HORSE, -1, 0), md(SPARROW, 1,1));
		game.makeMove(HORSE, new HantoCoordinateImpl(-1, 0), new HantoCoordinateImpl(-1, 2));
	}
	
	//4
	//This should NOT throw an exception
	@Test
	public void jumpHorseOverButterflyValid() throws HantoException
	{
		setup();
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(CRAB, 0, -1), md(CRAB, 0, 2),
				md(HORSE, -1, 0), md(SPARROW, 1,1),
				md(HORSE, -1, 0, 1, 0));
	}
	
	//5
	//Resignation no moves left
	@Test
	public void validResignationNoMovesLeft() throws HantoException
	{
		setup();
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 1, 0),
				md(SPARROW, -1, 1), md(BUTTERFLY, 1, 0, 0, 1),
				md(CRAB, 0, -1), md(BUTTERFLY, 0, 1, 1, 0),
				md(SPARROW, -1, 0), md(BUTTERFLY, 1, 0, 0, 1),
				md(CRAB, 1, -2), md(BUTTERFLY, 0, 1, 1, 0),
				md(SPARROW, -1, 0, 2, 0));
				boolean canPlacePieces = ((BaseHantoGame) game).getBoard().canPlacePieces(RED);
				boolean canMovePieces =  ((BaseHantoGame) game).getBoard().canMovePieces(RED);
				assertEquals(false, canPlacePieces);
				assertEquals(false, canMovePieces);
				//This should work because blue has no pieces and cannot move without causing disconnects
				game.makeMove(null, null, null);
	}
	
	//6
	@Test(expected=HantoException.class)
	public void walkCrabTooFar() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, 0, -1), md(SPARROW, 1, 1),
				md(SPARROW, 1, -2), md(SPARROW, 2, 0),
				md(CRAB, 2, -2), md(CRAB, 3,0),
				md(CRAB, 0, -2), md(CRAB, 4, 0),
				md(CRAB,0,-2,-1, 1), md(CRAB,5,0));
	}
	
	//7
	@Test
	public void northWestDirection(){
		HantoCoordinateImpl origin = new HantoCoordinateImpl(1,-2);
		HantoCoordinateImpl other = new HantoCoordinateImpl(-1,0);
		assertEquals(HantoDirection.NORTHWEST, origin.direction(other));
	}
	
	//9
	@Test
	public void southEastDirection(){
		HantoCoordinateImpl origin = new HantoCoordinateImpl(-1,2);
		HantoCoordinateImpl other = new HantoCoordinateImpl(0,1);
		assertEquals(HantoDirection.SOUTHEAST, origin.direction(other));
	}
	
	//10
	@Test
	public void northDirection(){
		HantoCoordinateImpl origin = new HantoCoordinateImpl(0,0);
		HantoCoordinateImpl other = new HantoCoordinateImpl(0,1);
		assertEquals(HantoDirection.NORTH, origin.direction(other));
	}
	
	//11
	@Test
	public void southDirection(){
		HantoCoordinateImpl origin = new HantoCoordinateImpl(0,0);
		HantoCoordinateImpl other = new HantoCoordinateImpl(0,-1);
		assertEquals(HantoDirection.SOUTH, origin.direction(other));
	}
	
	//12
	@Test
	public void westDirection(){
		HantoCoordinateImpl origin = new HantoCoordinateImpl(0,0);
		HantoCoordinateImpl other = new HantoCoordinateImpl(-1,0);
		assertEquals(HantoDirection.WEST, origin.direction(other));
	}
	
	//13
	@Test
	public void eastDirection(){
		HantoCoordinateImpl origin = new HantoCoordinateImpl(0,0);
		HantoCoordinateImpl other = new HantoCoordinateImpl(1,0);
		assertEquals(HantoDirection.EAST, origin.direction(other));
	}
	
	//14
	@Test
	public void noDirection(){
		HantoCoordinateImpl origin = new HantoCoordinateImpl(0,0);
		HantoCoordinateImpl other = new HantoCoordinateImpl(1,1);
		assertEquals(HantoDirection.NONE, origin.direction(other));
	}
	
	//15
	@Test (expected=HantoException.class)
	public void jumpHorseNotStraightLine() throws HantoException
	{
		setup();
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(CRAB, 0, -1), md(CRAB, 0, 2),
				md(HORSE, -1, 0), md(SPARROW, 1,1),
				md(HORSE, -1, 0, 1, -1));
	}
	
	//16
	@Test (expected=HantoException.class)
	public void jumpHorseNotStraightLine2() throws HantoException
	{
		setup();
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(CRAB, 0, -1), md(CRAB, 0, 2),
				md(HORSE, -1, 0), md(SPARROW, 1,1),
				md(HORSE, -1, 0, 0, 3));
	}
	
	//17
	@Test
	public void getCoordsInRadius() throws HantoException
	{
		HantoCoordinateImpl origin = new HantoCoordinateImpl(0,0);
		List<HantoCoordinateImpl> radius = origin.getCoordsInRadius(2);
		List<HantoCoordinateImpl> shouldContain = Arrays.asList(
				new HantoCoordinateImpl(0,2),
				new HantoCoordinateImpl(1,1),
				new HantoCoordinateImpl(1,0),
				new HantoCoordinateImpl(-1,1),
				new HantoCoordinateImpl(-1,2),
				new HantoCoordinateImpl(2,0),
				new HantoCoordinateImpl(2,-1),
				new HantoCoordinateImpl(1,-1),
				new HantoCoordinateImpl(0,1),
				new HantoCoordinateImpl(2,-2),
				new HantoCoordinateImpl(1,-2),
				new HantoCoordinateImpl(0,-1),
				new HantoCoordinateImpl(0,-2),
				new HantoCoordinateImpl(-1,-1),
				new HantoCoordinateImpl(-1,0),
				new HantoCoordinateImpl(-2,0),
				new HantoCoordinateImpl(-2,1),
				new HantoCoordinateImpl(-2,2));
		assertEquals(radius, shouldContain);
	}
	
	//18
	public void getValidJumpMoves() throws HantoException{
		setup();
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(CRAB, 0, -1), md(CRAB, 0, 2),
				md(HORSE, -1, 0), md(SPARROW, 1,1));
		
		IPieceValidator validator = new JumpValidator(Integer.MAX_VALUE);
		List<HantoCoordinateImpl> validMoves = validator.getValidMoves(
				(HantoBoardImpl) ((BaseHantoGame) game).getBoard(), new HantoCoordinateImpl(-1, 0));
		List<HantoCoordinateImpl> shouldContain = Arrays.asList(
		new HantoCoordinateImpl(1,-2),
		new HantoCoordinateImpl(1,0));
		assertEquals(validMoves, shouldContain);
	}
	
	//19
	@Test
	public void getValidJumpMoves2() throws HantoException{
		setup();
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(CRAB, 0, -1), md(CRAB, 0, 2),
				md(HORSE, -1, 0), md(SPARROW, 1,1),
				md(CRAB, 1, -1), md(SPARROW, 1,1,1,0));
		
		IPieceValidator validator = new JumpValidator(Integer.MAX_VALUE);
		List<HantoCoordinateImpl> validMoves = validator.getValidMoves(
				(HantoBoardImpl) ((BaseHantoGame) game).getBoard(), new HantoCoordinateImpl(-1, 0));
		List<HantoCoordinateImpl> shouldContain = Arrays.asList(
		new HantoCoordinateImpl(1,-2),
		new HantoCoordinateImpl(2,0));
		assertEquals(validMoves, shouldContain);
	}
}
