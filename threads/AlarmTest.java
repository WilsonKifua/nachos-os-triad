package nachos.threads;

import nachos.machine.*;

/**
 * A Tester for the Alarm class
 */
public class AlarmTest {

    private static class aTest implements Runnable {
        private long wTime;
        private Alarm alarm = new Alarm();
        public aTest(long x) {
          wTime=x;
        }
        public void run() {
             ThreadedKernel.alarm.waitUntil(wTime);
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
      aTest a1 = new aTest(wTime1);
      aTest a2 = new aTest(wTime2);
      aTest a3 = new aTest(wTime3);
      KThread aThread1 = new KThread( a1 );
      KThread aThread2 = new KThread( a2 );
      KThread aThread3 = new KThread( a3 );
      
      //name each thread
      aThread1.setName( "aThread-1" );
      aThread2.setName( "aThread-2" );
      aThread3.setName( "aThread-3" );

      //run threads with alarms      
      aThread1.fork();
      aThread2.fork();
      aThread3.fork();
      
  	  KThread.yield();
    	System.out.println("**** Alarm testing end ****");
    }
}