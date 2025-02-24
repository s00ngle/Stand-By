import { userStateAtom } from "@/atoms/userAtom";
import Button from "@/components/Button";
import PortfolioViewCard from "@/components/PortfolioViewCard";
import { portfolioAtom } from "@/store/portfolio";
import { useAtomValue } from "jotai";

interface IProps {
  userId: number;
  nickname: string;
  setIsEditing: (state: boolean) => void;
}

const PortfolioView = ({ userId, nickname, setIsEditing }: IProps) => {
  const userInfo = useAtomValue(userStateAtom);

  const portfolioData = useAtomValue(portfolioAtom);
  return (
    <div className="flex flex-col gap-4">
      <span className="text-2xl text-center font-bold">
        {nickname}님의 포트폴리오
      </span>
      {portfolioData.map((portfolio, i) => (
        <PortfolioViewCard key={i} portfolio={portfolio} />
      ))}

      {userId === userInfo?.userInfo?.userId && (
        <div className="mt-4">
          <Button label="수정" onClick={() => setIsEditing(true)} />
        </div>
      )}
    </div>
  );
};
export default PortfolioView;
