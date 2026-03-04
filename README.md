# SPRING PLUS
작성자 : 김대훈

## 주요 구현 내용
- JPQL -> QueryDSL로 변경

- Spring Security 적용

- QueryDSL로 검색기능 만들기

- API가 성공하든 실패하든 해당 정보를 log로 남기기

- AWS RDS 적용

## 구현하지 못한 내용
- AWS S3 버킷 연결

- 대용량 데이터 처리

## POSTMAN API 테스트
1. 회원가입(일반)
![img.png](image/img.png)
<br></br>
2. 로그인(일반)
![img_1.png](image/img_1.png)
<br></br>

3. 회원가입(매니저)
![img_2.png](image/img_2.png)
<b></br>

4. 로그인(매니저)
![img_3.png](image/img_3.png)
<br></br>

5. Todo 생성
![img_4.png](image/img_4.png)
<br></br>

6. 매니저 등록 성공
![img_5.png](image/img_5.png)
<br></br>

7. 매니저 등록 실패 - 존재하지 않는 유저
![img_6.png](image/img_6.png)
<br></br>

8. 매니저 등록 실패 - 권한이 없는 유저
![img_7.png](image/img_7.png)
<br></br>

9. 매니저 목록 조회
![img_8.png](image/img_8.png)
<br></br>

10. 매니저 삭제
![img_9.png](image/img_9.png)
 <br></br>

11. 8,9,10 후 LOG 테이블
![img.png](image/img_11.png)


## AWS 활용 <br>
### EC2
![img_1.png](image/img2_1.png)
탄력적 IP 주소 설정

### RDS
![img_2.png](image/img2_2.png)
![img_3.png](image/img2_3.png)

### 헬스 체크
![img.png](image/img2.png)