# Triple
트리플 여행자 클럽 마일리지 서비스
## 바로가기 
- [1. 프로젝트 구조](#1-프로젝트-구조)
- [2. 실행 방법](#2-실행-방법)
- [3. 기술 스택](#3-기술-스택)
- [4. 문제 해결 및 구현](#4-문제-해결-및-구현)

### 1. 프로젝트 구조
```
└── src
    └── main
        ├── java
        │   └── com
        │       └── report
        │           └── triple
        │               ├── TripleApplication.java
        │               ├── domain
        │               │   ├── common
        │               │   │   └── entity
        │               │   ├── point
        │               │   │   ├── controller
        │               │   │   ├── entity
        │               │   │   ├── repository
        │               │   │   ├── request
        │               │   │   ├── response
        │               │   │   └── service
        │               │   └── review
        │               │       ├── entity
        │               │       └── repository
        │               └── global
        │                   ├── aop
        │                   │   └── aspect
        │                   ├── config
        │                   │   ├── swagger
        │                   │   ├── JpaAuditingConfig.java
        │                   │   ├── QuerydslConfig.java
        │                   │   └── WebConfig.java
        │                   ├── error
        │                   │   ├── ErrorResult.java
        │                   │   ├── GlobalExceptionHandler.java
        │                   │   └── ValidationErrorResult.java
        │                   └── validation
        │                       └── UUID    
        └── resources
            ├── application-real.yml
            └── application.yml

```

### 2. 실행 방법

#### AWS EC2 서버에 배포 해두었습니다.
- [Swagger API 문서](http://3.39.140.31:8080/api-docs)
- [ddl.sql 파일](https://github.com/yanglet/Triple/blob/develop/sql/DDL.sql)

#### 로컬에서 실행 방법

##### DB 환경 설정
```
mysql 루트 게정 접속
create database triple default character set utf8;
create user 'triple'@'localhost' identified by 'triple';
grant all privileges on triple.* to triple@localhost; 
flush privileges;
``` 

##### DB 설정 후 실행
```
깃 클론 후 IDE를 통해 실행하셔도 됩니다.
git clone https://github.com/yanglet/Triple.git
cd Triple
./gradlew bootjar
java -jar ./build/libs/triple-0.0.1-SNAPSHOT.jar
```

### 3. 기술 스택
- Java
- Spring Boot, Spring MVC, Spring Data JPA
- JPA(Hibernate), Query DSL
- Junit5, AssertJ, Postman
- Mysql, MariaDB(배포)

### 4. 문제 해결 및 구현
```
1. DB설계
리뷰 이벤트에 따른 포인트 부여 및 이력, 개인별 누적 포인트 관리 목적에 맞는 DB 테이블만을 생성하였습니다.
https://github.com/yanglet/Triple/blob/develop/sql/DDL.sql

2. 인덱스
point, point_log에 인덱스를 적용해 포인트 부여 API에 필요한 SQL 수행 시 전체 테이블 스캔이 일어나지 않도록 구현했습니다.
처음에 point_log에 복합 인덱스를 적용했으나 실행 계획을 통해 순서에 따른 첫 번째 컬럼이 필터 조건에 존재하지 않는 경우 
인덱스를 타지 않는 것을 확인했고, 2개의 단일 인덱스를 적용하도록 변경하였습니다.
https://github.com/yanglet/Triple/pull/11 (feat: 포인트 적립 구현시 필요한 SQL은 인덱스를 타도록 수정)
https://github.com/yanglet/Triple/pull/17 (feat: 포인트 적립 이력 조회 API 구현)

3. 가독성 높은 코드
SonarCloud 정적 분석을 통해 문제점을 수정했습니다.
정적 팩토리 메서드를 통해 객체를 생성하도록 구현했습니다.
Constants 클래스에 상수를 정의하고, 생성자를 Private로 정의해 의미없는 생성을 막는 등을 구현했습니다.
https://github.com/yanglet/Triple/pull/23 (refactor: 변수명, 함수명 리팩토링)
https://github.com/yanglet/Triple/pull/25 (refaccor: 기타 수정 및 변경)

4. REQUIREMENTS, REMARKS의 모든 내용을 반영하려고 노력했습니다.
```