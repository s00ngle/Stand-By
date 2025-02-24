# 1. Nginx 최신 버전을 기반으로 이미지 생성
FROM nginx:latest

# 2. 로컬의 index.html 파일을 컨테이너의 기본 웹 경로로 복사
COPY ./index.html /usr/share/nginx/html/

# 3. 컨테이너에서 사용할 포트를 지정
EXPOSE 80

# 4. 컨테이너 실행 시 Nginx를 실행
CMD ["nginx", "-g", "daemon off;"]
