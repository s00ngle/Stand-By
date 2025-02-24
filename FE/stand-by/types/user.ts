export interface User {
  nickname: string;
  role: "user" | "admin";
  profileImage?: string;
}
