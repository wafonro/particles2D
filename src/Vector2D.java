import java.util.Random;

public class Vector2D{
	private double x,y;
	final static public Vector2D ZERO = new Vector2D(0,0);
	Vector2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	public Vector2D(Random r, double max_value){
		double radius = Math.sqrt(max_value*max_value*r.nextDouble()), angle = 2*Math.PI*r.nextDouble();
		
		this.x = Math.sqrt(radius)*Math.cos(angle);
		this.y = Math.sqrt(radius)*Math.sin(angle);
	}
	
	public Vector2D(Random r, double max_x, double max_y){
		this.x = 2*max_x*r.nextDouble() - max_x;
		this.y = 2*max_y*r.nextDouble() - max_y;
	}
	
	public Vector2D(Vector2D v) {
		this.x = v.x;
		this.y = v.y;
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
	public void sumWith(Vector2D other) {
		this.x += other.x();
		this.y += other.y();
	}
	public static Vector2D diff(Vector2D a, Vector2D b) {
		return new Vector2D(a.x - b.x, a.y - b.y);
	}
	public void diffWith(Vector2D other) {
		this.x -= other.x;
		this.y -= other.y;
	}
	public double dist(Vector2D b) {
		return new Vector2D(this.x - b.x, this.y - b.y).norm();
	}
	public static Vector2D multiply(Vector2D me, double a) {
		return new Vector2D(me.x()*a, me.y()*a);
	}
	
	public static Vector2D divide(Vector2D me, double a) {
		return new Vector2D(me.x()/a, me.y()/a);
	}
	
	
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	public double x() {
		return this.x;
	}

	public double y() {
		return this.y;
	}
	
}
