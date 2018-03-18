import java.util.Vector;

public class AccelerationUpdater implements Runnable{
	int begin, end;
	Force f;
	Vector<Particle> particles;
	public AccelerationUpdater(int begin, int end, Vector<Particle> particles, Force f) {
		this.begin = begin;
		this.end = end;
		this.particles = particles;
		this.f = f;
		
	}
	@Override
	public void run() {
		for(int i = begin; i < end; i++){
			particles.get(i).updateAcceleration(particles, f);
		}	
	}

}
