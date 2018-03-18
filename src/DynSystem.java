import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class DynSystem {
	Vector<Particle> sysParticles;
	public final AtomicInteger counterUpdates;//this counts how many acceleration updates we made, for sync purposes
	public final Force f =  new Gravity();
	public final double delta_t;
	
	public DynSystem(Vector<Particle> sys, double t) {
		this.sysParticles = sys;
		this.counterUpdates = new AtomicInteger(0);
		this.delta_t = t;
	}
	
	public DynSystem(int maxMass, int maxVelocity, int maxPosition, int nParticles, double t) {
		this.delta_t = t;
		this.counterUpdates = new AtomicInteger(nParticles);
		this.sysParticles = new Vector<Particle>(nParticles);
		Random r = new Random();
		for (int i = 0; i < nParticles; i++) {
			sysParticles.add(new Particle(r, maxMass, maxVelocity, maxPosition, i));
		}
	}
	
	public boolean updateAcceleration(int pos) {
		sysParticles.get(pos).updateAcceleration(sysParticles, f);
		return counterUpdates.decrementAndGet() == 0;
	}
	public boolean updateVelPos(int pos) {
		sysParticles.get(pos).updateVelocity(this.delta_t);
		sysParticles.get(pos).updatePosition(this.delta_t);
		return counterUpdates.incrementAndGet() == this.sysParticles.size();
	}
	
	public int n_of_particles() {
		return sysParticles.size();
	}

	//Energy Calculating functions!
	public double kinEnergy() {
		double energy = 0;
		for(Particle p: sysParticles) {
			energy += Particle.kinEnergy(p);
		}
		return energy;
	}
	
	public double potEnergy() {
		return f.calc_energy(sysParticles);
	}
	
	public double totalEnergy() {
		return kinEnergy()+potEnergy();
	}
	
}
