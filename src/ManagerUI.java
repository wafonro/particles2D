import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.application.Platform;
import javafx.scene.shape.Circle;

public class ManagerUI implements Runnable {
	int n_of_particles;
	int sizeX, sizeY;
	Circle[] circles;
	ManagerUI(Circle[] circles, int n_of_particles, int sizeX, int sizeY){
		this.circles = circles;
		this.n_of_particles = n_of_particles;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public void run() {
		LinkedBlockingQueue<Vector<Particle> > bufferOfParticles = new LinkedBlockingQueue<Vector<Particle> >();
		Thread updateParticles =  new Thread(new ManagerSimulation(bufferOfParticles, n_of_particles));
    	Thread updateCircle = new Thread(new Runnable(){
				@Override
				public void run() {
					while(true) {			
						try {
							Thread.sleep(1000/60);
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
											if(isOnBound(particles.get(i).x(),particles.get(i).y())){
													circles[i].setCenterX(particles.get(i).x());
													circles[i].setCenterY(particles.get(i).y());
													circles[i].setRadius(Math.floor(Math.sqrt(particles.get(i).mass())/5));
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
    		
    	});
    	updateParticles.start();
    	updateCircle.start();
      
    }
	
	public boolean isOnBound(double x,double y){
		return (x >= -50 && x <= sizeX + 50) && (y >= -50 && y <= sizeY + 50);
		
	}
		
}


