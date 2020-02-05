FROM openjdk:8

COPY ./build/libs/woowacrew-*.jar /usr/src/app/

WORKDIR /usr/src/app

CMD java -jar /usr/src/app/woowacrew-*.jar