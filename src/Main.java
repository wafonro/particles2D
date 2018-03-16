

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

	public static void main(String[] args) {
        launch(args);

	}
    public void start(Stage stage) {
    	int NMAX = 10;
		Random rand = new Random();
	    Pane canvas = new Pane();    	
	    Circle[] circles = new Circle[NMAX];
	    massParticle[] particles = new massParticle[NMAX];
    	for(int i = 0; i < NMAX; i++) {
    		circles[i] = new Circle(rand.nextInt(800)+1,rand.nextInt(800)+1, 1);
    		particles[i] = new massParticle(1, new Vector2D(rand.nextInt(800)+1,rand.nextInt(800)+1), new Vector2D(rand.nextInt(1),rand.nextInt(1)));
            canvas.getChildren().add(circles[i]);
    	}
    	Thread updateCircle = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					
					try {
						Thread.sleep(40);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(int i = 0; i < NMAX; i++) {
						particles[i].acceleration = new Vector2D(0.0,0);
						for(int j = 0; j < NMAX; j++) {
							if(j == i)continue;
							particles[i].acceleration = particles[i].acceleration.sum(particles[i].gravitationalForce(particles[j])).multiply(1/particles[i].mass);						
						}
						particles[i].updatePosition(1);
						particles[i].updateVelocity(10);
						circles[i].setCenterY(particles[i].position.getY());
						circles[i].setCenterX(particles[i].position.getX());
					}
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
