import Button from "./Button";

interface IProps {
  isOpen: boolean;
  onCancel?: () => void;
  onConfirm: () => void;
  message: string;
  cancelLabel?: string;
  confirmLabel?: string;
}

const ConfirmationModal = ({
  isOpen = false,
  onCancel,
  onConfirm,
  message,
  cancelLabel = "취소",
  confirmLabel = "확인",
}: IProps) => {
  if (!isOpen) return null;

  const handleOverlayClick = (e: React.MouseEvent<HTMLDivElement>) => {
    // onCancel 콜백이 있을 경우 Cancel을 기본 동작으로 설정
    if (e.target === e.currentTarget) {
      onCancel ? onCancel() : onConfirm();
    }
  };

  return (
    <div
      className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4"
      onClick={handleOverlayClick}
    >
      <div className="bg-white rounded-2xl w-full px-4 py-8">
        <div className="space-y-6">
          <h2 className="text-2xl font-bold text-center">{message}</h2>
          <div className="flex flex-row gap-4 px-4">
            {/* onCancel 콜백이 있을 때만 취소 버튼 생성 */}
            {onCancel && (
              <Button
                label={cancelLabel}
                variant="warning"
                onClick={onCancel}
              />
            )}
            <Button label={confirmLabel} onClick={onConfirm} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default ConfirmationModal;
