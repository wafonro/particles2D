
public class massParticle {
	double mass;
	Vector2D position;
	Vector2D velocity;
	Vector2D acceleration;
	
	Vector2D gravitationalForce(massParticle b) {
		double dist = this.position.dist(b.position);
		return b.position.diff(this.position).multiply(this.mass*b.mass/(dist*dist*dist));
	}
	void updatePosition(double delta) {
		position = position.sum(velocity.multiply(delta));
	}
	void updateVelocity(double delta) {
		velocity = velocity.sum(acceleration.multiply(delta));
	}
	massParticle(int mass, Vector2D pos, Vector2D vel){
		this.mass = mass;
		position = pos;
		velocity = vel;
		acceleration = new Vector2D(0,0);
	}
}
