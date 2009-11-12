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
	
	//Hello World 1
    Runnable HelloWorld1 = new Runnable() {
		public void run() {
	        System.out.println("Hello World!");
		}
	};
	
	//Hello World Thread 1 and run
    KThread newThread1 = new KThread(HelloWorld1);
    newThread1.setName("HelloWorldThread-1");
    Lib.debug('t',"Thread:" + newThread1.toString());
    HelloWorld1.run();
	
    //Hello World 2
    Runnable HelloWorld2 = new Runnable() {
		public void run() {
	        System.out.println("Hello World!");
		}
	};
 
    //Hello World Thread 2 and run
    KThread newThread2 = new KThread(HelloWorld2);
    newThread2.setName("HelloWorldThread-2");
    Lib.debug('t',"Thread:" + newThread2.toString());
    HelloWorld2.run();

	KThread.yield();
	System.out.println("**** Simple KThread testing ends ****");
    }
}