FROM adoptopenjdk/openjdk11:latest
VOLUME /tmp
EXPOSE 9090
RUN mkdir -p /app/
ADD build/libs/demo.jar app/app.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=test","/app/app.jar"]