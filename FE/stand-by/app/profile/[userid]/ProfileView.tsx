import Image from "next/image";
import Button from "@/components/Button";
import Tag from "@/components/Tag";
import { ProfileData, BoardData } from "@/types/Profile";
import DefaultAvatar from "@/public/default-avatar2.png";
import EvaluationChart from "@/components/EvaluationChart";
import {
  EmployeeEvaluationList,
  EmployerEvaluationList,
} from "@/data/evaluationList";
import { userStateAtom } from "@/atoms/userAtom";
import { useAtomValue } from "jotai";
import Link from "next/link";
import ProfileStats from "./ProfileStats";

interface ProfileViewProps {
  profileData: ProfileData;
  evaluationList: Review[] | null;
  boardData: {
    activeBoards: BoardData[] | null;
    closedBoards: BoardData[] | null;
    appliedBoards: BoardData[] | null;
    careerBoards: BoardData[] | null;
  };
  onEdit: () => void;
}

interface BoardSectionProps {
  title: string;
  boards: BoardData[];
  variant?: "active" | "closed";
}

const BoardSection = ({
  title,
  boards,
  variant = "active",
}: BoardSectionProps) => {
  const bgColor = variant === "active" ? "bg-white" : "bg-gray-100";
  const shadowClass = variant === "active" ? "shadow" : "";

  if (!boards || boards.length === 0) return null;

  return (
    <div className="mt-8">
      <h2 className="text-xl font-bold mb-4">{title}</h2>
      {boards.map((board, index) => (
        <Link href={`/post/${board.boardId}`}>
          <div
            key={index}
            className={`${bgColor} p-8 rounded-xl mb-2 ${shadowClass}`}
          >
            <div className="flex flex-col gap-2">
              <span className="text-xl">{board.title}</span>
              <span className="text-sm">
                {board.startDate} - {board.endDate}
              </span>
            </div>
          </div>
        </Link>
      ))}
    </div>
  );
};

const InfoItem = ({
  label,
  value,
}: {
  label: string;
  value: string | number;
}) => (
  <div className="flex items-center gap-2">
    <span className="text-gray-600 font-medium min-w-24">{label}</span>
    <span>{value}</span>
  </div>
);

