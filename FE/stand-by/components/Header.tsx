"use client";

import Image from "next/image";
import Link from "next/link";
import Logo from "./Logo";
import { useEffect, useState } from "react";
import DefaultAvatar from "@/public/default-avatar.png";
import { useAtom } from "jotai";
import { userStateAtom } from "@/atoms/userAtom";
import { useRouter } from "next/navigation";
import { deleteUser } from "@/api/auth";
import { getProfile } from "@/api/profile";

interface ProfileResponse {
  nickname: string;
  profileImage: string | null;
}

const Header = () => {
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [user, setUserState] = useAtom(userStateAtom);
  const router = useRouter();

  useEffect(() => {
    const syncProfile = async () => {
      if (!user?.userInfo?.userId) return;

      try {
        const response = await getProfile(user.userInfo.userId.toString());
        const profileData = response.profile;
        setUserState((prev) => {
          if (!prev || !prev.userInfo) return null;
          return {
            ...prev,
            userInfo: {
              ...prev.userInfo,
              nickname: profileData.nickname,
              profileImage: profileData.profileImage,
            },
          };
        });
      } catch (error) {
        console.error("프로필 동기화 실패:", error);
      }
    };

    // 초기 동기화 실행
    syncProfile();

    // 60초마다 동기화 실행
    const intervalId = setInterval(syncProfile, 60000);

    // 컴포넌트 언마운트 시 인터벌 정리
    return () => clearInterval(intervalId);
  }, [user?.userInfo?.userId, setUserState]);

  const handleDropdownToggle = () => {
    setIsDropdownOpen((prev) => !prev);
  };

  const handleLinkClick = () => {
    setIsDropdownOpen(false);
  };

  const handleLogout = async () => {
    handleLinkClick();
    try {
      setUserState(null);
      document.cookie =
        "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
      router.push("/auth");
    } catch (error) {
      console.error(error);
    }
  };

  const handleDeleteAccount = async () => {
    const isConfirmed = window.confirm(
      "정말 탈퇴하시겠습니까?\n회원 탈퇴 시 모든 데이터가 삭제되며 이 작업은 되돌릴 수 없습니다."
    );

    if (!isConfirmed) {
      handleDropdownToggle();
      return;
    }

    handleLinkClick();

    if (!user || !user.userInfo?.userId) {
      throw new Error("사용자 정보가 없습니다.");
    }
    if (!user.kakaoToken) {
      throw new Error("Kakao Token이 없습니다.");
    }

    try {
      await deleteUser(user.userInfo.userId);
      setUserState(null);
      document.cookie =
        "token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
      router.push("/auth");
    } catch (error) {
      console.error(error);
    }
  };

  const truncateNickname = (nickname: string | null) => {
    if (!nickname) return "";
    return nickname.length > 6 ? `${nickname.slice(0, 6)}...` : nickname;
  };

  return (
    <header className="flex justify-between items-center px-3 py-3 shadow z-10">
      <Link href="/">
        <Logo />
      </Link>
      <div className="flex items-center gap-2">
        <p className="text-md text-gray-700">
          {truncateNickname(user?.userInfo?.nickname ?? null)}
        </p>
        {user ? (
          <div className="relative">
            <button
              onClick={handleDropdownToggle}
              className="w-10 h-10 rounded-full focus:outline-none flex items-center justify-center"
            >
              {user.userInfo?.profileImage ? (
                <img
                  src={user.userInfo.profileImage}
                  width={400}
                  height={400}
                  alt="avatar"
                  className="w-10 h-10 rounded-full"
                />
              ) : (
                <Image
                  src={DefaultAvatar}
                  width={400}
                  height={400}
                  alt="avatar"
                  className="w-10 h-10 rounded-full"
                />
              )}
            </button>
            {isDropdownOpen && (
              <div className="absolute right-0 top-12 bg-white shadow-lg rounded-md">
                <ul className="py-2">
                  <li>
                    <Link
                      href={`/profile/${user.userInfo?.userId}`}
                      className="block px-4 py-2 text-gray-700 hover:bg-gray-100 whitespace-nowrap text-left"
                      onClick={handleLinkClick}
                    >
                      프로필
                    </Link>
                  </li>
                  <li>
                    <button
                      onClick={handleLogout}
                      className="block w-full px-4 py-2 text-gray-700 hover:bg-gray-100 text-left whitespace-nowrap"
                    >
                      로그아웃
                    </button>
                  </li>
                  <li>
                    <button
                      onClick={handleDeleteAccount}
                      className="block w-full px-4 py-2 text-gray-700 hover:bg-gray-100 text-left whitespace-nowrap"
                    >
                      회원탈퇴
                    </button>
                  </li>
                </ul>
              </div>
            )}
          </div>
        ) : (
          <Link
            href="/login"
            className="block px-4 py-2 text-gray-700 font-bold hover:bg-gray-100 whitespace-nowrap"
            onClick={handleLinkClick}
          >
            로그인
          </Link>
        )}
      </div>
    </header>
  );
};

export default Header;
