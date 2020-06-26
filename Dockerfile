FROM openjdk:8

COPY ./woowacrew-web/build/libs/woowacrew-web-*.jar /usr/src/app/

WORKDIR /usr/src/app

CMD java -jar -Dspring.profiles.active=prod /usr/src/app/woowacrew-web-*.jar