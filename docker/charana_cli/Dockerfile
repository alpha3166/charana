FROM adoptopenjdk/openjdk8:alpine as builder
RUN apk -U add maven
COPY charana-core /charana-core
WORKDIR /charana-core
RUN mvn install
COPY charana-cli /charana-cli
WORKDIR /charana-cli
RUN mvn package

FROM adoptopenjdk/openjdk8:alpine-jre as runner
COPY --from=builder /charana-cli/target/charana-cli-*-jar-with-dependencies.jar /charana.jar
ENTRYPOINT ["java", "-jar", "/charana.jar"]
