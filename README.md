#   🎶 My-Music-Note
- My-Music-Note는 일기와 음악을 통한 심리치료 서비스입니다.

### 구성원
| <a href="https://github.com/masiljangajji"><img src="https://github.com/masiljangajji.png" width="100px"><br>BE 이승재</a> | <a href="https://github.com/kooyoonji"><img src="https://github.com/kooyoonji.png" width="100px"><br>FE 구윤지</a> | <a href="https://github.com/NyongCho"><img src="https://github.com/NyongCho.png" width="100px"><br>AI 조준용</a> |<a href="https://github.com/UiinKim"><img src="https://github.com/UiinKim.png" width="100px"><br>AI 김의인</a> 
|-----|-----|-----|----|

### 개발 환경
- 개발도구: Intellij IDEA - Ultimate
- 언어: Java 17 LTS<br>
- 빌드도구: Gradle
- 개발
  - Spring Boot: 3.3.4
  - Spring Data
    - Spring Data JPA
- 테스트
  - Junit5
  - AssertJ
  - Mockito
  - SonarCloud
- AWS Infra
  - VPC
  - EC2
  - ALB
  - ASG
  - NAT Gateway
- CI/CD
  - AWS S3
  - AWS CodeDeploy
  - GitHub Actions
  - Docker
- 데이터베이스
  - AWS RDS PostgreSQL 16.3
  - AWS S3
- ERD
  - ERDCloud

### 사용 기술
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=gradle&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
<br>
![Spring Boot](https://img.shields.io/badge/Spring_Boot-%236DB33F.svg?style=for-the-badge&logo=spring-boot&logoColor=white)
![React](https://img.shields.io/badge/React-61DAFB.svg?style=for-the-badge&logo=react&logoColor=black)
<br>
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-007396.svg?style=for-the-badge&logo=java&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C.svg?style=for-the-badge&logo=hibernate&logoColor=white)
<br>
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![AWS S3](https://img.shields.io/badge/Amazon%20S3-569A31.svg?style=for-the-badge&logo=amazon-s3&logoColor=white)
![AWS CodeDeploy](https://img.shields.io/badge/AWS%20CodeDeploy-232F3E.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![AWS](https://img.shields.io/badge/Amazon%20AWS-232F3E.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white)
<br>
![Git](https://img.shields.io/badge/Git-F05032.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717.svg?style=for-the-badge&logo=github&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![DataGrip](https://img.shields.io/badge/DataGrip-000000.svg?style=for-the-badge&logo=datagrip&logoColor=white)
![SonarCloud](https://img.shields.io/badge/SonarCloud-F3702A.svg?style=for-the-badge&logo=sonarcloud&logoColor=white)


## AWS Infra
![My-Music-Note](https://github.com/user-attachments/assets/384049f4-670b-464b-b53f-b2b6579e8622)


## CI/CD

![CI:CD](https://github.com/user-attachments/assets/f96eb2c9-a07a-42cd-b910-4bd0344e22f2)

0. 인프라 관리비용 최소화를 위해 Jenkins 대신 Github Ations 사용
1. S3는 CodeDeploy가 실행시킬 Script를 저장
2. CodeDeploy를 사용하는 목적은 Blue/Green 배포 + ASG로 생성된 EC2배포 자동화의 목적 
3. Docker를 사용함으로서 동일한 환경을 제공


