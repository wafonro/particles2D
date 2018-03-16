

public class Vector2D {
	private double x,y;
	Vector2D(double x, double y){
		this.x = x;
		this.y = y;
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
	public Vector2D sum(Vector2D b) {
		return new Vector2D(this.x + b.x, this.y + b.y);
	}
	public Vector2D diff(Vector2D b) {
		return new Vector2D(this.x - b.x, this.y - b.y);
	}
	public double dist(Vector2D b) {
		return new Vector2D(this.x - b.x, this.y - b.y).norm();
	}
	public Vector2D multiply(double a) {
		return new Vector2D(this.x*a, this.y*a);
	}

}
