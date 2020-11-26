package tetris;

import java.util.Timer;
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
				//mino.rotate90();
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
	}
	
	private static void processInput(Tetromino mino)
	{
		if (input[0] == true)
		{
			mino.rotate90Right();
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
		int gameSpeed = 12;
		int gameSpeedCount = 0;
		configureBinds(textArea);
		
		while (quit == false)
		{
			//TIME
			try
			{
				Thread.sleep(40);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
				gameSpeedCount++;
			
			if (gameSpeedCount == gameSpeed)
			{
				forceTetrominoDown = true;
			}
			
			//INPUT
				processInput(currMino);
			
			//GAME
			if (newBlock == true)
			{
				currMino = nextMino;
				nextMino = new Tetromino(tetrominoes.returnRandomTetromino());				
				myBoard.nextTetromino(nextMino);
				newBlock = false;
			}
			
			myBoard.setGameBoard();
			
			if (myBoard.tryLockingTetromino(currMino, lastMove) == true)
			{
				newBlock = true;
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