import React, { useState } from "react";
import Button from "./Button";

interface MemoModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSave: (memo: string) => void;
  initialMemo?: string;
}

const MemoModal = ({
  isOpen,
  onClose,
  onSave,
  initialMemo = "",
}: MemoModalProps) => {
  const [memo, setMemo] = useState(initialMemo);

  if (!isOpen) return null;

  const handleSave = () => {
    onSave(memo);
    onClose();
  };

  return (
    <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-6">
      <div className="bg-white rounded-lg p-6 w-full max-w-100">
        <h2 className="text-xl font-bold mb-4">면접 메모</h2>
        <textarea
          className="w-full h-32 p-2 border border-gray-300 rounded-md mb-4 resize-none"
          placeholder="면접 메모를 입력하세요..."
          value={memo}
          onChange={(e) => setMemo(e.target.value)}
        />
        <div className="flex justify-end gap-2">
          <Button label="취소" variant="outline" onClick={onClose} />
          <Button label="저장" onClick={handleSave} />
        </div>
      </div>
    </div>
  );
};

export default MemoModal;
