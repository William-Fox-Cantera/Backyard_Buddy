package pkgMain;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Shape;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class - Model, Methods for handling logic. Ultimately serializes the garden.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria van Venrooy
 */
@SuppressWarnings({"static-access", "unused", "unlikely-arg-type", "rawtypes"})
public class Model implements Serializable{
	private double scale = 1; //feet/pixel
	private final double DIVERSITY_PERCENT = .1;
	private final double YEARROUND_PERCENT = .25;
	private final int FEET_TO_INCHES = 12;
	
	public HashMap<Integer, String> configs;
	public ArrayList<String> savedGardens;
	public ArrayList<Garden> saved;
	private ArrayList<Shape> gardenShapes = new ArrayList<>();
	private ArrayList<PlantIcon> newPlants = new ArrayList<>();
	private ArrayList<PlantIcon> prevPlants = new ArrayList<>();
	private HashMap<String, Plant> favoritesList = new HashMap<>();
	public static HashMap<String, Double> ratings = new HashMap<>();

	public static ArrayList<String> toBeDeleted = new ArrayList<>();
	public static ArrayList<String> toBeAdded = new ArrayList<>();
	public static ArrayList<GardenShapeSave> gShape;
	private static final long serialVersionUID = -3574497672964401835L;

	private HashSet<PlantIcon> allPlants = new HashSet<>();
	
	private double totalScore = 0;
	private HashSet<String> suggestions = new HashSet<>();

	
	public String soilConfig = "";
	public String laborConfig = "";
	public String waterConfig = "";
	public String seasonConfig = "";
	public String lightConfig = "";	

	/**
	 * constructor for Model, initializes configs, the list of saved gardens, a list of gardens, and a list of garden Shapes
	 */
	public Model() {
		configs = new HashMap<Integer, String>();
		savedGardens = new ArrayList<>();
		saved = new ArrayList<>();
		gShape  = new ArrayList<>();
	}
	
	
	/**
	 * Determines the scaling factor for gardens by figuring out how many inches are represented by each pixel
	 * in the drawGardenView
	 * @param A which is placed on the outside of the garden
	 * @param B which is placed on another part of the garden
	 * @param length a String representing the length in feet supplied by the user
	 */
	public void setGardenScale(Point A, Point B, String length) {
		try {
			int size = Integer.parseInt(length) * FEET_TO_INCHES; //length of garden in inches
			double pointDistance = Math.sqrt(Math.pow(A.getX() - B.getX(), 2) + Math.pow(A.getY() - B.getY(), 2)); //number of pixels 
			this.scale = size / pointDistance; //inches per pixel
		} catch (Exception e) {
			this.scale = 1;		
		}
	}

	
	/**
	 * Clears the lists of plants that have been added to the garden and the shapes that have been added 
	 * to the garden. To be used before loading a new garden or designing a new garden.
	 */
	public void clearGarden() {
		newPlants.clear();
		prevPlants.clear();
		allPlants.clear();
		gardenShapes.clear();
		suggestions.clear();
		totalScore =0;
	}
	
	
	/**
	 * removes all of the garden shapes that have been added
	 * used in DrawGardenView
	 */
	public void clearGardenShapes() {
		gardenShapes.clear();
	}
	
	
	/**
	 * Gives scale of feet / pixel as determined by the draw garden view
	 * @return the ratio of feet to pixel as a double
	 */
	public double getScale() {
		return this.scale;
	}
	
	
	/**
	 * returns the list of plants that are not currently in the user's garden, but those that they would like 
	 * to add to their garden
	 * 
	 * @return an ArrayList of PlantIcon that have been added in DesignGardenView
	 */
	public ArrayList<PlantIcon> getNewPlants() {
		return newPlants;
	}

	
	/**
	 * returns the list of plants that are already in the user's garden
	 * 
	 * @return an ArrayList of PlantIcon that were added in AddPreexistingView
	 */
	public ArrayList<PlantIcon> getPrevPlants() {
		return prevPlants;
	}
	
