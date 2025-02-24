"use client";

import Button from "./Button";
import Image from "next/image";
import DefaultAvatar from "@/public/default-avatar.png";
import Tag from "./Tag";
import Link from "next/link";
import {
  acceptApplication,
  acceptFinalApplication,
  memoApplication,
  rejectApplication,
} from "@/api/apply";
import { useState } from "react";
import MemoModal from "./MemoModal";
import EvaluationModal from "./EvaluationModal";
import Aperture from "./Aperture";

interface IProps {
  nickname: string;
  role: string;
  applicantStatus: string;
  userId: number;
  boardId: number;
  onStatusUpdate: (applicantId: number, newStatus: string) => void;
  memo?: string;
  isBoardClosed: boolean;
  authorId: number;
}

const RejectedButton = () => <Button label="거절됨" variant="disabled" />;
const AcceptedButton = () => <Button label="최종합격" variant="disabled" />;

const RejectButton = ({
  boardId,
  userId,
  onStatusUpdate,
}: {
  boardId: number;
  userId: number;
  onStatusUpdate: (applicantId: number, newStatus: string) => void;
}) => {
  const handleReject = async () => {
    try {
      await rejectApplication(boardId, userId);
      onStatusUpdate(userId, "거절");
      alert("지원자를 거절하였습니다.");
    } catch (error) {
      console.error("Failed to reject application:", error);
      alert("거절 처리 중 오류가 발생했습니다.");
    }
  };

  return <Button label="거절" variant="outline" onClick={handleReject} />;
};

const InterviewAcceptButton = ({
  boardId,
  userId,
  onStatusUpdate,
}: {
  boardId: number;
  userId: number;
  onStatusUpdate: (applicantId: number, newStatus: string) => void;
}) => {
  const handleAccept = async () => {
    try {
      await acceptApplication(boardId, userId);
      onStatusUpdate(userId, "면접합격");
      alert("면접수락하였습니다.");
    } catch (error) {
      console.error("Failed to accept application:", error);
      alert("면접 수락 처리 중 오류가 발생했습니다.");
    }
  };

  return <Button label="면접수락" onClick={handleAccept} />;
};

const AcceptButton = ({
  boardId,
  userId,
  onStatusUpdate,
}: {
  boardId: number;
  userId: number;
  onStatusUpdate: (applicantId: number, newStatus: string) => void;
}) => {
  const handleAccept = async () => {
    try {
      await acceptFinalApplication(boardId, userId);
      onStatusUpdate(userId, "최종합격");
      alert("최종수락하였습니다.");
    } catch (error) {
      console.error("Failed to accept application:", error);
      alert("최종 수락 처리 중 오류가 발생했습니다.");
    }
  };

  return <Button label="최종수락" onClick={handleAccept} />;
};

const InterviewMemoButton = ({
  boardId,
  userId,
  currentMemo,
  onMemoSaved,
}: {
  boardId: number;
  userId: number;
  currentMemo?: string;
  onMemoSaved?: () => void;
}) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleSaveMemo = async (memo: string) => {
    try {
      await memoApplication(boardId, userId, memo);
      alert("메모가 저장되었습니다.");
      if (onMemoSaved) {
        onMemoSaved();
      }
    } catch (error) {
      console.error("Failed to save memo:", error);
      alert("메모 저장 중 오류가 발생했습니다.");
    }
  };

  return (
    <>
      <Button
        label="면접메모"
        variant="outline"
        onClick={() => setIsModalOpen(true)}
      />
      <MemoModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSave={handleSaveMemo}
        initialMemo={currentMemo}
      />
    </>
  );
};

const ApplicantCard = ({
  nickname,
  role,
  applicantStatus,
  userId,
  boardId,
  onStatusUpdate,
  memo,
  authorId,
  isBoardClosed,
}: IProps) => {
  const applicantStatuses = ["지원완료", "면접합격", "최종합격", "거절"];
  const bgColor = {
    [applicantStatuses[0]]: "bg-white",
    [applicantStatuses[1]]: "bg-blue-50",
    [applicantStatuses[2]]: "bg-green-50",
    [applicantStatuses[3]]: "bg-gray-100",
  };

  const [isEvaluationModalOpen, setIsEvaluationModalOpen] = useState(false);

  return (
    <div
      className={`w-full py-4 gap-4 flex flex-col rounded-xl shadow-md ${bgColor[applicantStatus]}`}
    >
      <div className="relative px-8 flex items-center justify-between">
        <Link href={`/profile/${userId}`} className="flex items-center gap-4">
          <Image
            src={DefaultAvatar}
            width={400}
            height={400}
            alt="profile"
            className="rounded-full w-12 h-12"
          />
          <div className="absolute w-12 h-12">
            <Aperture isClosed={applicantStatus === applicantStatuses[3]} />
          </div>
          <span className="text-lg font-bold">{nickname}</span>
        </Link>
        <Tag label={role} />
      </div>
      <div className="flex mx-4 flex-col gap-2">
        <div className="flex gap-4 w-full">
          {applicantStatus === applicantStatuses[0] ? (
            <>
              <RejectButton
                boardId={boardId}
                userId={userId}
                onStatusUpdate={onStatusUpdate}
              />
              <InterviewAcceptButton
                boardId={boardId}
                userId={userId}
                onStatusUpdate={onStatusUpdate}
              />
            </>
          ) : applicantStatus === applicantStatuses[1] ? (
            <div className="flex flex-col gap-4 w-full">
              <div className="flex gap-4">
                <RejectButton
                  boardId={boardId}
                  userId={userId}
                  onStatusUpdate={onStatusUpdate}
                />
                <AcceptButton
                  boardId={boardId}
                  userId={userId}
                  onStatusUpdate={onStatusUpdate}
                />
              </div>
              <InterviewMemoButton
                boardId={boardId}
                userId={userId}
                currentMemo={memo}
              />
            </div>
          ) : applicantStatus === applicantStatuses[2] ? (
            <div className="flex flex-col gap-4 w-full">
              <AcceptedButton />
              <InterviewMemoButton
                boardId={boardId}
                userId={userId}
                currentMemo={memo}
              />
              {isBoardClosed && (
                <Button
                  label="평가하기"
                  onClick={() => {
                    setIsEvaluationModalOpen(true);
                  }}
                />
              )}
            </div>
          ) : (
            <RejectedButton />
          )}
        </div>
      </div>
      <EvaluationModal
        evaluatorId={authorId}
        evaluateeId={userId}
        boardId={boardId}
        role="employee"
        onConfirm={() => {
          setIsEvaluationModalOpen(false);
        }}
        onCancel={() => setIsEvaluationModalOpen(false)}
        isOpen={isEvaluationModalOpen}
      />
    </div>
  );
};

export default ApplicantCard;
