# Usage

## Build and Run

    docker build -f Dockerfile -t charana:web ../..
    docker run -itd -p 8080:8080 charana:web

or

    docker-compose up -d

## Web Page URL

- <http://localhost:8080/charana/faces/index.xhtml>

## REST OpenAPI

- <http://localhost:8080/charana/api/openapi.yaml>
- <http://localhost:8080/charana/api/openapi.json>

## REST Sample URLs

- <http://localhost:8080/charana/api/parse/U+6F22,U+5B57>
- <http://localhost:8080/charana/api/decompose/漢字>
- <http://localhost:8080/charana/api/findByName/calendar>
- <http://localhost:8080/charana/api/encode/漢字>
- <http://localhost:8080/charana/api/decode/e6bca2e5ad97>
- <http://localhost:8080/charana/api/escape/漢字>
- <http://localhost:8080/charana/api/unescape/%26%23x6F22%3B%26%23x5B57%3B>
