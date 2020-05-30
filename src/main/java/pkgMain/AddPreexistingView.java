package pkgMain;

import java.util.ArrayList;
import java.util.HashMap;

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
 * Class - AddPreexistingView, Methods for putting together the scene for the pre-existing
 * 							   garden. The scene that pops up before putting  newplants you 
 *                             would like to add to the garden.
 *                             
 * @author Diane Vinson
 * @author Maria van Venrooy
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author William Cantera
 */
@SuppressWarnings("static-access")
public class AddPreexistingView extends AddPlantsView{
	Controller controller;
	private TabPane tabPane = new TabPane(); // the tabPane will hold the Plants that the user can pick from
	private PanAndZoomPane panZoomPane1 = new PanAndZoomPane(); //TODO: change name to be more intuitive
	public BorderPane borderPane1 = new BorderPane(); // the borderpane that tabPane and panZoomPane1 will be put into
	private Scene scene;
	double x, y;
	private ArrayList<Plant> favorites = new ArrayList<>();
	ScrollBar scrollBar1 = new ScrollBar();
	VBox plantHoverBox = new VBox();
	HBox scrollBox = new HBox();
	VBox hoverBox = new VBox();

	
	/**
	 * getFlowerList, getter for the flowerList attribute.
	 * 
	 * @return flowerList ArrayList of type Plant, the list to be returned
	 */
	public HashMap<String,Plant> getFlowerList() {
		return this.flowerList; // I believe we want to change this to return list of allPlants?
	}

	
	/**
	 * The custom constructor for AddPreexistingView. Sets the controller.
	 * 
	 * @param stage the stage for this scene
	 * @param cont the Controller that this view will be interacting with
	 */
	public AddPreexistingView(Stage stage, Controller cont) {	
		controller = cont;
		super.setController(cont);
	}
	

	/**
	 * getScene, This is where the display garden scene gets put together. The plants 
	 *           from the lists are listed in the side bar the title bar gets added, etc.
	 * 
	 * @return scene the finished and ready scene to display DesignGardenScene
	 */
	@Override
	public Scene getScene() {
		super.reorderPlants(tabPane, favorites, SceneName.ADDPREEXISTINGVIEW);
		panZoomPane1.setStyle("-fx-background-color: #33FF99"); // so that it's easier to view them
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

        panZoomPane1.setOnMousePressed(event -> controller.setPaneDrag(event, SceneName.ADDPREEXISTINGVIEW, panZoomPane1));
        panZoomPane1.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> controller.dragPane(event, SceneName.ADDPREEXISTINGVIEW, panZoomPane1));
   
		borderPane1.setCenter(panZoomPane1);
		tabPane.setSide(Side.RIGHT);
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		borderPane1.setLeft(tabPane);
		borderPane1.setRight(hoverBox);
		
		FlowPane topBar = getTitleBar("Add Plants Already in Your Garden!", controller);
		Button configsIcon = super.getConfigsIcon();
		Button plantBankButton = new Button("Go to Plant Bank");
		plantBankButton.setOnMouseClicked(e -> controller.handleMousePress( SceneName.PLANTCATALOGVIEW));
		Button designButton = new Button("Design your new garden");
		designButton.setOnMouseClicked(e -> controller.changeAndDisplay(e, SceneName.DESIGNGARDENVIEW));

		topBar.getChildren().add(configsIcon);
		topBar.getChildren().add(plantBankButton);
		topBar.getChildren().add(designButton);
		borderPane1.setTop(topBar);
			
		scene = new Scene(borderPane1, WIDTH,HEIGHT);
		scene.getStylesheets().add("/images/DesignGardenStyles.css");
		return scene;
	}
	
	

	
	
	/**
	 * displayGardenShape, places the garden outline from the drawGardenView in this view.
	 */
	public void displayGardenShape() {
		for (Shape s : controller.getShapes()) {
			if (!panZoomPane1.getChildren().contains(s)) {
				Paint tmp = s.getFill();
				String color;
				if(tmp.equals(Color.GREEN)) {
					color = "green";
				}
				else color = "black";
				if(s instanceof Rectangle) {
					model.gShape.add(new GardenShapeSave(((Rectangle) s).getWidth(),((Rectangle) s).getHeight(),
							"rect",color,s.getTranslateX(),s.getTranslateY(),0));
				}
				if(s instanceof Circle) 
					model.gShape.add(new GardenShapeSave(0,0,"circ",color,s.getTranslateX(),s.getTranslateY(),((Circle) s).getRadius()));
				panZoomPane1.getChildren().add(s);
				s.setDisable(true);
			}
		}
	}
	

	/**
	 * clearGarden, removes all children from panZoomPane1 (the center PanAndZoomPane).
	 */
	public void clearGarden() {
		panZoomPane1.getChildren().clear();
	}
	
	
	/**
	 * makeIcon, This will get the icon from the plant(it'll most likely be a circle representing
	 *           the plant) and will render it in the view so it can be dragged. 
	 *  
	 * @param p the Plant whose icon we're displaying
	 * @param x the x location of where the icon will first be placed on the screen
	 * @param y the y location of where the icon will first be placed on the screen
	 */
	public void makeIcon(Plant p, double x, double y) {
		PlantIcon pi = super.makePlantIcon(p, x, y, SceneName.ADDPREEXISTINGVIEW);
		controller.addPrevPlant(pi);
		panZoomPane1.getChildren().add(pi.getCircle());
	}
	
	/**
	 * removeIcon, removes a plantIcon from the garden.
	 * 
	 * @param pi the plantIcon to be removed
	 */
	public void removeIcon(PlantIcon pi) {
		panZoomPane1.getChildren().remove(pi.getCircle());
		controller.removePrevPlant(pi);
	}
	
	
	/**
	 * setFavorites, sets the favorites list tab. 
	 * 
	 * @param fav the list of favorite plants
	 */
	public void setFavorites(ArrayList<Plant> fav) {
		this.favorites = fav;
		tabPane.getTabs().remove(5); // index of favorites list
		ScrollPane spane = super.makePlantList(fav, SceneName.ADDPREEXISTINGVIEW);
		Tab tab1 = new Tab("Favorites", spane);
		tabPane.getTabs().add(5,tab1);
	}
	
	
	/**
	 * setPane, This method allows the user to pan across the pan and zoom pane using 
	 *          the right click.
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
	 * makeHover, When the user hovers their mouse over a plantIcon, information on
	 *            the plant will popup on the right.
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
	 * reorderPlants, calls reorder plants from the superclass.
	 */
	public void reorderPlants() {
		super.reorderPlants(tabPane, favorites, SceneName.ADDPREEXISTINGVIEW);
	}
}


