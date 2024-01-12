# learning-mate-backend

## 프로젝트 내용


**● 📢 프로젝트 배경**

- 피라미드 기억 이론에 따르면 학습 내용에 대해 스스로 강의를 하게 되면 24시간 후에도 90%의 내용을 기억할 수 있습니다. 이 이론에 의거하여, 학습하는 내용을 앱에게 강의하는 과정에서 학습의 이해도와 장기 기억력을 높일 수 있습니다.
- 자신이 이해한 내용과 설명하는 방법에 대한 구체적인 피드백을 받아보는 것을 목표로, Whisper API를 활용하여 실시간으로 녹음해보고, ChatGPT에게 점수를 부여받고 부연 설명을 들을 수 있습니다.

**● 🏃프로젝트 목표**

- 기기 자동 로그인 구현
- CI/CD 구축
- chatGPT 및 whisper API 와의 연동
- 메인 페이지 / 유저 페이지 / 랭킹 페이지 로 구성하여 앱 출시
- 작게나마 수익화에 도전

**● 프로젝트 성과 및 의의**

- 서비스 출시 후 실제 사용자의 피드백을 받아보면서, 기획할 때는 보이지 않았던 불편함을 알게 되었고 일부 빠르게 개선하였습니다. **사용자의 피드백을 받아 프로덕트를 개선하는 경험**을 통해 성장할 수 있었습니다.
- **서버 부하 최소화와, 보안 처리** 등 아키텍처 개선 경험을 가지게 된 프로젝트였습니다.
- 현재 50명 가량의 유저를 보유 중입니다.

## 발생문제 및 해결방법

1. **chatGPT 및 whisper API 는 응답 시간이 불규칙하고, 무한 대기하는 현상이 발생할 수 있어 서버 부담이 증가합니다.**

 : ChatGPT와 whisper API 특성 상 응답 시간이 불규칙적이고, API 반환 오류가 생길 수 있습니다. 따라서 @Async 어노테이션을 사용하여 polling 기법으로 api 반환 하도록 구현하였습니다. 

FE에서 특정 간격으로 API 호출이 오면 해당 dto에 success와 fail로 반환하는 방식입니다. FE상에서는 정해진 시간이 지나면 timeout 됩니다.

- 해당 커밋 : https://github.com/sanhak-chatgpt/learning-mate-backend/commit/32e3ca5a13ea8ca532e394c08fa12984d814f3ac, https://github.com/sanhak-chatgpt/learning-mate-backend/commit/67afca18edca1eb3cee473256b30e37ef2957a29

2. **yml 파일에 환경변수 노출 오류**

yml 파일에 기입된 환경변수들은 본래 github action 환경변수 로 기입하였으나 이는 도커 이미지 노출 가능성이 있어 좋지 않은 설계 방법입니다. 따라서 env 외부 주입을 통해 보안을 강화하였습니다.

([블로그 글](https://youngseo-computerblog.tistory.com/80))

추가로 개발환경에서도 잘못해 commit이 일어나 환경변수가 노출되는 현상을 막기 위해, Intellij 상에 환경변수를 할당하는 방법을 팀원간 공유하였습니다.

3. **사용자의 접근성 편의를 위한 로그인 구현 단순화**

본래 목적은 소셜로그인 구현이었습니다. 그러나 해당 앱의 접근성이 높지 않아야 한다는 판단과, 보안 요구사항이 그리 높지 않은 상황으로 기기 자동 로그인으로 구현하였습니다.

Spring security를 이용한 자동 로그인을 구현하였습니다. 최초 회원가입 시 “사용자 {random}” 값으로 반환 후 SpringContext에 인증 토큰(Authentication Token) 저장 합니다.

- 해당 커밋 : https://github.com/sanhak-chatgpt/learning-mate-backend/commit/7b0d5cf1cac61ad589ab795dc80043dc96cbd54a, https://github.com/sanhak-chatgpt/learning-mate-backend/commit/c9ce2a3e8d677c07c6c4d601ee409d8245bc85bc

4. **광고비 대비 매출 비율(ROAS)는 0.6%로, 마케팅 효과 저하**

광고비는 48,992원을 집행하였으며, 이로 30명의 유저를 획득하였습니다. 즉, 유저 1명 당 획득 비용(CPI)은 1,700원을 지출하였습니다. 총 광고 수익은 1,278원으로 계산 시 293원의 수익을 올렸습니다. 이는 마케팅 초기임을 감안하더라도 지나치게 낮은 비율입니다. 

이상적인 목표를 가지고 시작했던 프로젝트였지만, 날카로운 판단을 내리고자 하였고 아래와 같이 수익이 적게 발생하는 원인과 서비스 실패 경험을 추적할 수 있었습니다.

**1) 허들이 높습니다.**
유저의 앱 사용에 대한 허들이 낮아야 앱의 처음부터 끝까지를 경험합니다. 휴대폰의 마이크에 강의를 진행하는 것과, 광고를 보는 조건인 강의의 진행까지의 허들이 높아 유저가 광고를 많이 보지 않습니다. 
**2) 리텐션 비율이 낮습니다.**
비싸게 데리고 온 유저가 지속적으로 앱을 사용해 줘야 비용을 회수할 수 있습니다. 애플리케이션의 설치 후 7일 리텐션은 0.9% 입니다. 즉 100명 중 1명만이 1주일 후에도 이 앱을 사용한다는 것입니다.

