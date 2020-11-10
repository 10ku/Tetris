package tetris;

public class Tetromino
{
	public int[][] tetromino;
	public int xOffset = 0;
	public int yOffset = 0;
	
	public Tetromino(int[][] tetromino)
	{
		this.tetromino = tetromino;
	}
}