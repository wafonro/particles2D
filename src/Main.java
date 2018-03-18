

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class Main extends Application {

	public static void main(String[] args) {
        launch(args);

	}
    public void start(Stage stage) {
    	int n_of_particles = 20;
	    // canvas where particles will be drawn
		Pane canvas = new Pane();   
	    Circle[] circles = new Circle[n_of_particles];
	    for(int i = 0; i < n_of_particles; i++) {
    		circles[i] = new Circle();
            canvas.getChildren().add(circles[i]);
    	}
	    Thread manager = new Thread(new ManagerUI(circles,n_of_particles));
    	manager.start();
    	Scene scene = new Scene(canvas, 800, 800);
        stage.setTitle("Circle");
        stage.setScene(scene);
        stage.show();
    }

}
