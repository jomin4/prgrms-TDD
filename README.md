# prgrms-TDD

**테스트 주도 개발(TDD)** 학습 프로젝트. 장희성 강사의 TDD 강의를 기반으로, `Red → Green → Blue` 사이클을 직접 체득하며 세 가지 산출물을 구현합니다.

> 📚 참고 강의
> - [TDD 기초 (slog.gg/p/14156)](https://www.slog.gg/p/14156)
> - [TDD로 다항식 계산기 구현 (slog.gg/p/14151)](https://www.slog.gg/p/14151)

## TDD 3단계 사이클

| 단계 | 의미 | 할 일 |
|------|------|-------|
| 🔴 **Red** | 실패 | 실패하는 테스트를 **먼저** 작성한다 (쉬운 목표부터) |
| 🟢 **Green** | 성공 | 꼼수를 써서라도 테스트를 통과시킨다 |
| 🔵 **Blue** | 리팩토링 | 중복 제거·일반화로 구조를 개선한다 |

## 산출물

| 클래스 | 설명 | 테스트 |
|--------|------|--------|
| [`App`](src/main/java/com/back/App.java) | 워밍업 계산기 (`plus`) — TDD 감 잡기 | [`AppTest`](src/test/java/com/back/AppTest.java) · 3개 |
| [`Rq`](src/main/java/com/back/Rq.java) | 쿼리스트링 파서 — `getActionName` / `getParam` / `getParamAsInt` | [`RqTest`](src/test/java/com/back/RqTest.java) · 12개 |
| [`Calc`](src/main/java/com/back/Calc.java) | **다항식 계산기** — 사칙연산·우선순위·괄호·단항 마이너스 | [`CalcTest`](src/test/java/com/back/CalcTest.java) · 28개 |

**총 43개 테스트 통과** ✅

### `Calc` 설계 — 재귀 하강 파서

요구사항: **`Stack` 자료구조 금지 → 재귀로 구현.** 연산자 우선순위를 메서드 계층으로 표현합니다.

```
expr   := term   ( ('+' | '-') term )*      // 덧셈 · 뺄셈
term   := factor ( '*' factor )*            // 곱셈 (먼저 묶임)
factor := '-' factor | '(' expr ')' | 숫자   // 단항 -, 괄호, 숫자
```

괄호를 만나면 `parseExpr`로 재귀 재진입하여, `Stack` 없이 중첩 괄호까지 처리합니다.
예) `-(8 + 2) * -(7 + 3) + 5 == 105`, `3 * 1 + (1 - (4 * 1 - (1 - 1))) == 0`

## 실행 방법

```bash
# 전체 테스트 실행
./gradlew test

# 테스트 리포트: build/reports/tests/test/index.html
```

IntelliJ IDEA에서는 테스트 클래스/메서드 옆의 초록 실행 버튼으로도 실행할 수 있습니다.

## 기술 스택

- **Java 25** (Temurin)
- **Gradle 9.1** (wrapper 포함 — 별도 설치 불필요)
- **JUnit 5** (`junit-bom 5.11.4`)
- **AssertJ** (`assertj-core 3.27.3`) — `assertThat(...).isEqualTo(...)`

## 프로젝트 구조

```
prgrms-TDD/
├── build.gradle.kts          # 의존성 · 테스트 설정
├── settings.gradle.kts
├── gradlew / gradlew.bat      # Gradle wrapper
└── src/
    ├── main/java/com/back/    # App.java · Rq.java · Calc.java
    └── test/java/com/back/    # AppTest · RqTest · CalcTest
```
