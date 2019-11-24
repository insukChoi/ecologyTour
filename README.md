생태 관광 서비스 API
==========================

## 빌드 및 실행 방법
```
git clone https://github.com/insukChoi/ecologyTour.git
cd ecologyTour
mvn clean test spring-boot:run 또는 ./mvnw clean test spring-boot:run
```

- http://localhost:8000/swagger-ui.html (API DOC - Swagger2)
- http://localhost:8000/h2-console (DB 관리콘솔)
- __UTF-8로 인코딩 된 download/sample2.csv 파일을 이용하여 업로드 API 실행__

## 실행 화면
- Swagger2
<img width="1236" alt="swagger" src="https://user-images.githubusercontent.com/14847562/69480890-06f2c480-0e4f-11ea-8729-f2d39a378011.png">

- DB 관리 콘솔
<img width="1019" alt="db" src="https://user-images.githubusercontent.com/14847562/69480912-48836f80-0e4f-11ea-99ef-0b38716d9127.png">

## 문제해결 전략
1. Entity 설계
    - Tour 
        - 관광정보 엔티티
        - 프로그램(Program)필드 ManyToOne 관계로 설계
        - 서비스지역(Region)필드 ManyToMany 관계(FetchType.LAZY)로 설계
    - Program
        - 프로그램(Program) 엔티티
    - Region
        - 서비스지역 엔티티
        - Tour 엔티티와 ManyToMany 관계 설계
        - mappedBy 설정   
    - UserInfo
        - 유저 엔티티
     
2. API 설계
    (HTTP Methods for RESTful 구현)
    1. 파일 업로드 API 
        - opencsv 라이브러리를 사용하여, csv 파일 업로드 구현. 업로드시 '서비스 지역' 레코드의 경우는 ' ' 로 split 하여 length 만큼 각각 Region 엔티티에 등록.
    2. 등록/수정/조회 API
        - 각 엔티티별 연관관계에 초점을 두고 개발.
    3. 특정 지역에서 진행되는 프로그램명과 테마 출력 API
        - 프로그램명과 테마를 속성으로 가지고 있는 ProgramDto 를 두어 객체 메세지 전달 방법 사용
    4. '프로그램 소개' 레코드에서 특정 문자열이 포함된 서비스 지역 개수 API
        - 입력 된 keyword로 Tour엔티티 like 검색 후, 데이터 가공을 위해 Collectors.groupingBy 로 서비스 지역 갯수를 구현.
    5. 모든 레코드의 프로그램 상세 정보에서 입력 단어의 출현빈도수 API
        - 입력 된 keyword로 Tour엔티티 like 검색 후, 출현빈도수를 구하기 위하여 Stream 과 SpringFramework의 StringUtils 사용.
    6. 생태관광 프로그램 추천 API
        - 가중치를 properties(yml) 로 분리 및 가중치를 계산하는 로직을 RecommendationPrgDto 객체 안으로 추상화하고, 서비스에서는 Stream의 최종연산으로 제일 높은 가중치의 프로그램 1개를 추천함.

3. JWT + Security
   - signin, signup API를 통해 token 발급시, access_token 과 refresh_token 발급.
   - access_token 만료 시간 : 1h
   - refresh_token 만료 시간 : 12h
   - access_token 만료시, 비교적 만료 시간이 긴 refresh 토큰을 이용하여 access_token 재발급.
   - Swagger Header에 default로 'Authorization: Bearer  ' 셋팅하여, 뒤에 토큰 추가하여 API 테스트.
   
4. 예외처리
    - @ControllerAdvice 클래스를 구현하여, 각 도메인별 Exception 발생시 해당 클래스에서 ErrorResponse.class 객체 형식으로 API 공통 Response 메세지 출력.

## 개발 프레임워크 및 라이브러리
- Java8
- SpringBoot 2.2.1
- JPA
- Spring Security 
- JWT
- H2DB (인메모리 DB사용)
- Lombok
  - 보일러플레이트 코드를 줄이기 위해 사용
- Maven
  - 프로젝트 빌드 툴
- Swagger2
  - API DOC 문서로 활용
- Opencsv 
  - CSV 파일 업로드를 위하여 사용
