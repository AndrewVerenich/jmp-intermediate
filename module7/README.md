### Task 
1. Download Java SE Development Kit 8 according to your OS and processor’s architecture.

2. Install Java Development Kit according to JDK installation instructions (see also PATH and CLASSPATH).

3. Download Apache Maven 3.6.0 according to your OS and processor’s architecture.

4. Install Apache Maven according to installation instructions.

5. Install Docker 8.x+ .

6. Install Docker Compose 12.x+.

7. Create micro-services maven project with business-services and platform-services modules:
 
 <groupId>com.epam.javacc.microservices</groupId> <artifactId>microservices-parent</artifactId> <version>0.0.1-SNAPSHOT</version> <packaging>pom</packaging> <modules> <module>business-services</module> <module>platform-services</module> </modules>

8.Create platform-services maven project with discovery and apigateway modules:

<parent> <groupId>com.epam.javacc.microservices</groupId> <artifactId>microservices-parent</artifactId> <version>0.0.1-SNAPSHOT</version> </parent> <name>Java CC Micro-services Reference Example Parent</name> <artifactId>platform-services-parent</artifactId> <packaging>pom</packaging> <modules> <module>discovery</module> <module>apigateway</module> </modules>

9.Elaborate discovery module according to guide Eureka Discovery Service.

10.Elaborate apigateway module according to guide Zuul Proxy Service.

11.Create business-services maven project with common, one, two and two-api modules:

<parent> <groupId>com.epam.javacc.microservices</groupId> <artifactId>microservices-parent</artifactId> <version>0.0.1-SNAPSHOT</version> </parent> <name>Java CC Microservices Reference Example Parent</name> <artifactId>business-services-parent</artifactId> <packaging>pom</packaging> <modules> <module>one</module> <module>two</module> <module>two-api</module> <module>common</module> </modules>

12.Elaborate common module according to guide Archaius Configuration Service.

13.Elaborate one module as REST Service integrated with Servo according to guide Servo Metrics Aggregation Service (see also Eureka Discovery Service).

14.Elaborate two module as REST Service integrated with Servo according to guide Servo Metrics Aggregation Service (see also Eureka Discovery Service).

15.Integrate Docker file into all Spring Boot applications using template:

FROM java:8-jre-alpine VOLUME /tmp LABEL ${LABEL_GROUP}="${LABEL_ID}" LABEL description="${LABEL_DESCRIPTION}" COPY ${JAR_NAME_TO_RUN} /${JAR_NAME_TO_RUN} EXPOSE ${PORT_TO_EXPOSE} ENTRYPOINT ["java", "-jar", "/${JAR_NAME_TO_RUN}"]

and maven plugin:

<plugin> <groupId>org.codehaus.mojo</groupId> <artifactId>build-helper-maven-plugin</artifactId> <executions> <execution> <id>add-source</id> <phase>generate-sources</phase> <goals> <goal>add-resource</goal> </goals> <configuration> <resources> <resource> <directory>${base-path}/docker/springboot-app</directory> <targetPath>${project.build.directory}</targetPath> <filtering>true</filtering> </resource> </resources> </configuration> </execution> </executions> </plugin>

16.Elaborate docker compose:

zipkin: image: openzipkin/zipkin ports: - "9411:9411" graphite: image: graphiteapp/graphite-statsd ports: - "2003:2003" discovery: build: ../platform-services/discovery/target/docker ports: - "8761:8761" apigateway: build: ../platform-services/apigateway/target/docker ports: - "8765:8765" links: - discovery one: build: ../business-services/one/target/docker links: - zipkin - graphite - discovery two: build: ../business-services/two/target/docker links: - zipkin - graphite - discovery

17.Start services and demonstrate Discovery Service Registry, Requests via API Gateway, Zipkin Metrics and REST API requests.

**RESULTS:**
 
Build project: 
  
  ```markdown
cd module7/
mvn install
  ```
  
Run microservices via docker compose: 
    
```markdown
docker-compose up
```
Please see - [source code][1].

[1]: ../module7/