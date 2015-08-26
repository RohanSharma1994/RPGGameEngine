/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Rohan Sharma rsharma1
 */
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import java.util.Random;
public class Player {
	/* Data members */
	/* An image of the player */
	private Image model;
	/* The x-coordinate of the player on the map */
	private double mapX;
	/* The y-coordinate of the player on the map */
	private double mapY;
	/* The x-coordinate of the player on the screen */
	private double screenX;
	/* The y-coordinate of the player on the screen */
	private double screenY;
	/* The speed of the player */
	private double speed;
	/* A flag indicated whether the player's image needs to be flipped*/
	private boolean flip;
	private Item[] item;
	private int items = 0;
	private boolean interact;
	
	
	/** The initial x-position on the map*/
	public static final double INITIAL_X = 756;
	/** The initial y-position on the map*/
	public static final double INITIAL_Y = 684;
	/** The x-position on the screen*/
	public static final double SCREEN_X = RPG.screenwidth/2;
	/** The y-position on the screen*/
	public static final double SCREEN_Y = RPG.screenheight/2;
	/** The speed of the player */
	public static final double INITIAL_SPEED = 0.25;
	private boolean attack;
	private int health = 100;
	public int max_health = 100;
	private int damage = 26;
	private int cooldown = 600;
	private int timer = 0;
	
	/*Constructors*/
	/** Create a player (image location is dir)
	 *  on the specified mapX, mapY position on the map and 
	 *  screenX, screenY position on the screen with a given speed. */
	public Player (String dir, double mapX, double mapY, double screenX, 
			       double screenY, double speed)
	throws SlickException {
		model = new Image(dir);
		this.mapX = mapX;
		this.mapY = mapY;
		this.screenX = screenX;
		this.screenY = screenY;
		this.speed = speed;
		flip = true;
		attack = false;
		item = new Item[4];
		interact = false;
	}
	
	/** Create a player (image location is dir)
	 *  on the specified x,y position on the map with a given
	 *  speed.*/
	public Player(String dir, double mapX, double mapY, double speed)
	throws SlickException {
		this(dir, mapX, mapY, SCREEN_X, SCREEN_Y, speed);
	}
	
	/** Create a player (Image location is dir)*/
	public Player(String dir)
	throws SlickException {
		this(dir, INITIAL_X, INITIAL_Y, SCREEN_X, SCREEN_Y, INITIAL_SPEED);
	}
    
	/*Methods*/
	/*Setters/getters*/
	/**
	 * @return the model
	 */
	public Image getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(Image model) {
		this.model = model;
	}

	/**
	 * @return the mapX
	 */
	public double getMapX() {
		return mapX;
	}

	/**
	 * @param mapX the mapX to set
	 */
	public void setMapX(double mapX) {
		this.mapX = mapX;
	}

	/**
	 * @return the mapY
	 */
	public double getMapY() {
		return mapY;
	}

	/**
	 * @param mapY the mapY to set
	 */
	public void setMapY(double mapY) {
		this.mapY = mapY;
	}

	/**
	 * @return the screenX
	 */
	public double getScreenX() {
		return screenX;
	}

	/**
	 * @param screenX the screenX to set
	 */
	public void setScreenX(double screenX) {
		this.screenX = screenX;
	}

	/**
	 * @return the screenY
	 */
	public double getScreenY() {
		return screenY;
	}

	/**
	 * @param screenY the screenY to set
	 */
	public void setScreenY(double screenY) {
		this.screenY = screenY;
	}

	/**
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
   /** Render the player on the screen
    *
    */
   public void render()
   throws SlickException
   {
	   model.drawCentered((int)screenX,(int)screenY);
   }
	
   /** Update the player state on the map.
    * @param dir_x The player's movement in the x axis (-1, 0 or 1).
    * @param dir_y The player's movement in the y axis (-1, 0 or 1).
    * @param delta Time passed since last frame (milliseconds).
    * @param world the World object which should have a block function which
    * detects collision.
    */
   public void update(double dir_x, double dir_y, int delta, World world)
   throws SlickException {
	   /* Orient the image appropriately */
	   if(health <= 0) {
		   mapX = INITIAL_X;
		   mapY = INITIAL_Y;
		   health = max_health;
		   return;
	   }
	   if(dir_x < 0 && flip) {
		   model = model.getFlippedCopy(true, false);
   		   flip = false;
   	   }
   	   else if(dir_x > 0 && !flip) {
   		   model = model.getFlippedCopy(true, false);
   		    flip = true;
   	   }
	   
	   /* Make sure there is no terrain in the way and also implement smooth walls*/
	   double final_position_x = mapX + dir_x*delta*speed;
	   double final_position_y = mapY + dir_y*delta*speed;
	   double original_dir_x = dir_x;
	   double original_dir_y = dir_y;
	  
	   /* The implementation for smooth blocking */
	   /* Indicates a collision in the x direction */
	   if(world.block(final_position_x, mapY)) {
		   dir_x = 0;
	   }
	   /* Indicates a collision in the y direction */
	   if(world.block(mapX, final_position_y)) {
		   dir_y = 0;
	   }
	   /* And finally if the player is trying to move diagonally and the smooth walls stop it*/
	   if(!world.block(final_position_x, final_position_y)) {
		   dir_x = original_dir_x;
		   dir_y = original_dir_y;
	   }
	   
	   /* Now update the player's position*/
	   mapX += dir_x*delta*speed;
	   mapY += dir_y*delta*speed;
	  
    }
   public void updateHealth(int health) {
	   this.health -= health;
   }
   public int getHealth(){
	   return health;
   }
   public int getDamage() {
	   return damage;
   }
   public int getCooldown() {
	   return cooldown;
   }
   public void setAttack(boolean x) {
	   attack = x;
   }
   public boolean getAttack() {
	   return attack;
   }
   public int getHit(int delta) {
	   if(timer <= 0) {
		   timer = cooldown;
		   Random random = new Random();
		   return random.nextInt(damage+1);
	   }
	   else {
	       timer -= delta;
	       return 0;
	   }
	   
   }
   public void addItem(Item item) {
	  this.item[items++] = item;
   }
   public Item[] getItem() {
	   
	   return item;
   }
   public void setCooldown(int cooldown) {
	   this.cooldown = cooldown;
   }
   public void setDamage(int damage) {
	   this.damage = damage;
   }
   public void setInteract(boolean interact) {
	   this.interact = interact;
   }
   public boolean getInteract() {
	   return interact;
   }
   public void setHealth(int health) {
	   this.health = health;
   }
}
