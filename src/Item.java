import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.lang.Math;
public class Item {
	private Image model;
	private String name;
	private int mapX;
	private int mapY;
	public boolean pickedup = false;
	public Item(String dir, String name, int mapX, int mapY) 
	throws SlickException
	{
		model = new Image(dir);
		this.name = name;
		this.mapX = mapX;
		this.mapY = mapY;
	}
	/*Methods*/
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	public void render(int x, int y, Camera c) {
		if(pickedup) {
			model.draw(x, y);
		}
		else {
			if(mapX > c.getMinX() - model.getWidth() && mapX < c.getMaxX() + model.getWidth() && mapY > c.getMinY() -model.getHeight() && mapY < c.getMaxY() + model.getHeight()) {
				int x_relative = mapX - c.getMinX();
				int y_relative = mapY - c.getMinY();
				model.drawCentered(x_relative, y_relative);
			}
		}
	}
	
	public void checkPickup(Player player) {
		if(!pickedup) {
			double dis_x = Math.abs(player.getMapX()-mapX);
			double dis_y = Math.abs(player.getMapY()-mapY);
			double distance = Math.sqrt(dis_x*dis_x+ dis_y*dis_y);
			if(distance <= 50) {
				player.addItem(this);
				pickedup = true;
				if(name.equals("Amulet of Vitality")) {
					player.max_health = 180;
				}
				if(name.equals("Sword of Strength")) {
					player.setDamage(player.getDamage()+30);
				}
				if(name.equals("Tome of Agility")) {
					player.setCooldown(player.getCooldown()-300);
				}
			}
		}
	}
	
}
