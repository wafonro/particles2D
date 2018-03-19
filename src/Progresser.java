import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//This is a thread that picks a group of particles and evolves them through time, it is synchronized with
//other threads such as to always have all particles of the system at the same moment in time
public class Progresser extends Thread {
	DynSystem systemState;
	int begin, end;//this determines the fraction of the whole dynamic system that this thread will progress
	ConcurrentLinkedQueue<Vector<Particle>> outputBuffer;//this is the channel where the thread sends the states to be displayed at each time
	
	
	
	Progresser(DynSystem sys, int begin, int end, ConcurrentLinkedQueue<Vector<Particle> > output){
		this.systemState = sys;
		this.begin = begin;
		this.end = end;
		outputBuffer = output;
		if(begin == 0) {
			outputBuffer.add(sys.sysParticles);
		}
	}
	
	public void run() {
		boolean finAcc, finPos;
		while(true) {
			finPos = false;
			for(int i = begin; i < end; i++) {
				finPos = this.systemState.updateVelPos(i);
				if(finPos) {
					outputBuffer.add(systemState.sysParticles);	// Here is where we send our newly calculated
					systemState.endPos.signalAll();
				}	
			}
			if(!finPos)
				systemState.endPos.awaitUninterruptibly();//waiting all of the particles update their acceleration
			
			finAcc = false;
			//updating acceleration
			for(int i = begin; i < end; i++) {
				finAcc = this.systemState.updateAcceleration(i);
				if(finAcc)
					systemState.endAcc.signalAll();
			}
			if(!finAcc)
				systemState.endAcc.awaitUninterruptibly();//waiting all of the particles update their acceleration
		}
	}
}
