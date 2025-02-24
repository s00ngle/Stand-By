"use client";
import PortfolioEditCard from "@/components/PortfolioEditCard";
import { portfolioAtom } from "@/store/portfolio";
import Button from "@/components/Button";
import { atom, useAtom, useAtomValue } from "jotai";
import { splitAtom } from "jotai/utils";
import { Portfolio } from "@/types/Portfolio";
import { userStateAtom } from "@/atoms/userAtom";

interface IProps {
  userId: number;
  setIsEditing: (state: boolean) => void;
}

const splitPortfolioAtom = splitAtom(portfolioAtom);

const newPortfolioAtom = atom<Portfolio[]>([]);
const splitNewPortfolioAtom = splitAtom(newPortfolioAtom);

const PortfolioEdit = ({ userId, setIsEditing }: IProps) => {
  const userInfo = useAtomValue(userStateAtom);
  const [portfolioData, setPortfolioData] = useAtom(splitPortfolioAtom);
  const [newPortfolioData, setNewPortfolioData] = useAtom(
    splitNewPortfolioAtom
  );

  const handleComplete = () => {
    if (newPortfolioData.length > 0) {
      console.log("등록되지 않은 포트폴리오가 있습니다.");
    } else {
      setIsEditing(false);
    }
  };
  return userId !== userInfo?.userInfo?.userId ? null : (
    <div className="flex flex-col gap-4">
      {portfolioData?.map((portfolio, index) => (
        <PortfolioEditCard
          key={index}
          type="edit"
          userId={userId}
          portfolioAtom={portfolio}
          onSave={() => {}}
          onRemove={(data) => {
            setPortfolioData({ type: "remove", atom: portfolio });
          }}
        />
      ))}
      {newPortfolioData.map((portfolio, index) => (
        <PortfolioEditCard
          key={index}
          type="create"
          userId={userId}
          portfolioAtom={portfolio}
          onSave={(data) => {
            // send PUT request to backend
            setNewPortfolioData({ type: "remove", atom: portfolio });
            setPortfolioData({ type: "insert", value: data });
          }}
          onRemove={() =>
            setNewPortfolioData({ type: "remove", atom: portfolio })
          }
        />
      ))}
      {/* 포트폴리오 추가 버튼 */}
      {newPortfolioData.length === 0 && (
        <Button
          label={"+"}
          onClick={() => {
            setNewPortfolioData({
              type: "insert",
              value: {
                title: "",
                content: "",
                youtubeUrl: "",
              },
            });
          }}
        />
      )}
      <Button label={"수정 완료"} onClick={handleComplete} />
    </div>
  );
};

export default PortfolioEdit;
