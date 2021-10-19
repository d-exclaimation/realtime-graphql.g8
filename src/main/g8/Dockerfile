FROM hseeberger/scala-sbt:17.0.0_1.5.5_2.12.15 as build

# Install Dependecies
WORKDIR /app

COPY project project
COPY build.sbt .
RUN sbt update

# Build jar
COPY . .
RUN sbt stage

FROM openjdk:18-jdk-alpine3.13

WORKDIR /app

# Get the jars
COPY --from=build /app/target/universal/stage/. .

ENV SCALA_ENV production

RUN mv bin/$(ls bin | grep -v .bat) bin/start
CMD ["./bin/start"]