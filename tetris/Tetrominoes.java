package tetris;

import java.util.Random;

public class Tetrominoes
{
	private final static int[][] iBlock = {
		{0,-1,1,2}, //x
		{0, 0,0,0}, //y
				 };
				
	private final static int[][] jBlock = {
		{0,-1,1,1}, 
		{0, 0,0,1}, 
				 };
	
	private final static int[][] lBlock = {
		{0,1,-1,-1}, 
		{0,0, 0, 1}, 
				  };
				
	private final static int[][] oBlock = {
		{0,1,0,1}, 
		{0,0,1,1}, 
				};
				
	private final static int[][] sBlock = {
		{0,1,0,-1}, 
		{0,0,1, 1}, 
				 };
	
	private final static int[][] tBlock = {
		{0,-1,0,1}, 
		{0, 0,1,0}, 
				 };
				
	private final static int[][] zBlock = {
		{0,-1,0,1}, 
		{0, 0,1,1}, 
				 };
	
	private Random random = new Random();
	
	public Tetrominoes()
	{
		
	}
	
	public int[][] returnRandomTetromino()
	{
		switch (random.nextInt(7))
		{
		case 0:
			return iBlock.clone();
		case 1:
			return jBlock.clone();
		case 2:
			return lBlock.clone();
		case 3:
			return oBlock.clone();
		case 4:
			return sBlock.clone();
		case 5:
			return tBlock.clone();
		case 6:
			return zBlock.clone();
		default:
			return iBlock.clone();
		}
	}
}