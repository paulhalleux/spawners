FROM openjdk:16-slim

MAINTAINER Kauzas

RUN DEBIAN_FRONTEND=noninteractive
VOLUME ["/vanilla"]

ADD ./scripts/start.sh /start_vanilla
RUN chmod +x /start_vanilla

EXPOSE ${MINECRAFT_PORT}
CMD ["/start_vanilla"]