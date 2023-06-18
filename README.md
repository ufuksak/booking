# Spring Boot GitHub repo REST Call: Building Rest API with Spring Data JPA

## Run Spring Boot application

1. Set the JAVA_HOME, min jdk11 is required

```
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-11.0.13.jdk/Contents/Home
```

2. Install the Application

```
mvn clean install
```
3. The Test Results should be similar

```
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- maven-jar-plugin:3.2.2:jar (default-jar) @ be-source ---
[INFO] Building jar: /Users/ufuksakar/Downloads/lead-dev-challenge-ufuksak 2/lead-dev-challenge-ufuksak/booking/target/be-source-0.0.1-SNAPSHOT.jar
[INFO] 
[INFO] --- spring-boot-maven-plugin:2.7.0:repackage (repackage) @ be-source ---
[INFO] Replacing main artifact with repackaged archive
[INFO] 
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ be-source ---
[INFO] Installing /Users/ufuksakar/Downloads/lead-dev-challenge-ufuksak 2/lead-dev-challenge-ufuksak/booking/target/be-source-0.0.1-SNAPSHOT.jar to /Users/ufuksakar/.m2/repository/com/ufuksakar/be-source/0.0.1-SNAPSHOT/be-source-0.0.1-SNAPSHOT.jar
[INFO] Installing /Users/ufuksakar/Downloads/lead-dev-challenge-ufuksak 2/lead-dev-challenge-ufuksak/booking/pom.xml to /Users/ufuksakar/.m2/repository/com/ufuksakar/be-source/0.0.1-SNAPSHOT/be-source-0.0.1-SNAPSHOT.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.931 s
[INFO] Finished at: 2023-06-18T23:07:25+03:00
[INFO] ------------------------------------------------------------------------
ufuksakar@ufuks-MacBook-Pro booking % mvn clean install    
```
4. Run the Application

```
mvn spring-boot:run
```
