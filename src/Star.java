
public class Star {
	private int xPos;
	private int yPos;
	private int speed;
	private int size;
	
	public Star(int x, int y, int moveSpeed, int starSize)
	{
		xPos = x;
		yPos = y;
		speed = moveSpeed;
		size = starSize;
	}
	
	public void move()
	{
		xPos -= speed;
	}
	
	public boolean offScreen()
	{
		if(xPos < 0)
		{
			return true;
		}
		return false;
	}
	
	public int getX()
	{
		return xPos;
	}
	
	public int getY()
	{
		return yPos;
	}
	
	public int getSize()
	{
		return size;
	}
	
}
