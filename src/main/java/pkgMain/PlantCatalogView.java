package pkgMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * Class - PlantCatalogView, this class is for displaying the catalog of plants the
 * 		   user is able to pick from. It will be scrolled through and plants can be
 * 		   hovered over for additional information or chosen for use. Warnings will 
 * 		   be given for plants that are not quite comaptible with the users conditions.
 *  
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria Venrooy
 */
@SuppressWarnings("static-access")
public class PlantCatalogView extends View {
	private Controller controller;

	// Constants
	private static final int CATALOG_IMAGE_SIZE = 100;
	private static final int HOVER_WIDTH = 150;
	private static final double INVENTORY_HEIGHT = 200;
	private static final double IMG_GAP = 50;
	private static final double IMG_GAP2 = 10;
	private static final double BUTTON_SPACING = 20;
	private static final double SELECTED_MIN_HEIGHT = 350;
	private static final double SIZEBAR_X_INCR = 100;
	private static final double SIZEBAR_WIDTH = 15;


	// Buttons 
	private Button homeButton; 
	private Button clearButton;
	private Button goBackButton;
	private Button searchButton;
	private Button resetButton;
	
	// TextField's / Text
	private TextField searchBar;
	private Text plantStorageText;
	private Text welcomeMsg;
	private Text tutorialMsg;
	
	// Panes and Boxes
	private Scene scene;
	private TilePane catalogPane;
	private FlowPane selectedPane;
	private StackPane infoWindow;
	private ScrollPane scrollWrapper1;
	private ScrollPane scrollWrapper2;
	private VBox root;
	private HBox topBar;
	private HBox bottomBar;
	private HBox inventoryBar;
	
