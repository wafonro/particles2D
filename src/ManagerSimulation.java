import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

public class ManagerSimulation implements Runnable{
	LinkedList<Vector<Particle> > bufferParticles;
	int n_of_particles;
	ManagerSimulation(LinkedList<Vector<Particle> > buffer, int n_of_particles){
		bufferParticles = buffer;
		this.n_of_particles = n_of_particles;
	}
	@Override
	public void run() {
		int n_of_threads = 1;
		int n_of_steps = 10000;
		Force f = new Gravity();
		double min_mass = 1;
		double max_mass = 100;
		double max_velocity = 10;//radial
		double max_position = 400;//radial
		double t_final = 100;
		double delta = 0.01;
		Random randomGenerator = new Random();
		Vector<Particle> particles = new Vector<Particle>();
		for(int i = 0; i < n_of_particles; i++){
			particles.add(new Particle(randomGenerator,min_mass, max_mass,max_position,max_velocity,i));

		}

		int n_particles_per_thread = n_of_particles / n_of_threads;

		while(true){
			Thread[] updateAccelerationThreads = new Thread[n_of_threads];
			Thread[] updatePositionAndSpeedThreads = new Thread[n_of_threads];
			for(int k = 0; k < n_of_threads-1; k++){
				updateAccelerationThreads[k] = new Thread(new AccelerationUpdater(k*n_particles_per_thread,(k+1)*n_particles_per_thread,particles,f));
				updatePositionAndSpeedThreads[k] = new Thread(new PositionAndSpeedUpdater(k*n_particles_per_thread,(k+1)*n_particles_per_thread,particles,delta));
				
			}
			updateAccelerationThreads[n_of_threads-1] = new Thread(new AccelerationUpdater((n_of_threads-1)*n_particles_per_thread,n_of_particles,particles,f));
			updatePositionAndSpeedThreads[n_of_threads-1] = new Thread(new PositionAndSpeedUpdater((n_of_threads-1)*n_particles_per_thread,n_of_particles,particles,delta));
			// update acceleration
			for(int j = 0; j < n_of_threads; j++){
				updateAccelerationThreads[j].start();
			}
			// wait for all processes to finish
			for(int j = 0; j < n_of_threads; j++){
				try {
					updateAccelerationThreads[j].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			bufferParticles.add(particles);
			// update velocity and position
			for(int j = 0; j < n_of_threads; j++){
				updatePositionAndSpeedThreads[j].start();
			}
			// wait for all process to finish
			for(int j = 0; j < n_of_threads; j++){
				try {
					updatePositionAndSpeedThreads[j].join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	

}
