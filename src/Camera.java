/* SWEN20003 Object Oriented Software Development
 * RPG Game Engine
 * Author: Rohan Sharma rsharma1
 */

import org.newdawn.slick.SlickException;

/** Represents the camera that controls our viewpoint.
 */
public class Camera
{

    /** The unit this camera is following */
    private Player unitFollow;
    
    /** The width and height of the screen */
    /** Screen width, in pixels. */
    public final int screenwidth;
    /** Screen height, in pixels. */
    public final int screenheight;

    
    /** The camera's position in the world, in x and y coordinates. */
    private int xPos;
    private int yPos;

    
    /** Create a new World object. */
    public Camera(Player player, int screenwidth, int screenheight)
    {   
        xPos = (int)player.getMapX();
        yPos = (int)player.getMapY();
        unitFollow = player;
        this.screenwidth = screenwidth;
        this.screenheight = screenheight;
    }

    /** Update the game camera to recentre it's viewpoint around the player 
     */
    public void update()
    throws SlickException
    {
        xPos = (int)unitFollow.getMapX();
        yPos = (int)unitFollow.getMapY();
    }
  
    /** @return the xPos of the camera in the world */
    public int getxPos() {
        return xPos;
    }

    /** @return the yPos of the camera in the world */
    public int getyPos() {
        return yPos;
    }
    
    /** Returns the minimum x value on screen 
     */
    public int getMinX(){
        return xPos - screenwidth/2;
    }
    
    /** Returns the maximum x value on screen 
     */
    public int getMaxX(){
        return xPos + screenwidth/2;
    }
    
    /** Returns the minimum y value on screen 
     */
    public int getMinY(){
        return yPos - screenheight/2;
    }
    
    /** Returns the maximum y value on screen 
     */
    public int getMaxY(){
        return yPos + screenheight/2;
    }

    /** Tells the camera to follow a given unit. 
     */
    public void followUnit(Object unit)
    throws SlickException
    {
        if(unit instanceof Player) {
        	unitFollow = (Player)unit;
        }
        else {
        	throw new SlickException("Please pass a player object to follow.");
        }
    }
    
}