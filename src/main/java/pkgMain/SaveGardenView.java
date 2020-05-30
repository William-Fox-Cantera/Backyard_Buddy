package pkgMain;


import java.util.HashSet;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class - SaveGardenView, this class sets up the scene for saving a garden.
 * 						   Displays the garden score, a box for entering a
 * 						   filename, and a button to save as well as suggestions
 * 						   for how to improve the garden.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria van Venrooy
 */
@SuppressWarnings("static-access")
public class SaveGardenView extends View {
	// Constants
	private static final double TOP_STACK_HEIGHT = 150;
	private static final double HOME_BUTTON_SIZE = 50;
	private static final double HOME_BUTTON_Y_INCR = 20;
	private static final double SCENE_TEXT_Y_INCR = 20;
	private static final double SAVE_MSG_Y_INCR = 100;
	private static final int IMPROVEMENTS_Y_INCR = 100;
	private static final double GARDEN_ENTRY_WIDTH = 400;
	private static final int SUGGESTION_HEIGHT = 225;
	private static final int SUGGESTION_WIDTH = 500;
	private static final int GARDEN_ENTRY_Y_INCR = 50;
	private static final double GARDEN_NAME_SIZE = 3;
	private static final int SUGGESTION_BOX_Y_INCR = 50;
	
