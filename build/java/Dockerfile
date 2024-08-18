# Example of custom Java runtime using jlink in a multi-stage container build
FROM eclipse-temurin:21 AS jre-build

# Create a custom Java runtime
RUN $JAVA_HOME/bin/jlink \
         --add-modules java.base,java.logging,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,java.sql \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /javaruntime

# set current directory to /work
WORKDIR /work

COPY gradle /work/gradle
COPY gradlew /work/
COPY build.gradle /work/
COPY settings.gradle /work/
COPY server /work/server
COPY lib /work/lib

RUN ls -la -R /work
RUN ./gradlew --no-daemon --no-build-cache --no-configure-on-demand --no-parallel --stacktrace --info clean test bootWar

# Define your base image
FROM debian:buster-slim
ENV JAVA_HOME=/opt/java/openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"
COPY --from=jre-build /javaruntime $JAVA_HOME

# Continue with your application deployment
RUN mkdir /opt/app
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
    curl \
    && apt-get -y clean \
    && rm -rf /var/lib/apt/lists/*
COPY --from=jre-build /work/server/build/libs/*.war /opt/app/server.war
CMD ["java", "-jar", "/opt/app/server.war"]