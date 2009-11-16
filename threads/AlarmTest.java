package nachos.threads;

import nachos.machine.*;

/**
 * A Tester for the Alarm class
 */
public class AlarmTest {

    //aTest class--waits and prints when finished waiting
    private static class aTest implements Runnable {
        //wait time
        private long wTime;

        //aTest constructor
        public aTest(long x) {
          wTime=x;
        }
        
        public void run() {
            //set wait time for thread
            ThreadedKernel.alarm.waitUntil(wTime);
            
            //finished waiting
            System.out.println("Alarm Ringing! (time = "
            +Machine.timer().getTime()+")");
        }
    }

    public static void runTest() {
	    System.out.println("**** Alarm testing begins ****");

      //wait intervals
      long wTime1=500000;
      long wTime2=1000000;
      long wTime3=5000000;
      
      //create threads for each new aTest object

      KThread aThread1 = new KThread( new aTest(wTime1) );
      KThread aThread2 = new KThread( new aTest(wTime2) );
      KThread aThread3 = new KThread( new aTest(wTime3) );
      
      //name each thread
      aThread1.setName( "aThread-1" );
      aThread2.setName( "aThread-2" );
      aThread3.setName( "aThread-3" );

      //run threads with alarms      
      aThread1.fork();
      aThread2.fork();
      aThread3.fork();
      
      //join threads with alarms
      aThread1.join();
      aThread2.join();
      aThread3.join();
      
  	  KThread.yield();
    	System.out.println("**** Alarm testing end ****");
    }
}