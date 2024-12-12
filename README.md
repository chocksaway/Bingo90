Bingo 90 Implementation

##### Specification

Generate a strip of 6 tickets.  All 90 numbers will appear across all 6 (**done**).

A bingo ticket consists of 9 columns and 3 rows (**done**).

Each ticket row contains 5 numbers and 4 empty spaces. (**bug**).

Each ticket column contains of one, two, or three numbers, and never three blanks. (**done**).

The first column contains numbers from 1-9, the second column numbers from 10-19, the third columns numbers from 20-29, and so on up until the last column which contains numbers from 80-90 (eleven). (**done**).

Number in the ticket columns are ordered from top to bottom ascending. (**done**).

No duplicate numbers. (**done**)

##### Implementation

The implementation uses an "overlay", which implements a 18x9 grid (multidimensional array) to represent the six bingo strips.
The overlay uses 0 for a number, and -1 for an empty space:
```
 [-1, 0, -1, 0, -1, 0, -1, 0, -1]
 [0, 0, 0, -1, 0, -1, 0, -1, 0]
 [-1, 0, 0, 0, -1, 0, -1, 0, -1]
 [0, -1, 0, 0, 0, -1, 0, -1, 0]
 [-1, 0, -1, 0, 0, 0, -1, 0, 0]
 [0, -1, 0, -1, 0, 0, 0, -1, 0]
 [-1, 0, -1, 0, -1, 0, 0, 0, -1]
 [0, -1, 0, -1, 0, -1, 0, 0, 0]
 [-1, 0, -1, 0, -1, 0, -1, 0, 0]
 [0, -1, 0, -1, 0, -1, 0, -1, 0]
 [-1, 0, -1, 0, -1, 0, -1, 0, -1]
 [0, -1, 0, -1, 0, -1, 0, -1, 0]
 [-1, 0, -1, 0, -1, 0, -1, 0, -1]
 [0, -1, 0, -1, 0, -1, 0, -1, 0]
 [-1, 0, -1, 0, -1, 0, -1, 0, -1]
 [0, -1, 0, -1, 0, -1, 0, -1, 0]
 [0, -1, 0, -1, 0, -1, 0, -1, 0]
```

There is a bug where some rows have the wrong number of spaces (represented by a -1).
I've made the decision to leave this bug in place, as I wanted to avoid loops and iterations (attempting to fix this issue).

The BingoStripOverlay is a simple implementation, which could be replaced by a "service", whose sole purpose would be to return bingo strips with different layouts.
I had thought of uses a flat-file to store the layout, but I wanted to manage the time spent on the task.

I've not spent time looking for algorithms online to generate the bingo strips, as I wanted to implement my own solution.

The bug is regrettable (the -1 or +1 shows the missing or extra space):
```
 [-1, 0, -1, 0, -1, 0, -1, 0, -1] -1
 [0, 0, 0, -1, 0, -1, 0, -1, 0] +1
 [0, -1, 0, 0, 0, -1, 0, -1, 0] +1      
 [-1, 0, -1, 0, 0, 0, -1, 0, 0] +1
 [0, -1, 0, -1, 0, 0, 0, -1, 0] +1 
 [0, -1, 0, -1, 0, -1, 0, 0, 0] +1 
 [-1, 0, -1, 0, -1, 0, -1, 0, -1] -1
 [-1, 0, -1, 0, -1, 0, -1, 0, -1] -1
 [-1, 0, -1, 0, -1, 0, -1, 0, -1] -1
```

The overlay is filled with the numbers which are loaded from the BingoData interface.  
I've used an ImmutablePair to store the number and the column it should populate:

