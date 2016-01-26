# Question #2: Reading the Code (8pts) #
_Answer the following questions and the code in KThread.java. The goals here is for you to read the code and the comments therein._

**When does the Kernel get rid of the TCB for a thread that has completed? Do not answer "at line xxx" or "in method xxx", but in terms of OS events (e.g., "When a new thread is forked", "When interrupts are enabled", "When the ready queue becomes empty")**

> the Kernel gets rid of the TCB for a thread that has completed by restoring the tcb state of a previous saved tcb state


**Why is the sleep() method static, while the ready() method isn't?**

> sleep() does not use any instance variables from the KThread class it is defined in. It can only use things that are defined as static, local variables and parameters

> ready() does use the instance variable(s) from the KThread class (i.e. status)

**Why is there that call to ready() in the fork() method?**

> moves the current thread to the ready state and to the ready queue


**At which line in KThread.java is the CPU scheduler called to decide which KThread should be given the CPU?**

> the first line in the runNextThread() method: KThread nextThread = readyQueue.nextThread(); readyQueue is the thread queue for the scheduler and nextThread() method gets the next KThread