FROM eclipse-temurin:17-alpine as builder
RUN apk --update-cache add maven
COPY charana-core /charana-core
WORKDIR /charana-core
RUN mvn install
COPY charana-web /charana-web
WORKDIR /charana-web
RUN mvn package

FROM eclipse-temurin:17-jre-alpine as runner
COPY --from=builder /charana-web/target/charana-web-*.jar /charana-web.jar
EXPOSE 8080
CMD java -jar /charana-web.jar
