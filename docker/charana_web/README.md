# Usage

## With Docker

To build image:

    docker build -f Dockerfile -t charana-web ../..

To run:

    docker run -it --rm -p 8080:8080 charana-web

## With Docker-Compose

To run:

    docker-compose up

## URL

Web Page:

- <http://localhost:8080/charana/faces/index.xhtml>

REST Sample:

- <http://localhost:8080/charana/api/parse/U+6F22,U+5B57>
- <http://localhost:8080/charana/api/decompose/漢字>
- <http://localhost:8080/charana/api/findByName/calendar>
- <http://localhost:8080/charana/api/encode/漢字>
- <http://localhost:8080/charana/api/decode/e6bca2e5ad97>
- <http://localhost:8080/charana/api/escape/漢字>
- <http://localhost:8080/charana/api/unescape/%26%23x6F22%3B%26%23x5B57%3B>

REST OpenAPI:

- <http://localhost:8080/charana/api/openapi.yaml>
- <http://localhost:8080/charana/api/openapi.json>
