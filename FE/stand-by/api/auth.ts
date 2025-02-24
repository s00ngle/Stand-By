import { userStateAtom } from "@/atoms/userAtom";
import { useAtom } from "jotai";

export const loginUser = async (code: string) => {
  const response = await fetch("/api/auth/signin", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ code }),
  });
  if (!response.ok) {
    throw new Error("Kakao 로그인에 실패하였습니다.");
  }
  return response.json();
};

export const signupUser = async (
  nickname: string,
  role: string,
  kakaoId: number
) => {
  const response = await fetch("/api/auth/signup", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ nickname, role, kakaoId }),
  });
  if (!response.ok) {
    throw new Error("Kakao 회원가입에 실패하였습니다.");
  }
  return response.json();
};

// export const logoutUser = async () => {
//   const response = await fetch(`https://kauth.kakao.com/oauth/logout?client_id=${process.env.NEXT_PUBLIC_KAKAO_CLIENT_ID}`, {
//     method: "POST",
//     headers: {
//       Authorization: `Bearer ${kakaoToken}`,
//     },
//   });
//   if (!response.ok) {
//     throw new Error("로그아웃에 실패하였습니다.");
//   }
//   return response;
// };

export const deleteUser = async (userId: number) => {
  const response = await fetch(`/api/auth/user/${userId}`, {
    method: "DELETE",
  });

  // logoutUser();

  if (!response.ok) {
    throw new Error("회원탈퇴에 실패하였습니다.");
  }
  return response.json();
};
