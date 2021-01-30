FROM    ubuntu
# Suppress the warning "debconf: delaying package configuration, since apt-utils is not installed" from apt-get
ENV     DEBCONF_NOWARNINGS yes
RUN     sed -i.org -e 's#//archive#//jp.archive#' /etc/apt/sources.list && \
        apt-get update && \
        apt-get full-upgrade -y
# Suppress the timezone selection prompt while installing tzdata
ENV     DEBIAN_FRONTEND noninteractive
RUN     apt-get install -y openjdk-8-jdk && \
        apt-get install -y maven
RUN     apt-get install -y curl unzip && \
        curl -o /tmp/glassfish-5.1.0.zip http://ftp.yz.yamagata-u.ac.jp/pub/eclipse/glassfish/glassfish-5.1.0.zip && \
        unzip /tmp/glassfish-5.1.0.zip -d /usr/share && \
        rm -f /tmp/glassfish-5.1.0.zip
EXPOSE  8080 8181 4848
ENV     GLASSFISH_HOME /usr/share/glassfish5
ENV     PATH $PATH:$GLASSFISH_HOME/bin
CMD     ["tail", "-f", "/dev/null"]
