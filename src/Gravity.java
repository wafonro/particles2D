
public class Gravity {
	static calc_a(Particle me, Vector<Particle> others) {
		double f = 0, m = me.mass(), x = me.x(), y = me.y();
		for(Particle other: others) {
			f += m*others.mass()
		}
	}
}