	private Controller controller;
	private Text fileSaveMsg;
	private Text ratingText = new Text();
	private VBox root = new VBox();
	private Text improvementsText = new Text();
	private StackPane centerStack = new StackPane();
	private Text saveGardenText = new Text("Save Garden");
	private TextField nameEntryBox = new TextField("Enter Garden Name Here");
	private Scene scene;
	private Button saveButton = new Button("Save This Garden");;
	private ImageView homeView = new ImageView(new Image("images/homeButton.png"));
	private ScrollPane scroll = new ScrollPane();
	private StackPane topStack = new StackPane();
	private VBox vb1 = new VBox();
	private double initialWidth = 1000;
	private double initialHeight = 600;
	
	
	public SaveGardenView(Stage stage, Controller cont) {
		controller = cont;
		topStack.getChildren().addAll(homeView, saveGardenText);
		centerStack.getChildren().add(ratingText);
	    scroll.setContent(vb1);
	    scroll.setMaxSize(SUGGESTION_WIDTH, SUGGESTION_HEIGHT);
		centerStack.getChildren().addAll(scroll, nameEntryBox, saveButton);
		root.getChildren().addAll(topStack, centerStack);		
	    scene = new Scene(root, initialWidth, initialHeight);
	    // Scene setup
	    styleManager();
	    configureButtons();
	    configurePanes();
	}
	
	
	/**
	 * getScene, getter for the scene attribute.
	 * 
	 * @return scene 
	 */
	public Scene getScene() {
		return this.scene;
	}
	
	
	/**
	 * getGardenName, getter for the garden name text box
	 * 
	 * @return nameEntryBox
	 */
	public TextField getGardenName() {
		return this.nameEntryBox;
	}
	
	
	/**
	 * getSaveButton, getter for the save button attribute.
	 * 
	 * @return saveButton
	 */
	public Button getSaveButton() {
		return this.saveButton;
	}
	
	
	/**
	 * getInvalidFileMsg, getter for the file message.
	 * 
	 * @return fileSaveMessage
	 */
	public Text getInvalidFileMsg() {
		return this.fileSaveMsg;
	}
	
	
	/**
	 * getStack, getter for the stack pane attribute
	 * 
	 * @return grid
	 */
	public StackPane getStack() {
		return this.centerStack;
	}
	
	
	/**
	 * configurePanes, defines where the panes and buttons should be positioned in the screen.
	 */
	public void configurePanes() {
		scroll.setTranslateY(-SUGGESTION_BOX_Y_INCR);
		topStack.setPrefHeight(TOP_STACK_HEIGHT);
	    topStack.setAlignment(saveGardenText, Pos.CENTER);
	    topStack.setAlignment(homeView, Pos.CENTER_LEFT);
	    
	    homeView.setTranslateY(HOME_BUTTON_Y_INCR);
	    homeView.setFitHeight(HOME_BUTTON_SIZE);
		homeView.setFitWidth(HOME_BUTTON_SIZE); 
	    root.setVgrow(centerStack, Priority.ALWAYS);
	    
	    centerStack.setAlignment(ratingText, Pos.TOP_CENTER);
	    centerStack.setAlignment(saveButton, Pos.BOTTOM_CENTER);
	    centerStack.setAlignment(nameEntryBox, Pos.BOTTOM_CENTER);
	    centerStack.setAlignment(improvementsText, Pos.CENTER);
	    improvementsText.setTranslateY(-IMPROVEMENTS_Y_INCR);
	    nameEntryBox.setTranslateY(-GARDEN_ENTRY_Y_INCR);
	    
	    nameEntryBox.setMaxWidth(GARDEN_ENTRY_WIDTH);
		saveGardenText.setScaleX(GARDEN_NAME_SIZE);
	    saveGardenText.setScaleY(GARDEN_NAME_SIZE);
	    saveGardenText.setTranslateY(SCENE_TEXT_Y_INCR);	
	}
	
	
	/**
	 * configureButtons, sets the actions of the buttons. 
	 */
	public void configureButtons() {
		homeView.setOnMouseClicked(e -> controller.handleMousePress( SceneName.HOMEPAGEVIEW)); 
		nameEntryBox.setOnMouseClicked(e -> nameEntryBox.clear());
	    saveButton.setOnMouseClicked(e -> controller.saveGardenEvent(e));
	    saveButton.setOnMousePressed(e -> controller.changeButtonColor(e));
	    saveButton.setOnMouseReleased(e -> controller.changeButtonColor(e));
	}
	
	
	/**
	 * styleManager, styles certain elements on screen with different colors/font sizes. 
	 */
	public void styleManager() {
		improvementsText.setStyle("-fx-fill: black; -fx-font-size: 2.5em;");
		nameEntryBox.setStyle("-fx-font-size: 2.5em;");
	    centerStack.setStyle("-fx-background-color: #0000dd");
	    saveButton.setStyle("-fx-background-color: #00ff00; -fx-font-size: 2em;");
	    ratingText.setStyle("-fx-fill: white; -fx-font-size: 3em;");
	}
	
	
	/**
	 * displayInvalidFileMsg, puts a message onscreen letting the user know they haven't
	 * 						  entered a valid filename for their project when saving. If
	 * 						  the file saved correctly it will display a message that it did.
	 * 
	 * @param isValid true if the filename is valid, false otherwise
	 */
	public void displayFileMsg(boolean isValid) {
		if (fileSaveMsg != null)
			fileSaveMsg.setText(""); // Reset 
		String msg = "";
		if (!isValid) 
			msg = "Please enter a valid filename";
		else
			msg = "Saved Successfully!";
		fileSaveMsg = new Text(msg);
		fileSaveMsg.setStyle("-fx-fill: white; -fx-font-size: 2em;");
		fileSaveMsg.setTranslateY(fileSaveMsg.getY()-SAVE_MSG_Y_INCR);
		centerStack.getChildren().add(fileSaveMsg);
		centerStack.setAlignment(fileSaveMsg, Pos.BOTTOM_CENTER);
	}
	
	
	/**
	 * addRating, adds a rating to display for the overall garden quality. 
	 */
	public void addRating() {
	    HashSet<String> suggestions = controller.getSuggestions();
	    ratingText.setText("Garden Compatibility Rating: " + controller.getGardenRating() + "/10");
	    for (String s : suggestions) {
	    	Text text1 = new Text(s);
	    	vb1.getChildren().add(text1);
	    }
	}
	
	
	/**
	 * clearSuggestions, clears the suggestions window.
	 */
	public void clearSuggestions() {
		vb1.getChildren().clear();
	}
} 