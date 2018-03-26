import java.util.Random;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

public class ManagerSimulation implements Runnable{
	LinkedBlockingQueue<Vector<Particle> > bufferParticles;
	int n_of_particles;
	double max_mass = 1000;
	double max_velocity = 0.1;//radial
	ManagerSimulation(LinkedBlockingQueue<Vector<Particle> > buffer, int n_of_particles, double max_mass, double max_velocity){
		bufferParticles = buffer;
		this.n_of_particles = n_of_particles;
		this.max_mass = max_mass;
		this.max_velocity = max_velocity;
	}
	@Override
	public void run() {
		int n_of_threads = 4;
		Force f = new Gravity();
		double max_mass = 1000;
		double max_velocity = 0.1;//radial
		double max_position = 300;//radial
		double delta = 0.001;
		Random randomGenerator = new Random();
		Vector<Particle> particles = new Vector<Particle>();
		for(int i = 0; i < n_of_particles; i++){
			particles.add(new Particle(randomGenerator, max_mass,max_position,max_velocity,i));
		}
		bufferParticles.add(particles);
		DynSystem system = new DynSystem(particles,delta);

		int n_particles_per_thread = n_of_particles / n_of_threads;
		Thread[] progresserThreads = new Thread[n_of_threads];
		for(int k = 0; k < n_of_threads-1; k++){
			try {
				progresserThreads[k] = new Thread(new Progresser(system,k*n_particles_per_thread,(k+1)*n_particles_per_thread,bufferParticles));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		try {
			progresserThreads[n_of_threads-1] = new Thread(new Progresser(system,(n_of_threads-1)*n_particles_per_thread,n_of_particles,bufferParticles));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int j = 0; j < n_of_threads; j++){
			progresserThreads[j].start();
		}
		
	}
}
