import java.util.Vector;

public class PositionAndSpeedUpdater implements Runnable {
	int begin, end;
	double delta;
	Vector<Particle> particles;
	public PositionAndSpeedUpdater(int begin, int end, Vector<Particle> particles, double delta) {
		this.begin = begin;
		this.end = end;
		this.particles = particles;
		this.delta = delta;
		
	}
	@Override
	public void run() {
		for(int i = begin; i < end; i++){
			particles.get(i).updatePosition(delta);
			particles.get(i).updateVelocity(delta);;
		}	
	}

}
