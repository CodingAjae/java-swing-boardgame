# 보드 게임 프로젝트

## 개요
이 프로젝트는 테트리스와 헥사 보드 게임의 구현을 포함하는 보드 게임 프레임워크입니다. 핵심 목표는 **데이터 중심의 제어 구조**를 활용하여 효율적인 게임 상태 관리 및 로직 실행을 가능하게 하는 것입니다. 다양한 보드 구성에 따라 데이터 처리 방식과 게임 로직이 어떻게 달라지는지를 보여줍니다.

## 주요 기능
- **테트리스 보드 게임 구현**
- **헥사 보드 게임 구현**
- **보드 기반 게임을 위한 공통 프레임워크**
- **추상화된 보드 제어 로직**
- **데이터 중심의 게임 상태 관리**

## 아키텍처
본 프로젝트는 **모듈화된 설계**를 따르며, 기능별로 패키지를 분리하여 관리합니다:

### **1. 제어 패키지 (`control/`)**
- `BaseBoardControl.java`: 보드 인터랙션을 처리하는 기본 클래스.
- `TetrisBoardControl.java`: 테트리스 게임의 제어 로직 구현.
- `HexaBoardController.java`: 헥사 보드 게임의 제어 로직 구현.

### **2. 열거형 패키지 (`enums/`)**
게임에서 사용되는 다양한 열거형(Enum) 정의:
- `EnumAction.java`: 이동, 회전, 낙하 등의 동작 정의.
- `EnumBlock.java`: 다양한 블록 유형 정의.
- `EnumColor.java`: 블록 색상 정의.

### **3. 이벤트 패키지 (`event/`)**
게임 내 이벤트를 처리하는 클래스들:
- `KeyboardEvent.java`: 키보드 입력 처리.
- `TimerEvent.java`: 게임 내 시간 기반 이벤트 관리.

### **4. 모델 패키지 (`model/`)**
게임의 핵심 모델 클래스 포함:
- `BaseShape.java`: 기본 블록 형태 정의.
- `BoardBlock.java`: 헥사 보드의 개별 블록을 나타냄.
- `HexaBlockChecker.java`: 헥사 보드의 충돌 감지 및 규칙 검증을 담당.

## 테트리스 vs. 헥사 차이점
| 특징  | 테트리스 | 헥사 |
|----------|--------|------|
| **블록 관리 방식** | 리스트 기반 블록 추적 | 개별 `BoardBlock` 객체 관리 |
| **충돌 감지 방식** | XY 좌표 직접 체크 | `HexaBlockChecker`를 통한 이동 규칙 적용 |
| **게임 로직 실행** | 표준 중력 기반의 선형 업데이트 | 헥사 보드에 맞춘 별도 규칙 적용 |

## 설치 및 실행 방법
### **필수 사항**
- Java Development Kit (JDK) 8+
- Java 호환 IDE (IntelliJ, Eclipse, VS Code 등)

### **빌드 및 실행 방법**
1. 저장소 클론:
   ```sh
   git clone https://github.com/your-repo/boardgame.git
   cd boardgame
