
public class Static_Object 
{
	public static final byte SPAWN_POINT = 0;	//Interest type for spwaning AI
	public static final byte INTEREST_POINT = 1;//Interest type that AI want to visit
	
	private static int numSpawned = 0; //Number of interest points
	
	private Main root = null;	//Root object
	private int x = 0;			//x location
	private int y = 0;			//y location
	private byte type = 0;		//Type of interest point
	
	//Constructor
	public Static_Object(Main m, int locx, int locy, byte locType)
	{
		root = m;
		x = locx;
		y = locy;
		type = locType;
		root.setCollision(locx, locy, Main.COLLISION_PASS);
		if(type != SPAWN_POINT)
			numSpawned++;
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
	
	//Returns type of interest point
	public byte getType()
	{
		return type;
	}
	
	//Adjusts count for deleting interest
	public void delete()
	{
		if(type != SPAWN_POINT)
			numSpawned--;
	}
	
	//Returns number of interest points
	public static int getSize()
	{
		return numSpawned;
	}
}
