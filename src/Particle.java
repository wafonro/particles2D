import java.util.Random;

public class Particle {
	private double mass;
	Vec velocity, aceleration, position;
	public Particle(double mass, Vec position, Vec velocity) {
		this.mass = mass;
		this.velocity = velocity;
		this.aceleration = Vector2D.ZERO;//adapt this to generalize
		this.position = position;
	}
	
	static public Particle randomParticle(double max_mass, double max_velocity, double max_position) {
		Random r = new Random();
		return new Particle(max_mass * r.nextDouble(), new Vector2D(r,max_position), new Vector2D(r, max_velocity));
	}
	
	public updatePosition() {}
	
	public void updateVelocity(double delta) {
		this.velocity.sumWith(Vector2D.multiply(this.aceleration, delta));
	}
	
	public updateAcceleration() {}

	public double mass() {
		return mass;
	}

	public double x() {
		return this.position.x();
	}

	public double y() {
		return this.position.y();
	}
	
	
	
}