그럼에도 불구하고, 저희 팀은 IT 서비스의 초기 기획, 디자인, 개발, AWS 배포, 유지보수, 앱스토어 출시 뿐 아니라 수익화 시도까지 개발 단계의 전체 사이클을 진행해 볼 수 있었습니다.

5. **Sentry로 유저의 오류 상황 실시간 대응**

로그인 로직 배포 이후 서비스 도중, JWT 에러가 발생한 것을 Sentry를 통해 확인할 수 있었습니다. 디버깅 환경으로 에러를 트래킹하던 도중 Spring security doFilter 코드가 반복 호출되어 무제한 필터처리가 진행되고 있었습니다. 

이후 모니터링 툴을 사용하여 고객서비스를 신속하게 해결할 수 있었습니다.



- Sentry 연동 커밋 : https://github.com/sanhak-chatgpt/learning-mate-backend/commit/0676a8969faea8114583cbb9515b844f00e5d4a7
- 에러 트래킹 커밋 : https://github.com/sanhak-chatgpt/learning-mate-backend/commit/39d526eed736684cecbc107b4b77c1f60f15d5a6

6. **S3 저장소와 EC2 인스턴스를 이용한 파일 업로드/다운로드 이슈 해결 과정**

: 유저가 업로드하는 음성녹음 파일을 저장하기 위해 S3를 사용하였습니다. Private으로 접근 제어 설정하여 데이터 탈취를 막을 수 있습니다.  파일 업로드는 FE에서 직접 진행하기 위해 서버에서는 Presigned URL을 생성하여 반환합니다.

하지만 배포 이후, FE에서의 파일 업로드 및 백엔드에서의 파일 다운로드가 실패하는 이슈가 발생하였습니다. 이 이슈는 AccessDenied 로, EC2 인스턴스에서 S3 버킷에 접근할 수 있는 접근 권한이 없다는 오류입니다. 

IAM 키를 환경 변수로 설정하면 이 문제를 해결할 수 있지만, 크리덴셜 키가 외부에 탈취될 경우 그대로 공격 대상이 될 수 있습니다. 따라서 AWS의 보안 Best Practice를 따라 EC2 인스턴스에 IAM Role을 지정하는 방법을 선택하였습니다. 

하지만 이후 권한 오류가 계속해서 발생하였고, 추가로, IAM Role을 이용하는 경우 AWS 서버에서 임시 토큰을 발급받는 AWS Security Token Service 라이브러리 의존성을 추가해야 한다는 정보를 확인하게 되었습니다. 

이에, `com.amazonaws:aws-java-sdk-s3` 에 이어 `com.amazonaws:aws-java-sdk-sts` 의존성을 추가하여 이슈를 해결할 수 있었습니다. 

## 아키텍처


### 프로젝트 아키텍처
![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/989a5d80-a9b6-47ac-a1d1-002f772ba7ca/Untitled.png)

### CD 파이프라인 아키텍처

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/9d9b279f-4588-42d8-9942-a641103dda59/Untitled.png)

## GIT 링크

 [ [Frontend](https://github.com/sanhak-chatgpt/learning-mate-frontend) ] [ [Backend](https://github.com/sanhak-chatgpt/learning-mate-backend) ]

## 사이트 주소

 [ [Frontend](https://www.thelearningmate.com/) ] [ [Backend](https://api.thelearningmate.com/) ] [ [Swagger](https://api.thelearningmate.com/swagger-ui/index.html) ]
