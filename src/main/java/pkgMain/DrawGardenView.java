package pkgMain;

import java.util.ArrayList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;


/**
 * Class - DrawGardenView, this class sets up the scene for building the initial garden.
 * 						   The user can choose to add squares and circles of a size 
 * 					 	   determined by the users length entered. There are two circles
 * 						   A and B which the user places to indicate the length of their
 * 						   garden which is also used to calculate the scale of the garden.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria van Venrooy
 * 
 * @version 0.0.1
 */
public class DrawGardenView extends View {
	// Constants 
	private static final double SHAPE_X_OFFSET = 10;
	private static final double SHAPE_Y_OFFSET = 50;
	private static final double SCALET_FONT_SIZE = 20;
	private static final double CIRCLE_RADIUS = 15;
	private static final double CIRCLE_RADIUS2 = 10;
	private static final double RECTANGLE_WIDTH = 48;
	private static final double RECTANGLE_HEIGHT = 30;
	
	Controller controller;
	FlowPane titleBar; 
	GridPane gp1 = new GridPane();
	TilePane tiles = new TilePane(Orientation.HORIZONTAL);
	BorderPane bp1 = new BorderPane();
	ArrayList<ImageView> shapes;
	Point pointA = new Point("A", 30, 30, 30, 20,new Image(getClass().getResourceAsStream("/images/A.png")));
	Point pointB = new Point("B", 30, 30, 120, 20,new Image(getClass().getResourceAsStream("/images/B.png")));
	WritableImage gardenPic;
	double rWid = 12;
	double rLen = 10;
	TextField recLen = new TextField("10");
	TextField recWid = new TextField("12");
	TextField cirDiam = new TextField("10");
	double cRad = 10;
	Rectangle r = new Rectangle();
	Circle circle = new Circle();
	Rectangle r2 = new Rectangle();
	Circle circle2 = new Circle();
	TextField abLen;
	@SuppressWarnings("serial")
	ArrayList<Point> points = new ArrayList<Point>() {{add(pointB); add(pointA);}}; 
	private Scene scene;
	
	
	/**
	 * DrawGardenView, The custom constructor for the DrawGardenView. This 
	 *                 takes in a garden and displays it on the right side of the view
	 *                 for the user to drag plants into.
	 *                 
	 * @param stage the stage of this scene
	 * @param con an instance of the controller
	 */
	public DrawGardenView(Stage stage, Controller con) {
		controller = con;
		titleBar = getTitleBar("Draw Garden", controller);
	}
	
	
	/**
	 * getScene, getter for the scene. Sets up a border-pane and grid-pane.
	 * 
	 * @return scene the scene
	 */
	public Scene getScene() {
		tiles.setTileAlignment(Pos.CENTER_LEFT);
		addPoints(points);
		gp1.setStyle("-fx-background-color: #33FF99"); // so that it's easier to view them
		displayShapesAndSidebar();
		bp1.setTop(titleBar);
		bp1.setCenter(gp1);
		bp1.setLeft(tiles);
		scene = new Scene(bp1, WIDTH,HEIGHT);
		return scene;
	}
	
	
	/**
	 * addPoints, sets the size and location of the points A and B in the scene.
	 * 
	 * @param points the list of points
	 */
	public void addPoints(ArrayList<Point> points) {
		for (Point p : points) {
			ImageView piv = new ImageView();
			piv.setImage(p.getImage());
			piv.setFitHeight(p.getHeight());
			piv.setFitWidth(p.getWidth());
			piv.setTranslateX(p.getX());
			piv.setTranslateY(p.getY());
			piv.setOnMouseDragged(event -> controller.onPointDrag(event, p, piv));
			gp1.getChildren().add(piv);
		}
	}
	
	
	/**
	 * displayGardenShape, places the garden outline from the drawGardenView in this view.
	 * 
	 * @param shapes the ArrayList of shapes to be placed in the garden
	 */
	public void displayGardenShape(ArrayList<Shape> shapes) {
		for (Shape s : shapes) {
			if (!gp1.getChildren().contains(s)) {
				gp1.getChildren().add(s);
				s.setDisable(false);
			}
		}
	}
	
	
	/**
	 * displayShapesAndSidebar, displays all of the input boxes on the screen and places
	 * 							the initial shapes on screen and sets their behaviour for
	 * 							interaction from user input.
	 */
	public void displayShapesAndSidebar() {
		Text addShapesT = new Text(SHAPE_X_OFFSET, SHAPE_Y_OFFSET, "Add Shapes");
		addShapesT.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
		addShapesT.setFill(Color.BLUE);
		tiles.getChildren().add(addShapesT);
		Text blank = new Text(SHAPE_X_OFFSET, SHAPE_Y_OFFSET,"");
		tiles.getChildren().add(blank);
		Text shapeDir = new Text(SHAPE_X_OFFSET, SHAPE_Y_OFFSET, "Click a shape to add it");
		tiles.getChildren().add(shapeDir);
		Text shapeDir2 = new Text(SHAPE_X_OFFSET, SHAPE_Y_OFFSET, "Update Dimensions Below");
		tiles.getChildren().add(shapeDir2);
		Text gardenShapesT = new Text(20, 50, "Garden Shapes");
		gardenShapesT.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		gardenShapesT.setFill(Color.GREEN);
		tiles.getChildren().add(gardenShapesT);
		Text blankA = new Text(RECTANGLE_WIDTH, RECTANGLE_HEIGHT,"");
		tiles.getChildren().add(blankA);
		r.setWidth(RECTANGLE_WIDTH);
		r.setHeight(RECTANGLE_HEIGHT);
		r.setFill(Color.GREEN);
		r.setOnMousePressed(controller.getShapeHandler("newRect", r, rWid, rLen));
		tiles.getChildren().add(r);
		
		circle.setRadius(CIRCLE_RADIUS);
		circle.setFill(Color.GREEN);
		circle.setOnMousePressed(controller.getShapeHandler("newCirc", circle, cRad, 0));
		
		tiles.getChildren().add(circle);
			
		Text obsShapesT = new Text(20, 50, "Obstructions");
		obsShapesT.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
		obsShapesT.setFill(Color.BLACK);
		tiles.getChildren().add(obsShapesT);
		
		Text blankB = new Text(10,50,"");
		tiles.getChildren().add(blankB);
		
		r2.setWidth(RECTANGLE_WIDTH);
		r2.setHeight(RECTANGLE_HEIGHT);
		r2.setFill(Color.BLACK);
		r2.setOnMousePressed(controller.getShapeHandler("newRect", r2, rWid, rLen));
	
		tiles.getChildren().add(r2);
		
		circle2.setRadius(CIRCLE_RADIUS);
		circle2.setFill(Color.BLACK);
		circle2.setOnMousePressed(controller.getShapeHandler("newCirc", circle2, cRad, 0));
		
		tiles.getChildren().add(circle2);
			
		Text rectT = new Text(10, 50, "Enter rectangle length:");
		tiles.getChildren().add(rectT);
		tiles.getChildren().add(recLen);
		Text rectT2 = new Text(SHAPE_X_OFFSET, SHAPE_Y_OFFSET, "Enter rectangle width:");
		tiles.getChildren().add(rectT2);
		tiles.getChildren().add(recWid);
		Text t2 = new Text(SHAPE_X_OFFSET, SHAPE_Y_OFFSET, "Enter circle Diameter:");
		tiles.getChildren().add(t2);
		tiles.getChildren().add(cirDiam);
		Text t3 = new Text(SHAPE_X_OFFSET, SHAPE_Y_OFFSET, "Click to update:");
		tiles.getChildren().add(t3);
		Button update = new Button("Refresh");
		tiles.getChildren().add(update);
		update.setOnMouseClicked(e -> updateDimensions());
		
		//SCALE GARDEN SECTION
		Text scaleT = new Text(20, 50, "Scale Garden");
		scaleT.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, SCALET_FONT_SIZE));
		scaleT.setFill(Color.BLUE);
		tiles.getChildren().add(scaleT);
		Text blank2 = new Text(SHAPE_X_OFFSET, SHAPE_Y_OFFSET,"");
		tiles.getChildren().add(blank2);
		Text abInst = new Text(SHAPE_X_OFFSET, SHAPE_Y_OFFSET,"Drag points A and B to a known ");
		tiles.getChildren().add(abInst);
		Text abInst2 = new Text(SHAPE_X_OFFSET, SHAPE_Y_OFFSET,"length of your garden");
		tiles.getChildren().add(abInst2);
		Text lenText = new Text(SHAPE_X_OFFSET, SHAPE_Y_OFFSET, "Length in ft from A to B  ");
		TextField abLen = new TextField("10");
		tiles.getChildren().add(lenText);
		tiles.getChildren().add(abLen);
		Button clear = new Button("Clear");
		titleBar.getChildren().add(clear);
		clear.setOnMouseClicked(e -> clearGarden());
		Button done = new Button("Add Plants Already in your Garden");
		titleBar.getChildren().add(done);
		done.setOnMouseClicked(e -> controller.onDoneButton(e, SceneName.ADDPREEXISTINGVIEW, pointA, pointB, abLen.getText()));
		
		}
	
	
	/**
	 * makeShape, makes a shape and sets its style and behaviour.
	 * 
	 * @param shape the name of the shape
	 * @param x the x location of the shape
	 * @param y the y location of the shape
	 * @param c the color of the shape
	 */
	public void makeShape(String shape, double x, double y, Paint c) {
		switch(shape) {
			case "rect":
				Rectangle r = new Rectangle();
				r.setWidth(CIRCLE_RADIUS2 * x);
				r.setHeight(CIRCLE_RADIUS2 * y);
				r.setFill(c);
				r.setOnMouseDragged(controller.getShapeHandler("dragRect", r, 0,0));
				r.setOnMouseClicked(controller.getShapeHandler("optRect", r,0,0));
				gp1.getChildren().add(r);
				controller.addShape(r);
				break;
			case "circ":
				Circle circle = new Circle();
				circle.setRadius(CIRCLE_RADIUS2 * x);
				circle.setFill(c);
				circle.setOnMouseDragged(controller.getShapeHandler("dragCirc", circle,0,0));
				gp1.getChildren().add(circle);
				controller.addShape(circle);
				break;
		}
	}
	
	
	/**
	 * clearScene, removes everything from the scene.
	 */
	public void clearScene() {
		this.gp1.getChildren().removeAll(this.gp1);
		controller.clearShape();
	}
	
	
	/**
	 * updateTF, updates the scene when a new shape is added.
	 */
	public void updateDimensions() {
		double rWid = Double.parseDouble(recWid.getText());
		double rLen = Double.parseDouble(recLen.getText());
		cRad = Double.parseDouble(cirDiam.getText());
		r.setOnMousePressed(controller.getShapeHandler("newRect", r, rWid, rLen));
		circle.setOnMousePressed(controller.getShapeHandler("newCirc", circle, cRad, 0));
		r2.setOnMousePressed(controller.getShapeHandler("newRect", r2, rWid, rLen));
		circle2.setOnMousePressed(controller.getShapeHandler("newCirc", circle2, cRad, 0));
	}
	
	
	/**
	 * clearGarden, removes objects from the scene.
	 */
	public void clearGarden() {
		gp1.getChildren().clear();
		addPoints(points);
		controller.clearGardenShapes();
	}
	
	
	/**
	 * setPointLocation, sets the x and y location of a point and also sets its translate 
	 * 					 x and y.
	 * 
	 * @param p the point being set
	 * @param x the x location of the point
	 * @param y the y location of the point
	 * @param piv the image of the point
	 */
	public void setPointLocation(Point p, double x, double y, ImageView piv) {
		p.setX(x);
		p.setY(y);
		piv.setTranslateX(x);
		piv.setTranslateY(y);
	}
	
	
	/**
	 * setRectLocation, sets the translate x and y of a rectangle.
	 * 
	 * @param r the rectangle being set
	 * @param x the x location
	 * @param y the y location
	 */
	public void setRectLocation(Rectangle r, double x, double y) {
		r.setX(x);
		r.setY(y);
	}
	
	
	/**
	 * setCircLocation, sets the translate x and y of a circle.
	 * 
	 * @param c the circle being set
	 * @param x the x location
	 * @param y the y location
	 */
	public void setCircLocation(Circle c, double x, double y) {
		c.setCenterX(x);
		c.setCenterY(y);
	}
}
