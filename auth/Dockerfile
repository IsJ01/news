FROM openjdk:21-jdk

WORKDIR /code

COPY ./.env/ .
COPY ./target/auth-0.0.1-SNAPSHOT.jar .

CMD java -jar auth-0.0.1-SNAPSHOT.jar