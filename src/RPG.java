/* 433-294 Object Oriented Software Development
 * RPG Game Engine
 * Author: Matt Giuca <mgiuca>
 */

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Font;

/** Main class for the Role-Playing Game engine.
 * Handles initialisation, input and rendering.
 */
public class RPG extends BasicGame
{
    private World world;
    public static final int PANELHEIGHT = 70;
    /** Screen width, in pixels. */
    public static final int screenwidth = 800;
    /** Screen height, in pixels. */
    public static final int screenheight = 600;
    private Font font;

    /** Create a new RPG object. */
    public RPG()
    throws SlickException
    {
        super("RPG Game Engine");
    }

    /** Initialise the game state.
     * @param gc The Slick game container object.
     */
    @Override
    public void init(GameContainer gc)
    throws SlickException
    {
        world = new World();
        font = FontLoader.loadFont("assets/DejaVuSans-Bold.ttf", 13);
    }

    /** Update the game state for a frame.
     * @param gc The Slick game container object.
     * @param delta Time passed since last frame (milliseconds).
     */
    @Override
    public void update(GameContainer gc, int delta)
    throws SlickException
    {
        // Get data about the current input (keyboard state).
        Input input = gc.getInput();

        // Update the player's movement direction based on keyboard presses.
        double dir_x = 0;
        double dir_y = 0;
        boolean attack = false;
        boolean interact = false;
        boolean teleport = false;
        boolean item1 = false;
        boolean item2 = false;
        boolean item3 = false;
        boolean item4 = false;
        boolean run = false;
        if (input.isKeyDown(Input.KEY_DOWN))
            dir_y += 1;
        if (input.isKeyDown(Input.KEY_UP))
            dir_y -= 1;
        if (input.isKeyDown(Input.KEY_LEFT))
            dir_x -= 1;
        if (input.isKeyDown(Input.KEY_RIGHT))
            dir_x += 1;
        if (input.isKeyDown(Input.KEY_A)) {
        	attack = true;
        }
        if (input.isKeyPressed(Input.KEY_T)) {
        	interact = true;
        }
        if(input.isKeyPressed(Input.KEY_R)) {
        	teleport = true;
        }
        if(input.isKeyPressed(Input.KEY_1)) {
        	item1 = true;
        }
        if(input.isKeyPressed(Input.KEY_2)) {
        	item2 = true;
        }
        if(input.isKeyPressed(Input.KEY_3)) {
        	item3 = true;
        }
        if(input.isKeyPressed(Input.KEY_4)) {
        	item4 = true;
        }
        if(input.isKeyDown(Input.KEY_SPACE)) {
        	run = true;
        }
        
        // Let World.update decide what to do with this data.
        world.update(dir_x, dir_y, delta, attack, interact, teleport, item1, item2, item3, item4, run);
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param gc The Slick game container object.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(GameContainer gc, Graphics g)
    throws SlickException
    {
    	// Overwrite that ugly slick font
    	g.setFont(font);
        // Let World.render handle the rendering.
        world.render(g);
    }

    /** Start-up method. Creates the game and runs it.
     * @param args Command-line arguments (ignored).
     */
    public static void main(String[] args)
    throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new RPG());
        // setShowFPS(true), to show frames-per-second.
        app.setShowFPS(false);
        app.setDisplayMode(screenwidth, screenheight, false);
        app.start();
    }
}
