import Button from "@/components/Button";
const JoinStep2 = ({ onNext }: { onNext: () => void }) => {
  return (
    <>
      <div className="flex-1 flex flex-col items-center justify-center">
        회원가입 2단계
      </div>
      <div className="flex flex-col gap-2 mt-4">
        <Button label="다음" onClick={onNext} />
      </div>
    </>
  );
};

export default JoinStep2;
