package pkgMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class - MainView, the parent of all of the view classes for this project.
 * 		   contains general methods for displaying things on-screen like scrollbars
 * 		   and text.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria van Venrooy
 * 
 * @version 0.0.1
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class View{
	
	private Stage stage;
	private String labelText;
	private EventHandler handler;
	public Controller controller = new Controller();

	protected HashMap<String,Plant> flowerList = readPlantsFromCSV(getClass().getResourceAsStream("/plantInfo/flowers.csv"), PlantType.FLOWER);
	protected HashMap<String,Plant> treeList = readPlantsFromCSV(getClass().getResourceAsStream("/plantInfo/trees.csv"), PlantType.TREE);
	protected HashMap<String,Plant> shrubList = readPlantsFromCSV(getClass().getResourceAsStream("/plantInfo/shrubs.csv"), PlantType.SHRUB);
	protected HashMap<String,Plant> vineList = readPlantsFromCSV(getClass().getResourceAsStream("/plantInfo/vines.csv"), PlantType.VINE);
	protected HashMap<String,Plant> grassList = readPlantsFromCSV(getClass().getResourceAsStream("/plantInfo/grass.csv"), PlantType.GRASS);
	//protected HashMap<String,Plant> otherList = readPlantsFromCSV(getClass().getResourceAsStream("/plantInfo/other.csv"), PlantType.OTHER);
	protected HashMap<String, Plant> favoritesList = new HashMap<>();
	
	public static HashMap<String,Plant> allPlants =  new HashMap<>();;
	

	
	protected final int insetVal = 30;
	protected final int scaleVal = 3;
	final int titleBarGap = 20;
	protected final int yTranslateVal = 20;

	Model model = new Model(); 
	static int WIDTH = 1000;
	static int HEIGHT = 600;
	
	/** 
	 * Construct a view base
	 * based on code from @author ksnortum
	 * 
	 * @param stage the primary stage
	 * @param labelText the text for the label
	 * @param controller the controller
	 */
	public View(Stage stage, String labelText, Controller controller) {
		this.stage = stage;
		this.labelText = labelText;
	}
	
	/**
	 * MainView, constructor
	 */
	View() {
		
	}
	
	public Model getModel() {
		return this.model;
	}
	
	public Controller getController() {
		return this.controller;
	}

	
	/**
	 * displayTitleBar, makes a title-bar on screen containing a home button.
	 * 
	 * @param title the title of the bar 
	 * @param control the controller
	 * @return the titleBar
	 */
	public FlowPane getTitleBar(String title, Controller control) {
		FlowPane titleBar = new FlowPane();
		titleBar.setStyle("-fx-background-color: #3498EB"); 
		
		Button b1 = new Button();
		Image homeImg = new Image(getClass().getResourceAsStream("/images/homeButton.png"));
		ImageView home = new ImageView();
		home.setImage(homeImg);
		home.setFitHeight(40);
		home.setFitWidth(40);		
		b1.setGraphic(home);
		b1.setOnMousePressed(e -> control.handleMousePress( SceneName.HOMEPAGEVIEW));
		
		Text t = new Text(100,500, title);
		t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20)); 
		t.setFill(Color.LIGHTCYAN);
		
		titleBar.getChildren().addAll(b1,t);
		titleBar.setAlignment(Pos.CENTER_LEFT);
		titleBar.setHgap(titleBarGap);
		
		return titleBar; 
	}

	
	/**
	 * gives a scene that a button press will direct to
	 * based on code by @ksnortum
	 * 
	 * @return the next scene that the user will go to
	 */
	public Scene getScene() {
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(10));
		Label label = new Label(labelText);
		label.setFont(new Font(32));
		root.setCenter(label);
		
		Button backButton = new Button("Back");
		backButton.setOnMousePressed(handler);
		Button closeButton = new Button("Close");
		closeButton.setOnMousePressed(e -> stage.close());
		
		ButtonBar bbar = new ButtonBar();
		bbar.setPadding(new Insets(10, 0, 0, 10));
		bbar.getButtons().addAll(backButton, closeButton);
		root.setBottom(bbar);
		
		return new Scene(root);
	}
	
	
	/**
	 * This function reads through a CSV file of plant data and returns a map
	 * of the Plant objects made from the data received
	 * 
	 * @param stream the InputStream for the CSV of plant data
	 * @param pt the PlantType of the plants it's creating
	 * @return a map that has the Plant's name as the key and the Plant as the object
	 */
	public HashMap<String,Plant> readPlantsFromCSV(InputStream stream, PlantType pt) {
		HashMap<String,Plant> plants = new HashMap<>();
		
		try (BufferedReader br =  new BufferedReader(new InputStreamReader(stream, "UTF-8"))) {
			String line = br.readLine();
			line = br.readLine(); // call again so we ignore the first line
			
			while (line != null) {
				String[] attributes = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				Plant p1 = createPlant(attributes, pt);
				try {
					plants.put(p1.getName(),p1);
					allPlants.put(p1.getName(),p1);
				} catch (Exception e) {
					//e.printStackTrace();
				}
				line = br.readLine();
			}
		} catch (IOException ioe) {			
			ioe.printStackTrace();
		}
		
		return plants;
	}
	
	/**
	 * This takes a String[] and creates a new Plant object using the information from the array
	 * 
	 * @param info a String array of the information used in the Plant constructor to make the plant
	 * @param pt the PlantType from the PlantType enum 
	 * @return a Plant made with all of the given info
	 */
	private Plant createPlant(String[] info, PlantType pt) {
		for (int i = 0; i < info.length; i++) {
			if (info[i].length() > 1) {
				if (info[i].charAt(0) == '"') {
					String temp = info[i];
					info[i] = temp.substring(1,temp.length()-1);
				}
			}
		}
		int width; 
		Image plantPic;
		try {
			width = Integer.parseInt(info[3]);
		} catch (Exception e) {
			width = 10;
		}
		try {
			plantPic = new Image(getClass().getResourceAsStream("/plantPhotos/" + info[9]));
		} catch (Exception NullPointerException) {
			plantPic = null;
		}
		try {
		Plant p = new Plant(info[0],info[1], info[2],width, info[4], info[5],info[6],info[7],
				info[8], plantPic,info[10], pt);
		return p;
		} catch (Exception e) {
			return null;
		}
	}
	public void displayGardenShape() {}
	public void removeIcon(PlantIcon pi) {}
	public void makeHover(Plant p) {}	
	public void makeIcon(Plant p, double x, double y) {}
	public void setPlantIconLocation(PlantIcon pi, double x, double y) {}
	public void setPane(double x, double y, double spotX, double spotY) {}
}
