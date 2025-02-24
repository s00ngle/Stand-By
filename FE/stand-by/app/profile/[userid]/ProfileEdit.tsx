"use client";

import { useState, useRef } from "react";
import Button from "@/components/Button";
import InputField from "@/components/InputField";
import Image from "next/image";
import { ProfileData } from "@/types/Profile";
import DefaultAvatar from "@/public/default-avatar2.png";
import { updateProfile } from "@/api/profile";
import { useParams } from "next/navigation";
import { userStateAtom } from "@/atoms/userAtom";
import { useAtom } from "jotai";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { image } from "framer-motion/client";

interface ProfileEditProps {
  profileData: ProfileData;
  onSave: () => void;
}

const ProfileEdit: React.FC<ProfileEditProps> = ({ profileData, onSave }) => {
  const { userid } = useParams();
  const [userAtom, setUserAtom] = useAtom(userStateAtom);

  console.log(userAtom?.userInfo?.userId);

  const [profile, setProfile] = useState<ProfileData>(profileData);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [previewImage, setPreviewImage] = useState<string | null>(
    profile.profileImage || null
  );
  const fileInputRef = useRef<HTMLInputElement>(null);

  const [selectedGender, setSelectedGender] = useState(
    profileData.gender || ""
  );
  const [birthDate, setBirthDate] = useState<Date | null>(
    profileData.birthDate ? new Date(profileData.birthDate) : null
  );

  const handleChange = (key: keyof ProfileData, value: any) => {
    if (isLoading) return;

    setProfile((prev) => ({
      ...prev,
      [key]: value,
    }));
  };

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (!file) return;

    // 이미지 파일 검증
    if (!file.type.startsWith("image/")) {
      setError("이미지 파일만 업로드 가능합니다.");
      return;
    }

    // 파일 크기 제한 (예: 5MB)
    if (file.size > 5 * 1024 * 1024) {
      setError("파일 크기는 5MB 이하여야 합니다.");
      return;
    }

    setImageFile(file);
    setPreviewImage(URL.createObjectURL(file));
  };

  const handleSubmit = async () => {
    if (!userid || typeof userid !== "string") {
      setError("유저 ID를 찾을 수 없습니다.");
      return;
    }

    setIsLoading(true);
    setError(null);

    try {
      // userAtom?.userInfo?.userId 갑과 userid 값이 같은지 확인
      if (userAtom?.userInfo?.userId !== parseInt(userid)) {
        setError("유저 ID가 일치하지 않습니다.");
        return;
      }

      const profileUpdateData = {
        userId: parseInt(userid),
        nickname: profile.nickname,
        introduction: profile.introduction,
        height: profile.height,
        weight: profile.weight,
        workCounts: profile.workCounts,
        workYears: profile.workYears,
        gender: selectedGender,
        birthDate: birthDate?.toISOString().split("T")[0],
      };

      await updateProfile(userid, profileUpdateData, imageFile || undefined);

      setProfile((prev) => ({
        ...prev,
        profileImage: imageFile ? URL.createObjectURL(imageFile) : undefined,
      }));

      // 유저 Atom 업데이트
      setUserAtom((prev) => {
        if (!prev || !prev.userInfo) return prev;
        return {
          ...prev,
          userInfo: {
            ...prev.userInfo,
            nickname: profile.nickname,
            profileImage: imageFile
              ? URL.createObjectURL(imageFile)
              : undefined,
          },
        };
      });

      onSave();
    } catch (err) {
      setError(
        err instanceof Error
          ? err.message
          : "프로필 수정 중 오류가 발생했습니다."
      );
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="bg-blue-50 p-6 rounded-xl">
      {error && (
        <div className="mb-4 p-4 bg-red-100 text-red-700 rounded-lg">
          {error}
        </div>
      )}

      <div className="flex flex-col items-center gap-4">
        {previewImage ? (
          <img
            src={previewImage}
            width={1000}
            height={1000}
            className="rounded-full w-40 h-40 object-cover"
            alt="Profile Image"
          />
        ) : (
          <Image
            src={DefaultAvatar}
            width={1000}
            height={1000}
            className="rounded-full w-40 h-40 object-cover"
            alt="Profile Image"
          />
        )}

        <div>
          <input
            type="file"
            ref={fileInputRef}
            className="hidden"
            accept="image/*"
            onChange={handleImageChange}
          />
          <Button
            label="사진 업로드"
            variant="outline"
            onClick={() => fileInputRef.current?.click()}
          />
        </div>
      </div>

      <div
        className={`flex flex-col gap-4 mt-6 ${
          isLoading ? "pointer-events-none opacity-60" : ""
        }`}
      >
        <InputField
          title="닉네임"
          value={profile.nickname}
          onChange={(e) => handleChange("nickname", e.target.value)}
          showButton={false}
          placeholder="닉네임을 입력하세요"
        />

        <InputField
          title="한 줄 소개"
          value={profile.introduction}
          onChange={(e) => handleChange("introduction", e.target.value)}
          showButton={false}
          placeholder="한 줄 소개를 입력하세요"
        />

        {profile.role === "employee" && (
          <>
            <div>
              {/* 성별 선택 */}
              <span className="text-sm font-bold">성별</span>
              <div className="flex gap-4 mt-2">
                <Button
                  label="남자"
                  variant={selectedGender === "남자" ? "default" : "outline"}
                  onClick={() =>
                    setSelectedGender(selectedGender === "남자" ? "" : "남자")
                  }
                />
                <Button
                  label="여자"
                  variant={selectedGender === "여자" ? "default" : "outline"}
                  onClick={() =>
                    setSelectedGender(selectedGender === "여자" ? "" : "여자")
                  }
                />
              </div>
            </div>

            <div>
              <h2 className="text-lg font-bold mb-2">생년월일</h2>
              <div className="flex">
                <DatePicker
                  selected={birthDate}
                  onChange={(date) => setBirthDate(date)}
                  dateFormat="yyyy-MM-dd"
                  placeholderText="생년월일"
                  className="w-full p-2 border border-gray-300 rounded-md"
                  showYearDropdown
                  scrollableYearDropdown
                  yearDropdownItemNumber={new Date().getFullYear() - 1900} // 현재 연도 - 1900 = 약 125년
                  minDate={new Date("1900-01-01")} // 1900년 1월 1일이 최소 선택 가능 날짜
                  maxDate={new Date()} // 현재 날짜가 최대 선택 가능 날짜
                />
              </div>
            </div>

            <InputField
              title="키"
              value={profile.height?.toString() || ""}
              onChange={(e) =>
                handleChange("height", parseInt(e.target.value) || null)
              }
              showButton={false}
              placeholder="키를 입력하세요"
            />

            <InputField
              title="몸무게"
              value={profile.weight?.toString() || ""}
              onChange={(e) =>
                handleChange("weight", parseInt(e.target.value) || null)
              }
              showButton={false}
              placeholder="몸무게를 입력하세요"
            />

            <InputField
              title="작품 수"
              value={profile.workCounts || ""}
              onChange={(e) => handleChange("workCounts", e.target.value)}
              showButton={false}
              placeholder="참여 작품 수를 입력하세요."
            />

            <InputField
              title="경력"
              value={profile.workYears || ""}
              onChange={(e) => handleChange("workYears", e.target.value)}
              showButton={false}
              placeholder="경력을 입력하세요(ex. 1~2년)"
            />
          </>
        )}

        <div className="mt-4 flex flex-col gap-4">
          <Button label="수정 완료" variant="outline" onClick={handleSubmit} />
          <Button label="취소" variant="warning" onClick={onSave} />
        </div>
      </div>
    </div>
  );
};

export default ProfileEdit;
