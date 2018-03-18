import java.util.Random;
import java.util.Vector;
// This class tests the Particle and DynSystem classes, with the help as well of the Gravity class implemented through the Force interface
public class Test {
	public static void testParticleConstructorRandom(double max_mass, double max_position, double max_velocity) {
		System.out.println("\nTesting Constructor");
		Random r = new Random();
		Particle pOne = new Particle(r, max_mass, max_position, max_velocity, 1);
		displayPosition(pOne);
	}

	public static void testParticleUpdatePosition(double mass, Vector2D position, Vector2D velocity, double delta_t) {
		System.out.println("\nTesting Update Position");
		Particle pOne = new Particle(mass, position, velocity, 1);
		displayPosition(pOne);
		System.out.println("-[delta_t]>");
		pOne.updatePosition(delta_t);
		displayPosition(pOne);		
	}
	
	
	public static void displayPosition(Particle p) {
		System.out.println("position particle" + p.id + ": " + p.position_().toString());
	}
	
	public static void displaySystem(DynSystem system) {
		for(Particle p: system.sysParticles) {
			System.out.println("position particle" + p.id + ": " + p.position_().toString());
		}
		System.out.println("energy: " + system.totalEnergy());
	}
	
	public static void testSystemRandomParticles(int n_of_particles, double mass_of_both, double max_position, double max_velocity, Force f, double t_final, int n_of_steps) {
		Random r = new Random();
		Vector<Particle> system = new Vector<Particle>(n_of_particles);
		for (int i = 0; i < n_of_particles; i++) {
			system.add(new Particle(r, mass_of_both,mass_of_both, max_position, max_velocity, i+1));//mass is fixed but the rest is random with radial symmetry
		}
		
		System.out.println("In case you want this specific test case to check some stuff, this is the constructor for every particle");
		for(Particle p : system) {
			System.out.println(p.toConstructor());
		}
		DynSystem dSys = new DynSystem(system, t_final/n_of_steps);
		testSystemParticles(dSys, n_of_steps);
	}
	
	public static void testSystemParticles(DynSystem system, int n_of_steps) {
		int n_of_particles = system.n_of_particles();
		
		System.out.println("\nTesting System of " + n_of_particles + " particles");
		double delta_t = system.delta_t;
		
		for (int i = 0; i < n_of_steps; i++) {
			if((10*i)%n_of_steps == 0) {
				System.out.println("\nTime = " + i*delta_t);
				displaySystem(system);
			}//just showing some steps
				
			for (int j = 0; j < n_of_particles; j++) {
				system.updateAcceleration(j);
			}
			
			for (int j = 0; j < n_of_particles; j++) {
				system.updateVelPos(j);
			}
		}
	}



	public static void main(String[] args) {
		//parameters
		int n_of_particles = 2;
		int n_of_steps = 1000;
		Force f = new Gravity();
		double mass_for_all = 1;
		double max_velocity = 1;//radial
		double max_position = 10;//radial
		double t_final = 1;
		
		
		//testParticleConstructorRandom(1, 1, 1);
		//testParticleUpdatePosition(1, new Vector2D(0, 0), new Vector2D(1, 0.5), 0.01);
		testSystemRandomParticles(n_of_particles, mass_for_all, max_position, max_velocity, f, t_final, n_of_steps);
	}

}
