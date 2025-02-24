import { ProfileData } from "@/types/Profile";

export const getProfile = async (userid: string) => {
  const response = await fetch(`/api/profiles/${userid}`);
  if (!response.ok) {
    throw new Error("프로필을 불러오는데 실패하였습니다.");
  }
  return response.json();
};

export const updateProfile = async (
  userid: string,
  profile: object,
  imageFile?: File
) => {
  const formData = new FormData();

  // JSON을 Blob으로 변환하여 추가
  const profileBlob = new Blob([JSON.stringify(profile)], {
    type: "application/json",
  });
  formData.append("profile", profileBlob);

  // 이미지 파일이 있다면 추가
  if (imageFile) {
    formData.append("image", imageFile);
  }

  const response = await fetch(`/api/profiles/${userid}`, {
    method: "PUT",
    body: formData,
  });

  if (!response.ok) {
    throw new Error("프로필을 수정하는데 실패하였습니다.");
  }

  return response.json();
};
