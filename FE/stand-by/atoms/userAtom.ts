import { atom } from "jotai";
import { atomWithStorage } from "jotai/utils";

export interface UserInfo {
  nickname: string | null;
  profileImage?: string | null;
  // 'recruiter'는 구인자, 'jobSeeker'는 구직자를 의미합니다.
  role: "employer" | "employee" | null;
  // API 호출 시 필요한 사용자 ID
  userId: number;
}

export interface UserState {
  userInfo: UserInfo | null;
  // 카카오톡 로그인 회원 토큰
  kakaoToken: string | null;
}

// 초기 상태 (로그인 전)
// const defaultUserState: UserState = {
//   userInfo: null,
//   kakaoToken: null,
// };

// export const userStateAtom = atom<UserState | null>(null);
// atom with localstorage
export const userStateAtom = atomWithStorage<UserState | null>(
  "userState",
  null
);
