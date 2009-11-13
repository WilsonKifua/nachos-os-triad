package nachos.threads;

import nachos.machine.*;

/**
 * A Simple Tester for the KThread class. 
 */
public class KThreadSimpleTest {

  /**
   * Tests whether this module is working.
   */
  public static void runTest() {

    System.out.println("**** Simple KThread testing begins ****");

  /**
   * Class for HelloWorld implements Runnable
   */
    class HelloWorld implements Runnable {
      public void run() {
        System.out.println("Hello World!");
      }
    }
    
    //create runnable HelloWorld objects
    HelloWorld hw1 = new HelloWorld();
    HelloWorld hw2 = new HelloWorld();
    
    //create threads
    KThread newThread1 = new KThread(hw1);
    KThread newThread2 = new KThread(hw2);
    
    //set thread names
    newThread1.setName("HelloWorldThread-1");
    newThread2.setName("HelloWorldThread-2");

    //start running
    newThread1.fork();
    newThread2.fork();
    
    KThread.yield();
    System.out.println("**** Simple KThread testing ends ****")
  }
}