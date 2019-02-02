import java.awt.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

/* Simple GUI of to demonstrate maze generation using minimum spanning tree algorithms */

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
	}
}
