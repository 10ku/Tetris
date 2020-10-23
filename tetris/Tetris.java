package tetris;

import java.util.Arrays;
import java.util.Timer;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

public class Tetris
{
	public static void main(String[] args)
	{
		Board myBoard = new Board();
		System.out.println(myBoard.toString());
	}
}