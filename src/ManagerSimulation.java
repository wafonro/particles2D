import java.util.Random;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

public class ManagerSimulation implements Runnable{
	LinkedBlockingQueue<Vector<Particle> > bufferParticles;
	int n_of_particles;
	ManagerSimulation(LinkedBlockingQueue<Vector<Particle> > buffer, int n_of_particles){
		bufferParticles = buffer;
		this.n_of_particles = n_of_particles;
	}
	@Override
	public void run() {
		int n_of_threads = 10;
		int n_of_steps = 1000000;
		Force f = new Gravity();
		double min_mass = 1;
		double max_mass = 1000;
		double max_velocity = 1;//radial
		double max_position = 300;//radial
		double t_final = 1000;
		double delta = 0.001;
		Random randomGenerator = new Random();
		Vector<Particle> particles = new Vector<Particle>();
		for(int i = 0; i < n_of_particles; i++){
			particles.add(new Particle(randomGenerator,min_mass, max_mass,max_position,max_velocity,i));
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
