# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
# Run application
./mvnw spring-boot:run

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=HelloControllerTest

# Run a single test method
./mvnw test -Dtest=HelloControllerTest#hello_WithDefaultName_ReturnsHelloWorld

# Build without tests
./mvnw package -DskipTests

# Clean build
./mvnw clean package
```

## Architecture

Spring Boot 3.2.2 / Java 17 / Maven 프로젝트

**레이어 구조**: Controller → Service → Repository → Model (JPA Entity)

- **Controller** (`/api/*`): REST 엔드포인트, 요청 검증 (`@Valid`)
- **Service**: 비즈니스 로직, `@Transactional` 관리
- **Repository**: Spring Data JPA 인터페이스 (`JpaRepository` 상속)
- **Model**: JPA 엔티티, Lombok 어노테이션 사용

**Database**: H2 인메모리 (개발용), 콘솔 `/h2-console`에서 접근 가능

## Key Dependencies

- Lombok: `@RequiredArgsConstructor`, `@Getter`, `@Setter`, `@Builder`
- Jakarta Validation: `@NotBlank`, `@Email` 등 검증 어노테이션
- Spring Data JPA: Repository 메서드 네이밍 규칙으로 쿼리 자동 생성
