package nachos.threads;

import nachos.machine.*;

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
  Lock mutex = new Lock();
  Condition cond = new Condition(mutex);
  long wakeTime;
  
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
	mutex.acquire();  //mutex lock
	if(wakeTime<=Machine.timer().getTime()){  //test if time to wake
	  cond.wake();  //cond wake
  }
	mutex.release();  //mutex unlock
  KThread.currentThread().yield();  //yield
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

    mutex.acquire();  //mutex lock
    wakeTime = Machine.timer().getTime() + x; //calc wake time
    Lib.debug(dbgAlarm,"In Wait Until (wakeTime = "+wakeTime+")");
    if(wakeTime > Machine.timer().getTime()){ //if not wake time 
      cond.sleep(); //cond sleep, wait wake from timerInterrupt
      Lib.debug(dbgAlarm,"In Wake Up: (wakeTime = "+wakeTime+")");
    }
    mutex.release();  //mutex unlock
  }

  /**
   * Tests whether this module is working.
   */
  public static void selfTest() {
	AlarmTest.runTest();
  }

  private static final char dbgAlarm = 'a';
}