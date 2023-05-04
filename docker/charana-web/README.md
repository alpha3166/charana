# Usage

## With Docker

To build image:

    docker build -f Dockerfile -t charana-web ../..

To run:

    docker run -it --rm -p 8080:8080 charana-web

## With Docker Compose

To run:

    docker compose up -d

## URL

Web Page:

- <http://localhost:8080/>

REST Sample:

- <http://localhost:8080/api/parse/U+6F22,U+5B57>
- <http://localhost:8080/api/decompose/漢字>
- <http://localhost:8080/api/findByName/calendar>
- <http://localhost:8080/api/encode/漢字>
- <http://localhost:8080/api/decode/e6bca2e5ad97>
- <http://localhost:8080/api/escape/漢字>
- <http://localhost:8080/api/unescape/%26%23x6F22%3B%26%23x5B57%3B>
