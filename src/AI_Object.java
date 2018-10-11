
public class AI_Object 
{
	public static final byte DIRECTION_UP = 0;		//Value for UP
	public static final byte DIRECTION_DOWN = 1;	//Value for DOWN
	public static final byte DIRECTION_LEFT = 2;	//Value for LEFT
	public static final byte DIRECTION_RIGHT = 3;	//Value for RIGHT
	
	private static int numSpawned = 0; 	//Number of AI objects created
	
	private Main root = null;			//Root object
	private int interest = 0;			//Current interest point
	private int x = 0;					//X location
	private int y = 0;					//Y location
	private int goalX = 0;				//X location of interest
	private int goalY = 0;				//Y location of interest
	private int stamina = 500;			//Stamina (AI will return to interest at 0)
	private byte lastDirection = -1;	//Last direction traveled
	
	private boolean up = false;			//If up is passable
	private boolean down = false;		//If down is passable
	private boolean left = false;		//If left is passable
	private boolean right = false;		//If right is passsable
	private byte possibilities = 0;		//Number of possibilities
	
	//Constructor
	public AI_Object(Main m, Static_Object spawn)
	{
		root = m;
		x = spawn.getX();
		y = spawn.getY();
		
		interest = (int) (Math.random()*Main.NUM_INTEREST);
		goalX = root.getInterest()[interest].getX();
		goalY = root.getInterest()[interest].getY();
		
		numSpawned++;
	}
	
