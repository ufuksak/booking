FROM amazoncorretto:17.0.4 as build
ARG APP_HOME=/usr/src/api
WORKDIR $APP_HOME
COPY pom.xml .
COPY .mvn ./.mvn
COPY mvnw .
RUN chmod 775 $APP_HOME/mvnw
# caching the dependency libraries on image build
RUN $APP_HOME/mvnw dependency:go-offline
COPY . .
RUN chmod 775 $APP_HOME/mvnw
RUN $APP_HOME/mvnw package -DskipTests

FROM amazoncorretto:17.0.4 as run
ARG APP_HOME=/usr/src/api
WORKDIR /root/
COPY --from=build $APP_HOME/target/be-source-0.0.1-SNAPSHOT.jar /root/api.jar
CMD java -jar /root/api.jar
