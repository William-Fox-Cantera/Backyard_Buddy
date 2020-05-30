package pkgMain;
import java.io.Serializable;

/**
 * Class - PlantSave, the class handles putting a plant into an object which can
 * 					  be serialized. 
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria Venrooy
 */
public class PlantSave implements Serializable{
	public double x;
	public double y;
	public String name;
	
	
	private static final long serialVersionUID = -2048157321468495452L;
	/**
	 * PlantSave constructor
	 * @param x x location
	 * @param y y location
	 * @param p name of plant
	 */
	public PlantSave(double x, double y, String p) {
		this.x = x;
		this.y = y;
		this.name = p;
	}

	
	/**
	 * toString, prints the plantsave objects values
	 */
	@Override
	public String toString() {
		return "X: " + this.x + " Y: " + this.y;
	}

	
	/**
	 * getX, getter for the x location.
	 * @return x the x location
	 */
	public double getX() {
		return x;
	}

	
	/**
	 * setX, sets the gardenshape x location
	 * @param x the gardenshape x location
	 */
	public void setX(double x) {
		this.x = x;
	}

	
	/**
	 * getY, getter for the y location.
	 * @return y the y location
	 */
	public double getY() {
		return y;
	}

	
	/**
	 * setY, sets the gardenshape y location
	 * @param y the gardenshape y location
	 */
	public void setY(double y) {
		this.y = y;
	}

	
	/**
	 * getName, getter for the name.
	 * 
	 * @return name the garden name
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * setName, sets the gardenshape name
	 * 
	 * @param name the gardenshape name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
