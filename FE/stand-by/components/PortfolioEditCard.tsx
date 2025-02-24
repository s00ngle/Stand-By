"use client";
import {
  createPortfolio,
  deletePortfolio,
  modifyPortfolio,
} from "@/api/portfolio";
import Button from "@/components/Button";
import InputField from "@/components/InputField";
import { Portfolio } from "@/types/Portfolio";
import { PrimitiveAtom, useAtom } from "jotai";
import { useState } from "react";

interface IProps {
  portfolioAtom: PrimitiveAtom<Portfolio>;
  userId: number;
  type: "create" | "edit";
  onSave: (data: Portfolio) => void;
  onRemove: (data: Portfolio) => void;
}

const PortfolioEditCard = ({
  portfolioAtom,
  userId,
  type,
  onSave,
  onRemove,
}: IProps) => {
  const [portfolio, setPortfolio] = useAtom(portfolioAtom);
  const [isEdited, setIsEdited] = useState<boolean>(false);
  const [imageFile, setImageFile] = useState<File | null>(null);
  const [imagePreview, setImagePreview] = useState<string | null>(null);

  const handleImageUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      setImageFile(file);
      const reader = new FileReader();
      reader.onload = () => setImagePreview(reader.result as string);
      reader.readAsDataURL(file);
    }
  };
  const parseYoutube = () => {
    let url = portfolio.youtubeUrl;

    if (
      portfolio.youtubeUrl &&
      portfolio.youtubeUrl.split("embed").length !== 2
    ) {
      if (url.split("watch?v=").length === 2) {
        url = url.replace("watch?v=", "embed/");
      } else if (url.split("shorts").length === 2) {
        url = url.replace("shorts", "embed");
      }
      setPortfolio({ ...portfolio, youtubeUrl: url });
    }
    return url;
  };

  const handleCreate = async () => {
    let url = parseYoutube();
    const formData = new FormData();

    formData.append("userId", userId.toString());

    // JSON을 Blob으로 변환하여 추가
    const cardBlob = new Blob(
      [JSON.stringify({ ...portfolio, youtubeUrl: url } as Portfolio)],
      {
        type: "application/json",
      }
    );
    formData.append("card", cardBlob);

    // 이미지 추가
    if (imageFile) {
      formData.append("files", imageFile);
    }

    console.log(`Send POST requeset to backend with: `, formData);

    try {
      const response = await createPortfolio(formData, userId);
      console.log(response);
      alert("포트폴리오가 성공적으로 등록되었습니다.");
      setPortfolio(response.cards[0]);
      onSave(response.cards[0]);
      completeEdit();
    } catch (error) {
      console.error(error);
      alert("포트폴리오 등록에 실패했습니다.");
    }
  };

  const handleModify = async () => {
    let url = parseYoutube();
    // FormData 생성
    const formData = new FormData();

    // JSON을 Blob으로 변환하여 추가
    const cardBlob = new Blob(
      [JSON.stringify({ ...portfolio, youtubeUrl: url } as Portfolio)],
      {
        type: "application/json",
      }
    );
    formData.append("card", cardBlob);

    // 이미지 추가
    if (imageFile) {
      formData.append("files", imageFile);
    }

    console.log(`Send PUT requeset to backend with:`, portfolio);

    try {
      if (!portfolio.cardId) {
        throw new Error("포트폴리오 Id를 찾을 수 없습니다..");
      }

      const response = await modifyPortfolio(formData, portfolio.cardId);
      alert("포트폴리오가 성공적으로 수정되었습니다.");
      setPortfolio(response.cards[0]);
      onSave(response.cards[0]);
      completeEdit();
    } catch (error) {
      console.error(error);
      alert("포트폴리오 수정에 실패했습니다.");
    }
  };

  const handleDelete = async () => {
    console.log(`Send DELETE requeset to backend with: `, portfolio);

    try {
      if (!portfolio.cardId) {
        throw new Error("포트폴리오 Id를 찾을 수 없습니다..");
      }
      await deletePortfolio(portfolio.cardId);
      alert("포트폴리오가 성공적으로 삭제되었습니다.");
      onRemove(portfolio);
    } catch (error) {
      console.error(error);
      alert("포트폴리오 삭제에 실패했습니다.");
    }
  };

  const completeEdit = () => {
    if (isEdited) {
      console.log("수정이 완료되었습니다.");
      setIsEdited(false);
      setImageFile(null);
      setImagePreview(null);
    } else {
      console.log("수정된 사항이 없습니다.");
    }
  };

  return (
    <div
      className={`${
        type === "edit" ? "bg-blue-50" : "bg-lime-50"
      } p-6 rounded-xl`}
    >
      <div className="flex flex-col gap-4">
        {/* 이미지 미리보기 */}

        <div className="mt-4 flex justify-center">
          {imagePreview ? (
            <img
              src={imagePreview}
              alt="Uploaded"
              className="max-w-full h-auto rounded-lg"
            />
          ) : (
            portfolio.imageUrl &&
            portfolio.imageUrl.length > 0 && (
              <img
                src={portfolio.imageUrl[0]}
                alt="Uploaded"
                className="max-w-full h-auto rounded-lg"
              />
            )
          )}
        </div>

        {/* 이미지 업로드 버튼 */}
        <div className="mt-4">
          <label className="w-full cursor-pointer flex justify-center items-center p-3 rounded-xl border-2 transition-all duration-300 bg-blue-500 text-white">
            이미지 등록
            <input
              type="file"
              accept="image/*"
              className="hidden"
              onChange={(e) => {
                handleImageUpload(e);
                setIsEdited(true);
              }}
            />
          </label>
        </div>

        {/* 제목 */}
        <InputField
          title={"제목"}
          value={portfolio.title}
          required={true}
          onChange={(e) => {
            setPortfolio((data) => ({ ...data, title: e.target.value }));
            setIsEdited(true);
          }}
          showButton={false}
          placeholder="이력 제목"
        />

        {/* 설명명 */}
        <InputField
          title={"설명"}
          value={portfolio.content}
          onChange={(e) => {
            setPortfolio((data) => ({ ...data, content: e.target.value }));
            setIsEdited(true);
          }}
          showButton={false}
          placeholder="요약 입력"
        />

        {/* 유튜브 */}
        <InputField
          title={"유튜브"}
          value={portfolio.youtubeUrl}
          onChange={(e) => {
            setPortfolio((data) => ({ ...data, youtubeUrl: e.target.value }));
            setIsEdited(true);
          }}
          showButton={false}
          placeholder="유튜브 링크"
        />
        {type === "create" ? (
          <Button
            label="등록"
            variant={isEdited ? "default" : "disabled"}
            onClick={handleCreate}
          />
        ) : (
          <Button
            label="수정"
            variant={isEdited ? "default" : "disabled"}
            onClick={handleModify}
          />
        )}
        <Button label="삭제" variant="warning" onClick={handleDelete} />
      </div>
    </div>
  );
};

export default PortfolioEditCard;
