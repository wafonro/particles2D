import java.util.Random;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

public class ManagerSimulation implements Runnable{
	LinkedBlockingQueue<Vector<Particle> > bufferParticles;
	int n_of_particles;
	double max_mass = 1000;
	double max_velocity = 0.1;//radial
	boolean withBorder;
	ManagerSimulation(LinkedBlockingQueue<Vector<Particle> > buffer, int n_of_particles, double max_mass, double max_velocity, boolean withBorder){
		bufferParticles = buffer;
		this.n_of_particles = n_of_particles;
		this.max_mass = max_mass;
		this.max_velocity = max_velocity;
		this.withBorder = withBorder;
	}
	@Override
	public void run() {
		int n_of_threads = Math.min(4,n_of_particles); // There are at most as many threads as particles		
		int max_size_of_buffer = 120;
		double max_position = 300; 
		double delta = 0.001; // interval of time
		Random randomGenerator = new Random();
		
		// bounds the size of the buffer
		while(bufferParticles.size() > max_size_of_buffer){
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Generates randomly the particles
		Vector<Particle> particles = new Vector<Particle>();
		for(int i = 0; i < n_of_particles; i++){
			particles.add(new Particle(randomGenerator, max_mass,max_position,max_velocity,i));
		}
		bufferParticles.add(particles);
		DynSystem system = new DynSystem(particles,delta);

		// Creates the threads that will update the position and velocity
		// of the particles
		int n_particles_per_thread = n_of_particles / n_of_threads;
		Thread[] progresserThreads = new Thread[n_of_threads];
		for(int k = 0; k < n_of_threads-1; k++){
			try {
				progresserThreads[k] = new Thread(new Progresser(system,k*n_particles_per_thread,(k+1)*n_particles_per_thread,bufferParticles, withBorder));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		try {
			progresserThreads[n_of_threads-1] = new Thread(new Progresser(system,(n_of_threads-1)*n_particles_per_thread,n_of_particles,bufferParticles,withBorder));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Start all threads
		for(int j = 0; j < n_of_threads; j++){
			progresserThreads[j].start();
		}
		
	}
}
