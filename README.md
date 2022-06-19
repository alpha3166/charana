# What is CharAna?

CharAna is a simple character analyzer, written in Java. It accepts a sequence of characters, or in other words, a string. It can be useful to know:

- What code points the string consists of (漢字 → <U+6F22, U+5B57>)
- What the code point notation represents (<U+6F22, U+5B57> → 漢字)
- What is the escaped form of the string (漢字 → \u6F22\u5B57)
- What are the characters the escaped string represents (\u6F22\u5B57 → 漢字)
- What are the possible encodings for the string (漢字 → UTF-8:e6bca2e5ad97)
- What characters the hexadecimal string represents (e6bca2e5ad97 → UTF-8:漢字)
- What characters contain the name (calendar → 📅,📆,🗓)

## CharAna Core

CharAna Core is a library of analytical methods for a string. It's just a thin wrapper of standard Java APIs. The library is used from CharAna CLI and Web.

## How to build CharAna Core

Go to the `charana-core` directory, and `install` it with Maven. Since the CLI and Web mentioned below depend on this core library, `mvn package` will not suffice.

    cd charana/charana-core
    mvn install

## CharAna CLI

CharAna CLI is a simple command-line interface for CharAna.

![CharAna CLI Screenshot](screenshot/cli.png)

## How to build CharAna CLI

Go to the `charana-cli` directory, and build with `mvn package`.

    cd charana/charana-cli
    mvn package

## How to run CharAna CLI

Go to the `target` directory under `charana-cli`.

    cd charana/charana-cli/target

There should be two JARs. `charana-cli-x.x.x.jar` contains only a small main class, which requires several depending JARs to be executed. `charana-cli-x.x.x-jar-with-dependencies.jar` is a "fat" JAR, which contains all necessary classes to go. So basically you can use the latter one. It takes a variable number of strings to analyze as arguments.

    java -jar charana-cli-*-jar-with-dependencies.jar 漢字

## CharAna Web

CharAna Web is a web application interface, and REST APIs for CharAna.

![CharAna Web Screenshot](screenshot/web.png)

## How to build CharAna Web

Go to the `charana-web` directory, and build with `mvn package`.

    cd charana/charana-web
    mvn package

## How to run CharAna Web

Go to the `target` directory under `charana-web`.

    cd charana/charana-web/target

Just kick the "fat" JAR to start a web server.

    java -jar charana-web-x.x.x.jar

You can get to the web page through the URL.

    http://localhost:8080/
