#   🎶 My-Music-Note
My-Music-Note는 일기와 음악을 통한 나만의 AI친구 사비스입니다.
- URL: www.musicdiaryclub.com
- [api-server-repo](https://github.com/My-Music-Note/back)



### 개발 환경
- 개발도구: Intellij IDEA - Ultimate
- 언어: Java 17 LTS<br>
- 빌드도구: Gradle
- 개발
  - Spring Boot: 3.3.4
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
- ERD
  - ERDCloud
- ETC
   - Post Man

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

## 문제 정의 및 해결 과정

프로젝트 초기에는 아래와 같은 기술적 과제와 한계가 있었습니다.  
이를 해결하기 위해 다양한 기술 스택과 AWS 서비스를 활용하여 다음과 같은 방안을 도입했습니다.

### 0. 서비스 안정성과 확장성 확보
- **문제점**: 단일 서버 구성으로 장애 발생 시 서비스가 중단될 위험이 있었으며, 트래픽 증가 시 확장성이 부족했습니다.
- **해결방안**:
  - `Auto Scaling Group과 Application Load Balancer`를 결합하여 고가용성 아키텍처를 구축했습니다.
  - `Blue/Green 배포` 방식을 적용하여 무중단 배포를 실현했습니다.
  - `JMeter`를 이용해 가상 사용자 4000명 테스트를 진행했으며, Auto Scaling Group이 트래픽 부하에 따라 3분 내에 새로운 인스턴스를 자동 생성하여 부하를 분산했습니다.

### 1. 효율적인 CI/CD 파이프라인 구축
- **문제점**: 수동 배포로 인해 시간 소요 및 인적 오류 가능성이 높았으며, 배포 중 다운타임이 발생했습니다.
- **해결방안**:
  - `Gradle`과 `npm`의 캐싱을 통해 빌드 시간을 약 40% 단축했습니다.
  - `Docker 기반 배포`로 일관된 배포 환경을 구현했습니다.
  - `GitHub Actions`와 `AWS CodeDeploy`를 결합해 CI/CD 파이프라인을 자동화했으며, Blue/Green 배포 방식으로 서비스의 가용성을 유지했습니다.

### 2. 보안 및 네트워크 설정 강화
- **문제점**:
  - Public/Private Subnet 간의 역할 구분 및 접근 제한이 불명확했습니다.
  - Backend 계층으로 외부 트래픽이 직접 도달할 수 있는 보안 취약점이 있었습니다.
- **해결방안**:
  - `Session Manager`를 도입하여 SSH 없이 안전하게 EC2에 접근하며, 추가 Bastion Host 비용을 절감했습니다.
  - Frontend와 Backend 계층에 `ALB와 ASG 연동 보안 그룹`을 설정하여 보안 수준을 강화했습니다.
  - HTTPS 통신 암호화를 위해 `SSL 인증서`를 Application Load Balancer에 적용했습니다.

### 3. DNS 및 서브도메인 관리
- **문제점**: 브랜드 도메인과 서브도메인 미설정으로 서비스 인지도와 사용자 경험이 저하되었습니다.
- **해결방안**:
  - `Route53`을 활용하여 **musicdiaryclub.com** 도메인과 `api.musicdiaryclub.com`, `www.musicdiaryclub.com` 서브도메인을 설정했습니다.

### 4. 코드 품질 관리 비용 절감
- **문제점**: 코드 품질 검사를 수동으로 진행해 시간과 비용이 과다하게 소모되었습니다.
- **해결방안**:
  - `SonarCloud`를 도입하여 GitHub Actions와 연동, Pull Request 단계에서 코드 스멜과 보안 취약점을 실시간 분석했습니다.


## CI/CD

![CI:CD](https://github.com/user-attachments/assets/f96eb2c9-a07a-42cd-b910-4bd0344e22f2)

0. 인프라 관리비용 최소화를 위해 `Jenkins` 대신 `Github Ations` 사용
1. `S3`는 `CodeDeploy`가 실행시킬 Script를 저장
2. `CodeDeploy`를 사용하는 목적은 `Blue/Green` 배포 + `ASG`로 생성된 `EC2`배포 자동화의 목적 
3. `Docker`를 사용함으로서 동일한 환경을 제공


