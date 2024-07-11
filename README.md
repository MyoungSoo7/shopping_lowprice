# 네이버 쇼핑 최저가 리스트
## [활용기술] <br>
* **Java 11** <br>
* **SpringBoot 2.7.1**<br>
* mariadb<br>
* spring-data-jpa<br>
* thymeleaf<br> 

## [ERD]<br>
![image](https://github.com/MyoungSoo7/shopping_lowprice/assets/13523622/d1b090d8-1975-4c29-a46c-e424e8817f5a)<br>

## [작업내용]<br>
* 회원가입(카카오Oauth가능) 
* 네이버 쇼핑 검색 API로 상품등록
* 사용자는 폴더로 상품관리(페이징, 가격, 상품명, 오름/내림차순검색) 
* 스케줄러로 상품 가격이 최저가 이하 도달시 최저가 표시
* 관리자 권한있는 회원은 회원별 API 사용시간과 사용횟수 확인 가능
* 상품유효성 체크, 예외처리, 로깅, 스프링 시큐리티, 패스워드(BCryptPasswordEncoder), AOP(API 측정), 로깅(관리자가입, API 사용시간 기록, 스케줄러)

## [테스트]<br>
* 상품이 회원이 지정한 최저가 도달시 변경
 

