package pkgMain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ArrayList;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.Scene;

/**
 * Class - Controller, Methods for handling io operations and communicating with 
 * 		   view and model.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria van Venrooy
 */
@SuppressWarnings({"unused", "static-access"})
public class Controller{
	private DesignGardenView designView;
	private PlantCatalogView pcView;
	private DrawGardenView drawView;
	private AddPreexistingView preView;
	private SaveGardenView sgView;
	private ConfigurationsView cfView;
	private PreviousGardensView pgView;
	private PreSurveyView presurvView;
	private HomePageView hpView;
	public static Map<SceneName, View> views = Main.getViews();
	private Stage stage;
	private Model model;
	public Garden garden;
	private static Map<SceneName, Scene> scenes = new HashMap<>();
	public HashMap<Integer,String> configs;
	public ArrayList<Plant> filteredPlantList = new ArrayList<Plant>();
	private SceneName lastScene = SceneName.HOMEPAGEVIEW;
	private SceneName currentScene = SceneName.HOMEPAGEVIEW;
	
	
	/**
	 * Assigns all of the views made in Main.java to variable names here
	 */
	public void setViews() {
		designView = (DesignGardenView) views.get(SceneName.DESIGNGARDENVIEW);
		drawView = (DrawGardenView) views.get(SceneName.DRAWGARDENVIEW);
		hpView = (HomePageView) views.get(SceneName.HOMEPAGEVIEW);
		cfView = (ConfigurationsView) views.get(SceneName.CONFIGURATIONSVIEW);
		pcView = (PlantCatalogView) views.get(SceneName.PLANTCATALOGVIEW);
		presurvView = (PreSurveyView) views.get(SceneName.PRESURVEYVIEW);
		pgView = (PreviousGardensView) views.get(SceneName.PREVIOUSGARDENSVIEW);
		sgView = (SaveGardenView) views.get(SceneName.SAVEGARDENVIEW);
		preView = (AddPreexistingView) views.get(SceneName.ADDPREEXISTINGVIEW);
		
	}
	
	
	/**
	 * getViews, getter for the views.
	 * 
	 * @return a map containing the scene name and views 
	 */
	public Map<SceneName, View> getViews() {
		return views;
	}
	
	
	/**
	 * setScenes, setter for the scenes map.
	 * 
	 * @param scenes a map containing the scene name and scene.
	 */
	public void setScenes(Map<SceneName, Scene> scenes) {
		this.scenes = scenes;
	}

	
	/**
	 * setModel, setter for model.
	 * 
	 * @param m the model
	 */
	public void setModel(Model m) {
		model = m;
	}
	
	
	/**
	 * getModel, getter for the model.
	 * 
	 * @return the model
	 */
	public Model getModel() {
		return this.model;
	}
	
	
	/**
	 * setStage, setter for the stage.
	 * 
	 * @param s the Javafx Stage.
	 */
	public void setStage(Stage s) {
		stage = s;
	}
	
	
	/**
	 * setGarden, setter for the garden.
	 * 
	 * @param g a garden object, mostly used for serializing
	 */
	public void setGarden(Garden g) {
		this.garden = g;
	}
	
	
	/**
	 * clears saved gardens in previousGardensView
	 * 
	 * @param event the event of someone clicking the clear button
	 * @param saved the list of saved gardens
	 */
	public void clearSavedGardens(Event event, ArrayList<String> saved) {
		Model.cleanSaved(saved,model.saved);
		handleMousePress(SceneName.PREVIOUSGARDENSVIEW);
	}
	
	
	/**
	 * clearGardenShapes, removes the garden data from the model.
	 */
	public void clearGardenShapes() {
		model.clearGardenShapes();
	}
	
	
	/**
	 * Changes the scene that is being displayed
	 * 
	 * @param name the next scene that should be displayed
	 */
	public void handleMousePress(SceneName name) {
		if(name.equals(SceneName.DRAWGARDENVIEW) || name.equals(SceneName.PREVIOUSGARDENSVIEW)) {
			clearShape();
		}
		this.lastScene = currentScene;
		this.currentScene = name;
		stage.setScene(Main.getScenes().get(name));
	}
	
	
	/**
	 * getLastScene, gets the last scene name.
	 * 
	 * @return lastScene the last scene name
	 */
	public SceneName getLastScene() {
		return this.lastScene;
	}
	
	
	/**
	 * handleSavePress calculates rating for current garden and changes 
	 *                 scene to the save screen.
	 */
	public void handleSavePress() {
		model.rateGarden();
		sgView.addRating();
		handleMousePress(SceneName.SAVEGARDENVIEW);
	}
	

