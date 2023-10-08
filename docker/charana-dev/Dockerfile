FROM eclipse-temurin:17-alpine

RUN apk update && \
    apk upgrade

ARG USERNAME=me
RUN addgroup -g 1000 $USERNAME && \
    adduser -u 1000 -G $USERNAME -D $USERNAME
RUN apk add sudo && \
    echo "$USERNAME ALL=(ALL) NOPASSWD: ALL" > /etc/sudoers.d/$USERNAME

RUN apk add git less
RUN apk add maven

EXPOSE 8080
USER $USERNAME
