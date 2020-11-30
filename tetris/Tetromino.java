package tetris;

public class Tetromino
{
	public int[][] tetromino;
	public int xOffset = 5;
	public int yOffset = 2;
	
	public Tetromino(int[][] tetromino)
	{
		this.tetromino = tetromino;
	}
	
	public void rotateTetromino90(boolean counterclockwise)
	{
		int row = 2;
		int column = tetromino[0].length;
		int[][] rotatedMatrix = new int[row][column];
		int[][] clockwiseMatrix = {
			{0,-1}, 
			{1, 0}, 
				 };
		
		int[][] counterclockwiseMatrix = {
			{ 0,1}, 
			{-1,0}, 
				 };
		
		int[][] rotationMatrix = counterclockwise ? counterclockwiseMatrix : clockwiseMatrix;
		
		for (int currRow = 0; currRow < row; currRow++)
		{
			for (int currColumn = 0; currColumn < column; currColumn++)
			{
				rotatedMatrix[currRow][currColumn] = rotationMatrix[currRow][0] * tetromino[0][currColumn] + rotationMatrix[currRow][1] * tetromino[1][currColumn];
			}
		}

		tetromino = rotatedMatrix;
	}
}