	//The main process for controlling were AI object goes
	public void move()
	{
		possibilities = 0;
		
		//Determining Possibilities
		
		//If AI is not at map boundary x
		if(x < root.getCollision().length - 1)
		{
			//Check right tile
			if(root.getCollision()[x + 1][y] == Main.COLLISION_PASS)
			{
				right = true;
				possibilities++;
			}
			else
				right = false;
		}
		else
			right = false;
		
		//If AI is not at map boundary x
		if(x > 0)
		{
			//Check left tile
			if(root.getCollision()[x - 1][y] == Main.COLLISION_PASS)
			{
				left = true;
				possibilities++;
			}
			else
				left = false;
		}
		else
			left = false;
		
		//If AI is not at map boundary y
		if(y < root.getCollision()[0].length - 1)
		{
			//Check bottom tile
			if(root.getCollision()[x][y + 1] == Main.COLLISION_PASS)
			{
				down = true;
				possibilities++;
			}
			else
				down = false;
		}
		else
			down = false;
		
		//If AI is not at map boundary y
		if(y > 0)
		{
			//Check top tile
			if(root.getCollision()[x][y - 1] == Main.COLLISION_PASS)
			{
				up = true;
				possibilities++;
			}
			else
				up = false;
		}
		else
			up = false;
		
		//Moving - All possibilities are mapped out (what makes it lightweight)
		if(possibilities > 2)
		{
			if(goalX > x && right && lastDirection != DIRECTION_RIGHT)
			{
				//System.out.println("RIGHT!");
				x++;
				lastDirection = DIRECTION_LEFT; //COME FROM
			}
			else if(goalX < x && left && lastDirection != DIRECTION_LEFT)
			{
				//System.out.println("LEFT!");
				x--;
				lastDirection = DIRECTION_RIGHT; //COME FROM
			}
			else if(goalY > y && down && lastDirection != DIRECTION_DOWN)
			{
				//System.out.println("DOWN!");
				y++;
				lastDirection = DIRECTION_UP; //COME FROM
			}
			else if(goalY < y && up && lastDirection != DIRECTION_UP)
			{
				//System.out.println("UP!");
				y--;
				lastDirection = DIRECTION_DOWN; //COME FROM
			}
			else if(right && lastDirection != DIRECTION_RIGHT)
			{
				//System.out.println("RIGHT!");
				x++;
				lastDirection = DIRECTION_LEFT; //COME FROM
			}
			else if(left && lastDirection != DIRECTION_LEFT)
			{
				//System.out.println("LEFT!");
				x--;
				lastDirection = DIRECTION_RIGHT; //COME FROM
			}
			else if(down && lastDirection != DIRECTION_DOWN)
			{
				//System.out.println("DOWN!");
				y++;
				lastDirection = DIRECTION_UP; //COME FROM
			}
			else if(up && lastDirection != DIRECTION_UP)
			{
				//System.out.println("UP!");
				y--;
				lastDirection = DIRECTION_DOWN; //COME FROM
			}
			else if(right)
			{
				//System.out.println("RIGHT!");
				x++;
				lastDirection = DIRECTION_LEFT; //COME FROM
			}
			else if(left)
			{
				//System.out.println("LEFT!");
				x--;
				lastDirection = DIRECTION_RIGHT; //COME FROM
			}
			else if(down)
			{
				//System.out.println("DOWN!");
				y++;
				lastDirection = DIRECTION_UP; //COME FROM
			}
			else if(up)
			{
				//System.out.println("UP!");
				y--;
				lastDirection = DIRECTION_DOWN; //COME FROM
			}
		}
		else if(possibilities > 1)
		{
			if(right && lastDirection != DIRECTION_RIGHT)
			{
				//System.out.println("RIGHT!");
				x++;
				lastDirection = DIRECTION_LEFT; //COME FROM
			}
			else if(left && lastDirection != DIRECTION_LEFT)
			{
				//System.out.println("LEFT!");
				x--;
				lastDirection = DIRECTION_RIGHT; //COME FROM
			}
			else if(down && lastDirection != DIRECTION_DOWN)
			{
				//System.out.println("DOWN!");
				y++;
				lastDirection = DIRECTION_UP; //COME FROM
			}
			else if(up && lastDirection != DIRECTION_UP)
			{
				//System.out.println("UP!");
				y--;
				lastDirection = DIRECTION_DOWN; //COME FROM
			}
			else if(right)
			{
				//System.out.println("RIGHT!");
				x++;
				lastDirection = DIRECTION_LEFT; //COME FROM
			}
			else if(left)
			{
				//System.out.println("LEFT!");
				x--;
				lastDirection = DIRECTION_RIGHT; //COME FROM
			}
			else if(down)
			{
				//System.out.println("DOWN!");
				y++;
				lastDirection = DIRECTION_UP; //COME FROM
			}
			else if(up)
			{
				//System.out.println("UP!");
				y--;
				lastDirection = DIRECTION_DOWN; //COME FROM
			}
		}
		else
		{
			if(right)
			{
				//System.out.println("RIGHT!");
				x++;
				lastDirection = DIRECTION_LEFT; //COME FROM
			}
			else if(left)
			{
				//System.out.println("LEFT!");
				x--;
				lastDirection = DIRECTION_RIGHT; //COME FROM
			}
			else if(down)
			{
				//System.out.println("DOWN!");
				y++;
				lastDirection = DIRECTION_UP; //COME FROM
			}
			else if(up)
			{
				//System.out.println("UP!");
				y--;
				lastDirection = DIRECTION_DOWN; //COME FROM
			}
		}
		
		//If goal is reached
		if(x == goalX && y == goalY)
			changeInterest();
		
		//If the AI is out of stamina
		if(stamina > 0)
			stamina--;
		else
		{
			//System.out.println("I WANT TO GO HOME!");
			interest = Main.ENTRANCE;
			goalX = root.getInterest()[interest].getX();
			goalY = root.getInterest()[interest].getY();
		}
	}
	
	//Changes interest of the AI_Object
	public void changeInterest()
	{
		//System.out.println("I MADE IT!");
		interest = (int) (Math.random()*Main.NUM_INTEREST);
		goalX = root.getInterest()[interest].getX();
		goalY = root.getInterest()[interest].getY();
		stamina += 10;
	}
	
	//Returns x location
	public int getX()
	{
		return x;
	}
	
	//Returns y location
	public int getY()
	{
		return y;
	}
	
	//Adjusts count when AI is removed
	public void delete()
	{
		numSpawned--;
	}
	
	//Returns number of AI objects initialized
	public static int getSize()
	{
		return numSpawned;
	}
}
