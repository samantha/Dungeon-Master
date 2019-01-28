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

	private int NUM_ROWS;

	private int NUM_COLS;

	/** Initialize the map and boolean values */
	private Map()
	{
		setBounds(10, 10, 200, 200);

		// Configurable settings
		NUM_ROWS = 10;
		NUM_COLS = 10;

		// initialize covered map
		map = new char[NUM_ROWS][NUM_COLS];
		// Fill each row with 1.0
		for (char[] row: map)
		{
			Arrays.fill(row, 'n');
		}

		// all values will be false by default
		revealed = new boolean[NUM_ROWS][NUM_COLS];

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
	 * Generate a maze using randomized variant of Kruskal's algorithm.
	 * Kruskal's algorithm is a method for producing a minimal spanning tree
	 * from a weighted graph. Randomized to make a maze.
	 *
	 * The algorithm is implemented as follows:
	 *
	 * (1) Designate the "walls" between cells as edges.
	 * (2) Pull out an edge at random (originally edge with the lowest weight).
	 * (3) If the selected edge connects two disjoint trees, join the trees.
	 * (4) Otherwise, throw that edge away.
	 * (5) Repeat at Step 2 until there are no more edges left.
	 *
	 */

	/**
	 * Generate a random map
	 * @param mapNum
	 */
	public void loadMap()
	{
		Random rand = new Random();
		double randomNum;
		insertStartElements('s', rand.nextInt(map.length), rand.nextInt(map[0].length));
		while(!insertStartElements('f', rand.nextInt(map.length), rand.nextInt(map[0].length)));

		// store elements in map
		for (int i = 0; i < map.length; i++)
		{
			for(int j = 0; j < map[i].length; j++)
			{
				randomNum = rand.nextFloat();
				insertElements(randomNum, i, j);
				revealed[i][j] = false;
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
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

		int SIDE_LENGTH = 100;
		int IMG_WIDTH = 75;
		int IMG_HEIGHT = 75;
		int IMG_LOC = 12;

		for (int i = 0; i < map.length; i++)
		{
			for(int j = 0; j < map[i].length; j++)
			{
				// g.drawLine(10, 10, 20, 20);
				// g.drawRect((SIDE_LENGTH * j), 0 + (SIDE_LENGTH * i), SIDE_LENGTH, SIDE_LENGTH);
				if (i == x && j == y)
				{
					try
					{
						BufferedImage img = ImageIO.read(new File("../img/link.png"));
						g.drawImage(img, IMG_LOC + (SIDE_LENGTH * j), IMG_LOC + (SIDE_LENGTH * i), IMG_WIDTH, IMG_HEIGHT, null);
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
				// else if(revealed[i][j] == false)
				// {
				// 	g.setFont(new Font("Calibri", Font.PLAIN, 50));
				// 	g.drawString("X", 90 + (SIDE_LENGTH * j), 120 + (SIDE_LENGTH * i));
				// }
				// if revealed, show element
				else if(revealed[i][j] == true)
				{
					BufferedImage img;

					try
					{
						if(map[i][j] == 's')
						{
							img = ImageIO.read(new File("../img/shop.png"));
							g.drawImage(img, IMG_LOC + (SIDE_LENGTH * j), IMG_LOC + (SIDE_LENGTH * i), IMG_WIDTH, IMG_HEIGHT, null);
						}

						else if(map[i][j] == 'n')
						{
							img = ImageIO.read(new File("../img/ghost.png"));
							g.drawImage(img, IMG_LOC + (SIDE_LENGTH * j), IMG_LOC + (SIDE_LENGTH * i), IMG_WIDTH, IMG_HEIGHT, null);
						}

						else if(map[i][j] == 'm')
						{
							img = ImageIO.read(new File("../img/monster.png"));
							g.drawImage(img, IMG_LOC + (SIDE_LENGTH * j), IMG_LOC + (SIDE_LENGTH * i), IMG_WIDTH, IMG_HEIGHT, null);
						}

						else if(map[i][j] == 'i')
						{
							img = ImageIO.read(new File("../img/item.png"));
							g.drawImage(img, IMG_LOC + (SIDE_LENGTH * j), IMG_LOC + (SIDE_LENGTH * i), IMG_WIDTH, IMG_HEIGHT, null);
						}
						else if(map[i][j] == 'f')
						{
							img = ImageIO.read(new File("../img/finish.png"));
							g.drawImage(img, IMG_LOC + (SIDE_LENGTH * j), IMG_LOC + (SIDE_LENGTH * i), IMG_WIDTH, IMG_HEIGHT, null);
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

/**
 * Tree structure to model set that is used in Kruskal to build the graph.
 */
class Tree
{
	/* Initialize parent */
	private Tree parent = null;

	/** If we are joined (not null), return root.
	 * Else, return this object instance.
	 */
	public Tree root()
	{
		return parent != null ? parent.root() : this;
	}

	/* Validate connection to tree */
	public boolean connected(Tree tree)
	{
		return this.root() == tree.root();
	}

	/* Connect to the tree */
	public void connect(Tree tree)
	{
		tree.root().parent = this;
	}
}

/**
 * Start coordinates of edge, and direction in which it points.
 */
class Edge
{
	private int x;
	private int y;
	private int direction;

	public Edge(int x, int y, int direction)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public int getDirection()
	{
		return direction;
	}
}