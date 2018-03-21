

import java.util.Random;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class Main extends Application {

	public static void main(String[] args) {
        launch(args);

	}
    public void start(Stage stage) {
    	int n_of_particles = 400;
    	int sizeX = 800, sizeY = 800;
    	Random rand = new Random();
	    // canvas where particles will be drawn
		Pane canvas = new Pane();   
	    Circle[] circles = new Circle[n_of_particles];
	    for(int i = 0; i < n_of_particles; i++) {
    		circles[i] = new Circle();
    		circles[i].setFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            canvas.getChildren().add(circles[i]);
    	}
	    Thread manager = new Thread(new ManagerUI(circles,n_of_particles,sizeX,sizeY));
	    // forces thread to stop when window closes
	    manager.setDaemon(true);
    	manager.start();
    	Scene scene = new Scene(canvas, sizeX, sizeY);
        stage.setTitle("Circle");
        stage.setScene(scene);
        stage.show();
    }

}
