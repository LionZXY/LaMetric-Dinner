FROM openjdk:24-slim as builder

WORKDIR /app/

COPY . .

RUN ./gradlew buildFatJar

FROM openjdk:24-slim as runner

WORKDIR /app/

COPY --from=builder /app/build/libs/lametric-dinner-*.jar web.jar
COPY static static

CMD java -jar web.jar
