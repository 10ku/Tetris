package tetris;

public class Board
{
	/*
	 * 0 - Empty Space
	 * 1 - Wall
	 * 2 - Floor
	 * 3 - Tetromino
	 * 4 - Locked Tetromino
	 */
	public int[][] gameBoard;
	public char[][] graphicsBoard;
	public int score = 0;
	public int level = 0;
	public int lines = 0;
	public int nextTetromino = 0;

	public Board()
	{
		graphicsBoard = new char[19][12];
		gameBoard = new int[graphicsBoard.length][graphicsBoard[0].length];
		this.setGameBoard();
	}
	
	public void setGameBoard()
	{
		for (int i = 0; i < gameBoard.length; i++)
		{
			for (int j = 0; j < gameBoard[0].length; j++)
			{
				if (j == 0 || j == gameBoard[0].length - 1)
				{
					gameBoard[i][j] = 1; 
				}
				else if (i == gameBoard.length - 1)
				{
					gameBoard[i][j] = 2;
				}
				else if (gameBoard[i][j] != 4)
				{
					gameBoard[i][j] = 0;
				}
			}
		}
	}
	
	public void setBoardGraphics()
	{
		for (int i = 0; i < graphicsBoard.length; i++)
		{
			for (int j = 0; j < graphicsBoard[0].length; j++)
			{
				switch (gameBoard[i][j])
				{
				case 0:
					graphicsBoard[i][j] = '.';
					break;
				case 1:
					graphicsBoard[i][j] = '|';
					break;
				case 2:
					graphicsBoard[i][j] = '=';
					break;
				case 3:
					graphicsBoard[i][j] = 'X';
					break;
				case 4:
					graphicsBoard[i][j] = '+';
					break;
				default:
					graphicsBoard[i][j] = '*';
					break;
				}
			}
		}
	}
	
	public boolean tryInsertingTetromino(Tetromino tetromino)
	{
		int x = 0;
		int y = 0;
		boolean lockTetromino = false;
		
		for (int i = 0; i < 4; i++)
		{
			x = tetromino.tetromino[0][i];
			y = tetromino.tetromino[1][i];
			
			if (gameBoard[y + tetromino.yOffset][x + tetromino.xOffset] == 1)
			{
				if (tetromino.xOffset <= 5)
				{
					tetromino.xOffset++;
				}
				else
				{
					tetromino.xOffset--;
				}
				i = 0;
			}
			else if (gameBoard[y + tetromino.yOffset][x + tetromino.xOffset] == 4)
			{
				lockTetromino = true;
				tetromino.yOffset--;
				i = 0;
			}
			else if (gameBoard[y + tetromino.yOffset][x + tetromino.xOffset] == 2)
			{
				lockTetromino = true;
				tetromino.yOffset--;
				break;
			}
		}
		insertTetromino(tetromino, lockTetromino);
		return lockTetromino;
	}
	
	private void insertTetromino(Tetromino tetromino, boolean lockTetromino)
		{
		int x = 0;
		int y = 0;
		
		for (int i = 0; i < 4; i++)
			{
			x = tetromino.tetromino[0][i];
			y = tetromino.tetromino[1][i];
			
			if (lockTetromino == false)
				{
				gameBoard[y + tetromino.yOffset][x + tetromino.xOffset] = 3;
				}
				else
				{
				gameBoard[y + tetromino.yOffset][x + tetromino.xOffset] = 4;
			}
		}
	}
	
	public String toString()
	{
		String boardString = "";
		
		for (int i = 0; i < graphicsBoard.length; i++)
		{
			for (int j = 0; j < graphicsBoard[0].length; j++)
			{
				//Draw before board
				if (j == 0)
				{
					boardString += "                        ";
				}
				//Draw board
				boardString += graphicsBoard[i][j];
				//Draw after board
				if (i == 2 && j == graphicsBoard[0].length - 1)
				{
					boardString += "        Score\n";
				}
				else if (i == 3 && j == graphicsBoard[0].length - 1)
				{
					boardString += "        " + score + "\n";
				}
				else if (i == 6 && j == graphicsBoard[0].length - 1)
				{
					boardString += "        Level\n";
				}
				else if (i == 7 && j == graphicsBoard[0].length - 1)
				{
					boardString += "        " + level + "\n";
				}
				else if (i == 9 && j == graphicsBoard[0].length - 1)
		{
					boardString += "        Lines\n";
				}
				else if (i == 10 && j == graphicsBoard[0].length - 1)
			{
					boardString += "        " + lines + "\n";
				}
				else if (j == graphicsBoard[0].length - 1)
				{
					boardString += "\n";
				}
			}
		}
		return boardString;
	}
}
