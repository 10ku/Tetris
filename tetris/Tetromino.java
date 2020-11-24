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
	
	public void rotate90()
	{
		int row = 2;
		int column = tetromino[0].length;
		int[][] rotatedMatrix = new int[row][column];
		int[][] rotationMatrix = {
			{ 0,1}, 
			{-1,0}, 
				 };
		
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