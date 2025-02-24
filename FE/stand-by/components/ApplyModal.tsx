import { useState } from "react";
import Button from "./Button";
import CategorySelector from "./CategorySelector";
import { sendApplication } from "@/api/apply";

interface IProps {
  isOpen: boolean;
  userId: number;
  boardId: number;
  onCancel: () => void;
  onConfirm: () => void;
  categories?: string[];
}

const ApplyModal = ({
  isOpen = false,
  userId,
  boardId,
  categories,
  onCancel,
  onConfirm,
}: IProps) => {
  if (!isOpen) return null;

  const [message, setMessage] = useState<string>("지원하시겠습니까?");
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);

  const handleOverlayClick = (e: React.MouseEvent<HTMLDivElement>) => {
    // onCancel 콜백이 있을 경우 Cancel을 기본 동작으로 설정
    if (e.target === e.currentTarget) {
      onCancel();
    }
  };

  const handleConfirm = async () => {
    if (!selectedCategory) {
      setMessage("카테고리를 선택해주세요");
      return;
    }

    try {
      await sendApplication(boardId, userId, selectedCategory);
      console.log("지원 성공");
      onConfirm(); // 부모 컴포넌트의 데이터 리페치 트리거
    } catch (error) {
      console.error(error);
      console.log("지원 실패");
      setMessage("지원에 실패했습니다");
    }
  };

  return (
    <div
      className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4"
      onClick={handleOverlayClick}
    >
      <div className="bg-white rounded-2xl w-full px-4 py-8 max-w-100">
        <div className="space-y-6">
          <h2 className="text-2xl font-bold text-center">{message}</h2>
          {categories && (
            <CategorySelector
              categories={categories}
              selectedCategory={selectedCategory}
              setSelectedCategory={setSelectedCategory}
            />
          )}
          <div className="flex flex-row gap-4 px-4">
            <Button label="취소" variant="warning" onClick={onCancel} />
            <Button label="지원" onClick={handleConfirm} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default ApplyModal;
