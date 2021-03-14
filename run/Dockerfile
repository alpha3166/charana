FROM    adoptopenjdk/openjdk8:alpine
RUN     apk update && apk add curl zip && \
        curl -o glassfish-5.1.0.zip http://ftp.yz.yamagata-u.ac.jp/pub/eclipse/glassfish/glassfish-5.1.0.zip && \
        unzip glassfish-5.1.0.zip -d / && \
        rm -f glassfish-5.1.0.zip
EXPOSE  8080 8181 4848
ENV     GLASSFISH_HOME /glassfish5
ENV     PATH $PATH:$GLASSFISH_HOME/bin
CMD     ["tail", "-f", "/dev/null"]
