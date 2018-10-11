import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

/*
 * Sean Rannie
 * August 26th, 2018
 * 
 * This program experiments with primitive AI that puts little
 * strain on the system. The AI is inspired by the algorithm
 * used in Roller Coaster Tycoon. Each AI object has a generated
 * interest to get to. This algorithm allows 65536+ AI objects to
 * be processed at one time. This algorithm does not respawn AI
 * objects when they leave.
 */
public class Main 
{
	public static final double scale = 0.75;
	public static final int[] resolution = {(short) (1440*scale),(short) (720*scale)};
	
	public static final int FRAMES_PER_SPAWN = 60;	//Determines how fast AI spawns (# of frames per spawn)
	public static final int FRAMES_PER_MOVEMENT = 2;//Determines how fast AI move (# of frames per move)
	public static final int ENTRANCE = 0;			//The location of the entrance/spawn location
	public static final int NUM_INTEREST = 12;		//The number of interest location
	public static final int SIZE = 8;				//Factor for the size of the movable field				
	public static final byte COLLISION_IMPASS = 0;	//Value that represents boundaries
	public static final byte COLLISION_PASS = 1;	//Value that represents crossable terrain
	
	private static JFrame frame = new JFrame("AI TEST");	//JFrame
	private Interface graphics;								//JPanel
	private Input input = new Input();						//MouseListener
	
	private byte[][] collision = new byte[resolution[0]/SIZE][resolution[1]/SIZE];	//The map for the AI to roam
	private AI_Object[] AIList = new AI_Object[0xFFFF];								//The AI objects
	private Static_Object[] interestList = new Static_Object[255];					//The interest locations
	
	private int AIPortion = 0;						//# of split processes (AI does not need to be calculated very frame)
	private int frames = FRAMES_PER_SPAWN;			//Dynamic frames per spawn
	
	public static void main(String[] args) {new Main();}
	
	//Declaration and Initialization
	public Main()
	{
		graphics = new Interface(this);
		
		graphics.setPreferredSize(new Dimension(resolution[0],resolution[1]));
		graphics.setLayout(null);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(new Dimension(resolution[0],resolution[1]));
        frame.setLocation(0, 0);
        frame.setBackground(Color.WHITE);
        frame.add(graphics);
        frame.addMouseListener(input);
        frame.addMouseMotionListener(input);
    
        interestList[ENTRANCE] = new Static_Object(this, 0, 0, Static_Object.SPAWN_POINT);
        for(int i = 1; i < NUM_INTEREST; i++)
        {
        	interestList[i] = new Static_Object(this, (int)(Math.random()*collision.length), (int)(Math.random()*collision[0].length), Static_Object.INTEREST_POINT);
        }
        
        /*Code that will spawn the maximum # of objects at once
        for(int i = 0; i < AIList.length; i++)
        {
        	AIList[i] = new AI_Object(this, interestList[ENTRANCE]);
        }*/
        
        //Map is generated
        for(int x = 0; x < collision.length; x++)
        {
        	for(int y = 0; y < collision[x].length; y++)
            {
        		collision[x][y] = COLLISION_PASS;
            }
        }
        
        frame();
	}
	
	//Each individual frame of the program
	public void frame()
	{
		graphics.setDoubleBuffered(true);
		
		//Frame loops until program ends
		while(true)
		{
			//Frame data
			try {
				AI();
				control();
				graphics.repaint();
				Thread.sleep(16, 666);
			} catch (InterruptedException e) {}
		}
	}
	
	//Sets tile collision
	public void setCollision(int x, int y, byte col)
	{
		collision[x][y] = col;
	}
	
	//Returns tile collision
	public byte[][] getCollision()
	{
		return collision;
	}
	
	//Returns the array of interest locations
	public Static_Object[] getInterest()
	{
		return interestList;
	}
	
	//Returns array of AI objects
	public AI_Object[] getAI()
	{
		return AIList;
	}
	
	//Processes all AI objects
	public void AI()
	{
		//The program splits the process across the # of frames per movement (more frames = less strain)
		for(int i = 0; i < AIList.length/FRAMES_PER_MOVEMENT; i++)
		{
			if(AIList[AIList.length/FRAMES_PER_MOVEMENT*AIPortion + i] != null)
				AIList[AIList.length/FRAMES_PER_MOVEMENT*AIPortion + i].move();
		}
		
		//The portion being processed gets incremented and reset
		AIPortion++;
		if(AIPortion == FRAMES_PER_MOVEMENT)
			AIPortion = 0;
		
		//If the timer for a new spawn is reached
		if(frames == FRAMES_PER_SPAWN)
		{
			frames = 0;
			if(AI_Object.getSize() < AIList.length - 1)
				AIList[AI_Object.getSize()] = new AI_Object(this, interestList[ENTRANCE]);
			else
				System.out.println("LIMIT HIT");
		}
		frames++;
	}
	
	//Processes the inputs recorded by listener
	public void control()
	{
		boolean interrupt = false;
		
		//Left click = add passable terrain
		if(input.click() == Input.CLICK_LEFT)
		{
			//If in map
			if(input.getX() > 0 && input.getY() > 0 && input.getX() < resolution[0] && input.getY() < resolution[1])
			{
				//If interest point exists in that spot
				for(int i = 0; i < NUM_INTEREST; i++)
				{
					if(input.getX()/SIZE == interestList[i].getX() && input.getY()/SIZE == interestList[i].getY())
						interrupt = true;
				}
					
				if(!interrupt)
					collision[input.getX()/SIZE][input.getY()/SIZE] = COLLISION_PASS;
	
				if(!input.isDragged())
					input.click(Input.CLICK_NULL);
			}
		}
		//Right click = add boundary
		if(input.click() == Input.CLICK_RIGHT)
		{
			//If in map
			if(input.getX() > 0 && input.getY() > 0 && input.getX() < resolution[0] && input.getY() < resolution[1])
			{
				//If interest point exists in that spot
				for(int i = 0; i < NUM_INTEREST; i++)
				{
					if(input.getX()/SIZE == interestList[i].getX() && input.getY()/SIZE == interestList[i].getY())
						interrupt = true;
				}
					
				if(!interrupt)
					collision[input.getX()/SIZE][input.getY()/SIZE] = COLLISION_IMPASS;
				
				if(!input.isDragged())
					input.click(Input.CLICK_NULL);
			}
		}
	}
}
