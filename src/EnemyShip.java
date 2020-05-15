import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

public class EnemyShip extends Ship {

    final static int INTERCEPT_DENOM_X = 5;
    final static int INTERCEPT_DENOM_Y = 1;

    final static int DESTROYER = 1;
    final static int INTERCEPTOR = 2;
    final static int STRIKER = 3;
    final static int TIE = 4;
    
    //reloads in ticks

    final static int strikerReload = 60;
    final static int interceptorReload = 220;
    final static int destroyerReload = 60;
    final static int destroyerBurstEnd = 120;
    private int reloadIterator;
    final static int laserSpeed = 9;
    
    
    private ArrayList<Turret> turrets;
    private boolean firing;
    
    final static int DELTA = 2;
    final static Random r = new Random();
    final static int CHANCE = 500; // where the chance is 1/x the
                                   // probabilityof a match

    private PathIterator pathIterator; // interface
    private Path2D path; // interface

    private BufferedImage sprite;

    private int type;
    private int healthDrop; // drops could be health/shields/powerup
    private int powerupDrop;

    private int width;
    private int height;

    public EnemyShip(int x, int y, int width, int height, int health, int type, Path2D p) {
        super(x, y, width, height, health);
        this.type = type;
        setSprite();
        setBounds();

        // healthDrop = r.nextInt(CHANCE);
        // powerupDrop = r.nextInt(CHANCE);
        path = p;
        path.moveTo(x, y);
        setPath(x, y);
        pathIterator = path.getPathIterator(null);
        switch(type)
        {
        case(DESTROYER):
        	turrets = new ArrayList<Turret>();
        	turrets.add(new Turret(this, 80, 44));
	        turrets.add(new Turret(this, 80, -44));
	        turrets.add(new Turret(this, 110, -50));
	        turrets.add(new Turret(this, 110, 50));
	        firing = false;
        	break;
        case(INTERCEPTOR):
        	reloadIterator = (int)(Math.random()*interceptorReload);
        	break;
        case(STRIKER):
        	reloadIterator = (int)(Math.random()*strikerReload);
        	break;
        }
    }

    public void setBounds() {
        switch (type) {
            case (DESTROYER):
                width = XWing.destroyerWidth/2;
                height = XWing.destroyerHeight;
                break;
            case (INTERCEPTOR):
                width = XWing.interceptorWidth;
                height = XWing.interceptorHeight;
                break;
            case (STRIKER):
                width = XWing.strikerWidth;
                height = XWing.strikerHeight;
                break;
            case (TIE):
                width = XWing.TIEwidth;
                height = XWing.TIEheight;
                break;
        }
    }

    public void setSprite() {
        switch (type) {
            case (DESTROYER):
                sprite = XWing.destroyerSprite;
                break;
            case (INTERCEPTOR):
                sprite = XWing.interceptorSprite;
                break;
            case (STRIKER):
                sprite = XWing.strikerSprite;
                break;
            case (TIE):
                sprite = XWing.TIESprite;
                break;
        }
    }

    public void setPath(int x, int y) {
        switch (type) {
            case (DESTROYER):
            	for (int i = 0; i < XWing.SCREEN_WIDTH + 100; i+=1) {
                    path.lineTo(x - i, y);
                }
                break;
            case (INTERCEPTOR):
                double pathX = x;
                double pathY = y;
                while (pathX > -XWing.interceptorWidth) {
                    for (double j = 1; j < 21; j++) {
                        pathX -= (j / INTERCEPT_DENOM_X);
                        pathY += (j / INTERCEPT_DENOM_Y);
                        path.lineTo(pathX, pathY);
                        path.lineTo(pathX, pathY);
                    }
                    path.lineTo(pathX, pathY);
                    for (double j = 1; j < 21; j++) {
                        pathX -= (j / INTERCEPT_DENOM_X);
                        pathY -= (j / INTERCEPT_DENOM_Y);
                        path.lineTo(pathX, pathY);
                        path.lineTo(pathX, pathY);
                    }
                }
                break;
            case (STRIKER):
            	for (int i = 0; i < XWing.SCREEN_WIDTH + 100; i+=2) {
                    path.lineTo(x - i, y);
                }
                break;
            case (TIE):
            	int speed = 4+(int)Math.random()*5;
                for (int i = 0; i < XWing.SCREEN_WIDTH + 100; i+=speed) {
                    path.lineTo(x - i, y);
                }
                break;
        }
    }
    
