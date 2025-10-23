# Stage 1: Build the app
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

COPY mvnw .          
COPY .mvn/ .mvn
COPY pom.xml ./
COPY src ./src

# Give execute permission for mvnw
RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

# Stage 2: Run the app
FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

EXPOSE 2000

ENTRYPOINT ["/app/wait-for-it.sh", "mysqldb:3306", "--timeout=60", "--", "java", "-jar", "app.jar"]