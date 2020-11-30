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
	static boolean quit = false;
	static boolean newGame = false;
	
	/*
	 * index 0 - up
	 * index 1 - down
	 * index 2 - left
	 * index 3 - right
	 */
	static boolean[] input = new boolean[4];
	static boolean forceTetrominoDown = false;
	static int lastMove = -1;
	
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
			lastMove = 0;
		}
		else if (input[1] == true)
		{
			forceTetrominoDown = true;
		}
		else if (input[2] == true)
		{
			mino.xOffset--;
			lastMove = 2;
		}
		else if (input[3] == true)
		{
			mino.xOffset++;
			lastMove = 3;
		}
		for (int i = 0; i < 4; i++)
		{
			input[i] = false;
		}
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
		Board myBoard = new Board();
		Tetrominoes tetrominoes = new Tetrominoes();
		Tetromino currMino = null;
		Tetromino nextMino = new Tetromino(tetrominoes.returnRandomTetromino());
		boolean newBlock = true;
		int lockTetromino = 0;
		int gameSpeed = 12;
		int gameSpeedCount = 0;
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
			
			if (myBoard.diffCounter / 10 > 0)
			{
				myBoard.diffCounter %= 10;
				if (gameSpeed > 2)
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
			}
			
			if (myBoard.hasLines == false)
			{
				myBoard.setGameBoard();
			}
			
			if (myBoard.hasLines == true)
			{
				myBoard.clearLines();
				myBoard.hasLines = false;
			}
			
			lockTetromino = myBoard.tryLockingTetromino(currMino, lastMove);
			
			if (lockTetromino == 1)
			{
				newBlock = true;
			}
			else if (lockTetromino == 2)
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
					myBoard = new Board();
					currMino = null;
					nextMino = new Tetromino(tetrominoes.returnRandomTetromino());
					newBlock = true;
					lockTetromino = 0;
					gameSpeed = 12;
					gameSpeedCount = 0;
					newGame = false;
					continue;
				}
				break;
			}
			else if (forceTetrominoDown == true)
			{
				currMino.yOffset++;
				lastMove = 1;
				forceTetrominoDown = false;
				gameSpeedCount = 0;
			}
			
			//RENDER
			textArea.setText(null);
			myBoard.setBoardGraphics();
			System.out.println(myBoard.toString());
		}
		
		frame.dispose();
	}
}