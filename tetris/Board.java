package tetris;

public class Board
{
	//gameBoard values
	private static final byte EMPTY_SPACE = 0;
	private static final byte WALL = 1;
	private static final byte FLOOR = 2;
	private static final byte TETROMINO = 3;
	private static final byte LOCKED_TETROMINO = 4;
	private static final byte LINE = 5;

	//gameBoard size
	private static final byte BOARD_HEIGHT = 19;
	private static final byte BOARD_LENGTH = 12;

	//graphicsBoard character array
	private static final String CHARACTER_GRAPHICS = ".|=X+#";

	public int[][] gameBoard;
	public char[][] graphicsBoard;
	public char[][] nextTetrominoGraphic;
	public boolean hasLines = false;
	public int score = 0;
	public int level = 0;
	public int lines = 0;
	public int difficultyCounter = 0;

	public Board()
	{
		nextTetrominoGraphic = new char[4][4];
		gameBoard = new int[BOARD_HEIGHT][BOARD_LENGTH];
		graphicsBoard = new char[gameBoard.length][gameBoard[0].length];
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
					gameBoard[i][j] = WALL;
				}
				else if (i == gameBoard.length - 1)
				{
					gameBoard[i][j] = FLOOR;
				}
				else if (gameBoard[i][j] != LOCKED_TETROMINO)
				{
					gameBoard[i][j] = EMPTY_SPACE;
				}
			}
		}
	}

	public void setGraphicsBoard()
	{
		for (int i = 0; i < graphicsBoard.length; i++)
		{
			for (int j = 0; j < graphicsBoard[0].length; j++)
			{
				graphicsBoard[i][j] = CHARACTER_GRAPHICS.charAt(gameBoard[i][j]);
			}
		}
	}

	public int tryLockingTetromino(Tetromino tetromino, int lastMove)
	{
		int x = 0;
		int y = 0;
		int lockTetromino = 0;

		//Detect collision and resolve
		for (int i = 0; i < 4; i++)
		{
			x = tetromino.tetromino[0][i];
			y = tetromino.tetromino[1][i];

			if (y + tetromino.yOffset < 0)
			{
				return GlobalConstants.GAMEOVER;
			}
			else if (gameBoard[y + tetromino.yOffset][x + tetromino.xOffset] != EMPTY_SPACE)
			{
				switch (lastMove)
				{
				case GlobalConstants.UP:
					tetromino.rotateTetromino90(true);
					i = -1;
					break;
				case GlobalConstants.NONE:
				case GlobalConstants.DOWN:
					lockTetromino = GlobalConstants.SUCCEEDED;
					tetromino.yOffset--;
					i = -1;
					break;
				case GlobalConstants.LEFT:
					tetromino.xOffset++;
					i = -1;
					break;
				case GlobalConstants.RIGHT:
					tetromino.xOffset--;
					i = -1;
					break;
				}
				lastMove = GlobalConstants.NONE;
			}
		}
		//Draw tetromino
		insertTetromino(tetromino, lockTetromino);
		return lockTetromino;
	}

	public void clearLines()
	{
		int linesCleared = 0;

		for (int i = 0; i < gameBoard.length; i++)
		{
			if (gameBoard[i][1] == LINE)
			{
				lines++;
				linesCleared++;
				difficultyCounter++;
				for (int j = 0; j < gameBoard[0].length; j++)
				{
					gameBoard[i][j] = EMPTY_SPACE;
				}

				moveLinesDownTo(i);
			}
		}

		switch (linesCleared)
		{
		case 1:
			score += 40 * (level + 1);
			break;
		case 2:
			score += 100 * (level + 1);
			break;
		case 3:
			score += 300 * (level + 1);
			break;
		case 4:
			score += 1200 * (level + 1);
			break;
		default:
			score += 57005 * (level + 1);
			break;
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
				nextTetrominoGraphic[i][j] = CHARACTER_GRAPHICS.charAt(EMPTY_SPACE);
			}
		}

		for (int k = 0; k < 4; k++)
		{
			x = tetromino.tetromino[0][k];
			y = tetromino.tetromino[1][k];

			nextTetrominoGraphic[y + 1][x + 1] = CHARACTER_GRAPHICS.charAt(TETROMINO);
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

	private void insertTetromino(Tetromino tetromino, int lockTetromino)
	{
		int x = 0;
		int y = 0;

		for (int i = 0; i < 4; i++)
		{
			x = tetromino.tetromino[0][i];
			y = tetromino.tetromino[1][i];

			if (lockTetromino == GlobalConstants.FAILED)
			{
				gameBoard[y + tetromino.yOffset][x + tetromino.xOffset] = TETROMINO;
			}
			else
			{
				gameBoard[y + tetromino.yOffset][x + tetromino.xOffset] = LOCKED_TETROMINO;
			}
		}

		if (lockTetromino == GlobalConstants.SUCCEEDED)
		{
			checkForLines();
		}
	}

	private void checkForLines()
	{
		int num = 0;

		for (int i = 0; i < gameBoard.length; i++)
		{
			num = 0;
			for (int j = 0; j < gameBoard[0].length; j++)
			{
				if (gameBoard[i][j] == LOCKED_TETROMINO)
				{
					num++;
				}
				if (num == gameBoard[0].length - 2)
				{
					hasLines = true;
					for (int k = 1; k < gameBoard[0].length - 1; k++)
					{
						gameBoard[i][k] = LINE;
					}
				}
			}
		}
	}

	private void moveLinesDownTo(int i)
	{
		while (i != 0)
		{
			for (int j = 0; j < gameBoard[0].length; j++)
			{
				gameBoard[i][j] = gameBoard[i-1][j];
			}
			i--;
		}
	}
}
