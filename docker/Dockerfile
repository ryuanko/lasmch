FROM openjdk:8-jre-alpine
RUN apk upgrade musl
RUN apk del linux-pam
RUN apk --no-cache add tzdata && cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo "Asia/Seoul" > /etc/timezone

ENV LANG ko_KR.UTF-8
COPY lasmch*.jar          /jars/lasmch.jar
COPY application.yml      /jars/application.yml

EXPOSE 8080
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/jars/lasmch.jar", "--spring.config.location=file:/jars/application.yml"]
