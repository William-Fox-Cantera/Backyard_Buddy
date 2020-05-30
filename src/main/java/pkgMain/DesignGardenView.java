package pkgMain;

import java.util.ArrayList;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

/**
 * Class - DesignGardenView, the view for the main scene of the application. This displays 
 * 							 the garden and the tabs for all the plants. Gives the options 
 * 							 for the users to save their garden, visit the plant bank, save 
 * 							 the garden, and go back to the opening scene.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria Venrooy
 */
@SuppressWarnings("static-access")
public class DesignGardenView extends AddPlantsView{
	Controller controller;
	private TabPane tabPane = new TabPane(); // the tabPane will hold the Plants that the user can pick from
	private PanAndZoomPane panZoomPane1 = new PanAndZoomPane();
	public BorderPane borderPane1 = new BorderPane(); // the borderpane that tabPane and panZoomPane1 will be put into
	private Scene scene;
	ScrollBar scrollBar1 = new ScrollBar();
	VBox plantHoverBox = new VBox();
	HBox scrollBox = new HBox();
	VBox hoverBox = new VBox();
	private ArrayList<Plant> favorites = new ArrayList<>();
	
	
	/**
	 * gets the ArrayList of all of the plants from the hashmap of all of the plants
	 * 
	 * @return flowerList the list of all plants to be returned
	 */
	public ArrayList<Plant> getPlantList() {
		ArrayList<Plant> pl = new ArrayList<>();
		for(Map.Entry<String, Plant> p : allPlants.entrySet()) {
			Plant tmp = p.getValue();
			pl.add(tmp);
		}
		return pl;
	}
	
	
	/**
	 * DesignGardenView, constructor for DesignGardenView.
	 * 
	 * @param stage the stage
	 * @param cont the Controller that the DesignGardenView will interact with
	 */
	public DesignGardenView(Stage stage, Controller cont) {	
		controller = cont;
		super.setController(cont);
	}
	
	
	/**
	 * getBP, getter for the border pane
	 * 
	 * @return the border pane
	 */
	public BorderPane getBP() {
		return borderPane1;
	}
	
	
	/**
	 * clearScene, clears the grid pane.
	 */
	public void clearScene() {
		this.panZoomPane1.getChildren().clear();
	}
	
	
	/**
	 * getpanZoomPane1, getter for the grid pane.
	 * 
	 * @return gp1 the grid pane
	 */
	public PanAndZoomPane getpanZoomPane1() {
		return panZoomPane1;
	}

	
	/**
	 * setpanZoomPane1, setter for grid pane.
	 * 
	 * @param panZoomPane1 the custom pane
	 */
	public void setpanZoomPane1(PanAndZoomPane panZoomPane1) {
		this.panZoomPane1 = panZoomPane1;
	}

	
	/**
	 * getScene, This is where the display garden scene gets put together. The 
	 *           plants from the lists are listed in the side bar, the title bar 
	 *           gets added, etc.
	 * 
	 * @return scene the finished and ready to display DesignGardenScene
	 */
	@Override
	public Scene getScene() {
		//Displaying all of the plant lists in the side bar on the left
		reorderPlants();
		
		panZoomPane1.setStyle("-fx-background-color: #33FF99"); 
		borderPane1.setStyle("-fx-background-color: #33FF99");

		scrollBar1 = super.getScrollBar();
		
        scrollBar1.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	panZoomPane1.zoomFunction(new_val.doubleValue() - old_val.doubleValue());
            }
        });
        
        scrollBox = super.getScrollBox(scrollBar1);
        
        hoverBox.getChildren().addAll(plantHoverBox, scrollBox);
        hoverBox.setVgrow(scrollBox, Priority.ALWAYS);
        hoverBox.setFillWidth(true);
        
        panZoomPane1.setOnMousePressed(event -> controller.setPaneDrag(event, SceneName.DESIGNGARDENVIEW, panZoomPane1));
        panZoomPane1.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> controller.dragPane(event, SceneName.DESIGNGARDENVIEW, panZoomPane1));
   
	
		borderPane1.setCenter(panZoomPane1);
		tabPane.setSide(Side.RIGHT);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		borderPane1.setLeft(tabPane);
		borderPane1.setRight(hoverBox);

		FlowPane topBar = getTitleBar("Design Your Garden!", controller);
		Button configsIcon = super.getConfigsIcon();
		Button plantBankButton = new Button("Go to Plant Bank");
		plantBankButton.setOnMouseClicked(e -> controller.handleMousePress(SceneName.PLANTCATALOGVIEW));
		Button saveButton = new Button("Save");
		saveButton.setOnMouseClicked(e -> controller.handleSavePress());

		topBar.getChildren().add(configsIcon);
		topBar.getChildren().add(plantBankButton);
		topBar.getChildren().add(saveButton);
		
		borderPane1.setTop(topBar);
		
		scene = new Scene(borderPane1, WIDTH,HEIGHT);
		scene.getStylesheets().add("/images/DesignGardenStyles.css");
		return scene;
	}
	
	
	
	
	/**
	 * displayGardenShape, places the garden outline from the drawGardenView in this view.
	 */
	public void displayGardenShape() {
		for (Shape s : controller.getShapes())
			if (!panZoomPane1.getChildren().contains(s)) {
				panZoomPane1.getChildren().add(s);
				s.setDisable(true);
			}
		for (PlantIcon pi : controller.getPrevPlants()) 
			if (!panZoomPane1.getChildren().contains(pi.getCircle())) {
				panZoomPane1.getChildren().add(pi.getCircle());
				pi.getCircle().setDisable(true);
			}
	}
	
	
	/**
	 * makeIcon, This will get the icon from the plant(it'll most likely be a
	 *           circle representing the plant) and will render it in the view 
	 *           so it can be dragged. 
	 *  
	 * @param p the Plant whose icon we're displaying
	 * @param x the x location of where the icon will first be placed on the screen
	 * @param y the y location of where the icon will first be placed on the screen
	 */
	public void makeIcon(Plant p, double x, double y) {
		PlantIcon pi = super.makePlantIcon(p, x, y, SceneName.DESIGNGARDENVIEW);
		controller.addNewPlant(pi);	
		panZoomPane1.getChildren().add(pi.getCircle());
	}
	
	
	/**
	 * makeShape, Change color of icon based on season set in configs.
	 * 
	 * @param shape the shape name
	 * @param width the width
	 * @param height the height
	 */
	public void makeShape(String shape, int width, int height) {
		switch(shape) {
			case "rect":
				Rectangle r = new Rectangle();
				r.setWidth(width);
				r.setHeight(height);
				r.setFill(Color.GREEN);
				r.setOnMouseDragged(controller.getShapeHandler("dragRect", r, 0,0));
				r.setOnMouseClicked(controller.getShapeHandler("optRect", r,0,0));
				panZoomPane1.getChildren().add(r);
				controller.addShape(r);
				break;
			case "circ":
				Circle circle = new Circle();
				circle.setRadius(width);
				circle.setFill(Color.GREEN);
				circle.setOnMouseDragged(controller.getShapeHandler("dragCirc", circle,0,0));
				panZoomPane1.getChildren().add(circle);
				controller.addShape(circle);
				break;
		}
	}
	
	
	/**
	 * makeShape, makes a shape with the given x and y coordinates and the given translate
	 * 			  x and y and the given radius.
	 * 
	 * @param shape the name of the shape
	 * @param color the color of the shape
	 * @param width width of shape
	 * @param height height of shape
	 * @param x x loc of shape
	 * @param y y loc of shape
	 * @param rad a radius for the circle
	 * @return the new shape
	 */
	public Shape makeShape(String shape, String color, int width, int height, int x, int y, int rad) {
		Paint paint;
		if(color.equals("green")) {
			paint = Color.GREEN;
		}
		else paint = Color.BLACK;
		switch(shape) {
			case "rect":
				Rectangle r = new Rectangle();
				r.setWidth(width);
				r.setHeight(height);
				r.setTranslateX(x);
				r.setTranslateY(y);
				r.setFill(paint);
				r.setDisable(true);
				panZoomPane1.getChildren().add(r);
				controller.addShape(r);
				return r;
			case "circ":
				Circle circle = new Circle();
				circle.setRadius(rad);
				circle.setTranslateX(x);
				circle.setTranslateY(y);
				circle.setFill(paint);
				circle.setDisable(true);
				panZoomPane1.getChildren().add(circle);
				controller.addShape(circle);
				return circle;
		}
		return null;
	}
	

	/**
	 * clearGarden, removes all children from the center area of the borderpane.
	 */
	public void clearGarden() {
		panZoomPane1.getChildren().clear();
	}
	
	
	/**
	 * setFavorites, sets thefavorites list.
	 * 
	 * @param fav the favorite list
	 */
	public void setFavorites(ArrayList<Plant> fav) {
		this.favorites = fav;
		tabPane.getTabs().remove(5); // index of favorites list
		ScrollPane spane = super.makePlantList(fav, SceneName.DESIGNGARDENVIEW);
		Tab tab1 = new Tab("Favorites", spane);
		tabPane.getTabs().add(5,tab1);
	}
	
	
	/**
	 * removeIcon, removes a plantIcon from the garden.
	 * 
	 * @param pi the plantIcon to be removed
	 */
	public void removeIcon(PlantIcon pi) {
		panZoomPane1.getChildren().remove(pi.getCircle());
		controller.removeNewPlant(pi);
	}
	
	/**
	 * This method allows the user to pan across the pan and zoom pane using 
	 * the right click
	 * 
	 * @param x the difference in x position from the drag
	 * @param y the difference in y position from the drag
	 * @param spotX the x position where the mouse was clicked
	 * @param spotY the y position where the mouse was clicked
	 */
	public void setPane(double x, double y, double spotX, double spotY) {
	    panZoomPane1.setTranslateX(x + panZoomPane1.getTranslateX() - spotX);
	    panZoomPane1.setTranslateY(y + panZoomPane1.getTranslateY() - spotY);
	}
	
	
	/**
	 * makeHover, When the user hovers their mouse over a plantIcon, information
	 *            on the plant will popup on the right.
	 * 
	 * @param p the Plant whose icon has been hovered over
	 */
	public void makeHover(Plant p) {
		plantHoverBox = super.makeHoverPane(p);
		hoverBox.getChildren().remove(0);
		hoverBox.getChildren().add(0, plantHoverBox);
		borderPane1.setRight(hoverBox);
	}
	
	
	/**
	 * reorderPlants, calls the superclass to use the reorder plants method.
	 */
	public void reorderPlants() {
		super.reorderPlants(tabPane, favorites, SceneName.DESIGNGARDENVIEW);
	}
}
