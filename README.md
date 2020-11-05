# Simple API

- Spring boot 2.3.4
- Spring Data JPA
- Querydsl
- Test with Jacoco
- Multi-Module Project

<br>

## 인텔리제이 IDE 사용한다면?

> Preferences 에서 `Gradle` 검색후, `Build and run using`을 `Gradle` -> `IntelliJ IDEA`로 수정하고, `Run tests using`을 `Gradle` -> `IntelliJ IDEA`로 수정하기

![image](https://user-images.githubusercontent.com/23515771/98190422-5516b000-1f5a-11eb-90c5-0b65bb8dcde2.png)

> Preferences 에서 `Annotation Processors` 검색후, `Enable annotation processing` 체크하기

![image](https://user-images.githubusercontent.com/23515771/98190727-ff8ed300-1f5a-11eb-961e-a9af336b0994.png)

<br>

## admin-api 모듈

- 필요한 자원들이 반드시 설치되어야 함 (JDK 11, Java 등등...)

- Multi-Module 프로젝트이므로 `admin-api`와 `external-api` 모듈의 `application.yml` 설정이 다를 수 있음을 참고해야 함

### Local 환경 실행

> 인텔리제이 IDE가 아니라면, 아래의 방법으로 실행해야 한다.

```shell script
# 1) Jar를 파일을 생성한다.
./gradlew clean build

# 2) profiles.active를 local 상태로 두고, 실행하고자 하는 Jar 파일을 실행한다. (admin-api 또는 external-api)
java -Dspring.profiles.active=local \
      -Xms2048m -Xmx2048m -XX:ParallelGCThreads=2 \
      -jar admin-api/build/libs/admin-api-0.0.1-SNAPSHOT.jar 
```

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
