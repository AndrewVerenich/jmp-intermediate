## Task 3 Remote JVM profiling
Using [JMX Technology](https://docs.oracle.com/javase/8/docs/technotes/guides/management/agent.html)

For insecure remote connection use parameters:
```
    -Dcom.sun.management.jmxremote
    -Dcom.sun.management.jmxremote.port=7890
    -Dcom.sun.management.jmxremote.authenticate=false
    -Dcom.sun.management.jmxremote.ssl=false
```
```
    java -jar -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=7890 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false simple-1.0.0-SNAPSHOT.jar
```
Connect to JVM using jconsole:
```
    jconsole localhost:7890
```

**RESULTS:** 

[JConsole Overview][1]

[JConsole Memory][2]

[JConsole Threads][3]

[JConsole Classes][4]

[JConsole VMSummary][5]

[JConsole MBeans][6]

[1]: attachments/jconsole_overview.png
[2]: attachments/jconsole_memory.png
[3]: attachments/jconsole_threads.png
[4]: attachments/jconsole_classes.png
[5]: attachments/jconsole_vmsummary.png
[6]: attachments/jconsole_mbeans.png