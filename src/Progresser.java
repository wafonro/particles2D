import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Progresser extends Thread {
	DynSystem systemState;
	int begin, end;
	ConcurrentLinkedQueue<DynSystem> outputBuffer;
	
	final Lock lock;
	final Condition endAcc, endPos;
	
	Progresser(DynSystem sys, int begin, int end){
		this.systemState = sys;
		this.begin = begin;
		this.end = end;
		lock = new ReentrantLock();
		endAcc = lock.newCondition();
		endPos = lock.newCondition();
		outputBuffer = new ConcurrentLinkedQueue<DynSystem>();
		outputBuffer.add(sys);
	}
	
	public void run() {
		boolean finAcc = false, finPos = false;
		while(true) {
			for(int i = begin; i < end; i++) {
				finPos = this.systemState.updateVelPos(i);
				if(finPos) {
					outputBuffer.add(systemState);	// Here is where we send our newly calculated
					endPos.signalAll();
				}	
			}
			if(!finPos)
				endPos.awaitUninterruptibly();//waiting all of the particles update their acceleration
			
			
			//updating acceleration
			for(int i = begin; i < end; i++) {
				finAcc = this.systemState.updateAcceleration(i);
				if(finAcc)
					endAcc.signalAll();
			}
			if(!finAcc)
				endAcc.awaitUninterruptibly();//waiting all of the particles update their acceleration
		}
	}
}
