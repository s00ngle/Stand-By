"use client";

import { useEffect, useState } from "react";
import ProfileEdit from "./ProfileEdit";
import ProfileView from "./ProfileView";
import { useParams } from "next/navigation";
import Loading from "@/components/Loading";
import { getProfile } from "@/api/profile";

import { ProfileData, BoardData } from "@/types/Profile";
import { getEvaluation } from "@/api/evaluation";

const ProfilePage: React.FC = () => {
  const { userid } = useParams();

  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [profileData, setProfileData] = useState<ProfileData | null>(null);
  // const [boardData, setBoardData] = useState<BoardData | null>(null);
  const [activeBoards, setActiveBoards] = useState<BoardData[] | null>(null);
  const [closedBoards, setClosedBoards] = useState<BoardData[] | null>(null);
  const [appliedBoards, setAppliedBoards] = useState<BoardData[] | null>(null);
  const [careerBoards, setCareerBoards] = useState<BoardData[] | null>(null);

  const [evaluationList, setEvaluationList] = useState<Review[] | null>(null);

  const [loading, setLoading] = useState<boolean>(false);

  const fetchProfileData = async () => {
    if (typeof userid === "string") {
      setLoading(true);
      try {
        const response = await getProfile(userid);
        setProfileData(response.profile);
        // setBoardData(response.boards);
        setActiveBoards(response.activeBoards);
        setClosedBoards(response.closedBoards);
        setAppliedBoards(response.appliedBoards);
        setCareerBoards(response.careerBoards);
      } catch (error) {
        console.error("Error fetching profile:", error);
      } finally {
        setLoading(false);
      }
    }
  };

  const fetchEvaluationList = async () => {
    if (typeof userid === "string") {
      setLoading(true);
      try {
        const response = await getEvaluation(userid);
        setEvaluationList(response.evaluationList);
      } catch (error) {
        console.error("Error fetching evaluation list:", error);
      } finally {
        setLoading(false);
      }
    }
  };

  useEffect(() => {
    fetchProfileData();
    fetchEvaluationList();
  }, [userid]);

  // console.log("profileData:", profileData);
  // console.log("boardData:", boardData);
  // console.log("activeBoards:", activeBoards);
  // console.log("closedBoards:", closedBoards);
  // console.log("appliedBoards:", appliedBoards);
  // console.log("careerBoards:", careerBoards);
  console.log("evaluationList:", evaluationList);

  if (loading) return <Loading />;
  if (!profileData) return <div>프로필을 찾을 수 없습니다.</div>;

  return (
    <div className="flex flex-1 flex-col p-8">
      {isEditing ? (
        <ProfileEdit
          profileData={profileData}
          onSave={async () => {
            setIsEditing(false);
            await fetchProfileData(); // 최신 프로필 데이터 가져오기
          }}
        />
      ) : (
        <ProfileView
          profileData={profileData}
          evaluationList={evaluationList}
          boardData={{
            activeBoards: activeBoards,
            closedBoards: closedBoards,
            appliedBoards: appliedBoards,
            careerBoards: careerBoards,
          }}
          onEdit={() => setIsEditing(true)}
        />
      )}
    </div>
  );
};

export default ProfilePage;
