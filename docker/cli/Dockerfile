FROM maven:3-adoptopenjdk-8 as builder
COPY charana-core /charana-core
WORKDIR /charana-core
RUN mvn install
COPY charana-cli /charana-cli
WORKDIR /charana-cli
RUN mvn package

FROM adoptopenjdk/openjdk8:alpine as runner
COPY --from=builder /charana-cli/target/charana-cli-0.1.0-jar-with-dependencies.jar /charana.jar
ENTRYPOINT ["java", "-jar", "/charana.jar"]
