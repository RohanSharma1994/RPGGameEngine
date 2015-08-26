/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Rohan Sharma rsharma1
 */

import org.newdawn.slick.SlickException;

import org.newdawn.slick.Graphics;
import java.lang.Math;
import org.newdawn.slick.Color;

import org.newdawn.slick.Image;

public class Villager implements Dialogue {
	/* Data members */
	/* An image of the villager */
	private Image model;
	/* The x-coordinate of the villager on the map */
	private double mapX;
	/* The y-coordinate of the villager on the map */
	private double mapY;
	/* Health of the villagers */
	private double hp;
	private String name;
	private int timer = 0;
	private String say;
	private boolean talk = false;
	
	/* Constructors */
	/**
	 * Create the villager with an image located in dir, 
	 * at the position mapX, and mapY on the map.
	 * */
	public Villager(String dir, double mapX, double mapY, String name)
	throws SlickException {
		model = new Image(dir);
		this.mapX = mapX;
		this.mapY = mapY;
		hp  = 1 ;
		this.name = name;
	}
	/* Methods */
	/* Getters/setters */

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
	// Speaks methods
	public void speak(String quote) {
		// To be decided
	}
	
	/* Only time you render a villager is when there is a camera on him */
	public void render(Camera c, Graphics g)
	throws SlickException {
		if(mapX > c.getMinX() - model.getWidth() && mapX < c.getMaxX() + model.getWidth() && mapY > c.getMinY() -model.getHeight() && mapY < c.getMaxY() + model.getHeight()) {
			/* Need to render the villager relative to the camera */
			int x_relative = (int)mapX - c.getMinX();
			int y_relative = (int)mapY - c.getMinY();
			int height = 15;
			Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp
			Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
			Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.6f);   // Black, transp
			int width = 60;
			model.drawCentered(x_relative, y_relative);
			g.setColor(BAR);
			g.fillRect((float)x_relative-25,(float)y_relative-45, (float)width,(float) height);
			
			g.setColor(VALUE);
			if(name.equals("Prince Aldric")) {
				g.drawString("Aldric",(int)(mapX-c.getMinX())-15, (int)(mapY-c.getMinY()) - 45);
			}
			else {
				g.drawString(name, (int)(mapX-c.getMinX())-15, (int)(mapY-c.getMinY()) - 45);
			}
			if(talk) {
				
				g.setColor(BAR_BG);
				// Print the background for the string
				g.fillRect((float)(mapX-c.getMinX())-150,(float)(mapY-c.getMinY()) -60, say.length()*8,(float) height);
				g.setColor(VALUE);
				g.drawString(say, (int)(mapX-c.getMinX())-150+5, (int)(mapY-c.getMinY()) - 60);
			}
			
		}
		
	}
	public void talk(Player player, int delta) {
		timer -= delta;
		if(player.getInteract()) {
			double dist_x = player.getMapX() - mapX;
			double dist_y = player.getMapY() - mapY;
			double distance = Math.sqrt(dist_x*dist_x + dist_y*dist_y);
			if(distance <= 50) {
				timer = 4000;
				talk = true;
				if(name.equals("Prince Aldric")) {
					Item[] items = player.getItem();
					boolean has_elixir = false;
					for(Item item: items) {
						if(item!= null) {
							if(item.getName().equals("Elixir of Life")) {
								has_elixir = true;
								say = "The elixir! My father is cured! Thank you!";
							}
						}
					}
					if(!has_elixir) {
						say = "Please seek out the Elixir of Life to cure the king.";
					}
				}
				if(name.equals("Elvira")) {
					if(player.getHealth() == player.max_health) {
						say = "Return to me if you ever need healing";
					}
					else {
						say = "You're looking much healthier now.";
						player.setHealth(player.max_health);
					}
				}
				if(name.equals("Garth")) {
					Item[] items = player.getItem();
					boolean has_amulet = false;
					boolean has_sword = false;
					boolean has_tome = false;
					for(Item item: items) {
						if(item != null) {
							if(item.getName().equals("Amulet of Vitality")) {
								has_amulet = true;
							}
							if(item.getName().equals("Sword of Strength")) {
								has_sword = true;
							}
							if(item.getName().equals("Tome of Agility")) {
								has_tome = true;
							}
						}
					}
					if(!has_amulet) {
						say = "Find the Amulet of Vitality, across the river to the west.";
					}
					else if(!has_sword) {
						say = "Find the Sword of Strength - cross the river and back, on the east side.";
					}
					else if(!has_tome) {
						say = "Find the Tome of Agility, in the Land of Shadows.";
					}
					else {
						say = "You have found all the treasure I know of.";
					}
					
			}
				
			}
			
		}
		if(timer <= 0) {
			talk = false;
		}
		
	}

}
