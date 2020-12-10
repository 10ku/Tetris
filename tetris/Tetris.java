package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.PrintStream;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class Tetris
{
	static final byte REQUIRED_LINES_PER_LEVEL = 10;
	static final byte FASTEST_GAME_SPEED = 2;
	
	/*
	 * index 0 - up
	 * index 1 - down
	 * index 2 - left
	 * index 3 - right
	 */
	//Tetris input variables
	static boolean[] input = new boolean[4];
	static int lastMove = GlobalConstants.NONE;
	
	//Tetris state variables
	static boolean quit = false;
	static boolean newGame = false;
	
	//Tetris gameplay variables
	static Tetrominoes tetrominoes = new Tetrominoes();
	static Board myBoard;
	static Tetromino currMino;
	static Tetromino nextMino;
	static boolean newBlock;
	static int lockTetromino;
	static int gameSpeed;
	static int gameSpeedCount;
	static boolean forceTetrominoDown = false;
	
	private static void configureBinds(JTextArea textArea)
	{
		textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
		textArea.getActionMap().put("up", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//mino.rotateTetromino90(false);
				input[0] = true;
			}
		});
		textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		textArea.getActionMap().put("down", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//forceTetrominoDown = true;
				input[1] = true;
			}
		});
		textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
		textArea.getActionMap().put("left", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//mino.xOffset--;
				input[2] = true;
			}
		});
		textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
		textArea.getActionMap().put("right", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//mino.xOffset++;
				input[3] = true;
			}
		});
		textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "esc");
		textArea.getActionMap().put("esc", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				quit = true;
			}
		});
		textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
		textArea.getActionMap().put("enter", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				newGame = true;
			}
		});
	}
	
	private static void processInput(Tetromino mino)
	{
		if (input[0] == true)
		{
			mino.rotateTetromino90(false);
			lastMove = GlobalConstants.UP;
		}
		else if (input[1] == true)
		{
			forceTetrominoDown = true;
		}
		else if (input[2] == true)
		{
			mino.xOffset--;
			lastMove = GlobalConstants.LEFT;
		}
		else if (input[3] == true)
		{
			mino.xOffset++;
			lastMove = GlobalConstants.RIGHT;
		}
		for (int i = 0; i < 4; i++)
		{
			input[i] = false;
		}
	}
	
	private static void initGame()
	{
		myBoard = new Board();
		currMino = null;
		nextMino = new Tetromino(tetrominoes.returnRandomTetromino());
		newBlock = true;
		lockTetromino = GlobalConstants.FAILED;
		gameSpeed = 12;
		gameSpeedCount = 0;
		newGame = false;
	}
	
	public static void main(String[] args)
	{
		//GUI declarations
		JFrame frame = new JFrame("Tetris");
		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		PrintStream printStream = new PrintStream(new Output(textArea));
		
		//GUI parameters
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		textArea.setBackground(Color.BLACK);
		textArea.setForeground(Color.WHITE);
		textArea.setFont(new Font("Courier New", Font.PLAIN, 22));
		textArea.setEditable(false);
		textArea.setHighlighter(null);
		frame.add(scrollPane);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//GUI MISC
		System.setOut(printStream);
		System.setErr(printStream);
		
		//Tetris
		initGame();
		configureBinds(textArea);
		
		while (quit == false)
		{
			//TIME
			try
			{
				Thread.sleep(40);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			if (myBoard.hasLines == false)
			{
				gameSpeedCount++;
			}
			
			if (myBoard.difficultyCounter / REQUIRED_LINES_PER_LEVEL > 0)
			{
				myBoard.difficultyCounter %= REQUIRED_LINES_PER_LEVEL;
				if (gameSpeed > FASTEST_GAME_SPEED)
				{
					gameSpeed--;
					myBoard.level++;
				}
			}
			
			if (gameSpeedCount == gameSpeed)
			{
				forceTetrominoDown = true;
			}
			
			//GAME
			if (newBlock == true)
			{
				currMino = nextMino;
				nextMino = new Tetromino(tetrominoes.returnRandomTetromino());				
				myBoard.nextTetromino(nextMino);
				newBlock = false;
			}
			
			if (myBoard.hasLines == false)
			{
				processInput(currMino);
				myBoard.setGameBoard();
			}
			else if (myBoard.hasLines == true)
			{
				myBoard.clearLines();
				myBoard.hasLines = false;
			}
			
			lockTetromino = myBoard.tryLockingTetromino(currMino, lastMove);
			
			if (lockTetromino == GlobalConstants.SUCCEEDED)
			{
				newBlock = true;
			}
			else if (lockTetromino == GlobalConstants.GAMEOVER)
			{
				textArea.setText(null);
				System.out.println("Game Over!\n");
				System.out.println("ESC - End Game | ENTER - Restart");
				while (quit == false && newGame == false)
				{
					try
					{
						Thread.sleep(300);
					} 
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
				if (newGame == true)
				{
					initGame();
					continue;
				}
				break;
			}
			else if (forceTetrominoDown == true)
			{
				currMino.yOffset++;
				lastMove = GlobalConstants.DOWN;
				forceTetrominoDown = false;
				gameSpeedCount = 0;
			}
			
			//RENDER
			textArea.setText(null);
			myBoard.setGraphicsBoard();
			System.out.println(myBoard.toString());
		}
		
		frame.dispose();
	}
}