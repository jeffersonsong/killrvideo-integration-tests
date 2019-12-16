FROM maven:3.6-jdk-8

WORKDIR /root

COPY pom.xml /root/
COPY src/ /root/src

RUN mvn clean compile dependency:copy-dependencies dependency:resolve-plugins

CMD ["mvn", "test"]
