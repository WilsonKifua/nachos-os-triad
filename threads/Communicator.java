package nachos.threads;

import nachos.machine.*;

/**
 * A <i>communicator</i> allows threads to synchronously exchange 32-bit messages. Multiple threads
 * can be waiting to <i>speak</i>, and multiple threads can be waiting to <i>listen</i>. But there
 * should never be a time when both a speaker and a listener are waiting, because the two threads
 * can be paired off at this point.
 */
public class Communicator {
  /* Lock for mutual exclusion and condition variables */
  public Lock mutex;
  /* Condition variables */
  public Condition isSpeaking;
  public Condition isListening;
  public Condition isDone;
  /* Booleans indicating buffer state */
  public boolean isEmpty;
  /* holds message */
  private int word;

  /**
   * Allocate a new communicator.
   */
  public Communicator() {
    this.mutex = new Lock();
    this.isSpeaking = new Condition(this.mutex);
    this.isListening = new Condition(this.mutex);
    this.isDone = new Condition(this.mutex);
    this.isEmpty = true;
  }

  /**
   * Wait for a thread to listen through this communicator, and then transfer <i>word</i> to the
   * listener.
   * 
   * <p>
   * Does not return until this thread is paired up with a listening thread. Exactly one listener
   * should receive <i>word</i>.
   * 
   * @param word the integer to transfer.
   */
  public void speak(int word) {
    this.mutex.acquire();
    while(isEmpty != true){
      this.isSpeaking.sleep();
    }
    this.word = word;
    this.isEmpty = false;
    this.isListening.wakeAll();
    this.mutex.release();
  }

  /**
   * Wait for a thread to speak through this communicator, and then return the <i>word</i> that
   * thread passed to <tt>speak()</tt>.
   * 
   * @return the integer transferred.
   */
  public int listen() {
    this.mutex.acquire();
    while(isEmpty != false){
      this.isListening.sleep();
    }
    int word = this.word;
    this.isEmpty = false;
    this.isSpeaking.wakeAll();
    this.mutex.release();
    return word;
  }

  /**
   * Tests whether this module is working.
   */
  public static void selfTest() {
    CommunicatorTest.runTest();
  }

}