	/**
	 * getGardenRating, retrieves the garden rating from model.
	 * 
	 * @return a double representing the rating of the garden out of 10
	 */
	public double getGardenRating() {
		return model.getGardenRating();
	}
	
	
	/**
	 * gets the list of suggestions on how one could improve their garden.
	 * 
	 * @return a hashset of Strings which are suggestions to be displayed to user
	 */
	public HashSet<String> getSuggestions() {
		return model.getSuggestions();
	}
	
	
	/**
	 * this sends the dimensions of the garden to the model and changes the scene
	 * 
	 * @param e the event of someone clicking the "done" button on DrawGardenView
	 * @param name the name of the scene to change to
	 * @param A the point A
	 * @param B the Point B
	 * @param length the user input length (distance in feet from A to B)
	 */
	public void onDoneButton(Event e, SceneName name, Point A, Point B, String length) {
		sendGardenDimensions( A, B, length);
		changeAndDisplay(e, name);
	}
	
	
	/**
	 * Loads the previously saved garden.
	 * 
	 * @param event the MouseEvent of someone clicking to load a garden
	 * @param name the next garden that should be displayed
	 */
	public void handleLoadGarden(Event event, String name) {
		model.clearGarden(); //removes any plants/shapes that are not a part of the garden you're loading in
		designView.clearGarden();
		preView.clearGarden();
		for(int i = 0; i < model.saved.size(); i++) {
			if(model.saved.get(i).getName().equals(name)) {
				System.out.println(model.saved.get(i).gardenShape.size());
				for(int j = 0; j < model.saved.get(i).gardenShape.size(); j++) {
					System.out.println(model.saved.get(i).gardenShape.get(j).toString());
					designView.makeShape(model.saved.get(i).gardenShape.get(j).getName(), model.saved.get(i).gardenShape.get(j).getColor(),
							(int)model.saved.get(i).gardenShape.get(j).getX(), 
							(int)model.saved.get(i).gardenShape.get(j).getY(), (int)model.saved.get(i).gardenShape.get(j).getxLoc(),
							(int)model.saved.get(i).gardenShape.get(j).getyLoc(), (int)model.saved.get(i).gardenShape.get(j).getRadius());
				}
				for(int j = 0; j < model.saved.get(i).saves.size(); j++) {
					Plant p = designView.allPlants.get(model.saved.get(i).saves.get(j).name);
					preView.makeIcon(p, model.saved.get(i).saves.get(j).getX(), model.saved.get(i).saves.get(j).getY()); // right now only prevPlants is being serialized
				}

				designView.displayGardenShape(); 
				handleMousePress(SceneName.DESIGNGARDENVIEW);
			}
		}
	}
	
	
	/**
	 * Deletes the specific garden.
	 * 
	 * @param event the MouseEvent of someone clicking to load a garden
	 * @param name the next garden that should be deleted
	 * @param savedArr an array of saved data for a given garden
	 * @param pane the flowpane containing the saved garden icon
	 */
	public void handleSpecificDelete(Event event, String name, ArrayList<String> savedArr, FlowPane pane) {
		Model.cleanSpecific(savedArr, model.saved, name);
	}
	
	
	/**
	 * changeAndDisplay, Calls designView to add the shapes that were added in drawView
	 * 					 and changes scene.
	 * 
	 * @param event the MouseEvent of someone clicking to move to another scene
	 * @param name the name of the next scene to be displayed
	 */
	public void changeAndDisplay(Event event, SceneName name) {
		views.get(name).displayGardenShape();
	    if (name == SceneName.DRAWGARDENVIEW && name.equals(SceneName.DRAWGARDENVIEW)) {
			clearShape();
			model.clearPlants();
			drawView.displayGardenShape(model.getShapes());
		}
		handleMousePress(name);
	}
	
	
	/**
	 * clearAndDisplay. clears all plants and garden shapes from model, designView, preView, saveGardenView. 
	 * 		            Changes the scene.
	 * 
	 * @param name the name of the scene to change to
	 */
	public void clearAndDisplay(SceneName name) {
		model.clearGarden();
		designView.clearGarden();
		preView.clearGarden();
		sgView.clearSuggestions();
		cfView.changeBackButtonToNext();
		handleMousePress(name);
	}
	
	
	/**
	 * getShapes, returns the shapes from the model.
	 * 
	 * @return the shape list
	 */
	public ArrayList<Shape> getShapes() {
		return model.getShapes();
	}
	
	
	/**
	 * saveSettings, calls the model to save the configurations. 
	 * 
	 * @param configs a map containing the configurations
	 */
	public void saveSettings(HashMap<Integer,String> configs) {
	    model.saveSettings(configs);
	    preView.reorderPlants();
	    designView.reorderPlants();
	}
	
	
	/**
	 * getOrderedPlantList, takes in a hash ma of plants and returns an array list of sorted plants. 
	 * 
	 * @param unorderedPlants the map of plants
	 * @return an array list of plants
	 */
	public ArrayList<Plant> getOrderedPlantList(HashMap<String, Plant> unorderedPlants) {
		return model.sortPlants(unorderedPlants);
	}
	
	
	/**
	 * getSaveGardenViewHandler, returns the event from the saveGardenEvent method.
	 * 
	 * @return a mouse event
	 */
	public EventHandler<MouseEvent> getSaveGardenViewHandler() {
		return event ->  saveGardenEvent((MouseEvent) event);
	}
	
	
	/**
	 * handleAndRemove, updates the next buttom and handles the mouse press for that button.
	 * 
	 * @param name the name of the scene
	 */
	public void handleAndRemove(SceneName name) {
		cfView.changeNextButtonToBack();
		handleMousePress(name);
	}
	
	
	/**
	 * changeButtonColor, changes the buttons color to red while it is pressed,
	 * 					  otherwise changes it back to green 
	 * 
	 * @param event the event
	 */
	public void changeButtonColor(MouseEvent event) {
		Node n = (Node) event.getSource();
		// Change the color of the save button when it is clicked
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED) 
			n.setStyle("-fx-background-color: #ff0000; -fx-font-size: 2em;");
		// Change it back when released
		if (event.getEventType() == MouseEvent.MOUSE_RELEASED) 
			n.setStyle("-fx-background-color: #00ff00; -fx-font-size: 2em;");
	}
	
	
	/**
	 * saveGardenEvent, handles the various events needed for the save garden scene.
	 * 					These include: Changing the color of the save button when it 
	 * 					is clicked, and changing it back on mouse release. Clearing the
	 * 					the default text provided in the space for entering a filename, 
	 * 					and calling on the model to serialize data when the save button 
	 * 					is pressed. Lastly, sets listeners for changes in the scene height
	 * 					and width in case the screen is resized.
	 * 
	 * @param event  a mouse event
	 */
	public void saveGardenEvent(MouseEvent event) {
		// Clear the space for entering a fileName when it is clicked
		Node n = (Node)event.getSource();
		// Calls on model to serialize data when the save button is pressed
		if (n == sgView.getSaveButton()) {
			String fileName = sgView.getGardenName().getText();
			ArrayList<PlantIcon> all = new ArrayList<>();
			all.addAll(model.getPrevPlants());
			all.addAll(model.getNewPlants());
			Garden garden = new Garden(all, model.gShape, model.configs, fileName, model.getScale(), model.getGardenRating());
			model.saved.add(garden);
			if (fileName.equals("Enter Garden Name Here")) {
				sgView.displayFileMsg(false); // false for invalid filename
			} else {
				sgView.displayFileMsg(true); // true for valid filename
				String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\savedGardenData\\";
				Model.toBeAdded.add(fileName);
				System.out.println(model.getGardenRating());
				model.ratings.put(fileName, model.getGardenRating());
			    model.saveGardenData(filePath+fileName, garden);
			}
		}
		designView.clearScene();
		drawView.clearScene();
	}
	
	
	/**
	 * onRightClick, mousehandler for a right click on a plant icon. Calls the remove icon method.
	 * 
	 * @param e the mouse event
	 * @param pi the plant icon clicked
	 * @param name the name of the scene
	 */
	public void onRightClick(MouseEvent e, PlantIcon pi, SceneName name) {
		if (e.getButton() == MouseButton.SECONDARY) 
			views.get(name).removeIcon(pi);
	}
	
	
    /**
	 * getShapeHandler, creates shapes that represent the user's garden plot.
	 * 
	 * @param action a String representing what shape to make
	 * @param p the Shape object that will have mouseActions occurring to it
	 * @param x the new location of the shape (X position)
	 * @param y the new y position of the shape
	 * @return an eventhandler that handles making and dragging shapes
	 */

	public EventHandler<MouseEvent> getShapeHandler(String action, Shape p, double x, double y)
	{
		//System.out.println("shape color: " + p.getFill() );
		switch(action) {
			case "newRect":
				//model.gShape.add(new GardenShapeSave(x,y,"rect"));
				return event -> drawView.makeShape("rect", x, y, p.getFill());
			case "newCirc":
				//model.gShape.add(new GardenShapeSave(x,y,"circ"));
				return event -> drawView.makeShape("circ", x, y, p.getFill());
			case "dragRect":
				return event -> onRectDrag((MouseEvent) event,(Rectangle)p);
			case "dragCirc":
				return event -> onCircDrag((MouseEvent) event,(Circle)p);
			case "optRect":
				//return event -> drawView.displayRectOptions((MouseEvent) event, (Rectangle) p);
			default:
				return null;
		}
	}
	
	
	/**
	 * onPointDrag, handles dragging points in DrawView.
	 * 
	 * @param event the mouse event of someone dragging a point
	 * @param p the point being dragged
	 * @param piv the imageView of the point being dragged
	 */
	public void onPointDrag(MouseEvent event, Point p, ImageView piv) {
		p.setX(p.getX() + event.getX()); //event.getX() is the amount of horiz drag
		p.setY(p.getY() + event.getY());
		drawView.setPointLocation(p, p.getX(), p.getY(), piv);
	}

	
	/**
	 * onRectDrag, handles dragging a rectangle.
	 * 
	 * @param event the event of the mouse dragging a rectangle
	 * @param r the rectangle being dragged
	 */
	private void onRectDrag(MouseEvent event, Rectangle r) {
		r.setTranslateX(r.getTranslateX() + event.getX() - r.getWidth()/2); //event.getX() is the amount of horiz drag
		r.setTranslateY(r.getTranslateY() + event.getY() - r.getHeight()/2);
		drawView.setRectLocation(r, r.getX(), r.getY());
	}
	
	
	/**
	 * onCircDrag, handles dragging a circle.
	 * 
	 * @param event the event of the mouse dragging a circle
	 * @param c the circle being dragged
	 */
	private void onCircDrag(MouseEvent event, Circle c) {
		c.setTranslateX(c.getTranslateX() + event.getX()); //event.getX() is the amount of horiz drag
		c.setTranslateY(c.getTranslateY() + event.getY());
		drawView.setCircLocation(c, c.getCenterX(), c.getCenterY());
	}
	
	
	/**
	 * addShape, adds a garden shape to the garden in the model.
	 * 
	 * @param s the shape being added
	 */
	public void addShape(Shape s) {
		model.addShape(s);
	}
	
	
	/**
	 * clearShape, removes all shapes from the model.
	 */
	public void clearShape() {
		model.clearShapes();
		designView.clearScene();
	}
	
	
	/**
	 * addNewPlant, adds a plant-icon to a list of new planticons in the garden.
	 * 
	 * @param pi the planticon being added
	 */
	public void addNewPlant(PlantIcon pi) {
		model.addNewPlant(pi);
	}
	
	
	/**
	 * removeNewPlants, removes new plants (used on right click).
	 * 
	 * @param pi the planticon to be removed
	 */
	public void removeNewPlant(PlantIcon pi) {
		model.removeNewPlant(pi);
	}
	
	
	/**
	 * addPrevPlant, adds an old plant.
	 * 
	 * @param pi the planticon to be added to old plants list
	 */
	public void addPrevPlant(PlantIcon pi) {
		model.addPrevPlant(pi);
	}
	
	
	/**
	 * removePrevPlant, removes an old plantIcon.
	 * 
	 * @param pi the planticon to be removed form the list of old plants
	 */
	public void removePrevPlant(PlantIcon pi) {
		model.removePrevPlant(pi);
	}
	
	
	/**
	 * getNewPlants, gets the new plant data from the model.
	 * 
	 * @return plantList the list of plant icons
	 */
	public ArrayList<PlantIcon> getNewPlants() {
		return model.getNewPlants();
	}
	
	
	/**
	 * getPrevPlants, gets the previous plant data from the model.
	 * 
	 * @return plantList the previous plant icon list
	 */
	public ArrayList<PlantIcon> getPrevPlants() {
		return model.getPrevPlants();
	}
	
	
	/**
	 * getScaleFactor, returns the scale for the garden from the model.
	 * 
	 * @return scale the garden scale
	 */
	public double getScaleFactor() {
		return model.getScale();
	}
	
	
	/**
	 * dragPlantIcon, handles dragging a plant object. 
	 * 
	 * @param event the event from the mouse
	 * @param pi the plant icon
	 * @param name the name of the scene
	 */
	public void dragPlantIcon(MouseEvent event, PlantIcon pi, SceneName name) {
		if (event.isPrimaryButtonDown()) {
			pi.getCircle().setTranslateX(pi.getCircle().getTranslateX() + event.getX()); //event.getX() is the amount of horiz drag
			pi.getCircle().setTranslateY(pi.getCircle().getTranslateY() + event.getY());
			views.get(name).setPlantIconLocation(pi, pi.getCircle().getCenterX(), pi.getCircle().getCenterY());
		}
	}
	
	
	/**
	 * dragPane, handles panning the garden around in AddPreexistingView and DesignGardenView.
	 * 
	 * @param event the event of the user dragging th emouse around
	 * @param name the name of the scene to change the garden in
	 * @param pzPane the pan and zoom pane
	 */
	public void dragPane(MouseEvent event, SceneName name, PanAndZoomPane pzPane) {
		if (!event.isPrimaryButtonDown()) 
			views.get(name).setPane(event.getX(), event.getY(), pzPane.getX(), pzPane.getY());
	}
	
	
	/**
	 * setPaneDrag. handles setting the distance the pane has been dragged in AddPreexistingView and 
	 *              DesignGardenView.
	 * 
	 * @param event the event of the user placing the panel
	 * @param name the name of the scene this is occuring in
	 * @param pzPane pan and zoom pane
	 */
	public void setPaneDrag(MouseEvent event, SceneName name, PanAndZoomPane pzPane) {
		if (!event.isPrimaryButtonDown()) {
			pzPane.setX(event.getX());
			pzPane.setY(event.getY());
		}
	}
	
	
	/**
	 * onHover, handles hovering over an object. Displays the plant info on hover.
	 * 
	 * @param p the plant being hovered
	 * @param name the name of the scene
	 */
	public void onHover(Plant p, SceneName name) {
		views.get(name).makeHover(p);		
	}
	
	
	/**
	 * catalogEventHandler, removes the images from the favorite square as well as the 
	 * 					 	faovrites list static attribute.  
	 * 
	 * @param event the event to be handled
	 */
	public void catalogEventHandler(MouseEvent event) {
		if (event.getSource().equals(pcView.getClearButton())) {
			pcView.getSelectedPane().getChildren().removeAll(pcView.getSelectedPane().getChildren());
			PlantCatalogView.favoritesList.clear();
		}
	}
	
	
	/**
	 * handlePlantSearch, calls the methods in model for filtering the plant list. The 
	 * 					  filtered list is then sent to the view to be displayed.  
	 *
	 * @param hayStack the list of plants to filter
	 * @param needle the plant being searched for
	 */
	public void handlePlantSearch(ArrayList<Plant> hayStack, String needle) {
		this.filteredPlantList = model.searchPlants(hayStack, needle);
		pcView.updateCatalog(this.filteredPlantList);
	}
	
	
	/**
	 * onPlantPress, handles pressing a mouse button on a plant.
	 * 
	 * @param event the event from the mouse
	 * @param p the plant clicked
	 * @param name the name of the scene
	 */
	public void onPlantPress(MouseEvent event, Plant p, SceneName name) {
		if (event.isPrimaryButtonDown()) {
			views.get(name).makeIcon(p, model.getShapes().get(0).getTranslateX(), model.getShapes().get(0).getTranslateY());
		}
	}

	
	/**
	 * updateFavorites, sets the favorites list for the design view and pre existing
	 * 					view scenes.
	 * 
	 * @param favs the list of favorites. 
	 */
	public void updateFavorites(ArrayList<Plant> favs) {
		designView.setFavorites(favs);
		preView.setFavorites(favs);
	}
	
	
	/**
	 * updateAndChange, updates the favorites list. 
	 * 
	 * @param favorites the list of favorites
	 * @param scene the name of the scene
	 */
	public void updateAndChange(ArrayList<Plant> favorites, SceneName scene) {
		updateFavorites(favorites);
		handleMousePress(scene);
	}

	
	/**
	 * sendGardenDimensions, Figures out feet/pixels based on inputs from drawGardenView. Also 
	 * 						 figures out how much it should increase the size of the garden to 
	 * 					     fill up the screen.
	 * 
	 * @param A point a 
	 * @param B point b
	 * @param length the length
	 */
	public void sendGardenDimensions(Point A, Point B, String length) {
		model.setGardenScale(A, B, length);
	}
	
	
	/**
	 * sendConfigsToModel, sends the settings entered by the users to the model to be serialized
	 * 					   at some point.
	 * 
	 * @param configs the configurations
	 */
	public void sendConfigsToModel(HashMap<Integer, String> configs) {
		model.saveSettings(configs);
	}


	/**
	 * getScenes, returns the scenes map.
	 * 
	 * @return scenes the map of scenes
	 */
	public static Map<SceneName, Scene> getScenes() {
		return scenes;
	}

	
	/**
	 * resetOpacity, resets the opacity of the irrelevent plant images upon the reset button click
	 * 				 in the catalog view.
	 */
	public void resetOpacity() {
		for (Node view : pcView.getPlantImageList())
			view.setOpacity(1);
	}
}