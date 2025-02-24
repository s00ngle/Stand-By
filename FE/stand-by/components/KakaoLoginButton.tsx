"use client";

import { useEffect } from "react";
// import { useUser } from "@/hooks/useUser";
import KakaoLoginButtonImage from "@/public/kakao-login.png";
import SsafyImage from "@/public/ssafy.png";
import Image from "next/image";
// import { loginUser } from "@/lib/api/auth";

interface KakaoLoginButtonProps {
  onLoginSuccess?: () => void;
  onLoginError?: (error: Error) => void;
}

const KakaoLoginButton = ({
  onLoginSuccess,
  onLoginError,
}: KakaoLoginButtonProps) => {
  // const { login } = useUser();
  // const REST_API_KEY = process.env.NEXT_PUBLIC_KAKAO_REST_API_KEY;
  // const REDIRECT_URI =
  //   process.env.NEXT_PUBLIC_KAKAO_REDIRECT_URI ||
  //   "http://i12b211.p.ssafy.io/login";

  // const handleKakaoLogin = async (code: string) => {
  //   try {
  //     console.log("Received Kakao auth code:\n\t", code);
  //     // 실제 API 호출
  //     const response = await loginUser(code);
  //     console.log("Kakao login response:\n\t", response);

  //     const { token } = response;
  //     alert("token :" + token);
  //     const { nickname = "tester", role, profileImage = SsafyImage } = response;
  //     if (token) {
  //       localStorage.setItem("accessToken", token);
  //     } else {
  //       throw new Error("Access token is undefined");
  //     }

  //     // 로그인 성공 후 상태 업데이트
  //     login({ nickname, role, profileImage }, token);
  //     onLoginSuccess?.();
  //   } catch (err) {
  //     console.error("Kakao login error:", err);
  //     onLoginError?.(err as Error);
  //   }
  // };

  // useEffect(() => {
  //   // URL에서 인증 코드 확인
  //   const url = new URL(window.location.href);
  //   const code = url.searchParams.get("code");

  //   if (code) {
  //     // console.log("Received Kakao auth code:", code);
  //     handleKakaoLogin(code).catch((error) => {
  //       console.error("Failed to handle Kakao login:", error);
  //       onLoginError?.(error);
  //     });
  //   }
  // }, []);

  // const handleClick = () => {
  //   const kakaoLoginUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code&scope=profile_nickname`;
  //   // console.log("Redirecting to:", kakaoLoginUrl);
  //   window.location.href = kakaoLoginUrl;
  // };

  return (
    <button
      // onClick={handleClick}
      className="social-login-button group relative overflow-hidden"
    >
      <Image
        src={KakaoLoginButtonImage}
        alt={"kakao-login"}
        width={500}
        height={0}
        priority
        className="transition-all duration-300 group-hover:brightness-90"
      />
    </button>
  );
};

export default KakaoLoginButton;
