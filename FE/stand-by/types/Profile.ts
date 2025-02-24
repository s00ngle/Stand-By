// types.ts
export interface ProfileScores {
  score1_avg: number;
  score2_avg: number;
  score3_avg: number;
  score4_avg: number;
  score5_avg: number;
}

export interface ProfileData {
  role: "employee" | "employer";
  userId: number;
  nickname: string;
  introduction: string;
  birthDate?: string;
  mostGenre: string;
  mostRoleList: string[];
  recruitingCount: number;
  gender?: string;
  height?: number;
  weight?: number;
  workCounts?: string;
  workYears?: string;
  profileImage?: string;
  scores?: number[];
}

export interface BoardData {
  boardId: number;
  authorId: number;
  authorName: string;
  title: string;
  startDate: string;
  endDate: string;
  status: boolean;
}
