import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Interface extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private Main root = null;			//Root Object
	
	private static long lastFrame = 0;	//Time of last frame
	private static int frameCount = 0;	//Number of frames
	private static int frameRate = 0;	//Frames per second
	
	//Constructor
	public Interface(Main m)
	{
		root = m;
	}
	
	//Default JPanel canvas function
	public void paintComponent(Graphics g) 
    {  
		//MAP COLLISION
		for(int x = 0; x < root.getCollision().length; x++)
		{
			for(int y = 0; y < root.getCollision()[x].length; y++)
			{
				if(root.getCollision()[x][y] == Main.COLLISION_IMPASS)
					g.setColor(Color.BLACK);
				else if(root.getCollision()[x][y] == Main.COLLISION_PASS)
					g.setColor(Color.GRAY);
				
				g.fillRect(x*Main.SIZE, y*Main.SIZE, Main.SIZE, Main.SIZE);
			}
		}
		
		//INTEREST LOCATION
		g.setColor(Color.BLUE);
		for(int i = 0; i < Main.NUM_INTEREST; i++)
		{
			if(root.getInterest()[i].getType() == Static_Object.SPAWN_POINT)
				g.setColor(Color.RED);
			else if(root.getInterest()[i].getType() == Static_Object.INTEREST_POINT)
				g.setColor(Color.BLUE);
			
			g.fillRect(root.getInterest()[i].getX()*Main.SIZE, root.getInterest()[i].getY()*Main.SIZE, Main.SIZE, Main.SIZE);
		}
		
		//AI LOCATION
		g.setColor(Color.GREEN);
		for(int i = 0; i < root.getAI().length; i++)
		{
			if(root.getAI()[i] != null)
			{
				g.fillRect(root.getAI()[i].getX()*Main.SIZE, root.getAI()[i].getY()*Main.SIZE, Main.SIZE, Main.SIZE);
			}
		}
		
		//FRAMECOUNT
		g.setColor(Color.WHITE);
		frameCount++;
		if(System.currentTimeMillis()%1000 < lastFrame)
		{
			frameRate = frameCount;
			frameCount = 0;
		}
		lastFrame = System.currentTimeMillis()%1000;
		g.drawString(frameRate + "", Main.resolution[0] - 40, (short) (Main.resolution[1]*0.70));
    }
}
