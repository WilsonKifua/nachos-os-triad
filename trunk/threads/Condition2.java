package nachos.threads;

import nachos.machine.*;

/**
 * An implementation of condition variables that disables interrupt()s for synchronization.
 * 
 * <p>
 * You must implement this.
 * 
 * @see nachos.threads.Condition
 */
public class Condition2 {
  private static final char dbgCondition = 'c';
  private Lock conditionLock;
  private ThreadQueue waitQueue = ThreadedKernel.scheduler.newThreadQueue(false);

  /**
   * Allocate a new condition variable.
   * 
   * @param conditionLock the lock associated with this condition variable. The current thread must
   * hold this lock whenever it uses <tt>sleep()</tt>, <tt>wake()</tt>, or <tt>wakeAll()</tt>.
   */
  public Condition2(Lock conditionLock) {
    this.conditionLock = conditionLock;
  }

  /**
   * Atomically release the associated lock and go to sleep on this condition variable until another
   * thread wakes it using <tt>wake()</tt>. The current thread must hold the associated lock. The
   * thread will automatically reacquire the lock before <tt>sleep()</tt> returns.
   */
  public void sleep() {
    /* If the current thread doesn't hold the lock, then abort */
    Lib.assertTrue(conditionLock.isHeldByCurrentThread());
    boolean status = Machine.interrupt().disable();
    conditionLock.release();
    waitQueue.waitForAccess(KThread.currentThread());
    KThread.sleep();
    // acquires lock after being woke up
    conditionLock.acquire();
    Machine.interrupt().restore(status);

  }

  /**
   * Wake up at most one thread sleeping on this condition variable. The current thread must hold
   * the associated lock.
   */
  public void wake() {
    //if current thread doesn't hold the lock, then abort
    Lib.assertTrue(conditionLock.isHeldByCurrentThread());
    boolean status = Machine.interrupt().disable();
    KThread thread = waitQueue.nextThread();
    //wakes up thread
    if (thread != null) {
      thread.ready();
    }
    Machine.interrupt().restore(status);

  }

  /**
   * Wake up all threads sleeping on this condition variable. The current thread must hold the
   * associated lock.
   */
  public void wakeAll() {
    Lib.assertTrue(conditionLock.isHeldByCurrentThread());
    boolean status = Machine.interrupt().disable();
    //goes through the thread queue and wakes up all the threads
    for(KThread thread = waitQueue.nextThread(); thread != null; thread = waitQueue.nextThread()){
      thread.ready();
    }
    Machine.interrupt().restore(status);
  }

  /**
   * Tests whether this module is working.
   */
  public static void selfTest() {
    Condition2Test.runTest();
  }
}
