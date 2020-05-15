import java.awt.Point;
import java.util.ArrayList;

public class Turret {
	
	private int xPos;
	private int yPos;
	private int xOffset;
	private int yOffset;
	private int direction;
	
	private EnemyShip parentShip;
	
	final static int reloadTime = 10;
    private int reloadIterator;
    
	public Turret(EnemyShip pShip, int xOff, int  yOff)
	{
		parentShip = pShip;
		xOffset = xOff;
		yOffset = yOff;
		reloadIterator = (int)(Math.random()*reloadTime);
		direction = 180;
	}
	
	public void Tick(Point playerPos, ArrayList<Laser> laserList)
	{
		xPos = (int) (parentShip.getPosX()+xOffset);
		yPos = (int) (parentShip.getPosY()+yOffset);
		double deltaX = Math.abs(playerPos.getX() - xPos);
		double deltaY = (playerPos.getY()-yPos);
		int angle = 180-(int) Math.toDegrees(Math.atan(deltaY/deltaX));
		direction = angle;
		
		
			if(reloadIterator >= reloadTime && playerPos.getX() < xPos && parentShip.isFiring())
			{
					laserList.add(new Laser(xPos, yPos, angle, EnemyShip.laserSpeed));
					reloadIterator = 0;
			} else 
				reloadIterator++;
		
	}

	public double getDirection() {
		return direction;
	}
	
	public int getX()
	{
		return xPos;
	}
	
	public int getY()
	{
		return yPos;
	}
}
