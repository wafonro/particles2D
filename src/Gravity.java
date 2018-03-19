import java.util.Vector;

public class Gravity implements Force {
	public Vector2D calc_a(Particle me, Vector<Particle> system) {
		Vector2D force = new Vector2D(), pos = me.position(), relPos;
		double m = me.mass(), r;
		
		for(Particle other: system) {
				if(me.id != other.id) {
				relPos = Vector2D.diff(other.position_(),pos);
				r = relPos.norm();
				if(r > 10){
					force.sumWith(Vector2D.multiply(relPos, ((double)m*other.mass())/(r*r*r)));					
				}
			}
		}
		return Vector2D.divide(force,m);
	}
	
	public double calc_energy(Vector<Particle> system) {
		Particle p1, p2;
		double energy = 0;
		for(int i = 0; i < system.size(); i++) {
			p1 = system.get(i);
			for(int j = i+1; j < system.size(); j++) {
				p2 = system.get(j);
				energy += (p1.mass()*p2.mass())/(Vector2D.diff(p1.position_(), p2.position_()).norm());
			}
		}
		return -energy;
	}
}
