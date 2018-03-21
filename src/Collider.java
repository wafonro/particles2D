
public class Collider {
	final static double threshold = 2;
	final static double boxXmin = 0, boxXmax = 800,boxYmin = 0, boxYmax = 800;
	public static boolean collide(Particle a, Particle b){
		Vector2D aPos = a.position(),
			 	 bPos = b.position(),
			 	 aVel = a.velocity(),
			 	 bVel = b.velocity();
		double 	 aMass = a.mass(),
			 	 bMass = b.mass();
		Vector2D deltaPos = Vector2D.diff(aPos,bPos);
		if(deltaPos.norm() < threshold){
			Vector2D v1 = Vector2D.diff(aVel,Vector2D.multiply(deltaPos,(Vector2D.dot(deltaPos,Vector2D.diff(aVel,bVel))*(2*bMass/(aMass+bMass)/deltaPos.norm2()))));
			Vector2D v2 =  Vector2D.diff(bVel,Vector2D.multiply(deltaPos,(Vector2D.dot(deltaPos,Vector2D.diff(aVel,bVel))*(2*aMass/(aMass+bMass)/deltaPos.norm2()))));
			a.setVelocity(v1);
			b.setVelocity(v2);
			return true;
		}else{
			return false;
		}
	}
	public static void outerBoxCollision(Particle a){
		Vector2D aPos = a.position(),
			 	 aVel = a.velocity();
		if(aPos.x() < boxXmin || aPos.x() > boxXmax){
			aVel = new Vector2D(-aVel.x(),aVel.y());
			a.setVelocity(aVel);
		}
		if(aPos.y() < boxYmin || aPos.y() > boxYmax){
			aVel = new Vector2D(aVel.x(),-aVel.y());
			a.setVelocity(aVel);			
		}
		
	}

}
