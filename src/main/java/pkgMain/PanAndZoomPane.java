/**
 * Implementing some code by @RonSiven on StackOverflow 
 */
package pkgMain;

/**
 * Class - PanAndZoomPane, makes a pane for the garden which can be zoomed in
 * 						   or out and is draggable.
 * 
 * @author William Cantera
 * @author Diane Vinson
 * @author Ben Testani
 * @author Jacob Kuchma
 * @author Maria Venrooy
 */
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class PanAndZoomPane extends GridPane {
    public static final double DEFAULT_DELTA = 1.3d;
    DoubleProperty myScale = new SimpleDoubleProperty(1.0);
    public DoubleProperty deltaY = new SimpleDoubleProperty(0.0);
    private Timeline timeline;
    private double xPos =0;
    private double yPos = 0;

    
    /**
     * PandAndZoomPane, constructor.
     */
    public PanAndZoomPane() {
        this.timeline = new Timeline(60);
        // add scale transform
        scaleXProperty().bind(myScale);
        scaleYProperty().bind(myScale);
    }

    public void setX(double x) {
    	xPos= x;
    }
    
    public void setY(double y) {
    	yPos = y;
    }
    
    public double getX() {
    	return xPos;
    }
    
    public double getY() {
    	return yPos;
    }

    /**
     * getScale, gettter for the scale.
     * 
     * @return scale the scale
     */
    public double getScale() {
        return myScale.get();
    }

    
    /**
     * setScale, setter for the scale.
     * 
     * @param scale the scale to set
     */
    public void setScale(double scale) {
        myScale.set(scale);
    }
    
    
    /**
     * setPivot, setter for the pivot. PLays an aanimation of zooming.
     * 
     * @param x the x location
     * @param y the y location
     * @param scale the scale
     */
    public void setPivot(double x, double y, double scale) {
        // note: pivot value must be untransformed, i. e. without scaling
        // timeline that scales and moves the node
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.millis(200), new KeyValue(translateXProperty(), getTranslateX() - x)),
            new KeyFrame(Duration.millis(200), new KeyValue(translateYProperty(), getTranslateY() - y)),
            new KeyFrame(Duration.millis(200), new KeyValue(myScale, scale))
        );
        timeline.play();
    }

    
    /**
     * detDeltaY, getter for the deltaY.
     * 
     * @return deltaY the attribute to get
     */
    public double getDeltaY() {
        return deltaY.get();
    }
    
    
    /**
     * setDeltay, setter for the deltaY
     * 
     * @param dY the attribute to set
     */
    public void setDeltaY(double dY) {
        deltaY.set(dY);
    }
    
    
    /**
     * zoomFunction, finds the amount to zoom on the garden.
     *  
     * @param diff the amount for which to zoom
     */
    public void zoomFunction(double diff) {
        double delta = PanAndZoomPane.DEFAULT_DELTA;
        double scale = myScale.get(); // currently we only use Y, same value is used for X
        double oldScale = scale;

        setDeltaY(diff); 
        scale = deltaY.get() < 0 ? scale / delta : scale * delta;

        double f = (scale / oldScale)-1;
        double dx = (250 - (getBoundsInParent().getWidth()/2 + getBoundsInParent().getMinX()));
        double dy = (250 - (getBoundsInParent().getHeight()/2 + getBoundsInParent().getMinY()));
        setPivot(f*dx, f*dy, scale);
    }
}
