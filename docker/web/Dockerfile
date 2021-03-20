FROM maven:3-adoptopenjdk-8 as builder
COPY charana-core /charana-core
WORKDIR /charana-core
RUN mvn install
COPY charana-war /charana-war
WORKDIR /charana-war
RUN mvn package

FROM adoptopenjdk/openjdk8:alpine as runner
RUN apk -U --no-cache add curl zip && \
    curl -o glassfish-5.1.0.zip http://ftp.yz.yamagata-u.ac.jp/pub/eclipse/glassfish/glassfish-5.1.0.zip && \
    unzip glassfish-5.1.0.zip -d / && \
    rm -f glassfish-5.1.0.zip && \
    apk del -r curl zip
ENV GLASSFISH_HOME /glassfish5
ENV PATH $PATH:$GLASSFISH_HOME/bin
COPY --from=builder /charana-war/target/charana-war-*.war /charana.war
RUN asadmin start-domain && \
    asadmin deploy /charana.war && \
    asadmin stop-domain && \
    rm /charana.war
EXPOSE 8080
CMD asadmin start-domain && tail -f /glassfish5/glassfish/domains/domain1/logs/server.log
