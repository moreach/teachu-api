FROM maven:3.8.5-jdk-11

COPY . /var/local/teachu-api/
WORKDIR /var/local/teachu-api/

EXPOSE 8080

RUN mvn clean install -DskipTests=true -Dmaven.repo.local=/var/local/.m2 -Pdocker-config

ENTRYPOINT mvn verify -DskipTests=true -Dmaven.repo.local=/var/local/.m2 spring-boot:run -Pdocker-config