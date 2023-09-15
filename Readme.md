# Spring Boot - Bob Application

레시피를 스크랩하고, 내가 만든 레시피를 공유하는 앱

### 설명
1. 음식이름을 검색하면 만개의 레시피에서 리스트를 스크래핑해서 가져옴 (이름, 링크 를 DB 에 저장) + DB 에 저장되어있는 레시피 (사용자가 작성한) 리스트
2. 1.의 리스트 중에서 레시피를 선택하면 상세레시피를 볼수있음 (만개의 레시피 일 경우 스크래핑 & DB 에 저장 )
3. 상세페이지에서 북마크 추가
4. 카테고리별 북마크 조회 / 북마크 수정 / 북마크 삭제 / 북마크된 레시피 메모
5. 사용자 레시피 작성가능 / 레시피 수정 / 레시피 삭제
6. 레시피 리뷰(후기), 평점 작성
   
### 사용 기술
* Spring boot
* Spring Data JPA
* Spring Security
* Jsoup
* MySql
* aws S3
* Scheduler
* EC2

### 기능
* 회원가입 / 로그인 : 인증되지 않은 사용자는 레시피 검색만 가능 ( "/search", "/detail/{recipeLinkId}" )
* 공유 레시피 검색
* 레시피 CRUD
* 레시피 북마크
* 리뷰 작성


### ERD
![bob (1)](https://github.com/Krystal-13/bob/assets/129822965/8e88d439-bc65-4b70-b6fc-6d9246b7f221)