    public void shootLaser(Point playerPos, ArrayList<Laser> list, ArrayList<Missile> mList)
    {
    	switch(type){
    	case 4: //tie
    	{
    		break;
    	}
    	case (STRIKER): //striker
    	{

    		if(reloadIterator >= strikerReload && playerPos.getX() < posX) //so it won't shoot backwards
    		{
    			double deltaX = Math.abs(playerPos.getX() - posX);
    			double deltaY = (playerPos.getY()-posY);
    			int angle = 180-(int) Math.toDegrees(Math.atan(deltaY/deltaX));
    			list.add(new Laser((int)posX-10, (int)posY, angle, laserSpeed));
    			reloadIterator = 0;
    		} else
    			reloadIterator++;
    		break;
    	}
    	case (INTERCEPTOR): //interceptor
    	{
    		if(reloadIterator >= interceptorReload && playerPos.getX() < posX) //so it won't shoot backwards
    		{
    			double deltaX = Math.abs(playerPos.getX() - posX);
    			double deltaY = (playerPos.getY()-posY);
    			int angle = 180-(int) Math.toDegrees(Math.atan(deltaY/deltaX));
    			mList.add(new Missile((int)posX-10, (int)posY, angle, 6));
    			reloadIterator = 0;
    		} else
    			reloadIterator++;
    		break;
    	}
    	case 1: //destroyer
    	{
    		if(reloadIterator >= destroyerReload) //so it won't shoot backwards
    		{
    			firing = true;
    			if(reloadIterator >= destroyerBurstEnd)
    				reloadIterator = 0;
    		} else
    			firing = false;
    			reloadIterator++;
    		break;
    	}
    	
    	}
    }

    @Override
    public void setPosX(double posX) {
        // if (posX + XWing.xWingWidth + 1 > XWing.SCREEN_WIDTH) {
        // posX = XWing.SCREEN_WIDTH - XWing.xWingWidth - 1;
        // }
        // if (posX <= 1) {
        // posX = 1;
        // }
        this.posX = posX;
        if(type==DESTROYER)
        	hitbox = new Rectangle2D.Double(getPosX()-width/2, getPosY()-height/2, width, height);
        else 
        	hitbox = new Rectangle2D.Double(getPosX(), getPosY(), width, height);
    }

    @Override
    public void setPosY(double posY) {
        // if (posY + XWing.xWingWidth + 1 > XWing.SCREEN_HEIGHT) {
        // posY = XWing.SCREEN_HEIGHT - XWing.xWingWidth - 1;
        // }
        // if (posY <= XWing.BORDER) {
        // posY = XWing.BORDER + 1;
        // }
        this.posY = posY;
        if(type==DESTROYER)
        	hitbox = new Rectangle2D.Double(getPosX()-width/2, getPosY()-height/2, width, height);
        else 
        	hitbox = new Rectangle2D.Double(getPosX(), getPosY(), width, height);
    }

    public int getType() {
        return type;
    }
    
    public Turret[] getTurrets()
    {
    	return turrets.toArray(new Turret[4]);
    }
    
    public boolean isFiring()
    {
    	return firing;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public boolean isHealthDrop() {
        return healthDrop == 47; // ;)
    }

    public boolean isPowerupDrop() {
        return powerupDrop == 47; // ;)
    }

    public Path2D getPath() {
        return path;
    }

    public void step() {
        if (pathIterator.isDone()) {
            // System.out.print("done at point: ");
            // System.out.println("(" + path.getCurrentPoint().getX() + ", " +
            // path.getCurrentPoint().getY() + ")");
            return;
        }
        pathIterator.next();
        double[] coords = new double[6];
        pathIterator.currentSegment(coords);
        path.moveTo(coords[0], coords[1]);
        // System.out.print("setting point to: ");
        // System.out.println("(" + path.getCurrentPoint().getX() + ", " +
        // path.getCurrentPoint().getY() + ")");
        setPosX((int) coords[0]);
        setPosY((int) coords[1]);
    }

    public String toString() {
        return (type == DESTROYER ? "Destroyer"
                : type == INTERCEPTOR ? "Interceptor" : type == STRIKER ? "Striker" : "Tie Fighter") + " at " + "("
                + getPosX() + ", " + getPosY() + ")";
    }

}