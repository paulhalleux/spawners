#!/bin/bash

SPONGE_URL=https://cdn.getbukkit.org/spigot/spigot-${SPIGOT_VERSION}.jar
EXECUTABLE_JAR=spigot-${SPIGOT_VERSION}.jar

if [ ! -f "/vanilla/${EXECUTABLE_JAR}" ]; then
	wget -O /vanilla/"${EXECUTABLE_JAR}" "${SPONGE_URL}"
fi

if [ ! -f "/vanilla/server.properties" ]; then
	echo "server-port=${MINECRAFT_PORT}" > /vanilla/server.properties
fi

if [ -n "${MINECRAFT_EULA}" ]; then
	echo "eula=${MINECRAFT_EULA}" > /vanilla/eula.txt
fi

START_COMMAND="java"
if [ -n "${MINECRAFT_MAXHEAP}" ]; then
  START_COMMAND="${START_COMMAND} -Xmx${MINECRAFT_MAXHEAP}"
fi

START_COMMAND="${START_COMMAND} -jar ${EXECUTABLE_JAR} nogui"
cd /vanilla && ${START_COMMAND}