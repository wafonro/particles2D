import java.util.Random;

public class Vector2D implements Vec{
	private double x,y;
	static public Vector2D ZERO = new Vector2D(0,0);
	Vector2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	public Vector2D(Random r, double max_value){
		this.x = max_value*r.nextDouble();
		this.y = max_value*r.nextDouble();
	}
	
	public double norm2() {
		return x*x+y*y;
	}
	public double norm() {
		return Math.sqrt(norm2());
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public static Vector2D sum(Vector2D a, Vector2D b) {
		return new Vector2D(a.x + b.x, a.y + b.y);
	}
	public void sumWith(Vec other) {
		this.x += other.x();
		this.y += other.y();
	}
	public Vector2D diff(Vector2D b) {
		return new Vector2D(this.x - b.x, this.y - b.y);
	}
	public void diffWith(Vector2D other) {
		this.x -= other.x;
		this.y -= other.y;
	}
	public double dist(Vector2D b) {
		return new Vector2D(this.x - b.x, this.y - b.y).norm();
	}
	public static Vector2D multiply(Vec me, double a) {
		return new Vector2D(me.x()*a, me.y()*a);
	}
	
	public double x() {
		return this.x;
	}

	public double y() {
		return this.y;
	}
	
}
