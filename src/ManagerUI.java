import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.application.Platform;
import javafx.scene.shape.Circle;

public class ManagerUI implements Runnable {
	int n_of_particles;
	double max_mass,max_velocity;
	int sizeX, sizeY;
	Circle[] circles;
	LinkedBlockingQueue<Vector<Particle> > bufferOfParticles;
	ManagerUI(LinkedBlockingQueue<Vector<Particle> > bufferOfParticles, Circle[] circles, int n_of_particles,double max_mass, double max_velocity, int sizeX, int sizeY){
		this.bufferOfParticles = bufferOfParticles;
		this.circles = circles;
		this.n_of_particles = n_of_particles;
		this.max_mass = max_mass;
		this.max_velocity = max_velocity;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public void run() {
		while(true) {	
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {
				
			}
			Platform.runLater(() -> {
			    Vector<Particle> particles;
				if(!bufferOfParticles.isEmpty()){
	
					try {
						particles = bufferOfParticles.take();
							for(int i = 0; i < n_of_particles; i++) {
								// if particle is out of draw range does not update
								// the position of the circle that represent it
								if(isOnBound(circles[i],particles.get(i).x(),particles.get(i).y())){
										circles[i].setCenterX(particles.get(i).x());
										circles[i].setCenterY(particles.get(i).y());
										//System.out.println(particles.get(i).mass());
										circles[i].setRadius(2*Math.floor(Math.sqrt(particles.get(i).mass())));
								}
							}								
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});			
		}
	} 

	
	// redraws only particles that can be seen
	public boolean isOnBound(Circle circle,double x,double y){
		double r = 2*circle.getRadius();
		return (x >= -r && x <= sizeX + r) && (y >= -r && y <= sizeY + r);
		
	}
		
}


