import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;


//This is a thread that picks a group of particles and evolves them through time, it is synchronized with
//other threads such as to always have all particles of the system at the same moment in time
public class Progresser extends Thread {
	DynSystem systemState;
	int begin, end;//this determines the fraction of the whole dynamic system that this thread will progress
	private final LinkedBlockingQueue<Vector<Particle>> outputBuffer;//this is the channel where the thread sends the states to be displayed at each time
	boolean withBorder;
	
	
	
	Progresser(DynSystem sys, int begin, int end, LinkedBlockingQueue<Vector<Particle> > output, boolean withBorder) throws InterruptedException{
		this.systemState = sys;
		this.begin = begin;
		this.end = end;
		this.withBorder = withBorder;
		outputBuffer = output;
		if(begin == 0) {
			outputBuffer.put(sys.sysParticles);
		}
	}
	
	public void run() {
		boolean  finPos = false;
		while(true) {
			
			// update velocity
			finPos = systemState.updateVelPos(begin, end);
			if(finPos){
				try {
					outputBuffer.put(systemState.sysParticles);//this is ok just because we lock it in the acceleration part
					finPos = false;

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			// update acceleration
			systemState.updateAcceleration(begin, end);
			
			// if collides with outer bound
			if(withBorder){
				systemState.updateBounds(begin, end);
			}

		}
	}
}
