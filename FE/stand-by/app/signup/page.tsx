"use client";

import { signupUser } from "@/api/auth";
import { tempUserId } from "@/atoms/tempUserId";
import { UserInfo, userStateAtom } from "@/atoms/userAtom";
import Button from "@/components/Button";
import InputField from "@/components/InputField";
import { useAtom } from "jotai";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";
import Cookies from "js-cookie";

const JoinPage = () => {
  const [nickname, setNickname] = useState("");
  const [selectedRole, setSelectedRole] = useState<
    "employer" | "employee" | null
  >(null);
  const [userId, setUserId] = useAtom(tempUserId);
  const [user, setUser] = useAtom(userStateAtom);
  const router = useRouter();

  const handleNicknameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    // 10글자로 제한
    if (value.length <= 10) {
      setNickname(value);
    }
  };

  const handleSignup = async () => {
    if (!nickname || !selectedRole) {
      alert("닉네임과 역할을 선택해주세요.");
      return;
    }

    if (nickname.length > 10) {
      alert("닉네임은 10글자 이하여야 합니다.");
      return;
    }

    try {
      if (userId === null) {
        alert("User ID is missing.");
        return;
      }
      const response = await signupUser(nickname, selectedRole, userId);

      const userInfo: UserInfo = {
        userId: response.user.userId,
        nickname: nickname,
        profileImage: response.profileImageUrl,
        role: selectedRole,
      };

      // 쿠키에 토큰 저장 (7일 유효기간)
      Cookies.set("token", response.token, {
        expires: 7,
        path: "/",
        secure: process.env.NODE_ENV === "production",
        sameSite: "lax",
      });

      setUser({
        userInfo,
        kakaoToken: response.token,
      });

      window.location.href = "/";
    } catch (error) {
      if (error instanceof Error) {
        alert(error.message);
      } else {
        alert("An unknown error occurred.");
      }
    }
  };

  return (
    <div className="flex flex-1 flex-col justiry-between p-8">
      <div className="flex-1 flex flex-col mb-10">
        <div className="flex-1 flex flex-col justify-center gap-4">
          <InputField
            title="닉네임"
            label="중복확인"
            placeholder="닉네임을 입력하세요 (10글자 이하)"
            showButton={false}
            onChange={handleNicknameChange}
            value={nickname}
          />
        </div>
        <div className="flex gap-4">
          <Button
            label="구인자"
            variant={selectedRole === "employer" ? "default" : "outline"}
            onClick={() => {
              setSelectedRole("employer");
            }}
          />
          <Button
            label="구직자"
            variant={selectedRole === "employee" ? "default" : "outline"}
            onClick={() => {
              setSelectedRole("employee");
            }}
          />
        </div>
      </div>
      <div className="flex flex-col gap-2 mt-4">
        <Button label="회원가입" onClick={handleSignup} />
      </div>
    </div>
  );
};

export default JoinPage;
