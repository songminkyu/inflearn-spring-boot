# 서블릿 예제 모음

## 개요

이 패키지는 Jakarta EE 서블릿을 사용한 HTTP 요청/응답 처리 예제를 포함하고 있습니다.
서블릿 기반 웹 애플리케이션의 기본 동작 원리와 다양한 HTTP 통신 방식을 이해하는 데 도움이 됩니다.

## 패키지 구조

- `basic`: 기본적인 서블릿 예제
  - `request`: HTTP 요청 데이터 처리 예제
    - `RequestBodyJsonServlet`: JSON 형식의 요청 본문 처리
  - `response`: HTTP 응답 데이터 처리 예제

## 예제 사용법

### 요청 본문 JSON 처리 (`RequestBodyJsonServlet`)

이 서블릿은 `/request-body-json` 경로로 들어오는 JSON 형식의 요청 본문을 처리합니다.

요청 예시: