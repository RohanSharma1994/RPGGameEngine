/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Rohan Sharma rsharma1
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Image;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.newdawn.slick.Color;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
    /* Data members */
	/* The player object */
	private Player player;
	/* The camera object */
	private Camera camera;
	/* The map of the world */
	private TiledMap map;
	private Villager v1;
	private Villager v2;
	private Villager v3;
	private Image panel;
    private ArrayList <PassiveMonster> p_monsters;
    private ArrayList <AggresiveMonster>  a_monsters;
    private Item item1, item2, item3, item4;
	
	/* Constructor */
    /** Create a new World object. */
    public World()
    throws SlickException
    {
    	player = new Player("assets/units/player.png");
    	camera = new Camera(player, RPG.screenwidth, RPG.screenheight);
    	map = new TiledMap("assets/map.tmx", "assets/");
    	v1 = new Villager("assets/units/shaman.png", 738, 549,"Elvira");
    	v2 = new Villager("assets/units/peasant.png", 756, 870,"Garth");
    	v3 = new Villager("assets/units/prince.png", 467, 679,"Prince Aldric");
    	panel = new Image("assets/panel.png");
    	item1 = new Item("assets/amulet.png", "Amulet of Vitality", 965,3563);
    	item2 = new Item("assets/sword.png", "Sword of Strength", 4791,1253);
    	item3 = new Item("assets/book.png", "Tome of Agility",546,6707);
    	item4 =new Item("assets/elixir.png", "Elixir of Life", 1976,402);
    	a_monsters = new ArrayList <AggresiveMonster>(20);
    	p_monsters = new ArrayList <PassiveMonster>(20);
    	try {
    		/* Generate the monster spawns */
    		FileReader fr = new FileReader("assets/spawn/aggressive_monster_spawn");
    		BufferedReader br = new BufferedReader(fr);
    		String str;
    		while((str = br.readLine()) != null) {
    			StringTokenizer tokens = new StringTokenizer(str,", ");
    			String dir = "assets/units/"+ tokens.nextToken();
    			int start_x = Integer.parseInt(tokens.nextToken());
    			int start_y = Integer.parseInt(tokens.nextToken());
    			int health = Integer.parseInt(tokens.nextToken());
    			int damage = Integer.parseInt(tokens.nextToken());
    			int cooldown = Integer.parseInt(tokens.nextToken());
    			a_monsters.add(new AggresiveMonster(dir, start_x, start_y, health, damage, cooldown));
    	   }
    		fr.close();
    		br.close();
    		fr = new FileReader("assets/spawn/passive_monster_spawn");
    		br = new BufferedReader(fr);
    		while((str = br.readLine()) != null) {
    			StringTokenizer tokens = new StringTokenizer(str,", ");
    			String dir = "assets/units/"+ tokens.nextToken();
    			int start_x = Integer.parseInt(tokens.nextToken());
    			int start_y = Integer.parseInt(tokens.nextToken());
    			int health = Integer.parseInt(tokens.nextToken());
    			p_monsters.add(new PassiveMonster(dir, start_x, start_y, health));
    	   }
    	   
    		
    		
    	}
        catch(IOException e) {
        	System.out.println("File not found");
        	System.exit(0);
        	
        }
        	
        
    	
    }

    /** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void update(double dir_x, double dir_y, int delta, boolean attack, boolean interact, boolean teleport, boolean items1, boolean items2, boolean items3, boolean items4,
    		           boolean run)
    throws SlickException
    {      
    	/* At first update the player object */
    	player.update(dir_x, dir_y, delta, this);
    	/* Now based on this player, update the camera */
    	camera.update();
    	player.setAttack(attack);
    	player.setInteract(interact);
    	for(PassiveMonster monster: p_monsters) {
    		monster.update(delta, this, player);
    		if(monster.getHealth() <= 0) {
    			monster.alive = false;
    		}
    	}
    	for(AggresiveMonster monster: a_monsters) {
    		monster.update(delta, this, player);
    		if(monster.getHealth() <= 0) {
    			monster.alive = false;
    		}
    	}
    	item1.checkPickup(player);
    	item2.checkPickup(player);
    	item3.checkPickup(player);
    	item4.checkPickup(player);
    	v1.talk(player, delta);
    	v2.talk(player, delta);
    	v3.talk(player, delta);
    	if(teleport) {
    		player.setMapX(player.INITIAL_X);
    		player.setMapY(player.INITIAL_Y);
    		teleport = false;
    	}
    	if(items1) {
    		player.setMapX(965);
    		player.setMapY(3563);
    		items1 = false;
    	}
    	if(items2) {
    		player.setMapX(4791);
    		player.setMapY(1253);
    		items2 = false;
    	}
    	if(items3) {
    		player.setMapX(546);
    		player.setMapY(6707);
    		items3 = false;
    	}
    	if(items4) {
    		player.setMapX(1976);
    		player.setMapY(402);
    		items4 = false;
    	}
    	if(run) {
    		player.setSpeed(player.INITIAL_SPEED*2);
    		run = false;
    	}
    	else {
    		player.setSpeed(player.INITIAL_SPEED);
    	}
    	
    	
    	
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {
        /* Based on where the camera is we render the map */
    	int tile_x = camera.getMinX()/map.getTileWidth();
    	int tile_y = camera.getMinY()/map.getTileHeight();
    	int starting_x = -camera.getMinX()%map.getTileWidth();
    	int starting_y = -camera.getMinY()%map.getTileHeight();
    	
    	/* Width to render in tiles */
    	/* Adding 3 to make it render a bit more than the size of the screen*/
    	int width = (camera.getMaxX() - camera.getMinX())/map.getTileWidth() + 3;
        /* Height to render in tiles */
    	int height = (camera.getMaxY() - camera.getMinY())/map.getTileHeight() + 3;
    	/* And finally render the map */
        map.render(starting_x, starting_y, tile_x, tile_y, width,height);
    	/* And also render the player */
        v1.render(camera, g);
    	v2.render(camera, g);
        v3.render(camera, g);    	
    	for(PassiveMonster monster: p_monsters) {
    		monster.render(camera, g);
    	}
    	for(AggresiveMonster monster: a_monsters) {
    		monster.render(camera, g);
    	}
    	player.render();
    	renderPanel(g);
    }
    
    /** Check the collision of a player with map elements e.t.c
     * @param final_position_x The x position on the map where the player wants to move to
     * @param final_position_y The y position on the map where the player wants to move to
     * Returns true if the final (x,y) position is blocked, otherwise returns false meaning
     * the player can be moved there.
     */
    public boolean block(double final_position_x, double final_position_y) {
    	int x = (int)final_position_x;
    	int y = (int)final_position_y;
    	
    	/*Get the tileID where the player wants to move to*/
    	int tileId = map.getTileId((int)final_position_x/72, (int)final_position_y/72, 0);
    	
    	/*Check if the player is going to move off the map horizontally*/
    	if(x+5 >= map.getHeight()*map.getTileWidth() || x <= 0) {
    		return true;
    	}
    	/*Check if the player is going to move off the map vertically*/
    	if(y+5 >= map.getHeight()*map.getTileHeight() || y <= 0) {
    		return true;
    	}
    	
    	/* And finally check if the tile the player is moving on can be walked on*/
    	if(map.getTileProperty(tileId, "block", "0").equals("1")) {
    		return true;
    	}
    		
    	return false;
    	
    	
    }
    /** Renders the player's status panel.
     * @param g The current Slick graphics context.
     */
    public void renderPanel(Graphics g)
    {
        // Panel colours
        Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp

        // Variables for layout
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int hp_bar_width;           // Size of red (HP) rectangle
        int inv_x, inv_y;           // Coordinates to draw inventory item

        float health_percent;       // Player's health, as a percentage

        // Panel background image
        panel.draw(0, RPG.screenheight - RPG.PANELHEIGHT);

        // Display the player's health
        text_x = 15;
        text_y = RPG.screenheight - RPG.PANELHEIGHT + 25;
        g.setColor(LABEL);
        g.drawString("Health:", text_x, text_y);
        text = player.getHealth() + "/" + player.max_health;                                

        bar_x = 90;
        bar_y = RPG.screenheight - RPG.PANELHEIGHT + 20;
        bar_width = 90;
        bar_height = 30;
        health_percent = (float)player.getHealth()/player.max_health;                         
        hp_bar_width = (int) (bar_width * health_percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's damage and cooldown
        text_x = 200;
        g.setColor(LABEL);
        g.drawString("Damage:", text_x, text_y);
        text_x += 80;
        text = player.getDamage() +"";                                    
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
        text_x += 40;
        g.setColor(LABEL);
        g.drawString("Rate:", text_x, text_y);
        text_x += 55;
        text = player.getCooldown() +"";                             
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's inventory
        g.setColor(LABEL);
        g.drawString("Items:", 420, text_y);
        bar_x = 490;
        bar_y = RPG.screenheight - RPG.PANELHEIGHT + 10;
        bar_width = 288;
        bar_height = bar_height + 20;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);

        inv_x = 490;
        inv_y = RPG.screenheight - RPG.PANELHEIGHT
            + ((RPG.PANELHEIGHT - 72) / 2);
        Item[] items = {item1, item2, item3, item4};
        for(Item item: items)
        {
        	item.render(inv_x, inv_y, camera);
        	inv_x += 72;
        }
        
    }

}
