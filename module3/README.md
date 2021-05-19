### Task 1

Please, complete the following task: Factorial via FJP
Use FJP to calculate the factorial. Compare with the sequential implementation. Use BigInteger to keep values. 

**RESULTS:** Please see [FjpFactorialCalculator][1] and [FactorialCalculatorTest][2]

### Task 2

Please, complete the following task: Multithreading Sorting via FJP
Implement Merge Sort or Quick Sort algorithm that sorts a huge array of integers in parallel using Fork/Join framework.

**RESULTS:** Please see [ArrayMergeSorter][3] and [ArrayMergeSorterTest][4]

### Task 3

Please, complete the following task: File Scanner via FJP
Create CLI application that scans a specified folder and provides detailed statistics:

- File count.

 - Folder count.

 - Size (sum of all files size) (similar to Windows context menu Properties). Since the folder may contain a huge number of files the scanning process should be executed in a separate thread displaying an informational message with some simple animation like the progress bar in CLI (up to you, but I'd like to see that task is in progress).
Once the task is done, the statistics should be displayed in the output immediately. Additionally, there should be the ability to interrupt the process by pressing some reserved key (for instance c). Of course, use Fork-Join Framework for implementation parallel scanning.  

**RESULTS:** Please see [FjpFileScanner][5] and [SearchTask][6] and [KeyboardListener][7]


### Task 4

Please, complete the following task: Completable Future Helps to Build Open Salary Society 
Assume, we have REST endpoint that returns a list of hired Employees.

 - REST endpoint is wrapped by Java service class that consuming this endpoint.
 - Fetch a list of Employee objects asynchronously by calling the hiredEmployees().
 - Join another CompletionStage<List> that takes care of filling the salary of each hired employee, by calling the getSalary(hiredEmployeeId) method which returns a CompletionStage that asynchronously fetches the salary (again could be consuming a REST endpoint).
 - When all Employee objects are filled with their salaries, we end up with a List<CompletionStage>, so we call <special operation on CF> to get a final stage that completes upon completion of all these stages.
 - Print hired Employees with their salaries via <special operation on CF> on the final stage.
 
Provide correct solution with CF usage and use appropriate CF operators instead <special operation on CF>. Why does the CF usage improve performance here in comparison with the synchronous approach? Discuss it with a mentor. How thread waiting is implemented in a synchronous world?

**RESULTS:** Please see [OpenSalarySociety][8] and [EmployeeRestClient][9] and [OpenSalarySocietyTest][10]
   
[1]: src/main/java/com/epam/multithreading/task1/FjpFactorialCalculator.java
[2]: src/test/java/com/epam/multithreading/task1/FactorialCalculatorTest.java
[3]: src/main/java/com/epam/multithreading/task2/ArrayMergeSorter.java
[4]: src/test/java/com/epam/multithreading/task2/ArrayMergeSorterTest.java
[5]: src/main/java/com/epam/multithreading/task3/FjpFileScanner.java
[6]: src/main/java/com/epam/multithreading/task3/SearchTask.java
[7]: src/main/java/com/epam/multithreading/task3/KeyboardListener.java
[8]: src/main/java/com/epam/multithreading/task4/OpenSalarySociety.java
[9]: src/main/java/com/epam/multithreading/task4/EmployeeRestClient.java
[10]: src/test/java/com/epam/multithreading/task4/OpenSalarySocietyTest.java