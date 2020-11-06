package tetris;

public class Board
{
	public char[][] board;

	public Board()
	{
		board = new char[][]{
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'},
				{'|','.','.','.','.','.','.','.','.','.','.','|'}
																};
	}
	
	public String toString()
	{
		String boardString = "";
		
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[0].length; j++)
			{
				boardString += board[i][j];
				if (j + 1 == board[0].length)
				{
					boardString += "\n";
				}
			}
		}
		return boardString;
	}
}
