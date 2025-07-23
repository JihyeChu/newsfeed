# Newsfeed

Spring Boot와 JPA를 기반으로, 필요한 기능을 갖춘 뉴스피드 API를 구현한 프로젝트입니다.<br/>
요구사항에 맞춘 데이터 모델링, JWT 인증, 예외 처리, 테스트, 문서화까지 포함해 백엔드 개발의 핵심 구성요소를 학습하고 적용하는 데 중점을 두었습니다.

---

## 🎯 프로젝트 목표

- JPA 기반 DB 설계 및 연관관계 매핑: 뉴스피드 서비스 요구사항을 반영한 효율적인 데이터베이스 모델 설계
- Spring Web / Validation / Common 레이어 구성: 요청-응답 흐름과 유효성 검증, 전역 예외 처리 등 스프링 기반 API 구현의 기본기 습득
- JWT 기반 인증 흐름 이해 및 적용: 로그인 / 인증 / 권한 검증까지 전체 보안 흐름 구현
- 단위 테스트와 예외 상황 처리 : 서비스 로직 테스트를 통해 API 개발 연습
- Swagger 기반 API 문서화 경험

---

## 🧩 핵심 기능

### ✅ 사용자 인증 및 계정 관리
- 회원가입 / 로그인 / 로그아웃
- 비밀번호 변경 및 회원탈퇴
- 이메일 형식 및 비밀번호 정책 검증
- JWT 토큰 발급 및 인증

### ✅ 프로필 기능
- 사용자 프로필 조회 / 수정
- 민감정보 제한
- 비밀번호 수정 시 본인 확인

### ✅ 뉴스피드 게시글
- 게시글 CRUD
- 본인만 수정/삭제 가능
- 페이지네이션 적용 (10개 단위)

### ✅ 팔로우 기능
- 유저 팔로우 / 언팔로우
- 팔로우한 유저의 게시글 최신순 조회

---

## 🧪 테스트 및 품질
- PostService, UserService 단위 테스트 작성
- 예외 상황 처리 (존재하지 않는 유저, 권한 없음 등)

---

## ⚙ 사용 기술

- Java 17
- Spring Boot 3.x
- Spring Web, Spring Validation
- JPA (Hibernate), MySQL
- JWT (io.jsonwebtoken)
- JUnit5, Mockito
- Gradle

---

## 🧱 ERD

> <img width="900" height="528" alt="image" src="https://github.com/user-attachments/assets/1f7efadb-a291-4af2-b853-07292d8492c1" />


---

## 📄 API 명세서

> [Swagger](http://localhost:8080/swagger-ui/index.html)

> [Notion](https://www.notion.so/NEWSFEED-201c0ffd7f20806f921ec9c76a8b66b1?source=copy_link)

