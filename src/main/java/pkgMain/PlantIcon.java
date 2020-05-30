package pkgMain;
import javafx.scene.shape.Circle;

/**
 * Class - PlantIcon, this class makes an icon of a plant containing all of the
 * 					  relevant information about the plant.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria Venrooy
 */
public class PlantIcon extends Plant {
	private static final long serialVersionUID = 1L;
	private double x; //x coordinate of plant icon
	private double y; // y coordinate of plant icon
	private Circle circle;
	private Plant plant;
	
	
	/**
	 * custom constructor for plantIcon
	 * 
	 * @param circle the circle representing the plant (the actual icon)
	 * @param x the x coordinate of the circle
	 * @param y the y coordinate of the circle
	 * @param p the plant that a particular icon represents
	 */
	public PlantIcon(Circle circle, double x, double y, Plant p) {
		this.circle = circle;
		this.x = x;
		this.y = y;
		this.plant = p;
	}

	
	/**
	 * returns the Plant associated with a plant icon
	 * 
	 * @return the Plant an icon is representing
	 */
	public Plant getPlant() {
		return plant;
	}

	
	/**
	 * sets the plant for an icon
	 * 
	 * @param plant the Plant that the PlantICon is representing
	 */
	public void setPlant(Plant plant) {
		this.plant = plant;
	}
	

	public PlantIcon() {
		
	}
	
	
	/**
	 * Get the X location of the PlantIcon
	 * 
	 * @return a the X location of the PlantIcon
	 */
	public double getX() {
		return x;
	}
	
	
	/**
	 * Get the Y location of the PlantIcon
	 * 
	 * @return a the Y location of the PlantIcon
	 */
	public double getY() {
		return y;
	}
	
	
	/**
	 * Set the X location of the PlantIcon
	 * 
	 * @param x a double which will be the new X
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	
	/**
	 * Set the Y location of the PlantIcon
	 * 
	 * @param y a double which will be the new Y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	
	/**
	 * returns the circle that is the visual representation of the plant
	 * 
	 * @return a Circle that represents the PlantIcon
	 */
	public Circle getCircle() {
		return circle;
	}

	
	
}
