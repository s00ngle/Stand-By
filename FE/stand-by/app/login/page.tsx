"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { useSetAtom } from "jotai";
import KakaoLoginButton2 from "@/components/KakaoLoginButton2";
import { loginUser } from "@/api/auth";
import { userStateAtom, UserInfo } from "@/atoms/userAtom";
import { tempUserId } from "@/atoms/tempUserId";
import Loading from "@/components/Loading";
import DefaultAvatarImage from "@/public/default-avatar.png";
import Cookies from "js-cookie"; // 추가: js-cookie 라이브러리 사용

const LoginPage = () => {
  const router = useRouter();
  const setUserState = useSetAtom(userStateAtom);
  const setTempUserId = useSetAtom(tempUserId);
  const [loading, setLoading] = useState(false);
  const [code, setCode] = useState<string | null>(null);

  const tempUserInfo: UserInfo = {
    nickname: "Guest",
    role: "employer",
    userId: 1,
  };

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    setCode(urlParams.get("code"));
  }, []);

  useEffect(() => {
    if (code) {
      setLoading(true);
      loginUser(code)
        .then((data) => {
          console.log("카카오톡 로그인 데이터 :", data);

          const userData = data.user;

          if (!data.isAuthenticated) {
            // 회원가입 되지 않은 경우 페이지로 이동
            console.log("회원가입 되지 않은 사용자");
            setTempUserId(data.kakaoId);
            console.log("임시 사용자 아이디:", data.kakaoId);
            router.push("/signup");
          } else {
            // 회원가입이 되어있는 경우
            const userInfo: UserInfo = {
              userId: userData.userId,
              nickname: userData.nickName,
              profileImage: userData.profileImageUrl,
              role: userData.role,
            };

            // Jotai 상태 업데이트
            setUserState({
              userInfo,
              kakaoToken: data.token,
            });

            // 쿠키에 토큰 저장 (7일 유효기간)
            Cookies.set("token", data.token, {
              expires: 7,
              path: "/",
              secure: process.env.NODE_ENV === "production",
              sameSite: "lax",
            });

            console.log("로그인 성공:", data);
            window.location.href = "/";
            // router.push("/");
          }
        })
        .catch((err) => {
          console.error("Kakao 로그인 실패:", err);
          alert("로그인에 실패했습니다. 다시 시도해주세요.");

          // // 임시 사용자 정보로 상태 업데이트
          // setUserState({
          //   userInfo: tempUserInfo,
          //   kakaoToken: "this-is-kakao-test-token",
          // });

          // // 임시 토큰도 쿠키에 저장
          // Cookies.set("token", "this-is-kakao-test-token", {
          //   expires: 7,
          //   path: "/",
          //   secure: process.env.NODE_ENV === "production",
          //   sameSite: "lax",
          // });

          // console.log("임시 사용자 로그인 활성화:", tempUserInfo);
          // window.location.href = "/";
          // router.push("/");
        })
        .finally(() => {
          setLoading(false);
        });
    }
  }, [code, setUserState, router]);

  return (
    <div className="flex-1 p-8 flex flex-col justify-center gap-4">
      {loading ? <Loading /> : <KakaoLoginButton2 />}
    </div>
  );
};

export default LoginPage;
