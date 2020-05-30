package pkgMain;

import javafx.scene.image.Image;

/**
 * Class - Point, sets up attributes for an x and y cartesian position within the GUI.
 * 				  The location also has an image associated with it.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria Venrooy
 * 
 * @version 0.0.1
 */
public class Point {
	private String name;
	private int height;  
	private int width;
	double x;
	double y;
	private Image pImg; 
	
	
	/**
	 * Point, constructor.
	 * 
	 * @param name the name
	 * @param height the height of the point
	 * @param width the width of the point
	 * @param x the x position
	 * @param y the y position
	 * @param pImg the image
	 */
	public Point(String name, int height, int width, double x, double y, Image pImg) {
		this.name = name;
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
		this.pImg = pImg;
		
	}
	
	
	/**
	 * Point, constructor.
	 */
	public Point() {}
	
	
	/**
	 * getName, getter for the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
	/**
	 * getHeight, getter for the height.
	 * 
	 * @return height the height
	 */
	public double getHeight() {
		return height;
	}
	
	
	/**
	 * getWidth, getter for the width.
	 * 
	 * @return width the width
	 */
	public double getWidth() {
		return width;
	}
	
	
	/**
	 * getX, getter for the x position.
	 * 
	 * @return x the x position
	 */
	public double getX() {
		return x;
	}
	
	
	/**
	 * setX, setter for the x position.
	 * 
	 * @param x the x position
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	
	/**
	 * getY, getter for the y position.
	 * 
	 * @return y the y position
	 */
	public double getY() {
		return y;
	}
	
	
	/**
	 * setY, setter for the y position.
	 * 
	 * @param y the y position
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	
	/**
	 * getImage, getter for the image.
	 * 
	 * @return the image
	 */
	public Image getImage() {
		return pImg;
	}
}