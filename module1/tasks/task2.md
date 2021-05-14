## Task 2 Deadlock troubleshooting
#### Get deadlock
- Execute java application that simulates deadlock:

```
    java -jar deadlock-1.0.0-SNAPSHOT.jar
```

- Get thread dump and locate lines similar to:

```
Found one Java-level deadlock:
=============================
"Thread 2":
  waiting to lock monitor 0x000000001bf40b68 (object 0x000000076b7777c8, a java.lang.Object),
  which is held by "Thread 1"
"Thread 1":
  waiting to lock monitor 0x000000001bf43608 (object 0x000000076b7777d8, a java.lang.Object),
  which is held by "Thread 2"

Java stack information for the threads listed above:
===================================================
"Thread 2":
        at com.epam.jmp.mat.deadlock.SimulateDeadLock.method2(SimulateDeadLock.java:44)
        - waiting to lock <0x000000076b7777c8> (a java.lang.Object)
        - locked <0x000000076b7777d8> (a java.lang.Object)
        at com.epam.jmp.mat.deadlock.DeadLockMain$2.run(DeadLockMain.java:18)
"Thread 1":
        at com.epam.jmp.mat.deadlock.SimulateDeadLock.method1(SimulateDeadLock.java:24)
        - waiting to lock <0x000000076b7777d8> (a java.lang.Object)
        - locked <0x000000076b7777c8> (a java.lang.Object)
        at com.epam.jmp.mat.deadlock.DeadLockMain$1.run(DeadLockMain.java:11)

Found 1 deadlock.
```

#### Get thread dump

**RESULTS:**

1} jstack
```
    jstack -l 29681
    
    Found one Java-level deadlock:
    =============================
    "Thread 2":
      waiting to lock monitor 0x00007ffaf8006528 (object 0x000000076d095fb8, a java.lang.Object),
      which is held by "Thread 1"
    "Thread 1":
      waiting to lock monitor 0x00007ffaf8005088 (object 0x000000076d095fc8, a java.lang.Object),
      which is held by "Thread 2"
    
    Java stack information for the threads listed above:
    ===================================================
    "Thread 2":
    	at com.epam.jmp.mat.deadlock.SimulateDeadLock.method2(SimulateDeadLock.java:44)
    	- waiting to lock <0x000000076d095fb8> (a java.lang.Object)
    	- locked <0x000000076d095fc8> (a java.lang.Object)
    	at com.epam.jmp.mat.deadlock.DeadLockMain$2.run(DeadLockMain.java:18)
    "Thread 1":
    	at com.epam.jmp.mat.deadlock.SimulateDeadLock.method1(SimulateDeadLock.java:24)
    	- waiting to lock <0x000000076d095fc8> (a java.lang.Object)
    	- locked <0x000000076d095fb8> (a java.lang.Object)
    	at com.epam.jmp.mat.deadlock.DeadLockMain$1.run(DeadLockMain.java:11)
    
    Found 1 deadlock.

```
2} kill -3
```
    kill -3 29681
    
    Found one Java-level deadlock:
    =============================
    "Thread 2":
      waiting to lock monitor 0x00007ffaf8006528 (object 0x000000076d095fb8, a java.lang.Object),
      which is held by "Thread 1"
    "Thread 1":
      waiting to lock monitor 0x00007ffaf8005088 (object 0x000000076d095fc8, a java.lang.Object),
      which is held by "Thread 2"
    
    Java stack information for the threads listed above:
    ===================================================
    "Thread 2":
    	at com.epam.jmp.mat.deadlock.SimulateDeadLock.method2(SimulateDeadLock.java:44)
    	- waiting to lock <0x000000076d095fb8> (a java.lang.Object)
    	- locked <0x000000076d095fc8> (a java.lang.Object)
    	at com.epam.jmp.mat.deadlock.DeadLockMain$2.run(DeadLockMain.java:18)
    "Thread 1":
    	at com.epam.jmp.mat.deadlock.SimulateDeadLock.method1(SimulateDeadLock.java:24)
    	- waiting to lock <0x000000076d095fc8> (a java.lang.Object)
    	- locked <0x000000076d095fb8> (a java.lang.Object)
    	at com.epam.jmp.mat.deadlock.DeadLockMain$1.run(DeadLockMain.java:11)
    
    Found 1 deadlock.
    
    Heap
     PSYoungGen      total 74752K, used 45162K [0x000000076d000000, 0x0000000772300000, 0x00000007c0000000)
      eden space 64512K, 70% used [0x000000076d000000,0x000000076fc1aaa0,0x0000000770f00000)
      from space 10240K, 0% used [0x0000000771900000,0x0000000771900000,0x0000000772300000)
      to   space 10240K, 0% used [0x0000000770f00000,0x0000000770f00000,0x0000000771900000)
     ParOldGen       total 171008K, used 0K [0x00000006c7000000, 0x00000006d1700000, 0x000000076d000000)
      object space 171008K, 0% used [0x00000006c7000000,0x00000006c7000000,0x00000006d1700000)
     Metaspace       used 8983K, capacity 9310K, committed 9344K, reserved 1056768K
      class space    used 1029K, capacity 1130K, committed 1152K, reserved 1048576K

```
3} jvisualvm

**RESULT:** [VisualVM screenshot][1]

4} Windows (Ctrl + Break)

5} jcmd
```
    jcmd <pid> Thread.print
    
    Found one Java-level deadlock:
    =============================
    "Thread 2":
      waiting to lock monitor 0x00007ffaf8006528 (object 0x000000076d095fb8, a java.lang.Object),
      which is held by "Thread 1"
    "Thread 1":
      waiting to lock monitor 0x00007ffaf8005088 (object 0x000000076d095fc8, a java.lang.Object),
      which is held by "Thread 2"
    
    Java stack information for the threads listed above:
    ===================================================
    "Thread 2":
    	at com.epam.jmp.mat.deadlock.SimulateDeadLock.method2(SimulateDeadLock.java:44)
    	- waiting to lock <0x000000076d095fb8> (a java.lang.Object)
    	- locked <0x000000076d095fc8> (a java.lang.Object)
    	at com.epam.jmp.mat.deadlock.DeadLockMain$2.run(DeadLockMain.java:18)
    "Thread 1":
    	at com.epam.jmp.mat.deadlock.SimulateDeadLock.method1(SimulateDeadLock.java:24)
    	- waiting to lock <0x000000076d095fc8> (a java.lang.Object)
    	- locked <0x000000076d095fb8> (a java.lang.Object)
    	at com.epam.jmp.mat.deadlock.DeadLockMain$1.run(DeadLockMain.java:11)
    
    Found 1 deadlock.

```

[1]: attachments/threaddump.png