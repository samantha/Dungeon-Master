import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.awt.Point;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Map Class - representation of a map
 * Extends Rectangle to draw rectangles on GUI
 * Incorporates Singleton design pattern
 */
public class Map extends Rectangle
{
	/** Instance of Map that ensures that only one instance of Map is created*/
	private static Map instance = null;

	/** 5x5 grid of characters
	 * s = start, f = finish, m = monster, i = item, n = nothing
	 */
	private char[][] map;

	/** 5x5 grid of hidden/revealed elements */
	private boolean [][] revealed;

	/** configurable probability of placing an item on map */
	private double probabilities_item;

	/** configurable probability of placing a monster on map */
	private double probabilities_monster;

	/** Initialize the map and boolean values */
	private Map()
	{
		setBounds(10, 10, 200, 200);

		// initialize covered map
		map = new char[5][5];
		// Fill each row with 1.0
		for (char[] row: map)
		{
			Arrays.fill(row, 'n');
		}

		// all values will be false by default
		revealed = new boolean[5][5];

		// set probabilites for items and monsters
		probabilities_item = 0.3;
        probabilities_monster = 0.3;
	}

	/**
	 * Get Instance of Map
	 * @return new Map if instance is null, else return existing Map
	 */
	public static Map getInstance()
	{
		if(instance == null)
		{
			instance = new Map();
		}
		return instance;
	}

	/**
	 * Insert random start and finish elements
	 */
	public boolean insertStartElements(char element, int row, int col)
	{
		if(map[row][col] == 'n')
		{
			map[row][col] = element;
			return true;
		}

		return false;
	}

	/**
	 * Insert random monster and item elements
	 */
	public boolean insertElements(double randomNum, int row, int col)
	{
		// store elements in map
		if(map[row][col] == 'n')
		{
			if(randomNum < probabilities_item)
			{
				map[row][col] = 'i';
			}
			else if(randomNum < probabilities_item + probabilities_monster)
			{
				map[row][col] = 'm';
			}
			return true;
		}
		return false;
	}

	/**
	 * Generate a random map
	 * @param mapNum
	 */
	public void loadMap()
	{
		Random rand = new Random();
		double randomNum;
		insertStartElements('s', rand.nextInt(map.length), rand.nextInt(map.length));
		while(!insertStartElements('f', rand.nextInt(map.length), rand.nextInt(map.length)));

		// store elements in map
		for (int i = 0; i < map.length; i++)
		{
			for(int j = 0; j < map[i].length; j++)
			{
				randomNum = rand.nextFloat();
				insertElements(randomNum, i, j);
				revealed[i][j] = false;
			}
		}
	}

	/**
	 * Show the game map with hero location, hidden rooms, and cleared rooms
	 * @param p the location of the hero
	 * @param g enables Graphics, called by paintComponent
	 */
	public void drawMap(Point p, Graphics g)
	{
		int x, y;
		x = (int)p.getX();
		y = (int)p.getY();

		g.setColor(Color.WHITE);

		int side = 200;

		for (int i = 0; i < map.length; i++)
		{
			for(int j = 0; j < map[i].length; j++)
			{
				g.drawRect((side * j), 0 + (side * i), side, side);
				if (i == x && j == y)
				{
					try
					{
						BufferedImage img = ImageIO.read(new File("../img/link.png"));
						g.drawImage(img, 25 + (side * j), 25 + (side * i), 150, 150, null);
					}
					catch(FileNotFoundException e)
					{
						System.out.println("File not found!");
					}
					catch(IOException io)
					{
						System.out.println("IO Exception");
					}
				}
				// if hidden, room is covered
				else if(revealed[i][j] == false)
				{
					g.setFont(new Font("Calibri", Font.PLAIN, 50));
					g.drawString("X", 90 + (side * j), 120 + (side * i));
				}
				// if revealed, show element
				else if(revealed[i][j] == true)
				{
					BufferedImage img;

					try
					{
						if(map[i][j] == 's')
						{
							img = ImageIO.read(new File("../img/shop.png"));
							g.drawImage(img, 25 + (side * j), 25 + (side * i), 150, 150, null);
						}

						else if(map[i][j] == 'n')
						{
							img = ImageIO.read(new File("../img/ghost.png"));
							g.drawImage(img, 25 + (side * j), 25 + (side * i), 150, 150, null);
						}

						else if(map[i][j] == 'm')
						{
							img = ImageIO.read(new File("../img/monster.png"));
							g.drawImage(img, 25 + (side * j), 25 + (side * i), 150, 150, null);
						}

						else if(map[i][j] == 'i')
						{
							img = ImageIO.read(new File("../img/item.png"));
							g.drawImage(img, 25 + (side * j), 25 + (side * i), 150, 150, null);
						}
						else if(map[i][j] == 'f')
						{
							img = ImageIO.read(new File("../img/finish.png"));
							g.drawImage(img, 25 + (side * j), 25 + (side * i), 150, 150, null);
						}
					}

					catch(FileNotFoundException e)
					{
						System.out.println("File not found!");
					}
					catch(IOException io)
					{
						System.out.println("IO Exception");
					}

				}
			}
		}
	}


	/**
	 * Retrieve character at location
	 * @param p the location of the character
	 * @return the character at desired location
	 */
	public char getCharAtLoc(Point p)
	{
		int x, y;
		x = (int)p.getX();
		y = (int)p.getY();
		char gameElement = map[x][y];
		return gameElement;
	}

	/**
	 * Gets location of the starting point
	 * @return start point coordinates
	 */
	public Point findStart()
	{
		Point start = new Point();
		for (int i = 0; i < map.length; i++)
		{
			for(int j = 0; j < map[i].length; j++)
			{
				if(map[i][j] == 's')
				{
					start.setLocation(i,j);
				}

			}
		}
		return start;
	}

	/**
	 * Sets the element at the desired coordinate to be revealed
	 * @param p coordinates of the revealed element
	 */
	public void reveal(Point p)
	{
		int x = (int)p.getX();
		int y = (int)p.getY();
		revealed[x][y] = true;
	}

	/**
	 * Clears a room and sets character to 'n' to represent nothing there
	 * @param p coordinates of cleared room
	 */
	public void removeCharAtLoc(Point p)
	{
		int x = (int)p.getX();
		int y = (int)p.getY();
		map[x][y] = 'n';
	}
}