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
	public char[][] nextTetrominoGraphic;
	public int score = 0;
	public int level = 0;
	public int lines = 0;
	public int nextTetromino = 0;

	public Board()
	{
		nextTetrominoGraphic = new char[4][4];
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
	
	
	public void nextTetromino(Tetromino tetromino)
	{
		int x = 0;
		int y = 0;
		
		for (int i = 0; i < nextTetrominoGraphic.length; i++)
		{
			for (int j = 0; j < nextTetrominoGraphic[0].length; j++)
			{
				nextTetrominoGraphic[i][j] = '.';
			}
		}
		
		for (int k = 0; k < 4; k++)
		{
			x = tetromino.tetromino[0][k];
			y = tetromino.tetromino[1][k];
			
			nextTetrominoGraphic[y + 1][x + 1] = 'X';
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
				if (j == graphicsBoard[0].length - 1)
				{
					switch (i)
				{
					case 2:
						boardString += "        Score\n";
						break;
					case 3:
					boardString += "        " + score + "\n";
						break;
					case 6:
						boardString += "        Level\n";
						break;
					case 7:
						boardString += "        " + level + "\n";
						break;
					case 9:
						boardString += "        Lines\n";
						break;
					case 10:
						boardString += "        " + lines + "\n";
						break;
					case 13:
						boardString += "        ";
						for (int k = 0; k < nextTetrominoGraphic[0].length; k++)
				{
							boardString += nextTetrominoGraphic[0][k];
				}
						boardString += "\n";
						break;
					case 14:
						boardString += "        ";
						for (int k = 0; k < nextTetrominoGraphic[0].length; k++)
				{
							boardString += nextTetrominoGraphic[1][k];
				}
						boardString += "\n";
						break;
					case 15:
						boardString += "        ";
						for (int k = 0; k < nextTetrominoGraphic[0].length; k++)
		{
							boardString += nextTetrominoGraphic[2][k];
				}
						boardString += "\n";
						break;
					case 16:
						boardString += "        ";
						for (int k = 0; k < nextTetrominoGraphic[0].length; k++)
			{
							boardString += nextTetrominoGraphic[3][k];
				}
						boardString += "\n";
						break;
					default:
					boardString += "\n";
						break;
					}
				}
			}
		}
		return boardString;
	}
}
