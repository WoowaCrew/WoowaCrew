FROM openjdk:8

COPY ./build/libs/woowacrew-*.jar /usr/src/app/

WORKDIR /usr/src/app

CMD java -jar -Dspring.profiles.active=production /usr/src/app/WoowaCrew-*.jar &