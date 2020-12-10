package tetris;

public class GlobalConstants
{
	//boolean[] input indices
	public static final byte NONE = -1;
	public static final byte UP = 0;
	public static final byte DOWN = 1;
	public static final byte LEFT = 2;
	public static final byte RIGHT = 3;
	
	//tryLockingTetromino returns
	public static final byte FAILED = 0;
	public static final byte SUCCEEDED = 1;
	public static final byte GAMEOVER = 2;
}