	// Other
	private BackgroundFill background_fill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY); 
	private Background background = new Background(background_fill); 
	private double initialWidth = 1000;
	private double initialHeight = 700;
	private ArrayList<Plant> masterPlantList;
	private ArrayList<ImageView> plantImageList;
	public static ArrayList<Plant> favoritesList = new ArrayList<Plant>();

	
	// Getters
	/**
	 * getScene, getter for teh scene attribute
	 * 
	 * @return scene the scene for this view
	 */
	public Scene getScene() {
		return this.scene;
	}
	
	
	/**
	 * @return the controller
	 */
	public Controller getController() {
		return controller;
	}


	/**
	 * @return the homeButton
	 */
	public Button getHomeButton() {
		return homeButton;
	}

	
	/**
	 * getPlantImageList
	 * 
	 * @return the image list
	 */
	public ArrayList<ImageView> getPlantImageList() {
		return this.plantImageList;
	}
	

	/**
	 * @return the clearButton
	 */
	public Button getClearButton() {
		return clearButton;
	}


	/**
	 * @return the designSceneButton
	 */
	public Button getGoBackButton() {
		return goBackButton;
	}


	/**
	 * @return the searchButton
	 */
	public Button getSearchButton() {
		return searchButton;
	}


	/**
	 * @return the searchBar
	 */
	public TextField getSearchBar() {
		return searchBar;
	}


	/**
	 * @return the instruction
	 */
	public Text getInstruction() {
		return plantStorageText;
	}


	/**
	 * @return the welcomeMsg
	 */
	public Text getWelcomeMsg() {
		return welcomeMsg;
	}


	/**
	 * @return the catalogPane
	 */
	public TilePane getCatalogPane() {
		return catalogPane;
	}


	/**
	 * @return the selectedPane
	 */
	public FlowPane getSelectedPane() {
		return selectedPane;
	}


	/**
	 * @return the infoWindow
	 */
	public StackPane getInfoWindow() {
		return infoWindow;
	}


	/**
	 * @return the scrollWrapper1
	 */
	public ScrollPane getScrollWrapper1() {
		return scrollWrapper1;
	}


	/**
	 * @return the scrollWrapper2
	 */
	public ScrollPane getScrollWrapper2() {
		return scrollWrapper2;
	}


	/**
	 * @return the root
	 */
	public VBox getRoot() {
		return root;
	}


	/**
	 * @return the topBar
	 */
	public HBox getTopBar() {
		return topBar;
	}


	/**
	 * @return the bottomBar
	 */
	public HBox getBottomBar() {
		return bottomBar;
	}


	/**
	 * @return the inventoryBar
	 */
	public HBox getInventoryBar() {
		return inventoryBar;
	}


	/**
	 * @return the width
	 */
	public double getWidth() {
		return initialWidth;
	}


	/**
	 * @return the height
	 */
	public double getHeight() {
		return initialHeight;
	}


	
	/**
	 * PlantCatalogView, constructor, allocates memory for all of the panes, then makes the scene.
	 * 
	 * @param stage the stage for this scene
	 * @param controller the controller
	 */
	public PlantCatalogView(Stage stage, Controller controller) {
		this.controller = controller;
		scrollWrapper1 = new ScrollPane();
		homeButton = new Button("Go Home");
		searchButton = new Button("Search");
		catalogPane = new TilePane();
		tutorialMsg = new Text("This is a catalog with a searchbar to find a plant.\nThe red bars represent the sizes of the plants.\nThis box is to save plants you've clicked up top.\n       CLICK TO REMOVE THIS MESSAGE");
		selectedPane = new FlowPane(tutorialMsg);
	    topBar = new HBox();
        bottomBar = new HBox();
        goBackButton = new Button("Back");
        searchBar = new TextField("Lookup...");
        clearButton = new Button("Clear Selected Plants");
        resetButton = new Button("Reset");
        plantStorageText = new Text("Plant Storage");
        inventoryBar = new HBox();
        scrollWrapper2 = new ScrollPane(selectedPane);
        welcomeMsg = new Text("Plant Catalog");
        infoWindow = new StackPane(welcomeMsg);
        plantImageList = new ArrayList<ImageView>();
        root = new VBox();			 
        root.getChildren().addAll(topBar, scrollWrapper1, inventoryBar, bottomBar);
		scene = new Scene(root, initialWidth, initialHeight); // Make the scene with all of the above
		root.setVgrow(scrollWrapper1, Priority.ALWAYS);
       
		configurePanes(); // Setup for pane properties
		configureButtons(); // Define button behaviour
        styleManager(); // Style it
		setResizeListeners(); // Handle screen resizing

		DesignGardenView dgv = (DesignGardenView) controller.getViews().get(SceneName.DESIGNGARDENVIEW);
		masterPlantList = dgv.getPlantList();
		displayCatalog(masterPlantList); // Draws a bunch of the same plants in catalog, Delete later
	}
	
	
	/**
	 * hashMapToArrayList, takes the plant data in the given hashmap and puts it in an array list.
	 * 
	 * @param map A hash map containing plant data
	 * @return the new list of plants 
	 */
	public ArrayList<Plant> hashMapToArrayList(HashMap<String, Plant> map) {
		ArrayList<Plant> pl = new ArrayList<>();
		for(Map.Entry<String, Plant> p : map.entrySet()) {
			Plant tmp = p.getValue();
			pl.add(tmp);
		}
		return pl;
	}
	
	
	/**
	 * updateCatalog, this method displays the plants that were searched for. It will still display every plant,
	 * 				  but set the transparenccy value for the plants that didn't match the search to be near 
	 * 			      transparent. The relevant images
	 * 
	 * @param filteredPlants the list of plants filtered for the search
	 */
	public void updateCatalog(ArrayList<Plant> filteredPlants) {
		ObservableList<Node> workingCollection = FXCollections.observableArrayList(catalogPane.getChildren());
		int priorityIndex = 0;
		if (!filteredPlants.isEmpty()) { // Ignore empty search bar
			for (Plant p : masterPlantList) {
				int count = 0;
				if (filteredPlants.contains(p))
					for (ImageView iView : plantImageList) {
						if (p.getPhoto().equals(iView.getImage())) {
							Collections.swap(workingCollection, priorityIndex, count);
							catalogPane.getChildren().setAll(workingCollection);
							priorityIndex++;
							continue;
						}
						iView.setOpacity(.5); // Half visible
						count++;
					}
			}
		}
	}
	
	
	/**
	 * setResizeListeners, sets listeners on the viewport bounds property's for the two scrollPanes
	 * 					   in this class. This in turn, calls the resizeManager function to control
	 * 					   the behaviour upon resizing.
	 */
	public void setResizeListeners() {
		Pane[] catalog = {catalogPane};
		Pane[] inventory = {selectedPane, infoWindow};
		scrollWrapper1.viewportBoundsProperty().addListener(resizeManager(catalog));
		scrollWrapper2.viewportBoundsProperty().addListener(resizeManager(inventory));
	}

	
	/**
	 * displayCatalog, puts all of the plants in the catalog TilePane
	 * 
	 * @param plantList the list of plants to be displayed
	 */
	public void displayCatalog(ArrayList<Plant> plantList) {
		for (Plant plant : plantList) {
			Rectangle sizeBar = new Rectangle(SIZEBAR_WIDTH, plant.getWidth());
			sizeBar.setFill(Color.RED);
		    sizeBar.setTranslateX(SIZEBAR_X_INCR); // Putting the sizebar ont he righthand side of the imageview
		    StackPane plantAndSizePane = new StackPane(makeImage(plant), sizeBar);
			catalogPane.getChildren().add(plantAndSizePane);
			plantAndSizePane.setAlignment(sizeBar, Pos.BOTTOM_LEFT);
		}
	}
	
	
	/**
	 * configurePanes, sets the properties needed for the panes in this scene. Adds
	 * 				   child nodes to the panes.
	 */
	public void configurePanes() {
		tutorialMsg.setTranslateX(selectedPane.getWidth()/2); // Center the tutorial message
		tutorialMsg.setTranslateY(selectedPane.getHeight()/2);
		
		scrollWrapper1.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        scrollWrapper1.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollWrapper1.setContent(catalogPane);
        scrollWrapper2.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        scrollWrapper2.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollWrapper2.setContent(selectedPane);
        scrollWrapper2.setMinHeight(SELECTED_MIN_HEIGHT);
        
        inventoryBar.setPrefHeight(INVENTORY_HEIGHT);
        inventoryBar.getChildren().addAll(scrollWrapper2, infoWindow);
        inventoryBar.setHgrow(scrollWrapper2, Priority.ALWAYS);
        inventoryBar.setHgrow(infoWindow, Priority.ALWAYS);
        
		catalogPane.setPadding(new Insets(IMG_GAP, IMG_GAP, IMG_GAP, IMG_GAP));
        catalogPane.setVgap(IMG_GAP);
        catalogPane.setHgap(IMG_GAP);
		selectedPane.setPadding(new Insets(IMG_GAP2, IMG_GAP2, IMG_GAP2, IMG_GAP2));
        selectedPane.setVgap(IMG_GAP2);
        selectedPane.setHgap(IMG_GAP2);
      
        bottomBar.getChildren().addAll(clearButton, plantStorageText);
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setSpacing(BUTTON_SPACING);
        searchButton.setTranslateX(-BUTTON_SPACING);
        bottomBar.setBackground(background);
	    topBar.getChildren().addAll(homeButton, goBackButton, searchBar, searchButton, resetButton);
        topBar.setSpacing(BUTTON_SPACING);	
        topBar.setBackground(background);
	}
	
	
	/**
	 * configureButtons, sets the behaviour of the buttons in this scene. For example,
	 * 					 doing a mouse press on a button will make it change color until
	 * 					 the mouse is released at which point it will change back to its
	 * 					 original color.
	 */
	public void configureButtons() {
		tutorialMsg.setOnMouseClicked(e -> selectedPane.getChildren().remove(tutorialMsg));
        resetButton.setOnMousePressed(e -> controller.changeButtonColor(e));
        resetButton.setOnMouseReleased(e -> controller.changeButtonColor(e));
        resetButton.setOnMouseClicked(e -> controller.resetOpacity());
		searchButton.setOnMouseClicked(e -> controller.handlePlantSearch(masterPlantList, searchBar.getText()));
        clearButton.setOnMouseClicked(e -> controller.catalogEventHandler(e));
        clearButton.setOnMousePressed(e -> controller.changeButtonColor(e));
        clearButton.setOnMouseReleased(e -> controller.changeButtonColor(e));
        homeButton.setOnMousePressed(e -> controller.changeButtonColor(e));
        homeButton.setOnMouseReleased(e -> controller.changeButtonColor(e));
	    homeButton.setOnMouseClicked(e -> controller.handleMousePress(SceneName.HOMEPAGEVIEW));
	    goBackButton.setOnMousePressed(e -> controller.changeButtonColor(e));
	    goBackButton.setOnMouseReleased(e -> controller.changeButtonColor(e));
	    goBackButton.setOnMouseClicked(e -> controller.updateAndChange(favoritesList, controller.getLastScene()));
        searchBar.setOnMouseClicked(e -> searchBar.clear());
	}
	
	
	/**
	 * styleManager, sets the colors and font sizes for the buttons and some panes. 
	 */
	public void styleManager() {
        tutorialMsg.setStyle("-fx-font-size: 1.9em; -fx-fill: white;");
		searchButton.setStyle("-fx-font-size: 2em;");
	    homeButton.setStyle("-fx-border-color:black; -fx-background-color: #00ff00; -fx-font-size: 2em;");
	    resetButton.setStyle("-fx-background-color: #00ff00; -fx-font-size: 2em;");
	    clearButton.setStyle("-fx-background-color: #00ff00; -fx-font-size: 2em;");
        searchBar.setStyle("-fx-background-color: #0000ff;");
	    goBackButton.setStyle("-fx-background-color: #00ff00; -fx-font-size: 2em;");
	    searchBar.setStyle("-fx-background-color: #00ff00; -fx-font-size: 2em;");
		catalogPane.setStyle("-fx-background-color: green;");
        welcomeMsg.setStyle("-fx-background-color: #0000ff; -fx-font-size: 60px;");
        selectedPane.setStyle("-fx-background-color: black;");
        plantStorageText.setStyle("-fx-fill: white; -fx-font-size: 2em;");
	}
	
	
	/**
	 * resizeManager, 
	 * 
	 * @param paneList the panes to have listeners set to them.
	 * @return listener a listener to resize bounds when change is detected.
	 */
	public ChangeListener<Bounds> resizeManager(Pane[] paneList) {
	    ChangeListener<Bounds> listener = new ChangeListener<Bounds>() {
	    	public void changed(ObservableValue<? extends Bounds> ov, 
            		Bounds oldBounds, Bounds bounds) {
	    		for (Pane pane : paneList) {
	    			 pane.setPrefWidth(bounds.getWidth());
	                 pane.setPrefHeight(bounds.getHeight());
	    		}
            }
        };
        return listener;
	}
	
	
	/**
	 * makeImage, makes the ImageView's seen in the catalog portion of the screen. Sets their
	 * 			  mosue actions to create a copy of itself in the user plant storage space and
	 * 			  display information about the plant when hovered over.
	 * 
	 * @param p a plant
	 * @return an image of a plant
	 */
	public ImageView makeImage(Plant p) {
		ImageView iv1 = new ImageView(p.getPhoto());
		iv1.setFitHeight(CATALOG_IMAGE_SIZE);
		iv1.setFitWidth(CATALOG_IMAGE_SIZE);
		iv1.setOnMouseEntered(event -> controller.onHover(p, SceneName.PLANTCATALOGVIEW));
		iv1.setOnMousePressed(event -> makeCopy(iv1));
		plantImageList.add(iv1);
		return iv1;
	}
	
	
	/**
	 * makeCopy, when a plant in the catalog is clicked, a copy of it is sent to the users
	 * 		     plant storage box, which is a flowpane. The user can then drag the plant 
	 * 			 around.
	 * 
	 * @param dragged the image that was selected from the catalog
	 */
	public void makeCopy(ImageView dragged) {
		ImageView newView = new ImageView(dragged.getImage());
		newView.setFitHeight(CATALOG_IMAGE_SIZE);
		newView.setFitWidth(CATALOG_IMAGE_SIZE);
		selectedPane.getChildren().add(newView);
		for (Plant p : masterPlantList) 
			try {
				if (p.getPhoto().equals(newView.getImage())) 
					favoritesList.add(p);				
			} catch (Exception e) {
				//System.out.println(e.getStackTrace());
			}
	    //favoritesList.forEach(plant -> System.out.println(plant.getName()));
	}
	
	
	/**
	 * makeHover, when a plant is hovered over in the catalog, a window displaying an image
	 * 			  of the plant and information about is pops up. It remainds there until 
	 * 			  another plant is hovered over, in which case it is replaced with a description
	 * 			  of that plant.
	 *
	 * @param p the plant to be displayed
	 */
	public void makeHover(Plant p) {
		infoWindow.getChildren().removeAll(infoWindow.getChildren());
		VBox vbox1 = new VBox();
		ImageView iv = new ImageView(p.getPhoto());
		iv.setFitHeight(CATALOG_IMAGE_SIZE);
		iv.setFitWidth(CATALOG_IMAGE_SIZE);
		TextFlow descrip = new TextFlow(new Text(p.getDescription()));
		TextFlow stats = new TextFlow(new Text("Flower Color: " + p.getFlowerColor() +"\nHeight: " + p.getHeight() +"\nSoil Type: "+ p.getSoil()
		+ "\nWater Preference: " + p.getWaterAsString() + "\nBegins to Bloom: " + p.getBloomSeason()));
		descrip.setPrefWidth(HOVER_WIDTH);
		descrip.setStyle("-fx-font-size: .7em;");
		stats.setPrefWidth(HOVER_WIDTH);
		vbox1.setStyle("-fx-background-color: #33FF99; -fx-font-size: 1.6em;");
		vbox1.getChildren().add(new Text(p.getName()));
		vbox1.getChildren().add(iv);
		vbox1.getChildren().addAll(descrip, stats);
		vbox1.setAlignment(Pos.CENTER);
		vbox1.setMaxHeight(scene.getHeight()/2);
		infoWindow.getChildren().add(vbox1);
	}
}