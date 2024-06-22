
# < Project Title > Amazon Web Service 기반 “맛집 추천 커뮤니티 먹슐랭(Muksullang)” 시스템 개발 프로젝트

### 개발 기간 📆

2024년 04월 ~ 2024년 07월

### 프로젝트 니즈(Needs)

### 주요 개발 자원 (SW)

| 구 분 | 용 도 | 명칭 및 버전 |
| --- | --- | --- |
| OS | OS | Windows 11 |
| WAS | WEB/WAS | Embedded Tomcat |
| DB | DB | MySQL 8.0.32 |
| 사용 언어 | 백 앤드 | Java jdk 17 |
|  | 개발 프레임 워크 | Spring Boot 3.2.1 |
|  | 프론트 앤드 | HTML5 , CSS3 , javascript, jQuery |
| 소프트웨어 개발도구 | 개발도구 | Eclipse Jee 2023-06-R |
|  | DB | DBeaver |
| 문서 작성 | 에디터 | Microsoft Excel 
Microsoft PowerPoint 
한글 
dbDiagram.io
diagram.io |
| 형상 관리 | 소스 관리 및 버전관리 | Git hub , Git |
| 사용 라이브러리 및 API | 의존성 관리 | Gradle |
|  | DB | Mybatis-3.5.3 |
|  | 로그 | Logback , SQL Logging |
|  | 트랜잭션 | Spring Transaction |
|  | 스케쥴러 | Spring Scheduler |
|  | 디자인 | Bootstrap |
|  | 보안 | Spring Security-5.0.8 |
|  | 에디터 | Ckeditor4 |
|  | 우편주소 | DaumPostCode |
|  | 파일 | Spring File |
|  | 데이터 전송 | AJAX , JSON |
|  | Excel Export | Poi-3.17 |

### 주요 개발 자원 (H/W)

| 구 분 | 용 도 | 명칭 및 버전 |
| --- | --- | --- |
| OS | OS | Windows 11 |
| WAS | WEB/WAS | Embedded Tomcat |
| DB | DB | MySQL 8.0.32 |
| 사용 언어 | 백 앤드 | Java jdk 17 |
|  | 개발 프레임 워크 | Spring Boot 3.2.1 |
|  | 프론트 앤드 | HTML5 , CSS3 , javascript, jQuery |
| 소프트웨어 개발도구 | 개발도구 | Eclipse Jee 2023-06-R |
|  | DB | DBeaver |
| 문서 작성 | 에디터 | Microsoft Excel 
Microsoft PowerPoint 
한글 
dbDiagram.io
diagram.io |
| 형상 관리 | 소스 관리 및 버전관리 | Git hub , Git |
| 사용 라이브러리 및 API | 의존성 관리 | Gradle |
|  | DB | Mybatis-3.5.3 |
|  | 로그 | Logback , SQL Logging |
|  | 트랜잭션 | Spring Transaction |
|  | 스케쥴러 | Spring Scheduler |
|  | 디자인 | Bootstrap |
|  | 보안 | Spring Security-5.0.8 |
|  | 에디터 | Ckeditor4 |
|  | 우편주소 | DaumPostCode |
|  | 파일 | Spring File |
|  | 데이터 전송 | AJAX , JSON |
|  | Excel Export | Poi-3.17 |

### 요구사항 정의서

