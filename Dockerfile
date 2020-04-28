FROM openjdk:8-jre-alpine

RUN mkdir -p /opt/app
WORKDIR /opt/app

COPY ./ops/run_jar.sh Akka-HTTP-assembly-0.1.jar ./

RUN chmod +x run_jar.sh

ENTRYPOINT ["./ops/run_jar.sh"]