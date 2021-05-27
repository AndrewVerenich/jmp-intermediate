### Task 1

Backend Database Migration
 
Cost: 1 point.
 
Move one of your pet application/mentoring projects with DAO and simple CRUD to MongoDB.
 
Install MongoDB and use corresponding Java driver.
 
- Define data model for Mongo (the direct table-collection mapping is not the best idea).
- Write data migration job (via SQL and MongoDriver operations).
- Use aggregation mechanism to get grouped results from the database.
- (Optional, +5 bonus points) Run this job in one transaction for SQL database and in one multi-document transaction for Mongo.
- (Optional, +5 bonus points) Use appropriate indices to improve query performance.
- (Optional, +10 bonus points) Make a replica-set with 1 primary and 2 secondary nodes, execute your tests/client code over the Mongo cluster.

Note: all tasks should be implemented with newest versions of Mongo (>= 4.0) and Cassandra (>= 3.11.3). 

**RESULTS:** 

Build project: 
  
  ```markdown
cd module2/
mvn install
  ```
  
Run MongoDB via docker compose: 
    
```markdown
docker-compose up
```

Run app: 
    
```markdown
cd event-service-rest/target/
java -jar -Dserver.port=8083 event-service-rest-1.0-SNAPSHOT.jar
```
And please see changes for MongoDB [module2][1]

### Task 2

Task Manager with Mongo DB

Cost: 2 points.

Install MongoDB and use corresponding Java driver.

Create simple task manager console app. Your tasks should have next fields:

- date of creation;
- deadline;
- name;
- description;
- list of subtasks with simple structure (name/description);
- category.

Provide next operation:

- Display on console all tasks.
- Display overdue tasks.
- Display all tasks with the specific category (query parameter).
- Display all subtasks related to tasks with the specific category (query parameter).
- Perform insert/update/delete of the task.
- Perform insert/update/delete all subtasks of the given task (query parameter).
- Support full-text search by word in task description.
- Support full-text search by sub-task name.

For highest mark, you can try implement DAO with any ORM solution for Mongo (+10 bonus points).

**RESULTS:** Implemented Task Rest-service using Spring Boot

Build project: 
  
  ```markdown
cd module4/task2
mvn install
  ```
  
Run MongoDB via docker compose: 
    
```markdown
docker-compose -f docker-compose-mongo.yml up
```

Run app: 
    
```markdown
cd task2/target/
java -jar task2-1.0-SNAPSHOT.jar
```
And please see Task Manager implementation - [task2 source code][2]

### Task 3

The Cassandra Ring

Cost: 2 points.

- Install Cassandra and use corresponding Java driver.
- Build Cassandra cluster with 4 nodes.
- Balance the cluster and distribute keys.
- Provides screenshots of running cluster and filled tables.
- Write performance test app which stores logs (logs can be tracked events with different structure) from your working app into Cassandra Cluster.
- Measure different useful statistics for log writing (throughput - avg, 95th percentile and so on).
- Provide simple admin operations (to edit logs) via Cassandra Java driver.
- Use CQL to make queries for generating reports.
- List of desired reports and collected metrics should be provided by your mentor.

Note: all task should be implemented with newest versions of Mongo (>= 4.0) and Cassandra (>= 3.11.3).  

**RESULTS:** Implemented CLI app using Spring Boot

Build project: 
  
  ```markdown
cd module4/task3
mvn install
  ```
  
Run Cassandra cluster (with 4 nodes) via docker compose: 
    
```markdown
docker-compose -f docker-compose-cassandra-cluster.yml up
```

Run app: 
    
```markdown
cd task3/target/
java -jar task3-1.0-SNAPSHOT.jar
```
Please see implementation - [task3 source code][3]

[Screenshot][4] of running cluster

[Screenshot][5] of filled table

[Screenshot][6] cassandra writing statistic
   
[1]: ../module2
[2]: task2/src/main
[3]: task3/src/main
[4]: attachments/cluster.png
[5]: attachments/filledTable.png
[6]: attachments/statistics.png