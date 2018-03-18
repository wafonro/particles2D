import java.util.LinkedList;
import java.util.Vector;

import javafx.scene.shape.Circle;

public class ManagerUI implements Runnable {
	int n_of_particles;
	Circle[] circles;
	ManagerUI(Circle[] circles, int n_of_particles){
		this.circles = circles;
		this.n_of_particles = n_of_particles;
	}

	public void run() {
		LinkedList<Vector<Particle> > bufferOfParticles = new LinkedList<Vector<Particle> >();
    	Thread updateParticles =  new Thread(new ManagerSimulation(bufferOfParticles, n_of_particles));
    	Thread updateCircle = new Thread(new Runnable(){
				@Override
				public void run() {
				    Vector<Particle> particles;
					while(true) {			
						try {
							Thread.sleep(1000/60);
						} catch (InterruptedException e) {
							
						}
						if(!bufferOfParticles.isEmpty()){
							particles = bufferOfParticles.removeLast();
							for(int i = 0; i < n_of_particles; i++) {
								circles[i].setCenterX(particles.get(i).x());
								circles[i].setCenterY(particles.get(i).y());
								circles[i].setRadius(particles.get(i).mass()/10);
							}								
						}
						
										
					}
				}
    		
    	});
    	updateParticles.start();
    	updateCircle.start();
      
    }

		
}


