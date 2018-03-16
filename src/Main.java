

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

	public static void main(String[] args) {
		Random rand = new Random();
		for(int i = 0; i < 100; i++) {
			System.out.println(rand.nextInt(50));
		}
        launch(args);

	}
    public void start(Stage stage) {
    	int NMAX = 1;
		Random rand = new Random();
	    Pane canvas = new Pane();    	
	    Circle[] circles = new Circle[NMAX];
    	for(int i = 0; i < NMAX; i++) {
    		circles[i] = new Circle(rand.nextInt(800)+1,rand.nextInt(800)+1, rand.nextInt(10)+1);
            canvas.getChildren().add(circles[i]);
    	}
    	Thread updateCircle = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					circles[0] = new Circle(rand.nextInt(800)+1,rand.nextInt(800)+1, rand.nextInt(10)+1);
		            canvas.getChildren().add(circles[0]);
				}
			}
    		
    	});
    	updateCircle.start();
        Scene scene = new Scene(canvas, 800, 800);
        stage.setTitle("Circle");
        stage.setScene(scene);
        stage.show();
    }

}