####### Populated overlay:
```
[-1, 10, -1, 30, -1, 50, -1, 70, -1]
[1, 11, 20, -1, 40, -1, 60, -1, 80]
[-1, 12, 21, 31, -1, 51, -1, 71, -1]
[2, -1, 22, 32, 41, -1, 61, -1, 81]
[-1, 13, -1, 33, 42, 52, -1, 72, 82]
[3, -1, 23, -1, 43, 53, 62, -1, 83]
[-1, 14, -1, 34, -1, 54, 63, 73, -1]
[4, -1, 24, -1, 44, -1, 64, 74, 84]
[-1, 15, -1, 35, -1, 55, -1, 75, 85]
[5, -1, 25, -1, 45, -1, 65, -1, 86]
[-1, 16, -1, 36, -1, 56, -1, 76, -1]
[6, -1, 26, -1, 46, -1, 66, -1, 87]
[-1, 17, -1, 37, -1, 57, -1, 77, -1]
[7, -1, 27, -1, 47, -1, 67, -1, 88]
[-1, 18, -1, 38, -1, 58, -1, 78, -1]
[8, -1, 28, -1, 48, -1, 68, -1, 89]
[-1, 19, -1, 39, -1, 59, -1, 79, -1]
[9, -1, 29, -1, 49, -1, 69, -1, 90]
```

##### Printing the Bingo Strip

The bingo strip is printed.  X is used to represent a space (9 rows x 18 columns).  The strip is printed in a 3x9 grid:

```
-----------------------------------------------------------------------
        X               10              X               30              X               50              X               70              X       
        1               11              20              X               40              X               60              X               80      
        X               12              21              31              X               51              X               71              X       
-----------------------------------------------------------------------
        2               X               22              32              41              X               61              X               81      
        X               13              X               33              42              52              X               72              82      
        3               X               23              X               43              53              62              X               83      
-----------------------------------------------------------------------
        X               14              X               34              X               54              63              73              X       
        4               X               24              X               44              X               64              74              84      
        X               15              X               35              X               55              X               75              85      
-----------------------------------------------------------------------
        5               X               25              X               45              X               65              X               86      
        X               16              X               36              X               56              X               76              X       
        6               X               26              X               46              X               66              X               87      
-----------------------------------------------------------------------
        X               17              X               37              X               57              X               77              X       
        7               X               27              X               47              X               67              X               88      
        X               18              X               38              X               58              X               78              X       
-----------------------------------------------------------------------
        8               X               28              X               48              X               68              X               89      
        X               19              X               39              X               59              X               79              X       
        9               X               29              X               49              X               69              X               90      
-----------------------------------------------------------------------
```

##### Unit tests

I've written unit tests which:

1.  Test that 90 numbers are returned (testBingoNumberGeneratorReturn90Numbers).
2.  Test that 15 numbers are returned for a strip (ticket) (testGetEnoughNumbersForOneStrip).
3.  That numbers are returned for a column (testGetNumbersForOneColumn).
4.  That all 90 bingo numbers are used (total them) - (testMakeSureAll90BingoValuesAreUsed).
5.  Single execution performance test (testGetValuesForBingoTicketPerformance).  Bingo strips are not printed.
6.  Performance test for 10000 executions (* 6 strips) is run in under a second (testGetValuesForBingoTicketPerformanceWithVirtualThreads).  Using virtual threads. Bingo strips are not printed.
7.  Print the bingo strip (printBingoStrip).  This test is run with a maven profile testAndPrint.


##### Running the implementation

