package pkgMain;
import java.io.Serializable;

/**
 * Class - GardenShapeSave, saves a garden shape to a .ser file.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria Venrooy
 */
public class GardenShapeSave implements Serializable {
	public double x;
	public double y;
	public double xLoc;
	public double yLoc;
	public double radius;
	public String name;
	public String color;
	private static final long serialVersionUID = -2048157321468495452L;
	
	/**
	 * GardenSaveSave, constructor.
	 * 
	 * @param x x length
	 * @param y y length
	 * @param name the name of the gardenshape
	 * @param color the color of the gardenshape
	 * @param xLoc x location on the scene
	 * @param yLoc y location on the scene
	 * @param r radius
	 */
	public GardenShapeSave(double x, double y, String name, String color, double xLoc, double yLoc, double r) {
		this.radius = r;
		this.color = color;
		this.x = x;
		this.y = y;
		this.name = name;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
	}
	
	/**
	 * getColor, getter for the color.
	 * 
	 * @return the gardenshape color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * getxLoc, getter for the x location.
	 * @return the x location
	 */
	public double getxLoc() {
		return xLoc;
	}

	/**
	 * getyLoc, getter for the y location.
	 * @return the y location
	 */
	public double getyLoc() {
		return yLoc;
	}

	/**
	 * toString, prints the gardenshapesave objects values
	 */
	@Override
	public String toString() {
		return "Shape: " + this.name + " X: " + this.x + " Y: " + this.y + " xLoc: " + this.xLoc + " yLoc: " + this.yLoc + " radius: " + this.radius;
	}

	/**
	 * getRadius, getter for the Radius.
	 * @return the Radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * getX, getter for the x length.
	 * @return the x length
	 */
	public double getX() {
		return x;
	}
	/**
	 * getY, getter for the y length.
	 * @return the y length
	 */
	public double getY() {
		return y;
	}

	/**
	 * getName, getter for the name.
	 * 
	 * @return the garden name
	 */
	public String getName() {
		return name;
	}
}


