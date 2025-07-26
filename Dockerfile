FROM ubuntu:latest
LABEL authors="brady"

ENTRYPOINT ["top", "-b"]