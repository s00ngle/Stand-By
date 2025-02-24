# 1. Node.js 18 사용
FROM node:18 AS builder
WORKDIR /app

# 2. package.json만 복사 후 의존성 설치 (더 빠름)
COPY package.json package-lock.json ./
RUN npm ci

# 3. 프로젝트 코드 복사 및 Next.js 빌드 실행
COPY . .

ARG NEXT_PUBLIC_KAKAO_REST_API_KEY
ARG NEXT_PUBLIC_KAKAO_REDIRECT_URI

ENV NODE_ENV=production
ENV NEXT_PUBLIC_KAKAO_REST_API_KEY=$NEXT_PUBLIC_KAKAO_REST_API_KEY
ENV NEXT_PUBLIC_KAKAO_REDIRECT_URI=$NEXT_PUBLIC_KAKAO_REDIRECT_URI

#RUN npm install
RUN npm run build

# 4. 실행 환경 설정 (더 작은 이미지 사용)
FROM node:18-alpine AS runner
WORKDIR /app

# 5. 실행에 필요한 파일들만 복사
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/public ./public
COPY --from=builder /app/package.json ./package.json
COPY --from=builder /app/node_modules ./node_modules


EXPOSE 3000

CMD ["npm", "run", "start"]