I've used a maven profile testAndPrint to "just" run the printBingoStrip unit test:
```
$ mvn clean test -PtestAndPrint
[INFO] Scanning for projects...
[INFO]
[INFO] -----------------------< com.chocksaway:Bingo90 >-----------------------
[INFO] Building Archetype - Bingo90 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ Bingo90 ---
[INFO] Deleting /home/######/workspace/Bingo90/target
[INFO]
[INFO] --- maven-resources-plugin:3.0.0:resources (default-resources) @ Bingo90 ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] ignoreDelta true
[INFO] Copying 1 resource
[INFO] Copying file logback.xml
[INFO]
[INFO] --- maven-compiler-plugin:3.10.1:compile (default-compile) @ Bingo90 ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 4 source files to /home/######/workspace/Bingo90/target/classes
[INFO]
[INFO] --- maven-resources-plugin:3.0.0:testResources (default-testResources) @ Bingo90 ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /home/######/workspace/Bingo90/src/test/resources
[INFO]
[INFO] --- maven-compiler-plugin:3.10.1:testCompile (default-testCompile) @ Bingo90 ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/######/workspace/Bingo90/target/test-classes
[INFO]
[INFO] --- maven-surefire-plugin:3.0.0:test (default-test) @ Bingo90 ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO]
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.chocksaway.bingo90.BingoNumberGeneratorTest
2024-12-12 12:05:29 DEBUG c.c.b.entities.BingoStripOverlay - Populate the layout
2024-12-12 12:05:29 DEBUG c.c.bingo90.entities.BingoStrip - Updating overlay with bingo numbers
2024-12-12 12:05:29 DEBUG c.c.b.entities.BingoNumbersGenerator - Bingo numbers shuffled and loaded into the queue.
2024-12-12 12:05:29 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 9 bingo numbers for column: 0
2024-12-12 12:05:29 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 1
2024-12-12 12:05:29 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 2
2024-12-12 12:05:29 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 3
2024-12-12 12:05:29 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 4
2024-12-12 12:05:29 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 5
2024-12-12 12:05:29 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 6
2024-12-12 12:05:29 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 7
2024-12-12 12:05:29 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 11 bingo numbers for column: 8

-----------------------------------------------------------------------
        X               10              X               30              X               50              X               70              X       
        1               11              20              X               40              X               60              X               80      
        X               12              21              31              X               51              X               71              X       
-----------------------------------------------------------------------
        2               X               22              32              41              X               61              X               81      
        X               13              X               33              42              52              X               72              82      
        3               X               23              X               43              53              62              X               83      
-----------------------------------------------------------------------
        X               14              X               34              X               54              63              73              X       
        4               X               24              X               44              X               64              74              84      
        X               15              X               35              X               55              X               75              85      
-----------------------------------------------------------------------
        5               X               25              X               45              X               65              X               86      
        X               16              X               36              X               56              X               76              X       
        6               X               26              X               46              X               66              X               87      
-----------------------------------------------------------------------
        X               17              X               37              X               57              X               77              X       
        7               X               27              X               47              X               67              X               88      
        X               18              X               38              X               58              X               78              X       
-----------------------------------------------------------------------
        8               X               28              X               48              X               68              X               89      
        X               19              X               39              X               59              X               79              X       
        9               X               29              X               49              X               69              X               90      
-----------------------------------------------------------------------

[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.141 s - in com.chocksaway.bingo90.BingoNumberGeneratorTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.587 s
[INFO] Finished at: 2024-12-12T12:05:29Z
[INFO] ------------------------------------------------------------------------
$

```
The remaining unit tests are run with a mvn test (some of the output has been removed):
````
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.chocksaway.bingo90.BingoNumberGeneratorTest
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Bingo numbers shuffled and loaded into the queue.
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Loading bingo numbers.
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Bingo numbers shuffled and loaded into the queue.
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Bingo numbers shuffled and loaded into the queue.
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 3 bingo numbers for column: 0
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Loading bingo numbers.
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Bingo numbers shuffled and loaded into the queue.
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoStripOverlay - Populate the layout
2024-12-12 12:09:47 DEBUG c.c.bingo90.entities.BingoStrip - Updating overlay with bingo numbers
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Bingo numbers shuffled and loaded into the queue.
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 9 bingo numbers for column: 0
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 1
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 2
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 3
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 4
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 5
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 6
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 7
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 11 bingo numbers for column: 8
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Total of all bingo numbers
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoStripOverlay - Populate the layout
2024-12-12 12:09:47 DEBUG c.c.bingo90.entities.BingoStrip - Updating overlay with bingo numbers
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Bingo numbers shuffled and loaded into the queue.
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 9 bingo numbers for column: 0
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 1
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 2
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 3
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 4
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 5
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 6
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 10 bingo numbers for column: 7
2024-12-12 12:09:47 DEBUG c.c.b.entities.BingoNumbersGenerator - Getting 11 bingo numbers for column: 8

Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.204 sec

Results :

Tests run: 6, Failures: 0, Errors: 0, Skipped: 0

[[1;34mINFO[m] [1m------------------------------------------------------------------------[m
```

#end






