
# Usage

## With Docker

To build image:

    docker build -f Dockerfile -t charana-cli ../..

To run:

    docker run -it --rm charana-cli 漢字

## With Docker Compose

To run:

    docker compose run --rm ws 漢字
