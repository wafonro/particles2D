

import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

	    //Defining the Name text field
	    final NumberTextField particles = new NumberTextField();
	    particles.setPromptText("number of particles");
	    GridPane.setConstraints(particles, 0, 0);
	    grid.getChildren().add(particles);

	    //Defining the Last Name text field
	    final NumberTextField mass = new NumberTextField();
	    mass.setPromptText("max mass");
	    GridPane.setConstraints(mass, 0, 1);
	    grid.getChildren().add(mass);

	    //Defining the velocity text field
	    final NumberTextField velocity = new NumberTextField();
	    velocity.setPromptText("max velocity");
	    GridPane.setConstraints(velocity, 0, 2);
	    grid.getChildren().add(velocity);
	  velocity.setOnKeyPressed(ke-> System.out.println(ke.getCode()));

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
        	int n_of_particles = new Integer(particles.getText()).intValue();
        	double max_mass = new Double(mass.getText()).doubleValue();
        	double max_velocity = new Double(velocity.getText()).doubleValue();
           	Random rand = new Random();
    	    // canvas where particles will be drawn
    		Pane canvas = new Pane();   
    	    Circle[] circles = new Circle[n_of_particles];
    	    for(int i = 0; i < n_of_particles; i++) {
        		circles[i] = new Circle();
        		circles[i].setFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                canvas.getChildren().add(circles[i]);
        	}
        	Thread manager = new Thread(new ManagerUI(circles,n_of_particles, max_mass, max_velocity,sizeX,sizeY));
		    
	    	// forces thread to stop when window closes
		    manager.setDaemon(true);
	    	manager.start();
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
