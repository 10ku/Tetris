package tetris;

public class Tetrominoes
{
	private final static int[][] iBlock = {
			{0,0,0,0}, //x
			{0,1,2,3}, //y
					};
					
	private final static int[][] jBlock = {
			{0,0,1,2}, 
			{0,1,0,0}, 
					};
		
	private final static int[][] lBlock = {
			{0,1,2,2}, 
			{0,0,0,1}, 
					};
					
	private final static int[][] oBlock = {
			{0,0,1,1}, 
			{0,1,0,1}, 
					};
					
	private final static int[][] sBlock = {
			{0,1,1,2}, 
			{0,0,1,1}, 
					};
		
	private final static int[][] tBlock = {
			{0,1,1,2}, 
			{0,0,1,0}, 
					};
					
	private final static int[][] zBlock = {
			{0,1,1,2}, 
			{1,1,0,0}, 
					};
	
	
	public Tetrominoes()
	{
		
	}
	
	public int[][] rotate90(int[][] secondMatrix)
	{
		int row = 2;
		int column = secondMatrix[0].length;
		int[][] rotatedMatrix = new int[row][column];
		int[][] firstMatrix = {
			{ 0,1}, 
			{-1,0}, 
				 };
		
		for (int currRow = 0; currRow < row; currRow++)
		{
			for (int currColumn = 0; currColumn < column; currColumn++)
			{
				rotatedMatrix[currRow][currColumn] = firstMatrix[currRow][0] * secondMatrix[0][currColumn] + firstMatrix[currRow][1] * secondMatrix[1][currColumn];
			}
		}
		return rotatedMatrix;
	}
}