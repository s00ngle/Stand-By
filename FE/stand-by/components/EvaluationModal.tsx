"use client";
import { sendEvaluation } from "@/api/evaluation";
import EvaluationChart from "@/components/EvaluationChart";
import Button from "@/components/Button";
import InputField from "@/components/InputField";
import {
  EmployeeEvaluationList,
  EmployerEvaluationList,
} from "@/data/evaluationList";
import { useState } from "react";

interface IProps {
  evaluatorId: number;
  evaluateeId: number;
  boardId: number;
  role: "employer" | "employee";
  onConfirm: () => void;
  onCancel: () => void;
  isOpen: boolean;
}

const EvaluationModal = ({
  evaluatorId,
  evaluateeId,
  boardId,
  role,
  onConfirm,
  onCancel,
  isOpen,
}: IProps) => {
  if (!isOpen) return null;

  const [scores, setScores] = useState<number[]>([5, 5, 5, 5, 5]);
  const [comment, setComment] = useState("");
  const scoreLabel =
    role === "employer" ? EmployerEvaluationList : EmployeeEvaluationList;

  const handleOverlayClick = (e: React.MouseEvent<HTMLDivElement>) => {
    if (e.target === e.currentTarget) {
      onCancel();
    }
  };

  const handleSubmit = async () => {
    try {
      await sendEvaluation({
        evaluatorId: evaluatorId,
        evaluateeId: evaluateeId,
        boardId: boardId,
        comment: comment,
        score1: scores[0],
        score2: scores[1],
        score3: scores[2],
        score4: scores[3],
        score5: scores[4],
      } as Review);
      alert("평가 완료");
      onConfirm();
    } catch (error) {
      console.error(error);
      alert("평가 실패");
    }
  };

  return (
    <div
      className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 px-4 py-8 overflow-y-scroll"
      onClick={handleOverlayClick}
    >
      <div className="bg-white rounded-2xl w-full px-8 py-8 flex flex-1 flex-col gap-4 mt-8 max-w-100">
        <h1 className="text-xl font-bold text-center">구직자 평가하기</h1>

        <EvaluationChart
          scores={scores}
          labels={scoreLabel}
          maxScore={5}
          margin={0.3}
        />

        <div className="flex flex-col gap-2">
          {scoreLabel.map((value, i) => (
            <div key={i} className="flex items-center justify-between">
              <label className="text-md font-medium">{value}</label>
              <input
                type="range"
                min="0"
                max="5"
                step={0.1}
                value={scores[i]}
                onChange={(e) =>
                  setScores(
                    scores.map((v, index) =>
                      index === i ? Number(e.target.value) : v
                    )
                  )
                }
                className="w-2/3"
              />
              <span>{scores[i]}</span>
            </div>
          ))}
        </div>

        <InputField
          title="추가 의견"
          placeholder="추가 의견을 입력하세요."
          value={comment}
          onChange={(e) => setComment(e.target.value)}
        />

        <div className="flex flex-row gap-4 px-4">
          <Button label="취소" variant="warning" onClick={onCancel} />
          <Button label="평가" onClick={handleSubmit} />
        </div>
      </div>
    </div>
  );
};

export default EvaluationModal;
