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
	
	//Hello World Thread 1
    Runnable HelloWorld1 = new Runnable() {
		public void run() {
	        System.out.println("Hello World!");
		}
	};
    KThread newThread1 = new KThread(HelloWorld1);
    newThread1.setName("HelloWorldThread-1");
    HelloWorld1.run();
    
    //Hello World Thread 2
    Runnable HelloWorld2 = new Runnable() {
		public void run() {
	        System.out.println("Hello World!");
		}
	};
    KThread newThread2 = new KThread(HelloWorld2);
    newThread2.setName("HelloWorldThread-2");
    HelloWorld2.run();
	
	KThread.yield();
	System.out.println("**** Simple KThread testing ends ****");
    }
}