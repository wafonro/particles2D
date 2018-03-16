

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

	public static void main(String[] args) {
        launch(args);

	}
    public void start(Stage stage) {
    	/*int NMAX = 100;
		Random rand = new Random();
	    // canvas where particles will be drawn
		Pane canvas = new Pane();   
	    
	    Circle[] circles = new Circle[NMAX];
	    massParticle[] particles = new massParticle[NMAX];
    	for(int i = 0; i < NMAX; i++) {
    		circles[i] = new Circle();
    		particles[i] = new massParticle(rand.nextInt(200)+1, new Vector2D(rand.nextInt(800)+1,rand.nextInt(800)+1), new Vector2D(rand.nextInt(10)-5,rand.nextInt(10)-5));
            canvas.getChildren().add(circles[i]);
    	}
    	Thread updateCircle = new Thread(new Runnable(){

			@Override
			public void run() {
				// update loop runs at 60 fps
				while(true) {			
					try {
						Thread.sleep(1000/60);
					} catch (InterruptedException e) {
					}
					for(int i = 0; i < NMAX; i++) {
						particles[i].acceleration = new Vector2D(0.0,0);
						for(int j = 0; j < NMAX; j++) {
							if(j == i)continue;
							particles[i].acceleration = particles[i].acceleration.sum(particles[i].gravitationalForce(particles[j])).multiply(1/particles[i].mass);						
						}
						particles[i].updatePosition(1);
						particles[i].updateVelocity(1);
						circles[i].setCenterY(particles[i].position.getY());
						circles[i].setCenterX(particles[i].position.getX());
						circles[i].setRadius(particles[i].mass/20);
					}
				}
			}
    		
    	});
    	updateCircle.start();
        Scene scene = new Scene(canvas, 800, 800);
        stage.setTitle("Circle");
        stage.setScene(scene);
        stage.show();*/
    }

}
