import java.util.Random;
import java.util.Vector;

public class Particle {
	private double mass;
	private Vector2D velocity, acceleration, position;
	public final int id; //can be used for the color as well
	public Particle(double mass, Vector2D position, Vector2D velocity, int id) {
		this.mass = mass;
		this.velocity = velocity;
		this.acceleration = new Vector2D();//adapt this to generalize
		this.position = position;
		this.id = id;
	}
	
	public Particle(Random r, double max_mass, double max_position, double max_velocity, int id) {
		this(max_mass * r.nextDouble(), new Vector2D(r,max_position), new Vector2D(r, max_velocity), id);
	}
	
	public Particle(Random r, double min_mass, double max_mass, double max_position, double max_velocity, int id) {
		this(min_mass + (max_mass-min_mass) * r.nextDouble(), Vector2D.sum(new Vector2D(400,400),new Vector2D(r,max_position)), new Vector2D(r, max_velocity), id);
	}
	
	public void updatePosition(double delta) {
		this.position.sumWith(Vector2D.multiply(this.velocity, delta));
	}
	
	public void updateVelocity(double delta) {
		this.velocity.sumWith(Vector2D.multiply(this.acceleration, delta));
	}
	
	public void updateAcceleration( Vector<Particle> system, Force f) {
		this.acceleration = f.calc_a(this, system);
	}

	public static double kinEnergy(Particle p) {
		return (p.velocity_().norm2())*p.mass()/2;
	}
	
	public double mass() {
		return mass;
	}

	public double x() {
		return this.position.x();
	}

	public double y() {
		return this.position.y();
	}
	
	public Vector2D position() {
		return new Vector2D(this.x(), this.y());
	}
	
	public Vector2D position_() {
		return this.position;
	}
	
	public Vector2D velocity() {
		return new Vector2D(velocity);
	}
	
	public Vector2D velocity_() {
		return this.velocity;
	}

	@Override
	public String toString() {
		return "Particle " + id + "[mass=" + mass + ", velocity=" + velocity + ", acceleration=" + acceleration + ", position="
				+ position + ", id=" + id + "]";
	}
	
	public String toConstructor() {
		return "new Particle(" + this.mass() + ", new Vector2D" + this.position + ", new Vector2D" + this.velocity + ", " + this.id + ")";
	}
	
	
}
