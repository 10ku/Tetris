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
				//mino.yOffset++;
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
			mino.rotate90();
		}
		if (input[1] == true)
		{
			mino.yOffset++;
		}
		if (input[2] == true)
		{
			mino.xOffset--;
		}
		if (input[3] == true)
		{
			mino.xOffset++;
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
		Tetromino mino = null;
		boolean newBlock = true;
		configureBinds(textArea);
		
		while (quit == false)
		{
			//INPUT
			processInput(mino);
			
			//GAME
			if (newBlock == true)
			{
				mino = new Tetromino(tetrominoes.returnRandomTetromino());
				newBlock = false;
			}
			
			myBoard.setGameBoard();
			
			if (myBoard.tryInsertingTetromino(mino) == true)
			{
				newBlock = true;
			}
			else
			{
				mino.yOffset++;
			}
			
			//RENDER
			textArea.setText(null);
			myBoard.setBoardGraphics();
			System.out.println(myBoard.toString());
			try
			{
				Thread.sleep(500);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		frame.dispose();
	}
}