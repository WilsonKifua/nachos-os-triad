package nachos.threads;

import nachos.machine.*;

/**
 * A Tester for the Alarm class
 */
public class AlarmTest {

    private static class aTest implements Runnable {
        public void run() {
             System.out.println("Alarm Ringing! (time = "
             +Machine.timer().getTime()+")");
        }
    }

    public static void runTest() {
	    System.out.println("**** Alarm testing begins ****");

      //wait intervals
      long wTime1=900000;
      long wTime2=1000000;
      long wTime3=600000;


      //create alarm and test objects
      Alarm aRun = new Alarm();
      aTest aTest1 = new aTest();
      aTest aTest2 = new aTest();
      aTest aTest3 = new aTest();
      
      //create threads for each aTest object
      KThread aThread1 = new KThread( aTest1 );
      KThread aThread2 = new KThread( aTest2 );
      KThread aThread3 = new KThread( aTest3 );
      
      //name each thread
      aThread1.setName( "aThread-1" );
      aThread2.setName( "aThread-2" );
      aThread3.setName( "aThread-3" );

      //run threads with alarms      
      aRun.waitUntil(wTime1);
      aThread2.fork();
      aRun.waitUntil(wTime2);
      aThread1.fork();
      aRun.waitUntil(wTime3);
      aThread3.fork();
      
  	  KThread.yield();
    	System.out.println("**** Alarm testing end ****");
    }

}
