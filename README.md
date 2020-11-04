# Simple API

- Spring boot 2.3.4
- Spring Data JPA
- Querydsl
- Test with Jacoco
- Multi-Module Project

<br>

## admin-api 실행 방법

- **필요한 자원들이 반드시 설치되어야 함 (JDK 11, Java 등등...)**

- Multi-Module 프로젝트이므로 `admin-api`와 `external-api` 모듈의 `application.yml` 설정이 다를 수 있음을 참고해야 함

### Local 환경 실행

> 인텔리제이 IDE가 아니라면, 아래의 방법으로 실행해야 한다.

```shell script
# 1) Jar를 파일을 생성한다.
./gradlew clean build

# 2) profiles.active를 local 상태로 두고, 실행하고자 하는 Jar 파일을 실행한다. (admin-api 또는 external-api)
java -Dspring.profiles.active=local \
      -Xms2048m -Xms2048m -XX:ParallelGCThreads=2 \
      -jar ./admin-api/build/libs/admin-api-0.0.1-SNAPSHOT.jar 
```

<br>

### Dev 환경 실행

> Docker를 사용해서 Application, MySQL이 같이 실행되도록 구성했다.

```shell script
# 1) Jar를 파일을 생성한다.
./gradlew clean build

# 2) 생성한 Jar를 파일을 Docker Image로 만든다.
docker build -f admin-api/dev.Dockerfile -t product-admin-api:latest .

# 3) docker-compose 를 통해 Application과 MySQL 컨테이너를 실행한다.
docker-compose -f admin-api/docker-compose.dev.yml up
```
