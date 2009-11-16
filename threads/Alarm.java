package nachos.threads;
import java.util.LinkedList;
import nachos.machine.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * Uses the hardware timer to provide preemption, and to allow threads to sleep
 * until a certain time.
 */
public class Alarm {
 /**
  * Allocate a new Alarm. Set the machine's timer interrupt handler to this
  * alarm's callback.
  *
  * <p><b>Note</b>: Nachos will not function correctly with more than one
  * alarm.
  */
  
  public Alarm() {
	Machine.timer().setInterruptHandler(new Runnable() {
		public void run() { timerInterrupt(); }
	  });
  }

  /**
   * The timer interrupt handler. This is called by the machine's timer
   * periodically (approximately every 500 clock ticks). Causes the current
   * thread to yield, forcing a context switch if there is another thread
   * that should be run.
   */
  public void timerInterrupt() {
    Lib.debug(dbgAlarm,"In Interrupt Handler (time = "+Machine.timer().getTime()+")");
    //go through list of alarmThreads
    for(int i=0;i<alarmList.size();i++){

      //if alarmThread wait time is expired
      if(alarmList.get(i).getWakeTime()<=Machine.timer().getTime()){
        
        //put the thread in the ready state and ready queue
        boolean status = Machine.interrupt().disable();
        alarmList.get(i).getThread().ready();
        
        //remove alarm thread from list and keep dec counter 
        //so after loop finishes, counter will inc and will be the same
        //when remove() is called, the elements are shifted left
        alarmList.remove(i--);
        Machine.interrupt().restore(status);
      }
    }
    KThread.currentThread().yield();
  }

  /**
   * Put the current thread to sleep for at least <i>x</i> ticks,
   * waking it up in the timer interrupt handler. The thread must be
   * woken up (placed in the scheduler ready set) during the first timer
   * interrupt where
   *
   * <p><blockquote>
   * (current time) >= (WaitUntil called time)+(x)
   * </blockquote>
   *
   * @param	x	the minimum number of clock ticks to wait.
   *
   * @see	nachos.machine.Timer#getTime()
   */
  public void waitUntil(long x) {
	// This is a bad busy waiting solution 
//	 long wakeTime = Machine.timer().getTime() + x;
//	 while (wakeTime > Machine.timer().getTime())
//	  KThread.yield();
    wakeTime = Machine.timer().getTime() + x; //calc wake time
    Lib.debug(dbgAlarm,"In Wait Until (wakeTime = "+wakeTime+")");
    
    //if wakeTime did not pass
    if(wakeTime > Machine.timer().getTime()){
      boolean status = Machine.interrupt().disable();      
      
      //create alarmThread holding wait time and thread
      alarmThread aThread = new alarmThread(wakeTime, KThread.currentThread());
      
      //add to alarmThread list
      alarmList.add(aThread);
      
      //put currentthread to sleep
      KThread.currentThread().sleep();
      Machine.interrupt().restore(status);
    }
  }
  
  //alarmThread class, holds waitTime and currentThread
  public class alarmThread{
    private KThread thread;
    private long wakeTime;

    //alarmThread constructor
    public alarmThread (long wt, KThread t) {
      wakeTime = wt;
      thread = t;
    }

    //get wake time method
    public long getWakeTime(){
      return wakeTime;
    }

    //get thread method
    public KThread getThread(){
      return thread;
    }
  }

  //variables to use with waitUntil and timerInterrupt
  private static long wakeTime;
  private static ArrayList<alarmThread> alarmList = new ArrayList<alarmThread>();

  /**
   * Tests whether this module is working.
   */
  public static void selfTest() {
	AlarmTest.runTest();
  }

  private static final char dbgAlarm = 'a';
}