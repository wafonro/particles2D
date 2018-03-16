
public class massParticle {
	double mass;
	Vector2D position;
	Vector2D velocity;
	Vector2D acceleration;
	
	Vector2D gravitationalForce(massParticle a, massParticle b) {
		double dist = a.position.dist(b.position);
		return b.position.diff(a.position).multiply(a.mass*b.mass/(dist*dist*dist));
	}
	void updatePosition() {
		position = position.sum(velocity);
	}
	void updateVelocity() {
		velocity = velocity.sum(acceleration);
	}
}
