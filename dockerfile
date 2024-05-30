FROM gradle:latest AS builder

WORKDIR /app

COPY . .

RUN ./gradlew openapi3

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=builder /app /app

CMD ["java", "-jar", "/app/build/libs/your-app.jar"]