	/**
	 * saveGardenData, saves the textfield as the garden name and the plant objects in the
	 * 				   garden at the time of the save. The filename is "GardenData.ser"
	 * 
	 * @param object the object to be saved
	 * @param garden the garden
	 */
	public void saveGardenData(Object object, Garden garden) {
		File tmpDir = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\savedGardenData\\");
		boolean exists = tmpDir.exists();
		if(!exists) {
			System.out.println("Directory not found, creating...");
			String d = new String(System.getProperty("user.dir") + "\\src\\main\\resources\\savedGardenData\\");
			File file = new File(d);
			boolean bool = file.mkdir();
		      if(bool){
		         System.out.println("Directory created successfully");
		      }else{
		         System.out.println("Sorry couldn’t create specified directory");
		      }
		} 
		try { // Write the data to "GardenData.ser"
			String fileName = "";
			if (object instanceof String) { // For naming the file the name the user named the garden
				fileName = object + ".ser";
				System.out.println(fileName);
				savedGardens.add(fileName);
			}
			
			FileOutputStream fos = new FileOutputStream(fileName);

			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(garden);
			oos.close();
		} catch (Exception e) { // Catches io and file not found exception
			e.printStackTrace();
		}
	}


	/**
	 * loadSavedGardens, loads in all saved garden files
	 * Based off code from https://www.java2novice.com/java-file-io-operations/file-list-by-file-filter/
	 */
	public void loadSavedGardens() {
		try {
			File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\savedGardenData\\");
			File[] files = file.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.toLowerCase().endsWith(".ser")) {
						return true;
					}
					else return false;
				}
			});
			for(int i = 0; i < files.length; i++) {
				String tmp = parseLocation(files[i].toString());
				if(savedGardens.contains(tmp)) {
					System.out.println("Importing saved garden: "+ tmp);
				} else {
					savedGardens.add(tmp);
					System.out.println("Importing saved garden: "+ tmp);
				}
				
				readGardenData(files[i].toString());
			}
		}catch(Exception e) {
			System.out.println("No saved gardens");
		}
	}
	
	
	/**
	 * parseLocation, parses the raw location string of the saved file to just get the name
	 * @param location the location
	 * @return String of location
	 */
	public String parseLocation(String location) {
		int i = location.lastIndexOf('\\');
		int r = location.lastIndexOf('.');
		String tmp;
		tmp = location.substring(i+1, r);
		return tmp;
	}
	

	/**
	 * removes all of the gardens that had been saved from the arraylist of saved garden
	 * 
	 * @param saved a list of the names of the saved gardens
	 * @param gardenSaved a list of the actual saved gardens
	 */
	public static void cleanSaved(ArrayList<String> saved, ArrayList<Garden> gardenSaved) {
		if(saved.size()!= 0) {
			try {
				File arg0 = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\savedGardenData\\");
				for (File file: arg0.listFiles()) {
			        if (!file.isDirectory())
			            file.delete();
			    }			
				Arrays.stream(new File(System.getProperty("user.dir") + "\\src\\main\\resources\\savedGardenData\\").listFiles()).forEach(File::delete);
			} catch (Exception e) {
				System.out.println("No saved files");
			}
			saved.clear();
			gardenSaved.clear();
		}
	}
	

	/**
	 * removes one garden that had been saved
	 * 
	 * @param saved a list of the names of the saved gardens
	 * @param gardenSaved a list of the gardens that have been saved
	 * @param name the name of the garden that the user would like to remove
	 */
	public static void cleanSpecific(ArrayList<String> saved, ArrayList<Garden> gardenSaved, String name) {
		if(saved.size()!= 0) {
			try {
				System.out.println("try");
				File arg0 = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\savedGardenData\\");
				for (File file: arg0.listFiles()) {
			        if (file.getName().equals(name+".ser"))
			            file.delete();
			    }			
			} catch (Exception e) {
				System.out.println("No saved files");
			}
			saved.remove(name);
			gardenSaved.remove(name);
		}
	}
	
	/**
	 * readGardenData, reads the garden data from the .ser file. 
	 * 
	 * @param fileName the name of the file to read
	 * @return name the value read
	 */
	public String readGardenData(String fileName) {
		String name = "";
		try {
			FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Garden g = (Garden)ois.readObject();
			name = g.getName();
			saved.add(g);
			Model.gShape = g.gardenShape;
			this.scale = g.scale;
			loadConfigs(g);
			System.out.println(g.score);
			this.ratings.put(name, g.score);
			System.out.println(this.ratings.toString());
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;

	}
	

	/**
	 * searchPlants, returns an array list of plants containing the target word. 
	 * 
	 * @param target the plant that the user is looking for
	 * @param plantList list of plants
	 * @return the plants to be returned
	 */
	public ArrayList<Plant> searchPlants(ArrayList<Plant> plantList, String target) {
		ArrayList<Plant> targetList = new ArrayList<Plant>();
		for (Plant plant : plantList) {
			if (plant.getName().equalsIgnoreCase(target))
				targetList.add(plant);
		}
		return targetList;
	}	
	
	/**
	 * sortPlants, Sorts an ArrayList of plants by their scores, as determined in "getPlantScore"
	 * 
	 * @param plantList the map of plants to be sorted
	 * @return retList a list of the input plants in sorted order, with fitting best in the garden (based on configs) coming first
	 **/
	public ArrayList<Plant> sortPlants(HashMap<String, Plant> plantList) {
        ArrayList<Plant> retList = new ArrayList<>();
		Set<Entry<String, Plant>> entries = plantList.entrySet();

		TreeMap<String, Plant> sorted = new TreeMap<>(plantList);

		List<Entry<String, Plant>> listOfEntries = new ArrayList<Entry<String, Plant>>(entries);
		 
		for (Map.Entry<String, Plant> me : plantList.entrySet()) 
			me.getValue().setScore(getPlantScore(me.getValue()));
		
		Collections.sort(listOfEntries, new PlantComparator());
		for (Entry e : listOfEntries) 
			retList.add((Plant) e.getValue());
		
		Collections.reverse(retList);
	
		return  retList;
	}
	
	/**
	 * Gives a plant a score based on how compatible it is with the garden's conditions
	 * 
	 * @param p the plant to be scored
	 * @return the score of that plant
	 */
	public int getPlantScore(Plant p) {
		int score = 0;
		try {
			if (p.getSoil().toUpperCase().contains(soilConfig.toUpperCase())) 
				score += 10;
			if (p.getWaterAsString().toUpperCase().contains(waterConfig.toUpperCase()))
				score += 10;
			if (p.getSunAmount().toUpperCase().contains(lightConfig.toUpperCase()))
				score += 10;
			if (p.getBloomSeason().toUpperCase().contains(seasonConfig.toUpperCase()))
				score += 10;
		} catch (Exception e) {
		}
		return score;
	}
	
	/**
	 * saveSettings, saves the settings that the user has chosen.
	 * 
	 * @param configs the configurations that the user has chosen
	 */
	public void saveSettings(HashMap<Integer, String> configs) {
		Model.writeUsingFiles(configs);
		if (configs.get(1) == null) {
			this.soilConfig = "";
		} else {
			this.soilConfig = configs.get(1);
		}
		if (configs.get(2) == null) {
			this.waterConfig = "";
		} else {
			this.waterConfig = configs.get(2);
		}
		if (configs.get(3) == null) {
			this.seasonConfig = "";
		}else {
			this.seasonConfig = configs.get(3);
		}
		
		if (configs.get(4) == null) {
			this.lightConfig = "";
		} else {
			this.lightConfig = configs.get(4);
		}
		System.out.println("Light conf, season conf: " + this.lightConfig +", " + this.seasonConfig);
		}
	
	/**
	 * https://www.journaldev.com/878/java-write-to-file
     * writeUsingFilesUse, Files class from Java 1.7 to write files, internally uses OutputStream
     * @param configs the configurations that the user has set
     */
    private static void writeUsingFiles(HashMap configs) {
        try {
        	PrintWriter writer = new PrintWriter("configs.txt", "UTF-8");
        	for(int i = 0;  i < configs.size(); i++) {
        		writer.println(configs.get(i+1));
        	}
        	writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * loads the settings from a saved garden file
     * 
     * @param configs the configurations that the user had chosen
     * @throws Exception nullPointerException
     */
	public static void loadSettings(HashMap configs) throws Exception {}
	
		/**
		 * Loads the configurations that the user had chosen by setting them in the model
		 * @param g the garden that is being loaded
		 */
		public void loadConfigs(Garden g) {
			try {
				this.soilConfig = g.config.getConfigs().get(1);
				this.waterConfig = g.config.getConfigs().get(2);
				this.seasonConfig = g.config.getConfigs().get(3);
				this.lightConfig = g.config.getConfigs().get(4);	
			} catch (Exception e) {
				System.out.println("\nProblem with loading configs\n");
			}
		}
	
	/**
	 * load from file, load the settings that the user has chosen previously from the file.
	 * 
	 * @param configs the configurations that the user had set
     * @throws Exception  nullPointerException
	 */
	private static void loadFromFile(HashMap<Integer,String> configs) throws Exception{
		try {
			File f = new File("configs.txt");
			Scanner s = new Scanner(f);
			int line = 1;
			while(s.hasNextLine()) {
				configs.put(line,s.nextLine());
				line++;
			}
			s.close();
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("No config file");
		}
		
	}
	
	/**
	 * parseConfigs, returns an ArrayList of the saved configs parsed from a string.
	 * 
	 * @param configString a string that has all of the configurations that the user set
	 * @return the configurations that the user had set 
	 */
	public static ArrayList<String> parseConfigs(String configString) {
		ArrayList<String> arr = new ArrayList<>();
		for(int i = 0; i < configString.length(); i++) {
			if(isInt(Character.toString(configString.charAt(i)))) {
				if(Integer.parseInt(Character.toString(configString.charAt(i))) == 6) {
					arr.add(configString.substring(i+2, configString.lastIndexOf('}')));
				} else {
					int j = 0;
					for(j = i; j < configString.length(); j++) {
						if(configString.charAt(j) == ',') {
							break;
						}
					}
					arr.add(configString.substring(i+2, j));
				}
			}
		}
		
		return arr;
	}
	
	/**
	 * isInt, returns a boolean checking if the given input string (1 character) is an integer.
	 * 
	 * @param str a string that may or may not be an int
	 * @return true if the string is int, false if it is not. 
	 */
	public static boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	

	
	/** Adds a garden shape to the list of shapes, which then gets displayed on designGarden
	 * and AddPreexisting
	 * 
	 * @param shape, a shape to be added
	 */
	public void addShape(Shape shape) {
		gardenShapes.add(shape);
	}
	
	/** Clears garden shapes from the list of shapes
	 */
	public void clearShapes() {
		gardenShapes.clear();
	}
	
	/**
	 * Returns the list of shapes to be rendered
	 * 
	 * @return an ArrayList of shapes that represent the garden
	 */
	public ArrayList<Shape> getShapes() {
		return gardenShapes;
	}
	
	/**
	 * When a PlantIcon is added to the garden, this method
	 * will add it to the list of PlantIcons
	 * 
	 * @param p the Plant object that is being added to the garden
	 */
	public void addNewPlant(PlantIcon p) {
		newPlants.add(p);
	}
	
	/**
	 * Clears list of plants from garden
	 */
	public void clearPlants() {
		this.newPlants.clear();
		this.prevPlants.clear();
	}
	
	/**
	 * This method will remove a PlantIcon from the list of 
	 * PlantIcons
	 * 
	 * @param p the plantIcon that the user wishes to delete
	 */
	public void removeNewPlant(PlantIcon p) {
		newPlants.remove(p);
		
	}
	
	/**
	 * When a PlantIcon is added to the garden as an already existing
	 * plant, this method
	 * will add it to the list of PlantIcons
	 * 
	 * @param p the Plant object that is being added to the garden
	 */
	public void addPrevPlant(PlantIcon p) {
		prevPlants.add(p);
	}
	
	/**
	 * This method will remove a PlantIcon from the list of 
	 * PlantIcons that already exist
	 * 
	 * @param p the plantIcon that the user wishes to delete
	 */
	public void removePrevPlant(PlantIcon p) {
		prevPlants.remove(p);
	}
	
	/**
	 * uses configurations and attributes of each plant to rank how good
	 * the garden is
	 * 
	 * 
	 */
	public void rateGarden() {
		allPlants.addAll(newPlants);
		allPlants.addAll(prevPlants);
		
		//all scores are returned out of 1
		double yearRoundScore = yearRoundScore(); // score for having flowers available in all seasons
		double diversityScore = getDiversityScore(); // need different types of plants
		double pHScore = soilScore();
		double sunScore = sunScore();
		double waterScore = waterScore();	
		
		double overallScore = (yearRoundScore + diversityScore + pHScore + sunScore + waterScore) / 5 * 10; //finds average of scores and rescales out of 10
		this.totalScore = Math.floor(overallScore * 100) / 100.;	
	}
	
	/**
	 * Gets the rating of the garden
	 * @return Garden's score
	 */
	public double getGardenRating() {
		return this.totalScore;
	}
	
	/**
	 * returns the advice strings for how to improve the garden
	 * @return the advice strings for how to imporve a garden based on the configs set
	 */
	public HashMap<String, Double> getRatings() {
		return ratings;
	}



	/**
	 * Gets suggestions for how to improve the garden
	 * @return the suggestions
	 */
	public HashSet<String> getSuggestions() {
		return this.suggestions;
	}
	
	/**
	 * adds a plant to the list of all of the plants (new and old)
	 * @param pi the plantIcon to be added to the list of all of the plants
	 */
	public void addToAllPlants(PlantIcon pi) {
		allPlants.add(pi);
	}

	
	/**
	 * Uses the sun configuration and the desired sun levels of all the plants in the garden
	 * to calculate a score for how compatible the plants are with the sun
	 * 
	 * @return a double representing what percent of the plants will survive w the given sun amount
	 */
	public double sunScore() {
		double grossScore =0;
		double netScore = 0;		
		for (PlantIcon pi : allPlants) {
			if (pi.getPlant().getSunAmount().toUpperCase().contains(lightConfig.toUpperCase())) {
				grossScore++;
			} else {
				suggestions.add(pi.getPlant().getName() + " might not work with " + this.lightConfig);
			}
		}
		netScore = grossScore/ allPlants.size();
		return netScore;
	}
	
	/**
	 * returns the percent of plants in the garden that will survive on the given amounts
	 * of water
	 * 
	 * @return a double which is the percent of plants in the garden that would survive with 
	 * the selected water amount
	 */
	public double waterScore() {
		double grossScore =0;
		double netScore = 0;
		for (PlantIcon pi : allPlants) {
			if (pi.getPlant().getWaterAsString().toUpperCase().contains(this.waterConfig.toUpperCase())) {
				grossScore++;
			} else {
				suggestions.add(pi.getPlant().getName() + " might not work with in a " + this.waterConfig + " garden");
			}
		}
		netScore = grossScore/ allPlants.size();
		return netScore;	
	}
	
	/**
	 * calculates what percent of the plants would survive with the soil determined by pre-survey/configs 
	 * 
	 * @return a double representing the percent of plants that would succeed with this soil type
	 */
	public double soilScore() {
		double grossScore =0;
		double netScore = 0;
		for (PlantIcon pi : allPlants) {
			if (pi.getPlant().getSoil().toUpperCase().contains(this.soilConfig.toUpperCase())) {
				grossScore++;
			} else {
				suggestions.add(pi.getPlant().getName() + " might not work with " + this.soilConfig + " soil");
			}
		
		}
		netScore = grossScore/ allPlants.size();
		return netScore;
	}
	
	/**
	 * calculates the score for the year-round availability of plants based on if fewer
	 * than YEARROUND_PERCENT are available in that season
	 * 
	 * 
	 * @return an array list that includes that score and tips on how to improve your garden score
	 */
	public double yearRoundScore() {
		HashMap<String, Double> seasonCount = new HashMap<>();
		seasonCount.put("Spring", 0.);
		seasonCount.put("Summer", 0.);
		seasonCount.put("Autumn", 0.);
		double numOutliers = 0;
		for (PlantIcon pi : allPlants) {
			if (pi.getPlant().getBloomSeason().toLowerCase().contains("spring")) {
				seasonCount.put("Spring", seasonCount.get("Spring") + 1);
			} else if (pi.getPlant().getBloomSeason().toLowerCase().contains("summer")) {
				seasonCount.put("Summer", seasonCount.get("Summer") + 1);
			} else if (pi.getPlant().getBloomSeason().toLowerCase().contains("fall")) {
				seasonCount.put("Autumn", seasonCount.get("Autumn") + 1);
			}
		}
		
		//if less than 25% of the plants will be in bloom during a given season, it'll make a suggestion
		for (Map.Entry<String, Double> me : seasonCount.entrySet()) {
			if (me.getValue() < (double) allPlants.size() * YEARROUND_PERCENT) {
				suggestions.add("Add more plants that will bloom during the " + me.getKey());
				numOutliers++;
			}
		}
		return ((double) seasonCount.size()-numOutliers)/ (double) seasonCount.size(); // what % of seasons have a sufficient number of plants
	}
	
	/**
	 * finds how many of each type of plant exist in the garden
	 * 
	 * @return a double representing the number of plantTypes that are less than DIVERSITY_PERCENT of the plants in the garden
	 */
	public double getDiversityScore() {
		HashMap<PlantType, Double> numPlants = new HashMap<>();
		numPlants.put(PlantType.TREE, 0.);
		numPlants.put(PlantType.FLOWER,0.);
		numPlants.put(PlantType.GRASS,0.);
		numPlants.put(PlantType.SHRUB,0.);
		numPlants.put(PlantType.VINE,0.);
		double numOutliers =0; // number of PlantType that lie outside 1 sd of mean
		
		for (PlantIcon pi : allPlants) {
			PlantType key = pi.getPlant().getPlantType();
			numPlants.put(key, numPlants.get(key) + 1);
		}

		//if a PlantType has less than 10% of total plants, you'll receive a comment about it
		for (Map.Entry<PlantType, Double> me : numPlants.entrySet()) {
			if (me.getValue() / (double) allPlants.size() < DIVERSITY_PERCENT) {
				suggestions.add("Add more plants of type: " + me.getKey().name().toLowerCase());
				numOutliers++;
			}
		}		
		return ((numPlants.size() - numOutliers) / numPlants.size());		
	}
	

}