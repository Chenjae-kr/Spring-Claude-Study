# Spring Boot 게시판 백엔드

React 프론트엔드와 연동되는 Spring Boot REST API 백엔드 프로젝트입니다.

## 기술 스택

- **Java 17**
- **Spring Boot 3.2.2**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Maven**

## 프로젝트 구조

```
src/main/java/com/example/demo/
├── DemoApplication.java        # 메인 애플리케이션
├── config/
│   └── WebConfig.java          # CORS 설정
├── controller/
│   ├── HelloController.java    # 헬로 API
│   ├── PostController.java     # 게시글 API
│   └── UserController.java     # 사용자 API
├── dto/
│   └── PostCreateRequest.java  # 게시글 생성 DTO
├── model/
│   ├── Post.java               # 게시글 엔티티
│   └── User.java               # 사용자 엔티티
├── repository/
│   ├── PostRepository.java     # 게시글 리포지토리
│   └── UserRepository.java     # 사용자 리포지토리
└── service/
    ├── PostService.java        # 게시글 서비스
    └── UserService.java        # 사용자 서비스
```

## API 엔드포인트

### 게시글 API (`/api/posts`)

| Method | Endpoint | 설명 |
|--------|----------|------|
| GET | `/api/posts` | 전체 게시글 조회 |
| GET | `/api/posts/{id}` | 단일 게시글 조회 |
| POST | `/api/posts` | 게시글 생성 |
| PUT | `/api/posts/{id}` | 게시글 수정 |
| DELETE | `/api/posts/{id}` | 게시글 삭제 |
| GET | `/api/posts/search?keyword=` | 제목 검색 |
| GET | `/api/posts/author/{author}` | 작성자별 조회 |

### 요청/응답 예시

**게시글 생성 (POST /api/posts)**
```json
// Request
{
  "title": "게시글 제목",
  "content": "게시글 내용",
  "author": "작성자"
}

// Response
{
  "id": 1,
  "title": "게시글 제목",
  "content": "게시글 내용",
  "author": "작성자",
  "createdAt": "2024-01-01"
}
```

## 실행 방법

### 사전 요구사항

- Java 17 이상
- PostgreSQL 14 이상
- Maven

### 데이터베이스 설정

```bash
# PostgreSQL 서비스 시작
brew services start postgresql@14

# 데이터베이스 생성
createdb demo
```

### 애플리케이션 실행

```bash
# Maven Wrapper로 실행
./mvnw spring-boot:run

# 또는 Maven으로 실행
mvn spring-boot:run
```

서버는 `http://localhost:8080`에서 실행됩니다.

## 테스트

```bash
# 전체 테스트 실행
./mvnw test

# 특정 테스트 클래스 실행
./mvnw test -Dtest=PostControllerTest
```

## 프론트엔드 연동

이 백엔드는 React 프론트엔드(`http://localhost:5173`)와 연동됩니다.

CORS 설정이 `WebConfig.java`에 구성되어 있어 `localhost:5173`과 `localhost:3000`에서의 요청을 허용합니다.

## 환경 설정

`src/main/resources/application.yml`에서 데이터베이스 연결 정보를 수정할 수 있습니다.

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/demo
    username: ${USER}
    password:
```
