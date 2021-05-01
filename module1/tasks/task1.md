## Task 1 OutOfMemory (OOM) error troubleshooting
#### Get OOM error
Execute and press any key:
```
    java -jar -Xmx100m heap-1.0.0-SNAPSHOT.jar
```

#### Use jvisualvm to observe OOM
- Execute:

```
    java -jar -Xmx100m heap-1.0.0-SNAPSHOT.jar
```
- In jvisualvm connect to our java process
- Go to "Monitor" tab
- Press any key in our application
- Observe how heap grows

**RESULT:** [VisualVM screenshot][1]

#### Get heap dump
##### Using -XX:+HeapDumpOnOutOfMemoryError option
- Execute and press any key:

```
    java -jar -Xmx100m -XX:+HeapDumpOnOutOfMemoryError heap-1.0.0-SNAPSHOT.jar
```

**RESULT:** [Heap dump on out of memory][2]

##### [Optional] Using jcmd
Get pid using `jps` here and further through this document:
```
    jps -lvm
```
```
    jcmd <pid> GC.heap_dump <filename>
```

**RESULT:** [Heap dump using jcmd][3]

##### [Optional] Using jmap
```
    jmap -dump:format=b,file=snapshot.hprof <pid>
```

**RESULT:** [Heap dump using jmap][4]

#### Get heap histogram
##### Using jcmd
```
    jcmd <pid> GC.class_histogram
```

**RESULT:** [Heap histogram using jcmd][5]

##### Using jmap
```
    jmap -histo <pid> 
```

**RESULT:** [Heap histogram using jmap][6]

#### Analyze heap dump
##### Using Java Visual VM
- Open retrieved heap dump in jvisualvm
- Identify memory leak

**RESULT:** [VisualVM screenshot][7]. OOM error occurs due to a large number of object references in ArrayList

##### OQL
Execute OQL in jvisualvm:
```
    select objs from java.lang.Object[] objs where objs.length > 100
    select referrers(objs) from java.lang.Object[] objs where objs.length > 100
    select referrers(arr) from java.util.ArrayList arr where arr.size > 100
```

**RESULTS:**
```
    select objs from java.lang.Object[] objs where objs.length > 100
    java.lang.Object[]#177 : 212 items
    java.lang.Object[]#178 : 241 items
    java.lang.Object[]#283 : 1,024 items
    java.lang.Object[]#551 : 6,153,400 items

    select referrers(objs) from java.lang.Object[] objs where objs.length > 100
    sun.nio.cs.StandardCharsets$Aliases#1
    java.util.ArrayList#1

    select referrers(arr) from java.util.ArrayList arr where arr.size > 100
    com.epam.jmp.mat.heap.Process#1
```


Startup `jhat` (note: `jhat` was decommissioned in JDK 9)
```
    jhat <head_dump.hprof>
```
Execute OQL in jhat
```
    select [objs, objs.length] from [Ljava.lang.Object; objs where objs.length > 100
    select referrers(objs) from [Ljava.lang.Object; objs where objs.length > 100
    select referrers(arr) from java.util.ArrayList arr where arr.size > 100
```
**RESULTS:**
```
    select [objs, objs.length] from [Ljava.lang.Object; objs where objs.length > 100
    [ [Ljava.lang.Object;@0xfb390c50, 1024 ]
    [ [Ljava.lang.Object;@0xfb35e060, 241 ]
    [ [Ljava.lang.Object;@0xfb35dd00, 212 ]
    [ [Ljava.lang.Object;@0xfb7470d0, 6153400 ]

    select referrers(objs) from [Ljava.lang.Object; objs where objs.length > 100
    [ sun.nio.cs.StandardCharsets$Aliases@0xfb390c28, ]
    [ java.util.ArrayList@0xfb354800, ]

    select referrers(arr) from java.util.ArrayList arr where arr.size > 100
    [ com.epam.jmp.mat.heap.Process@0xfb3547f0, ]
```

Please note small OQL syntax difference in jhat and jvisualvm.

[1]: attachments/heap.png
[2]: attachments/java_pid6933.hpro
[3]: attachments/heap_dump
[4]: attachments/snapshot.hpro
[5]: attachments/jcmd_heap_histogram.txt
[6]: attachments/jmap_heap_histogram.txt
[7]: attachments/heapdump.png
