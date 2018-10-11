import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements MouseListener, MouseMotionListener
{
	private static final byte ADJUST_X = -3;	//Manual syncing of mouse cursor
	private static final byte ADJUST_Y = -26;
	public static final byte CLICK_NULL = 0;	//Value for no clicking
	public static final byte CLICK_LEFT = 1;	//Value for left-click
	public static final byte CLICK_MIDDLE = 2;	//Value for middle-click
	public static final byte CLICK_RIGHT = 3;	//Value for right-click
	
	private boolean drag = false;	//If dragging
	private byte click = 0;			//If clicking
	private int x = 0;				//X location of cursor
	private int y = 0;				//Y location of cursor
	
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	//Mouse is pressed
	public void mousePressed(MouseEvent e) 
	{
		if(e.getButton() == MouseEvent.BUTTON1)
			click = CLICK_LEFT;
		if(e.getButton() == MouseEvent.BUTTON2)
			click = CLICK_MIDDLE;
		if(e.getButton() == MouseEvent.BUTTON3)
			click = CLICK_RIGHT;
		drag = true;
	}

	//Mouse is released
	public void mouseReleased(MouseEvent e) 
	{
		drag = false;
	}
	
	//Returns click
	public byte click()
	{
		return click;
	}
	
	//Sets click
	public void click(byte set)
	{
		click = set;
	}
	
	//Returns x location of cursor
	public int getX()
	{
		return x;
	}
	
	//Returns y location of cursor
	public int getY()
	{
		return y;
	}
	
	//True if cursor is dragging
	public boolean isDragged()
	{
		return drag;
	}

	//When mouse is dragging
	public void mouseDragged(MouseEvent e) 
	{
		x = e.getX() + ADJUST_X;
		y = e.getY() + ADJUST_Y;
	}

	//When cursor moves
	public void mouseMoved(MouseEvent e) 
	{
		x = e.getX() + ADJUST_X;
		y = e.getY() + ADJUST_Y;
	}
}
