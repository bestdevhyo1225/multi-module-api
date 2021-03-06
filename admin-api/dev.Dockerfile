# Pull base image.
FROM openjdk:11.0.6-jdk

# JAVA
ENV HOME=/home/product
ENV TZ=Asia/Seoul

RUN mkdir -p /usr/local/lib/product

# JAR
COPY admin-api/build/libs/admin-api-0.0.1-SNAPSHOT.jar /usr/local/lib/product

EXPOSE 8080

CMD ["/usr/local/openjdk-11/bin/java", "-Dspring.profiles.active=dev", "-Xms2048m", "-Xmx2048m", "-XX:ParallelGCThreads=2", "-jar", "/usr/local/lib/product/admin-api-0.0.1-SNAPSHOT.jar"]
