/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Rohan Sharma rsharma1
 */
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Image;
import org.newdawn.slick.Color;

public class Monster {
	/* Data members */
	/* The image of the monster */
	private Image model;
	/* The x-coordinate of the monster on the map */
	private double mapX;
	/* The y-coordinate of the monster on the map */
	private double mapY;
	private int health;
	private int current_health;
	public boolean alive = true;
	/* The name of the monster*/
	String name;
	/* Constructor */
	/**
	 * Creates a monster with image in dir, and on the
	 * map position mapx, and mapy
	 * */
	public Monster(String dir, double mapX, double mapY, int health)
	throws SlickException {
		super();
		model = new Image(dir);
		this.mapX = mapX;
		this.mapY = mapY;
		if(dir.equals("assets/units/zombie.png")) {
			name = new String("Zombie");
		}
		if(dir.equals("assets/units/bandit.png")) {
			name = new String("Bandit");
		}
		if(dir.equals("assets/units/skeleton.png")) {
			name = new String("Skeleton");
		}
		if(dir.equals("assets/units/necromancer.png")) {
			name = new String("Draelic");
		}
		if(dir.equals("assets/units/dreadbat.png")) {
			name = new String("Giant Bat");
		}
		this.health = health;
		current_health = health;
	}
	/* Methods */
	/* Setters and getters */

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
	
	/* Methods, for now only a render method is required*/
	public void render(Camera c, Graphics g) 
	throws SlickException 
	{
		if(mapX > c.getMinX() - model.getWidth() && mapX < c.getMaxX() + model.getWidth() && mapY > c.getMinY() -model.getHeight() && mapY < c.getMaxY() + model.getHeight() && alive) {
			/* Need to render the villager relative to the camera */
			int x_relative = (int)mapX - c.getMinX();
			int y_relative = (int)mapY - c.getMinY();
			model.drawCentered(x_relative, y_relative);
			/* Need to render the monster's health bar above it */
			Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp
			Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
			Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
			float health_percent = (float)current_health/health;       // Monster's health, as a percentage
			g.setColor(BAR_BG);
			int height = 15;
			int width = 70;
			g.fillRect((float)x_relative-25,(float)y_relative-45, (float)width,(float) height);

			
			width = (int)(70*health_percent);
			
			
			g.setColor(BAR);
			g.fillRect((float)x_relative-25,(float)y_relative-45, (float)width,(float) height);
			g.setColor(VALUE);
			
			g.drawString(name, (float)x_relative-25, (float)y_relative - 45);
		
		}
	}
	public void hit(int hit) {
		current_health -= hit;
	}
	

	public void updateHealth(int health) {
		this.current_health -= health;
	}
	public int getHealth() {
		return current_health;
	}
	
}
