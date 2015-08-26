/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Rohan Sharma rsharma1
 */

import org.newdawn.slick.SlickException;
import java.lang.Math;
import java.util.Random;
public class AggresiveMonster extends Monster {
	/* For now just assume it doesn't have any data members*/
	/* Constructor */
	private double speed = 0.25;
	private int damage;
	private boolean fight;
	private int cooldown;
	private int timer;
	public AggresiveMonster(String dir, double mapX, double mapY, int health, int damage, int cooldown) 
    throws SlickException
	{
		super(dir, mapX, mapY, health);
		fight = false;
		this.damage = damage;
		this.cooldown = cooldown;
		timer = 0;
	}
	/** Update the monster state on the map.
	    * @param dir_x The player's movement in the x axis (-1, 0 or 1).
	    * @param dir_y The player's movement in the y axis (-1, 0 or 1).
	    * @param delta Time passed since last frame (milliseconds).
	    * @param world the World object which should have a block function which
	    * detects collision.
	    */
	   public void update(int delta, World world, Player player)
	   throws SlickException
	   {
		   
		   double distx = player.getMapX()-getMapX();
		   double disty = player.getMapY()-getMapY();
		   double dist_total = Math.sqrt(distx*distx+disty*disty);
		   int playerhit = player.getHit(delta);
		   if(dist_total <= 150 && alive) {
			   fight = true;
		   }
		   if(fight && alive) {
			   timer = timer - delta;
			   /* The monster is under threat */
			   /* It should attack the player */
			   if(dist_total > 150) {
				   /* Player ran away lol, or probably died*/
				   fight = false;
				   return;
			   }
			   if(dist_total <= 50) {
				   if(timer <= 0) {
					   attack(player);
					   timer = cooldown;
				   }
				   if(player.getAttack()) {
					   hit(playerhit);
				   }
				   
				   return;
			   }
			 
			   
			   
			   double dir_x = (distx/dist_total);
			   double dir_y = (disty/dist_total);
			   /* Make sure there is no terrain in the way and also implement smooth walls*/
			   double final_position_x = getMapX() + dir_x*delta*speed;
			   double final_position_y = getMapY() + dir_y*delta*speed;
			   double original_dir_x = dir_x;
			   double original_dir_y = dir_y;
	  
			   /* The implementation for smooth blocking */
			   /* Indicates a collision in the x direction */
			   if(world.block(final_position_x, getMapY())) {
				   dir_x = 0;
			   }
			   /* Indicates a collision in the y direction */
			   if(world.block(getMapX(), final_position_y)) {
				   dir_y = 0;
			   }
			   /* And finally if the player is trying to move diagonally and the smooth walls stop it*/
			   if(!world.block(final_position_x, final_position_y)) {
				   dir_x = original_dir_x;
				   dir_y = original_dir_y;
			   }
	   
			   /* Now update the player's position*/
			   setMapX(getMapX() + dir_x*delta*speed);
			   setMapY(getMapY() + dir_y*delta*speed);
		   }
	   }
	   private void attack(Player player) {
		   Random random = new Random();
		   player.updateHealth(random.nextInt(damage+1));
	   }
	  
	   
	

}
