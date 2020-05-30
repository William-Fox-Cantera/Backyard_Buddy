package pkgMain;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
/**
 * Class - AddPlantsView, this class contains methods that extend to other view scenes 
 * 						  to display things on screen like icons and plant descriptions. 
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria van Venrooy
 */
@SuppressWarnings("static-access")
public class AddPlantsView extends View {
	Controller controller;
	int HOVER_WIDTH = 200;
	int HOVER_HEIGHT = 400;
	int BAR_WIDTH = 15;
	Insets hoverPadding = new Insets(10);
	final int FONT_SIZE_MAIN = 12;
	final int FONT_SIZE_TITLE= 20;
	final int PLANT_IMAGE_WIDTH = 85;
	final int PLANT_NODE_WIDTH = 200;
	
	
	/**
	 * setController, setter for the controller instance
	 * 
	 * @param cont the controller instance
	 */
	public void setController(Controller cont) {
		this.controller = cont;
	}
	
	
	/**
	 * makePlantList, makes the panes for the plant lists and sets the content/policies.
	 * 
	 * @param plants a list of plants
	 * @param sceneName the name of the scene
	 * @return the new scroll pane
	 */
	public ScrollPane makePlantList(ArrayList<Plant> plants, SceneName sceneName) {
		TilePane tpane = new TilePane();
		ScrollPane spane = new ScrollPane();
		spane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
		tpane.setPrefColumns(1);
		tpane.setPrefTileWidth(PLANT_NODE_WIDTH);
		for(Plant p : plants) 
		    tpane.getChildren().add(makePlantNode(p, sceneName));
		spane.setContent(tpane);
		return spane;
	}
	
	
	/**
	 * makeHowTo, adds tutorial text to the how to node. 
	 * 
	 * @return the new tutorial pane.
	 */
	public ScrollPane makeHowTo() {
		TilePane instructions = new TilePane();
		ScrollPane spane = new ScrollPane();
		spane.hbarPolicyProperty().setValue(ScrollPane.ScrollBarPolicy.NEVER);
		instructions.setPrefColumns(1);
		instructions.setPrefTileWidth(PLANT_NODE_WIDTH);
		instructions.getChildren().add(makeHowToNode("Add a Plant to your Garden", "Left click on a plant from the side bar on the left to add it to your garden"));
		instructions.getChildren().add(makeHowToNode("Move a Plant Around", "Left-click on the plant in the garden and hold down the left mouse button while dragging it around to move the plant"));
		instructions.getChildren().add(makeHowToNode("Delete a Plant", "Right click on the plant once it's in your garden to remove it from the garden"));
		instructions.getChildren().add(makeHowToNode("Zoom in on Your Garden", "Use the scrollbar on the bottom right side of the screen to zoom in and out"));
		instructions.getChildren().add(makeHowToNode("Pan Around the Garden", "Right click anywhere in the garden and drag it around while holding down the right mouse button"));
		instructions.getChildren().add(makeHowToNode("Add Plants to Your Favorites", "Click on the 'Plant Bank' button at the top of the screen to view all of the plants and add them to your favorites tab"));
		instructions.getChildren().add(makeHowToNode("Change Configurations", "Click on the 'gear' icon at the top of the screen to change the configurations that were established during the pre-survey"));
		spane.setContent(instructions);
		return spane;
	}
	
	
	/**
	 * makeHowToNode, makes a box for displaying tutorial information.
	 * 
	 * @param title the name of the box
	 * @param tip the information 
	 * @return the new VBox
	 */
	public VBox makeHowToNode(String title, String tip) {
		VBox instructionPane = new VBox();
		Text heading = new Text(title);
		heading.getStyleClass().add("title");
		Text tipText = new Text(tip);
		heading.setWrappingWidth(PLANT_NODE_WIDTH - 20);
		tipText.setWrappingWidth(PLANT_NODE_WIDTH - 20);
		tipText.setTextAlignment(TextAlignment.LEFT);
		heading.setTextAlignment(TextAlignment.CENTER);
		instructionPane.getChildren().addAll(heading,tipText);
		instructionPane.getStyleClass().add("plant");
		return instructionPane;
	}
	
	
	/**
	 * makePlantNode, This will return a BorderPane that displays the plant's name,
	 * 				  description, and plantPic. These nodes will be used in the 
	 *                plant bank and on the DesignGardenView, and the user should be 
	 *                able to drag their corresponding PlantIcons from them.
	 * 
	 * @param p The Plant whose attributes will be used to make the BorderPane
	 * @param sceneName the name of the scene
	 * @return a BorderPane, which can then be placed as a node elsewhere
	 */
	public BorderPane makePlantNode(Plant p, SceneName sceneName) {
		BorderPane bp = new BorderPane();
		bp.getStyleClass().add("plant");
		ImageView photo = new ImageView(p.getPhoto());
		photo.setFitHeight(PLANT_IMAGE_WIDTH);
		photo.setFitWidth(PLANT_IMAGE_WIDTH);
		Text description = new Text(p.getDescription());
		description.getStyleClass().add("description");
		description.setWrappingWidth(PLANT_IMAGE_WIDTH);
		description.setTextAlignment(TextAlignment.CENTER);
		Text name = new Text(p.getName());
		name.getStyleClass().add("title");
		name.setWrappingWidth(PLANT_NODE_WIDTH);
		name.setTextAlignment(TextAlignment.CENTER);
		bp.setLeft(photo);
		bp.setTop(name);
		bp.setCenter(description);
		bp.setAlignment(photo, Pos.CENTER);
		bp.setAlignment(description, Pos.CENTER);
		bp.setOnMousePressed(event -> controller.onPlantPress(event, p, sceneName));
		bp.setOnMouseEntered(event -> controller.onHover(p, sceneName));
		return bp;
	}
	
	
	/**
	 * makePlantIcon, makes a circular image with a size relative to the size of a plant. The 
	 * 				  circle has an image of a plant attached to it so plants in the app are
	 * 				  displayed as circles of various sizes. 
	 * 
	 * @param p the type of plant
	 * @param x the x position
	 * @param y the y position 
	 * @param sceneName the name of the scene
	 * @return the new plant icon 
	 */
	public PlantIcon makePlantIcon(Plant p, double x, double y, SceneName sceneName) {
		double scale = controller.getScaleFactor();
		double radius = p.getWidth()/2; // radius of plant in inches
		Circle circle = new Circle(radius / scale);
		Image background = p.getPhoto();
		ImagePattern backgroundPattern = new ImagePattern(background);
		circle.setFill(backgroundPattern);
		PlantIcon pi = new PlantIcon(circle, x, y, p);
		pi.getCircle().setOnMouseDragged(event -> controller.dragPlantIcon(event, pi, sceneName));
		pi.getCircle().setOnMouseEntered(event -> controller.onHover(p, sceneName));
		pi.getCircle().setOnMouseClicked(event -> controller.onRightClick(event, pi, sceneName));
		pi.getCircle().setTranslateX(x);
		pi.getCircle().setTranslateY(y);
		return pi;
	}
	
	
	/**
	 * makeHoverPane, makes a Vbox that will pop up when a plant is hovered over. The box contains
	 * 				  information about the plant that is hovered over like an image and a description.
	 * 
	 * @param p the plant hovered over
	 * @return the box to display on screen
	 */
	public VBox makeHoverPane(Plant p) {
		VBox vbox1 = new VBox();
		vbox1.setAlignment(Pos.TOP_CENTER);
		ImageView iv = new ImageView(p.getPhoto());
		iv.setPreserveRatio(true);
		iv.setFitHeight(HOVER_WIDTH);
		iv.setFitWidth(HOVER_WIDTH);
		TextFlow descrip = new TextFlow(new Text(p.getDescription()));
		TextFlow stats = new TextFlow(new Text("Flower Color: " + p.getFlowerColor() +"\nHeight: " + p.getHeight() +"\nSoil Type: "+ p.getSoil()
				+ "\nWater Preference: " + p.getWaterAsString() + "\nBegins to Bloom: " + p.getBloomSeason()));
		descrip.setMaxWidth(HOVER_WIDTH- BAR_WIDTH);
		stats.setMaxWidth(HOVER_WIDTH - BAR_WIDTH);
		vbox1.setStyle("-fx-background-color: #33FF99");
		Text name = new Text(p.getName());
		name.setFont(Font.font("Calibri", FontPosture.REGULAR, FONT_SIZE_TITLE));
		name.getStyleClass().add("hoverName");
		name.setWrappingWidth(HOVER_WIDTH);
		name.setTextAlignment(TextAlignment.CENTER);
		vbox1.getChildren().add(name);
		Text latinName = new Text(p.getLatinName());
		latinName.setFont(Font.font("Calibri", FontPosture.ITALIC, FONT_SIZE_MAIN));
		latinName.getStyleClass().add("latinName");
		vbox1.getChildren().add(latinName);
		vbox1.getChildren().add(iv);
		vbox1.getChildren().addAll(descrip, stats);
		vbox1.setPrefWidth(HOVER_WIDTH);
		vbox1.setFillWidth(true);
		vbox1.setMaxHeight(HOVER_HEIGHT);
		vbox1.setMinHeight(HOVER_HEIGHT);
		return vbox1;
	}
	
	
	/**
	 * getScrollBar, makes a ScrollBar and returns it.
	 * 
	 * @return sb1 the new ScrollBar
	 */
	public ScrollBar getScrollBar() {
		ScrollBar sb1 = new ScrollBar();
		sb1.setMin(0);
		sb1.setMax(10);
		sb1.setValue(5);
		sb1.setOrientation(Orientation.HORIZONTAL);
		return sb1;
	}
	
	
	/**
	 * getConfigsIcon, makes an icon that takes the user to the configurations view when clicked.
	 * 				   Returns this new button. 
	 * 
	 * @return the new button 
	 */
	public Button getConfigsIcon() {
		Button button1 = new Button();
		ImageView configsIcon = new ImageView(new Image(getClass().getResourceAsStream("/images/gear.png")));
		button1.setOnMouseClicked(e -> controller.handleMousePress(SceneName.CONFIGURATIONSVIEW));
		configsIcon.setFitHeight(40);
		configsIcon.setFitWidth(40);
		button1.setGraphic(configsIcon);
		return button1;
	}
	
	
	/**
	 * getScrollBox, make a box for scrolling. The scrolling event zooms in on the garden in 
	 * 				 the design scene. 
	 * 
	 * @param sb1 therelevant scrollbar
	 * @return the new scrollbox
	 */
	public HBox getScrollBox(ScrollBar sb1) {
		HBox scrollBox = new HBox();
        Text plusIcon = new Text(" +");
        plusIcon.getStyleClass().add("scrollIcon");
        plusIcon.setTextAlignment(TextAlignment.CENTER);
        Text minusIcon = new Text("-  ");
        minusIcon.getStyleClass().add("scrollIcon");
        scrollBox.getChildren().addAll(minusIcon, sb1, plusIcon);
        scrollBox.setAlignment(Pos.BOTTOM_CENTER);
        scrollBox.setPadding(hoverPadding);
        scrollBox.setPrefWidth(HOVER_WIDTH);
        return scrollBox;
	}
	
	
	/**
	 * reorderPlants, re-orders the plants based on how they should be sorted from the 
	 * 				  configurations entered by the user.
	 * 
	 * @param tabPane the tab pane
	 * @param favorites the list of favorite plants
	 * @param name name of the scene
	 */
	public void reorderPlants(TabPane tabPane, ArrayList<Plant> favorites, SceneName name) {
		tabPane.getTabs().clear();
		displayPlantList(controller.getOrderedPlantList(flowerList), "Flowers", tabPane, name);
		displayPlantList(controller.getOrderedPlantList(treeList), "Trees", tabPane, name);
		displayPlantList(controller.getOrderedPlantList(shrubList), "Shrubs", tabPane, name);
		displayPlantList(controller.getOrderedPlantList(grassList), "Grasses", tabPane, name);
		displayPlantList(controller.getOrderedPlantList(vineList), "Vines", tabPane, name);
		displayPlantList(favorites, "Favorites", tabPane, name);
		displayHowTo(tabPane);
	}
	
	
	/**
	 * displayPlantList, Calls Plant.getPlantNode() to get nodes for each plant
	 *                   in the list, and then will add them as content to a new
	 *                   tab in the plant bank.
	 * 
	 * @param plants an ArrayList of Plant objects to be put in the side bar
	 * @param tabTitle a String representing the title to be put on the tab 
	 * @param tabPane a pane containing tabs
	 * @param name the name of the group
	 */
	public void displayPlantList(ArrayList<Plant> plants, String tabTitle, TabPane tabPane, SceneName name) {
		ScrollPane spane = makePlantList(plants, name);
		Tab tab1 = new Tab(tabTitle, spane);
		tabPane.getTabs().add(tab1);
	}
	
	
	/**
	 * displayHowTo, adds the tab containing the tutorial information to the tutorial tab.
	 *@param tabPane the tabPane
	 */
	public void displayHowTo(TabPane tabPane) {
		ScrollPane spane = makeHowTo();
		Tab tab1 = new Tab("How-To", spane);
		tabPane.getTabs().add(tab1);
	}
	
	/**
	 * setPlantIconLocation, This is passed a new x and y from the controller and
	 *                       thereby changes the x and y location of a specific plant icon.
	 * 
	 * @param pi PlantIcon, the PlantIcon whose location has been changed
	 * @param x double, the new x location of the PlantIcon
	 * @param y double, the new y location of the PlantIcon
	 */
	public void setPlantIconLocation(PlantIcon pi, double x, double y) {
		pi.getCircle().setCenterX(x);
		pi.getCircle().setCenterY(y);
	}
}
