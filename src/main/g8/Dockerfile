FROM hseeberger/scala-sbt:17.0.0_1.5.5_2.12.15 as build

# Install Dependecies
COPY project project
COPY build.sbt .
RUN sbt update

# Build jar
COPY . .
RUN sbt clean assembly

FROM openjdk:18-jdk-alpine3.13

WORKDIR /app

# Get the jars
COPY --from=build /root/target/scala-2.13/*.jar /app.jar

ENV RUNTIME_ENV production

CMD ["java", "-jar", "app.jar"]
