

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class Main extends Application {

	public static void main(String[] args) {
        launch(args);

	}
    public void start(Stage stage) {
    	
    	int sizeX = 800, sizeY = 800;
	    GridPane grid = new GridPane();
	    grid.setPadding(new Insets(350, 100, 400, 300));
	    grid.setVgap(5);
	    grid.setHgap(5);

	    //Defining the Number of particle
	    final NumberTextField particles = new NumberTextField();
	    particles.setPromptText("number of particles");
	    GridPane.setConstraints(particles, 0, 0);
	    grid.getChildren().add(particles);

	    //Defining the max mass
	    final NumberTextField mass = new NumberTextField();
	    mass.setPromptText("max mass");
	    GridPane.setConstraints(mass, 0, 1);
	    grid.getChildren().add(mass);

	    //Defining the max velocity
	    final NumberTextField velocity = new NumberTextField();
	    velocity.setPromptText("max velocity");
	    GridPane.setConstraints(velocity, 0, 2);
	    grid.getChildren().add(velocity);
	  
	  	CheckBox withBorder = new CheckBox("Border");
	  	GridPane.setConstraints(withBorder, 0, 3);
		grid.getChildren().add(withBorder);

	    //Defining the Submit button
	    Button submit = new Button("Submit");
	    GridPane.setConstraints(submit, 1, 0);
	    grid.getChildren().add(submit);

	    //Defining the Clear button
	    Button clear = new Button("Clear");
	    GridPane.setConstraints(clear, 1, 1);
	    grid.getChildren().add(clear);
	  //Adding a Label
	    final Label label = new Label();
	    GridPane.setConstraints(label, 0, 3);
	    GridPane.setColumnSpan(label, 2);
	    grid.getChildren().add(label);

	    
	    
	    
    	Scene scene = new Scene(grid, sizeX, sizeY);
        stage.setTitle("Circle");
        stage.setScene(scene);
        stage.show();
        
        submit.setOnAction((ActionEvent e) -> {
        	// gets the input from the user
        	int n_of_particles = new Integer(particles.getText()).intValue();
        	double max_mass = new Double(mass.getText()).doubleValue();
        	double max_velocity = new Double(velocity.getText()).doubleValue();
        	boolean bordered = withBorder.isSelected();
           	Random rand = new Random();
           	
    	    // canvas where particles will be drawn
    		Pane canvas = new Pane();   
    		
    		// Create the circles to be drawn
    	    Circle[] circles = new Circle[n_of_particles];
    	    for(int i = 0; i < n_of_particles; i++) {
        		circles[i] = new Circle();
        		circles[i].setFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                canvas.getChildren().add(circles[i]);
        	}
        	
    	    LinkedBlockingQueue<Vector<Particle> > bufferOfParticles = new LinkedBlockingQueue<Vector<Particle> >();
    	    Thread managerUI = new Thread(new ManagerUI(bufferOfParticles, circles,n_of_particles, max_mass, max_velocity,sizeX,sizeY));
    	    Thread updateParticles =  new Thread(new ManagerSimulation(bufferOfParticles, n_of_particles, max_mass, max_velocity, bordered));

    		
	    	// setDaemon forces thread to stop when window closes
    		updateParticles.setDaemon(true);
		    managerUI.setDaemon(true);
		    updateParticles.start();
	    	managerUI.start();
	    	Scene scene2 = new Scene(canvas, sizeX, sizeY);
	        stage.setTitle("Circle");
	        stage.setScene(scene2);
	        stage.show();
	    });

	    clear.setOnAction((ActionEvent e) -> {
	        particles.clear();
	        mass.clear();
	        velocity.clear();
	        label.setText(null);
	    });
    }

}
