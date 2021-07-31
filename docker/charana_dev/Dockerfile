FROM adoptopenjdk/openjdk8:alpine
RUN addgroup -g 1000 me && \
    adduser -u 1000 -G me -D me && \
    addgroup me wheel
RUN apk -U add sudo && \
    echo "%wheel ALL=(ALL) NOPASSWD: ALL" > /etc/sudoers.d/wheel
RUN apk -U add git
RUN apk -U add maven
RUN apk -U add curl zip && \
    curl -o glassfish-5.1.0.zip http://ftp.yz.yamagata-u.ac.jp/pub/eclipse/glassfish/glassfish-5.1.0.zip && \
    unzip glassfish-5.1.0.zip -d / && \
    rm -f glassfish-5.1.0.zip
ENV GLASSFISH_HOME /glassfish5
ENV PATH $PATH:$GLASSFISH_HOME/bin
EXPOSE 8080
