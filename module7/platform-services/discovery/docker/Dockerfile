FROM java:8-jre-alpine
VOLUME /tmp
COPY ${project.build.finalName}.jar /${project.build.finalName}.jar
EXPOSE 6001
ENTRYPOINT ["java", "-jar", "/${project.build.finalName}.jar"]