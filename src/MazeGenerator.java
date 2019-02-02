import java.awt.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

/* Simple GUI to demonstrate maze generation with randomized version of Kruska's algorithm.
 * Expand spanning tree into set of cells by removing walls.
 * Borders are used to keep outside surround of maze enclosed.
 * Disjoint-set data structure used to perform each union and find operation on two sets in nearly constant amortized time.
 * Running time is proportional to number of walls available to maze.
 */

public class MazeGenerator extends JFrame
{
	public static void main(String [] args)
	{
		MazeGenerator maze = new MazeGenerator();
	}

	public MazeGenerator()
	{
		final int FRAME_WIDTH = 700;
		final int FRAME_HEIGHT = 650;
		setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);//x,y,w,h of window
		setTitle("Maze Demo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		MazePanel panel = new MazePanel();
		add(panel);
	}

	/**
	 * Create a list of all walls and create a set for each cell, each containing just that one cell.
	 * For each wall, in some random order:
	 	* If the cells divided by this wall becong to a distinct set,
	 	remove the current wall and join the sets of formerly divided cells.
	 */

	public class MazePanel extends JPanel
	{
		private int[][] board;
		private int BOARD_WIDTH;
		private int BOARD_HEIGHT;

		public MazePanel()
		{
			BOARD_WIDTH = 5;
			BOARD_HEIGHT = 5;
			board = new int[BOARD_WIDTH][BOARD_HEIGHT];

			int counter = 1;
			for(int i = 0; i < BOARD_WIDTH; i++)
			{
				for(int j = 0; j < BOARD_HEIGHT; j++)
				{
					board[i][j] = counter;
					counter++;
				}
			}

			// Create a list of all walls
			final ArrayList<Map> walls = new ArrayList<Map>();
			createListOfWalls(walls);
		}

		private void createListOfWalls(ArrayList<Map> walls)
		{
			// incorporate cardinal direction from Hero class


		}

		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			g.setColor(Color.BLUE);
			g.drawLine(10,10,50,45);
		}
	}
}
