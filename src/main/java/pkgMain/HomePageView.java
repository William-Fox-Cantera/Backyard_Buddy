package pkgMain;
import javafx.scene.image.Image;

import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Class - HomePageView, sets up the scene for the home page
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria Venrooy
 */
public class HomePageView extends View {
	
	private static final double MAIN_TEXT_X = WIDTH/4;
	private static final double MAIN_TEXT_Y= HEIGHT/5;
	private static final double FONT_SIZE_30 = 60;
	private static final double BACKGROUND_SIZE_WIDTH = WIDTH;
	private static final double BACKGROUND_SIZE_HEIGHT = HEIGHT;
	private static final double DRAW_WIDTH = WIDTH/1.5;
	private static final double DRAW_HEIGHT = HEIGHT/9;
	private static final double DRAW_X = WIDTH/6;
	private static final double PRE_Y = HEIGHT/5;
	private static final double DRAW_Y = HEIGHT/4 + 100;
	private static final double PLANT_Y = HEIGHT/4 + 200;
	private static final double SAVED_Y = HEIGHT/4 + 300;


	Controller controller;	
	
	/**
	 * HomPageView, constructor.
	 * 
	 * @param stage the stage for this scene
	 * @param cont an instance of the controller
	 */
	public HomePageView(Stage stage, Controller cont) {
		controller = cont;
	}

	
	/**
	 * getScene, getter for the scene. Sets up the panes as well.
	 * 
	 * @return the scene
	 */
	public Scene getScene() {
		Text t = new Text (MAIN_TEXT_X, MAIN_TEXT_Y, "Backyard Buddy");
		t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, FONT_SIZE_30));
	    t.setStyle("-fx-highlight-fill: lightgray");

		t.setFill(Color.WHITE);
		
		Image image = new Image("images/Home-Page.jpg", BACKGROUND_SIZE_WIDTH, BACKGROUND_SIZE_HEIGHT,false, false);
		ImageView mv = new ImageView(image);
		
		Group root = new Group();
		root.getChildren().addAll(mv);
		
		// adding this to check that i can view the draw garden page; feel free to change
		Button draw = new Button("New Garden");
		draw.setPrefWidth(DRAW_WIDTH);
		draw.setPrefHeight(DRAW_HEIGHT);
		draw.setLayoutX(DRAW_X);
		draw.setLayoutY(DRAW_Y);
		
		root.getChildren().add(draw);
		draw.setOnMousePressed(e -> controller.clearAndDisplay(SceneName.CONFIGURATIONSVIEW));
		
		Button pre = new Button("Pre-Survey");
		pre.setPrefWidth(DRAW_WIDTH);
		pre.setPrefHeight(DRAW_HEIGHT);
		pre.setLayoutX(DRAW_X);
		pre.setLayoutY(PRE_Y);
		pre.setOnMousePressed(e -> controller.handleMousePress( SceneName.PRESURVEYVIEW));
		
		Button plant = new Button("Plant Bank");
		plant.setPrefWidth(DRAW_WIDTH);
		plant.setPrefHeight(DRAW_HEIGHT);
		plant.setLayoutX(DRAW_X);
		plant.setLayoutY(PLANT_Y);
		root.getChildren().add(plant);
		plant.setOnMousePressed(e -> controller.handleMousePress( SceneName.PLANTCATALOGVIEW));
		
		Button saved = new Button("View Saved");
		saved.setPrefWidth(DRAW_WIDTH);
		saved.setPrefHeight(DRAW_HEIGHT);
		saved.setLayoutX(DRAW_X);
		saved.setLayoutY(SAVED_Y);
		root.getChildren().add(saved);
		root.getChildren().add(t); //Adds title
		saved.setOnMousePressed(e -> {
			controller.handleMousePress( SceneName.PREVIOUSGARDENSVIEW /*,s*/);
		});

		Scene scene = new Scene(root, BACKGROUND_SIZE_WIDTH, BACKGROUND_SIZE_HEIGHT);
		return scene;
	}

}
