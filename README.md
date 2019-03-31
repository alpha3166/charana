# What is CharAna?

CharAna is a simple character analyzer, written in Java. It accepts a sequence of characters, or in other words, a string. It can be useful to know:

- What code points the string consists of (漢字 → <U+6F22, U+5B57>)
- What the code point notation represents (<U+6F22, U+5B57> → 漢字)
- What is the escaped form of the string (漢字 → \u6F22\u5B57)
- What are the characters the escaped string represents (\u6F22\u5B57 → 漢字)
- What are the possible encodings for the string (漢字 → UTF-8:e6bca2e5ad97)
- What characters the hexadecimal string represents (e6bca2e5ad97 → UTF-8:漢字)
- What characters contains the name (calendar → 📅,📆,🗓)

# CharAna Core

CharAna Core is a library of analytical methods for a string. Actually, it is just a thin wrapper of standard Java APIs. This core library is used from CharAna CLI and WAR.

## How to build CharAna Core

Go to the `charana-core` directory.

    cd charana/charana-core

And just `install` it with Maven. Since the CLI and WAR mentioned below depend on this core library, `mvn package` will not suffice.

    mvn install

Alternatively, you can do it with Docker.

    docker run -it --rm -v ~/.m2:/root/.m2 -v $PWD:/mnt -w /mnt maven mvn install

# CharAna CLI

CharAna CLI is Just a simple command line interface for CharAna.

## How to build CharAna CLI

Go to the `charana-cli` directory.

    cd charana/charana-cli

Build with `mvn package`.

    mvn package

Or you can do it with Docker.

    docker run -it --rm -v ~/.m2:/root/.m2 -v $PWD:/mnt -w /mnt maven mvn package

## How to run CharAna CLI

Go to the `target` directory under `charana-cli`.

    cd charana/charana-cli/target

There should be two JARs. `charana-cli-0.1.0.jar` contains only a small main class, which requires several depending JARs to be executed. `charana-cli-0.1.0-jar-with-dependencies.jar` is a "fat" JAR, which contains all necessary classes to go. So basically you can use the latter one. It takes a variable number of strings to analyze as arguments.

    java -jar charana-cli-0.1.0-jar-with-dependencies.jar 漢字

Or you can do it with Docker. Please beware to set locale to UTF-8. Language and country part will not matter.

    docker run -it --rm -v $PWD:/mnt -w /mnt -e LANG=en_US.utf8 openjdk java -jar charana-cli-0.1.0-jar-with-dependencies.jar 漢字
 
# CharAna WAR

CharAna WAR is a web application interface for CharAna, based on JavaServer Faces.

## How to build CharAna WAR

Go to the `charana-war` directory.

    cd charana/charana-war

Build with `mvn package`.

    mvn package

Or you can do it with Docker.

    docker run -it --rm -v ~/.m2:/root/.m2 -v $PWD:/mnt -w /mnt maven mvn package

## How to run CharAna WAR

Assume that you have installed GlassFish on your machine. Suppose the `bin` directory of GlassFish home is in the `PATH` environment variable.

Start the GlassFish server.

    asadmin start-domain

Go to the `target` directory under `charana-war`, and copy the WAR to a temporary file, to arrange the context root.

    cd charana/charana-war/target
    cp charana-war-0.1.0.war /tmp/charana.war

Deploy the temporary WAR.

    asadmin deploy /tmp/charana.war

Now you can get to the web page through the URL like this.

    http://localhost:8080/charana/faces/index.xhtml

If you run the GlassFish on Docker, make a Dockerfile first. Here is a simple sample Dockerfile for GlassFish 5.1.

```Dockerfile
FROM    openjdk:8
RUN     curl -o /tmp/glassfish-5.1.0.zip http://ftp.yz.yamagata-u.ac.jp/pub/eclipse/glassfish/glassfish-5.1.0.zip && \
        unzip /tmp/glassfish-5.1.0.zip -d /usr/share && \
        rm -f /tmp/glassfish-5.1.0.zip
EXPOSE  8080 8181 4848
ENV     GLASSFISH_HOME /usr/share/glassfish5
ENV     PATH $PATH:$GLASSFISH_HOME/bin
WORKDIR $GLASSFISH_HOME
CMD     ["asadmin", "start-domain", "--verbose"]
```

Build a Docker image.

    docker image build -t glassfish .

Create a Docker container using the image with a name and a port number mapping.

    docker run -d --name glassfish -p 8080:8080 glassfish

You can monitor the log with the following command. 

    docker logs -f glassfish

When the server started, copy the WAR into the container.

    cd charana/charana-war/target
    docker cp charana-war-0.1.0.war glassfish:/tmp/charana.war

And deploy it.

    docker exec -it glassfish asadmin deploy /tmp/charana.war

Now you can get to the web page through the URL like this.

    http://localhost:8080/charana/faces/index.xhtml
