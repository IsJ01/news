FROM openjdk:21-jdk

WORKDIR /code

COPY ./.env/ .
COPY ./target/news-0.0.1-SNAPSHOT.jar .

CMD java -jar news-0.0.1-SNAPSHOT.jar