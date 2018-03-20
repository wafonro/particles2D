import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DynSystem {
	Vector<Particle> sysParticles;
	volatile public AtomicInteger counterUpdates;//this counts how many acceleration updates we made, for sync purposes
	public final Force f =  new Gravity();
	public final double delta_t;
	public final Lock lock;
	public final Condition endPos, endAcc;
	
	
	public DynSystem(Vector<Particle> sys, double t) {
		this.sysParticles = sys;
		this.counterUpdates = new AtomicInteger(0);
		this.delta_t = t;
		this.lock = new ReentrantLock();
		this.endAcc = lock.newCondition();
		this.endPos = lock.newCondition();
	}
	
	public DynSystem(int maxMass, int maxVelocity, int maxPosition, int nParticles, double t) {
		this(new Vector<Particle>(nParticles), t);
		Random r = new Random();
		for (int i = 0; i < nParticles; i++) {
			sysParticles.add(new Particle(r, maxMass, maxVelocity, maxPosition, i));
		}
		
	}
	
	public boolean updateAcceleration(int pos) {
		sysParticles.get(pos).updateAcceleration(sysParticles, f);
		return counterUpdates.decrementAndGet() == 0;
	}
	
	public boolean updateAcceleration(int begin, int end) {
		boolean finAcc = false;
		for(int i = begin; i < end-1; i++) {
			finAcc = this.updateAcceleration(i);
		}		


		lock.lock();
		try{
			finAcc = this.updateAcceleration(end-1);
			if(!finAcc)
				this.endAcc.awaitUninterruptibly();//waiting all of the particles update their acceleration
			else this.endAcc.signalAll();
			return finAcc;
		}finally {
			lock.unlock();
		}
	}
	
	public boolean updateVelPos(int pos) {
		sysParticles.get(pos).updateVelocity(this.delta_t);
		sysParticles.get(pos).updatePosition(this.delta_t);
		
		return counterUpdates.incrementAndGet() == this.sysParticles.size();
	}
	
	public boolean updateVelPos(int begin, int end) {
		boolean finPos = false;
		for(int i = begin; i < end-1; i++) {
			finPos = this.updateVelPos(i);
		}

		
		lock.lock();
		try{
			finPos = this.updateVelPos(end-1);
			if(!finPos)
				this.endPos.awaitUninterruptibly();//waiting all of the particles update their acceleration
			else this.endPos.signalAll();
			return finPos;
		}finally {
			lock.unlock();
		}
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
