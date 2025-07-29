# Game Sales API Server for Discord Bot

디스코드 봇에서 게임 할인 정보를 제공하기 위한 백엔드 API 서버입니다.  
[IsThereAnyDeal](https://isthereanydeal.com) API를 활용하여, 인기 할인 정보, 검색 결과, 위시리스트 기능을 제공할 예정입니다.

## 주요 기능

- 🎮 **게임 검색**: 키워드로 게임을 검색해 title, slug, 이미지 등 메타데이터 반환
- 💰 **게임 할인 정보 조회**: ID 기준으로 다양한 상점들의 현재 가격/할인율/최저가 정보 제공
- 🧾 **인기 할인 게임 제공**: IsThereAnyDeal 기준 현재 인기 할인 게임 목록 제공
- 📋 **디스코드 서버별 위시리스트**:
  - 게임 등록 및 삭제
  - 위시리스트 조회
  - 디스코드 서버 ID 기반 분리 저장

---

## 실제 프로젝트 구조

```

app/
├── AppApplication.java
├── games/
│   ├── controller/             # GameController: /games API 엔드포인트
│   ├── service/                # GameService, DealService, PopularDealsService
│   ├── repository/             # JPA 기반 저장소
│   ├── application/            # Usecase 중심 Application 서비스 계층
│   ├── domain/                 # Game, GameDeal, PopularDeals 엔티티
│   └── dto/                    # 응답 DTO들
├── discord/
│   ├── controller/             # ServerController: /wishlist API 엔드포인트
│   ├── service/                # ServerService
│   ├── repository/             # ServerRepository, ServerWhishListRepository
│   └── domain/                 # Server, ServerWishList
├── common/
│   ├── annotation/             # @Application 애노테이션
│   ├── error/                  # GlobalExceptionHandler
│   └── domain/                 # BaseTimeEntity 등 공통 엔티티

````

---

## application 계층을 사용한 이유

이 프로젝트는 도메인 계층과 서비스 계층 사이에 **application 계층**을 두어 다음과 같은 목적을 달성합니다:

- 🎯 **유스케이스 단위 책임 분리**: `GameApplication`, `PopularDealsApplication` 등 기능 중심 구조로 코드 응집도 향상
- 🧩 **서비스 로직 분리**: 비즈니스 로직(`Service`)과 유스케이스(`Application`)를 구분하여 책임을 명확히 함
- 🔌 **확장 대비 구조**: 향후 이벤트 처리, 배치, 알림 로직 등을 유스케이스 단위로 관리 가능

---

## 기술 스택

- Java 17
- Spring Boot
- Spring Data JPA
- H2 (로컬 개발용 In-Memory DB)
- RestTemplate + Jackson(JsonNode) – IsThereAnyDeal API 호출
- Gradle

---

## API 요약

### 🎮 게임 관련

| Method | Endpoint                    | 설명                |
|--------|-----------------------------|-------------------|
| GET    | `/games/search`             | 키워드 기반 게임 검색      |
| GET    | `/games/deals{countrycode}` | 현재 인기 할인 게임 정보 조회 |

### 📋 위시리스트 관련 (아직 구현되지 않음)

| Method | Endpoint                 | 설명                      |
|--------|--------------------------|---------------------------|
| POST   | `/wishlist/{serverId}`   | 서버 ID 기준 게임 등록     |
| GET    | `/wishlist/{serverId}`   | 서버 위시리스트 목록 조회  |
| DELETE | `/wishlist/{serverId}`   | 서버 위시리스트 전체 삭제  |

