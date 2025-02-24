import Button from "@/components/Button";
import KakaoLoginButton2 from "@/components/KakaoLoginButton";

const JoinStep1 = ({ onNext }: { onNext: () => void }) => {
  return (
    <>
      <div className="flex-1 flex flex-col items-center justify-center">
        <div className="flex flex-col gap-4">
          <KakaoLoginButton2 />
        </div>
      </div>
      <div className="flex flex-col gap-2 mt-4">
        <Button label="다음" onClick={onNext} />
      </div>
    </>
  );
};

export default JoinStep1;
