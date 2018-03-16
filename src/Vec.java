import java.util.Random;

public interface Vec {
	double norm2();
	default double norm() {
		return Math.sqrt(norm2());
	}
	public double x();
	public double y();

	
	public Vector2D diff(Vector2D b);
	public double dist(Vector2D b);
	public void sumWith(Vec other);
}
