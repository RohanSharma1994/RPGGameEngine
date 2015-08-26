/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Rohan Sharma rsharma1
 */
import org.newdawn.slick.SlickException;
import java.util.Random;
import java.lang.Math;

public class PassiveMonster extends Monster {
	public static final double speed = 0.2;
	private int time;
	private double dir_x;
	private double dir_y;
	private Random random;
	public boolean threatened;
	/* For now just assume it doesn't have any data members*/
	/* Constructor */
	public PassiveMonster(String dir, double mapX, double mapY, int health) 
    throws SlickException
	{
		super(dir, mapX, mapY, health);
		time = 0;
		random = new Random();
		dir_x = 0;
		dir_y = 0;
		threatened = false;
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
		   
		   double distx = -player.getMapX()+getMapX();
		   double disty = -player.getMapY()+getMapY();
		   double dist_total = Math.sqrt(distx*distx+disty*disty);
		   int playerhit = player.getHit(delta);
		   if(dist_total <= 50 && player.getAttack()) {
			   threatened = true;
			   hit(playerhit);
		   }
		   if(!threatened) {
			   time += delta;
			   if(time >= 3000) {
				   time = 0;
				   dir_x = random.nextInt(3) - 1;
				   dir_y = random.nextInt(3) - 1;
			   
			   }
		   

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
		   else {
			   /* The monster is under threat */
			   /* It should aim to get away from the player */
			   time += delta;
			   if(time >= 5000) {
				   time = 0;
				   threatened = false;
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
	
}