const ProfileView: React.FC<ProfileViewProps> = ({
  profileData,
  evaluationList,
  boardData,
  onEdit,
}) => {
  const userId = useAtomValue(userStateAtom);
  const isOP = userId?.userInfo?.userId === profileData.userId;

  const scoreLabel =
    profileData.role === "employer"
      ? EmployerEvaluationList
      : EmployeeEvaluationList;
  console.log("profileData in ProfileView :", profileData);
  console.log("boardData in ProfileView :", boardData);

  const renderEmployeeInfo = () => {
    if (profileData.role !== "employee") return null;

    return <ProfileStats profileData={profileData} />;

    // profileData에 gender, birthDate, height, weight, workCounts, workYears, mostGenre, mostRoleList가 없으면 null 반환
    // if (
    //   !(
    //     profileData.gender ||
    //     profileData.birthDate ||
    //     profileData.height ||
    //     profileData.weight ||
    //     profileData.workCounts ||
    //     profileData.workYears ||
    //     profileData.mostGenre ||
    //     profileData.mostRoleList ||
    //     profileData.recruitingCount
    //   )
    // )
    //   return null;

    // return (
    //   <div className="mt-4 p-4 bg-white rounded-lg">
    //     <div className="flex flex-col gap-4">
    //       {profileData.gender && (
    //         <InfoItem label="성별" value={profileData.gender} />
    //       )}

    //       {profileData.birthDate && (
    //         <InfoItem label="생년월일" value={profileData.birthDate} />
    //       )}

    //       {profileData.height && (
    //         <InfoItem label="키" value={`${profileData.height}cm`} />
    //       )}

    //       {profileData.weight && (
    //         <InfoItem label="몸무게" value={`${profileData.weight}kg`} />
    //       )}

    //       {profileData.workCounts && (
    //         <InfoItem label="참여작품 수" value={profileData.workCounts} />
    //       )}

    //       {profileData.workYears && (
    //         <InfoItem label="경력" value={`${profileData.workYears}`} />
    //       )}

    //       {profileData.mostGenre && (
    //         <div className="flex flex-row gap-2">
    //           <span className="text-nowrap text-gray-600 font-medium min-w-24">
    //             선호 장르
    //           </span>
    //           <Tag label={profileData.mostGenre} bgColor="bg-lime-500" />
    //         </div>
    //       )}
    //       {profileData.mostRoleList.length > 0 && (
    //         <div className="flex flex-row flex-wrap gap-2">
    //           <span className="text-nowrap text-gray-600 font-medium min-w-24">
    //             선호 역할
    //           </span>
    //           <div className="flex gap-1">
    //             {profileData.mostRoleList.slice(0, 3).map((v) => (
    //               <Tag key={v} label={v} bgColor="bg-blue-500" />
    //             ))}
    //           </div>
    //         </div>
    //       )}
    //     </div>
    //   </div>
    // );
  };

  const renderBoards = () => {
    if (!boardData) return null;

    if (profileData.role === "employer") {
      if (!boardData.activeBoards || !boardData.closedBoards) return null;

      return (
        <>
          <BoardSection
            title="진행 중인 공고"
            boards={boardData.activeBoards}
            variant="active"
          />
          <BoardSection
            title="마감된 공고"
            boards={boardData.closedBoards}
            variant="closed"
          />
        </>
      );
    }

    if (profileData.role === "employee") {
      if (!boardData.appliedBoards || !boardData.careerBoards) return null;

      return (
        <>
          {isOP && (
            <BoardSection
              title="지원 중인 공고"
              boards={boardData.appliedBoards}
              variant="active"
            />
          )}
          <BoardSection
            title="경력"
            boards={boardData.careerBoards}
            variant="closed"
          />
        </>
      );
    }
  };

  return (
    <div className="space-y-6">
      <div className="bg-blue-50 p-6 rounded-xl">
        <div className="flex flex-col gap-4">
          <div className="flex gap-4 items-center">
            {profileData.profileImage ? (
              <img
                src={profileData.profileImage}
                width={1000}
                height={1000}
                className="rounded-full w-20 h-20"
                alt="Profile Image"
              />
            ) : (
              <Image
                src={DefaultAvatar}
                width={1000}
                height={1000}
                className="rounded-full w-20 h-20"
                alt="Profile Image"
              />
            )}

            <div className="flex flex-col">
              <p className="text-xl font-bold flex gap-2 items-center">
                {profileData.nickname}
                <Tag
                  label={profileData.role === "employee" ? "구직자" : "구인자"}
                />
              </p>
              <p className="text-gray-600">
                {profileData.introduction || "한 줄 소개 없음"}
              </p>
            </div>
          </div>
          {profileData.scores && (
            <EvaluationChart
              scores={profileData.scores}
              labels={scoreLabel}
              maxScore={5}
            />
          )}
          {renderEmployeeInfo()}
          <Link
            href={`/portfolio/${profileData.userId}`}
            className="flex items-center"
          >
            <Button label="포트폴리오 보기" />
          </Link>
          {isOP && <Button label="프로필 수정" onClick={onEdit} />}
        </div>
      </div>

      {evaluationList && evaluationList.length > 0 && (
        <div className="flex flex-col gap-2">
          <span className="text-xl font-bold mb-4">최근 평가</span>
          {evaluationList.map((v) => (
            <div className="w-full bg-blue-50 p-6 rounded-xl">
              <span className="text-gray-600 font-medium break-words">
                {v.comment}
              </span>
            </div>
          ))}
        </div>
      )}

      {renderBoards()}
    </div>
  );
};

export default ProfileView;
