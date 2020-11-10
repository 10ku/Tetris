package tetris;

import java.util.Arrays;
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
	
	private static void configureBinds(JTextArea textArea)
	{
		textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
		textArea.getActionMap().put("up", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("up");
			}
		});
		textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
		textArea.getActionMap().put("down", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("down");
			}
		});
		textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
		textArea.getActionMap().put("left", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("left");
			}
		});
		textArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
		textArea.getActionMap().put("right", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("right");
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
		textArea.setFont(new Font("Courier New", Font.PLAIN, 16));
		textArea.setEditable(false);
		textArea.setHighlighter(null);
		frame.add(scrollPane);
		frame.setSize(640, 480);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		//GUI MISC
		System.setOut(printStream);
		System.setErr(printStream);
		
		Board myBoard = new Board();
		boolean quit = false;
		
		System.out.println(myBoard.toString());
		
		Tetrominoes test = new Tetrominoes();
		int[][] testM = {
				{0,1,1,2},
				{1,1,0,0},
						};
		
		//jFrame.dispose();
	}
}