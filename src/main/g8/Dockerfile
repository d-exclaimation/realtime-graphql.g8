FROM hseeberger/scala-sbt:17.0.0_1.5.5_2.12.15 as build

# Install Dependecies
COPY project project
COPY build.sbt .
RUN sbt update

# Build project jar
COPY . .
RUN sbt clean assembly

FROM openjdk:18-jdk-alpine3.13

WORKDIR /app

# Get the assembled jar and renamed to generic "app"
COPY --from=build /root/target/scala-2.13/*assembly*.jar /app/app.jar

# Set any constant environment variables i.e. RUNTIME_ENV, PORT, DATABASE_URL, etc.
ENV RUNTIME_ENV production

# Run the assembled jar file
CMD ["java", "-jar", "app.jar"]
