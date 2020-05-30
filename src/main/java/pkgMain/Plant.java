package pkgMain;

import java.io.Serializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Class - Plant, this class builds a plant object which has all the relevant
 * 				  information relating to a plant for this proejct such as ph 	
 * 				  size, type, color, an image associated with it.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria Venrooy
 */
public class Plant implements Serializable{
	//TODO: Add sun exposure as a parameter
	//TODO: Icon wont be necessary unless we want to do things besides circles 
	/*
	 * From the powerpoint, it looks like configurations should 
	 * be light: sun, partial sun, shade, deep shade; water: wet
	 * moist, dry; soil pH; soil type; bloom time - want flowers
	 * available in all seasons for insects; food sources (seeds
	 * or berries) are positives; function in ecosystem(ground 
	 * cover, shrub layer, understory), and aesthetics
	 */
	
	private String name;
	private String latinName = "";
	private String height;  
	private int width;
	private String description;
	private String waterUse; 
	private String bloomSeason; // TODO: probably change to bloomSeason 
	private String flowerColor; 
	private String soilpH; //TODO: i believe you could just make this an int 1,2,3 for acidic, adaptable, or basic
	private Image plantPic; 
	private ImageView plantView;
	private String sunAmount; // TODO: change to being num 1-4, 1 is deep shade, 4 is full sun 
	private PlantType type;
	private int score;
	
	private static final long serialVersionUID = -3574497672964401835L;

	/**
	 * Custom constructor for plants
	 * 
	 * @param name a String representing the name of the plant
	 * @param latinName a String representing the Latin name of the plant
	 * @param height a String representing the height of the plant
	 * @param width an int representing the diameter of the plant in inches, used to make circle icon
	 * @param description a String giving a description of the plant
	 * @param waterUse a String representing how much water the plant needs
	 * @param bloomSeason a String representing when the plant will begin to bloom
	 * @param flowerColor a String representing the color of the plant's flowers
	 * @param soilpH a String representing the soil pH that the plant prefers
	 * @param plantPic an Image of the plant 
	 * @param sunAmount a String representing how much sun the plant prefers
	 * @param type the PlantType that the Plant is  
	 */
	public Plant(String name, String latinName, String height, int width, String description, String waterUse, String bloomSeason, String flowerColor,
			String soilpH, Image plantPic, String sunAmount, PlantType type) {
		this.name = name;
		this.latinName = latinName;
		this.height = height;
		this.width = width;
		this.description = description;
		this.waterUse = waterUse;
		this.bloomSeason = bloomSeason;
		this.flowerColor = flowerColor;
		this.soilpH = soilpH;
		this.plantPic = plantPic;
		this.sunAmount = sunAmount;
		this.type = type;
		this.plantView = new ImageView(this.plantPic);
		this.score = 0;
	}
	
	/**
	 * Default no-argument constructor that is passed to PlantIcon
	 */
	public Plant() {}
	
	public ImageView getPlantView() {
		return plantView;
	}
	
	
	public Image getPhoto() {
		return plantPic;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLatinName() {
		return latinName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getFlowerColor() {
		return flowerColor;
	}
	
	public String getHeight() {
		return height;
	}
	public double getWidth() {
		return width;
	}
	public String getBloomSeason() {
		return bloomSeason;
	}
	public String getSoil() {
		return this.soilpH;
	}
	
	public String getWaterAsString() {
		return waterUse;
	}
	
	public String getSunAmount() {
		return sunAmount;
	}
	
	public PlantType getPlantType() {
		return this.type;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int s)
	{
		score = s;
	}
	


}
