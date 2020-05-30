package pkgMain;

import java.util.*;
import java.io.Serializable;

/**
 * Class - Garden, the class is only used for help in serializing a garden object now.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria van Venrooy
 * 
 * @version 0.0.1
 */
public class Garden implements Serializable {
	String name;
	double gardenRate; //The garden's overall rate, determined in the model
	private static final long serialVersionUID = -3574497672964401835L;
	public ArrayList<PlantSave> saves;
	public ArrayList<GardenShapeSave> gardenShape;
	public ConfigSave config;
	public double scale;
	public double score;
	
	
	/**
	 * Garden, constructor.
	 */
	public Garden() {
		saves = new ArrayList<>();
		gardenShape = new ArrayList<>();
	}

	
	/**
	 * Garden, constructor.
	 * 
	 * @param p list of plant icons
	 * @param g list of shapes to save
	 * @param hm a map of configurations
	 * @param name the name of the garden
	 * @param scale the scale
	 * @param score the score of the garden
	 */
	public Garden(ArrayList<PlantIcon> p, ArrayList<GardenShapeSave> g, HashMap<Integer, String> hm, String name, double scale, double score) {
		gardenShape = new ArrayList<>();
		saves = new ArrayList<>();
		config = new ConfigSave(hm);
		this.scale = scale;
		this.score = score;
		for(int i = 0; i < g.size(); i++) 
			gardenShape.add(Model.gShape.get(i));
		for(int i = 0; i < p.size(); i++) 
			saves.add(new PlantSave(p.get(i).getCircle().getTranslateX(), p.get(i).getCircle().getTranslateY(), p.get(i).getPlant().getName()));
		this.name = name;
	}
	
	/**
	 * getName, getter for the name.
	 * 
	 * @return name the garden name
	 */
	public String getName() {
		return this.name;
	}
	
	
	/**
	 * toString, for turning a garden into a string.
	 * 
	 * @return name the garden name
	 */
	@Override
	public String toString() {
		return this.saves.toString();
	}
}
