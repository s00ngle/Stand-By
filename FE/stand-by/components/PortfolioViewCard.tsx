"use client";
import { Portfolio } from "@/types/Portfolio";

interface IProps {
  portfolio: Portfolio;
}

const PortfolioViewCard = ({ portfolio }: IProps) => {
  return (
    <div className="bg-blue-50 p-6 rounded-xl">
      <div className="flex flex-col gap-4">
        {/* 제목 */}
        <span className="break-words text-2xl font-bold text-center">
          {portfolio.title}
        </span>
        {/* 이미지 미리보기 */}
        {portfolio.imageUrl && portfolio.imageUrl.length > 0 && (
          <img
            src={portfolio.imageUrl[0]}
            alt="Uploaded"
            className="w-full mt-4 rounded-lg"
          />
        )}
        {/* 유튜브 */}
        {portfolio.youtubeUrl && (
          <div className="w-full">
            <iframe
              width={"100%"}
              height={"100%"}
              src={portfolio.youtubeUrl}
              title="YouTube video player"
              allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
              referrerPolicy="strict-origin-when-cross-origin"
              allowFullScreen={true}
              className="rounded-2xl"
            ></iframe>
          </div>
        )}
        {/* 설명 */}
        <span className="break-words text-lg">{portfolio.content}</span>
      </div>
    </div>
  );
};

export default PortfolioViewCard;
