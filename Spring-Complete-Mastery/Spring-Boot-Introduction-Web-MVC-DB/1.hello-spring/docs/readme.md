1. Spring Boot는 정적 및 템플릿 기반 환영 페이지를 모두 지원합니다. 먼저 설정된 정적 콘텐츠 위치에서 파일을 찾습니다 index.html. 파일이 없으면 템플릿을 찾습니다 index. 템플릿이 발견되면 자동으로 애플리케이션의 환영 페이지로 사용됩니다.
--> project/src/main/resources/static/index.html
2. 빌드 후 build 폴더 내부에 libs 생기지 않을때 해결 방법
 - 우측 gradle 탭 클릭 후 Tasks/build/clean 통해서 클린 빌드
 - 다시 재 build 하면 실행(.jar) 파일 나옴

3. @ResponseBody 부여 할경우 기본적으론 "Json" 포맷 형식으로 반환 하게 된다.
4. getter/setter 단축키
  - 윈도우 기준 (ALT + Insert)