| 요구사항 정의서 |  |  |  |  |  |  |
| --- | --- | --- | --- | --- | --- | --- |
| 프로젝트 | Amazon Web Service 맛집 추천 커뮤니티(먹슐랭) 시스템 개발 프로젝트 |  |  |  | 작성자 | 홍길동 |
| 시스템명 | Restaurant Recommendation System 1.0 |  |  |  | 작성일 | 2023-xx-xx |
| 요구사항ID | 업무구분 | 요구사항 | 유형 | 처리방안 |  |  |
| RQ-001 | 사용자 | 회원가입 | 기능 | 스프링 MVC 개발 |  |  |
| RQ-002 | 사용자 | 로그인 | 기능 | 스프링 MVC 개발 |  |  |
| RQ-003 | 사용자 | 로그아웃 | 기능 | 스프링 MVC 개발 |  |  |
| RQ-004 | 사용자 | 회원정보 수정(프로필, 패스워드, 이메일, 주소) | 기능 | 스프링 MVC 개발 |  |  |
| RQ-005 | 사용자 | 회원 탈퇴 | 기능 | 스프링 MVC 개발 |  |  |
| RQ-006 | 사용자 | (마이페이지) 찜한 게시물, 리뷰 작성한 게시물 모아보기 | 기능 | 스프링 MVC 개발 |  |  |
| RQ-006 | 사용자 | (마이페이지) 찜한 게시물만 모아보기 | 기능 | 스프링 MVC 개발 |  |  |
| RQ-007 | 사용자 | (마이페이지) 리뷰 작성한 게시물만 모아보기 | 기능 | 스프링 MVC 개발 |  |  |
| RQ-008 | 사용자 | 게시물 검색 (전체 조회) | 기능 | 스프링 MVC 개발 |  |  |
| RQ-009 | 사용자 | 게시물 보기 (상세 조회) | 기능 | 스프링 MVC 개발 |  |  |
| RQ-010 | 사용자 | 게시물 찜하기 | 기능 | 스프링 MVC 개발 |  |  |
| RQ-011 | 사용자 | 게시물에 리뷰 달기(Best Of에서는 리뷰 작성시 별평가도) | 기능 | 스프링 MVC 개발 |  |  |
| RQ-012 | 사용자 | 리뷰에 좋아요 달기 | 기능 | 스프링 MVC 개발 |  |  |
| RQ-013 | 관리자 | 게시물 등록 | 기능 | 스프링 MVC 개발 |  |  |
| RQ-014 | 관리자 | 게시물 수정 | 기능 | 스프링 MVC 개발 |  |  |
| RQ-015 | 관리자 | 게시물 삭제 | 기능 | 스프링 MVC 개발 |  |  |
| RQ-016 | 관리자 | 게시물 작성/수정 시 분류> 게시물 검색 (전체 조회) | 기능 | 스프링 MVC 개발 |  |  |
| RQ-017 | 관리자 | 게시물 작성/수정 시 분류> 게시물 보기 (상세 조회) | 기능 | 스프링 MVC 개발 |  |  |
| RQ-018 | 관리자 | 사용자 수, 사용자 리뷰 수, 게시물 찜하기 수 등 조회 | 기능 | 스프링 MVC 개발 |  |  |
| RQ-019 | 스케쥴러 | 스케쥴러를 통해 신규 회원 console에 출력 및 logger | 기능 | 스프링 MVC 개발 |  |  |
| RQ-020 | 스케쥴러 | 스케쥴러를 통해 inactiveYn이 n일 이상된 탈퇴 회원 삭제 | 기능 | 스프링 MVC 개발 |  |  |
| RQ-021 | 공통 | 디자인 레이아웃 | 디자인 | Tiles3 라이브러리로 구현 |  |  |

### 프로젝트 니즈(Needs)

**맛집 검색 플랫폼 통합** :

네이버 블로그, 리뷰 등을 통해 맛집 소개 및 정보가 활발히 이루어지고 있지만, 각 활동이 분산되어 있어 이를 한 곳에서 통합해 보여주는 플랫폼이 필요하다고 생각합니다. 사용자들은 이 플랫폼을 통해 다양한 맛집 정보를 찾을 수 있고, 리뷰와 평가를 남길 수 있습니다.

**통합된 맛집 정보와 관련된 추가 콘텐츠 제공 :**

맛집 정보뿐만 아니라, 건강 관련 정보나 레시피 등과 관련된 콘텐츠도 함께 제공하여 사용자들이 다양한 정보를 한 곳에서 얻을 수 있도록 합니다.

예를 들어, 이달에 “눈 건강”에 대한 이슈가 있으면 그와 관련된 음식을 소개하는 기사를 업데이트하는 등을 제공할 수 있습니다.

**개인화된 추천 기능 :**

사용자들이 맛집 추천, 추가 컨텐츠 제공에서 리뷰(comment) 작성이 가능함과 동시에 찜하기 기능을 추가해서 사용자 페이지를 통해 개인의 관심사를 한 번에 몰아 볼 수 있는 기능을 제공할 것입니다.

더 나아가, 사용자가 관심 있어 하는 음식, 지역 등을 카테고리로 묶어서 이와 관련된 맛집 추천이 가능하도록 웹을 구현해 보고 싶습니다.
