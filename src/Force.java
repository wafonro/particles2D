import java.util.Vector;

public interface Force {
	Vector2D calc_a(Particle me, Vector<Particle> system);//system contains me!
	double calc_energy(Vector<Particle> system);
}
