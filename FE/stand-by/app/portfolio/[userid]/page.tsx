"use client";
import Loading from "@/components/Loading"; // Loading 컴포넌트 임포트
import PortfolioEdit from "./PortfolioEdit";
import PortfolioView from "./PortfolioView";
import { portfolioAtom } from "@/store/portfolio";
import { getPortfolios } from "@/api/portfolio";
import { useEffect, useState } from "react";
import { useSetAtom } from "jotai";
import { useParams } from "next/navigation";
import { getProfile } from "@/api/profile";
import { ProfileData } from "@/types/Profile";

const PortfolioPage: React.FC = () => {
  const userId = useParams().userid;
  const [isEditing, setIsEditing] = useState<boolean>(false);
  const [loading, setLoading] = useState<boolean>(false);
  const [profileLoading, setProfileLoading] = useState<boolean>(false);
  const [authorProfile, setAuthorProfile] = useState<ProfileData | null>(null);
  const setPortfolioData = useSetAtom(portfolioAtom);

  useEffect(() => {
    const fetchPortfolioData = async () => {
      if (typeof userId === "string") {
        // userid가 string 타입일 때만 실행
        setLoading(true);
        try {
          const data = await getPortfolios(userId); // string으로 처리
          console.log(data);
          setPortfolioData(data.cards === null ? [] : data.cards);
        } catch (error) {
          console.error("Error fetching profile:", error);
          setPortfolioData([]);
        } finally {
          setLoading(false);
        }
      } else {
        console.error("Invalid userid:", userId);
      }
    };

    fetchPortfolioData();
  }, [userId]); // userid가 변경될 때마다 데이터 재호출

  useEffect(() => {
    const fetchAuthorProfile = async () => {
      if (typeof userId === "string") {
        setProfileLoading(true);
        try {
          const profileResponse = await getProfile(userId.toString());
          console.log("Author profile received:", profileResponse);
          setAuthorProfile(profileResponse.profile);
        } catch (error) {
          console.error("Failed to fetch author profile:", error);
        } finally {
          setProfileLoading(false);
        }
      }
    };

    fetchAuthorProfile();
  }, [userId]);

  return loading && profileLoading ? (
    <Loading />
  ) : authorProfile ? (
    <div className="flex flex-1 flex-col p-8">
      {isEditing ? (
        <PortfolioEdit
          userId={authorProfile.userId}
          setIsEditing={setIsEditing}
        />
      ) : (
        typeof userId === "string" && (
          <PortfolioView
            userId={authorProfile.userId}
            nickname={authorProfile.nickname}
            setIsEditing={(state: boolean) => setIsEditing(state)}
          />
        )
      )}
    </div>
  ) : (
    <div>포트폴리오를 찾을 수 없습니다.</div>
  );
};

export default PortfolioPage;
