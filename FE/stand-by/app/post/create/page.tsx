"use client";

import CategorySelector from "@/components/CategorySelector";
import MultiCategorySelector from "@/components/MultiCategorySelector";
import { useEffect, useState } from "react";
import InputField from "@/components/InputField";
import ToggleButton from "@/components/ToggleButton";
import InputBox from "@/components/Inputbox";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { createPost } from "@/api/post";
import { Post } from "@/types/Post";
import { genreCategoryList, roleCategoryList } from "@/data/postCategories";
import { useAtom } from "jotai";
import { userStateAtom } from "@/atoms/userAtom";
import { useRouter } from "next/navigation";
import { start } from "repl";

const PostCreatePage: React.FC = () => {
  const [selectedGenre, setSelectedGenre] = useState<string | null>(null);
  const [selectedRoleCategories, setSelectedRoleCategories] = useState<
    Set<string>
  >(() => new Set());
  const [title, setTitle] = useState("");
  const [location, setLocation] = useState("");
  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  const [selectedPayType, setSelectedPayType] = useState<"시급" | "일당">(
    "시급"
  );
  const [wage, setWage] = useState(0);
  const [hours, setHours] = useState("");
  const [totalWage, setTotalWage] = useState(0);
  const [content, setContent] = useState("");
  const [availablePositions, setAvailablePositions] = useState(0);
  const [image, setImage] = useState<File | null>(null);

  const [userAtom, setUserAtom] = useAtom(userStateAtom);

  const router = useRouter();

  useEffect(() => {
    if (selectedPayType === "시급" && wage && hours) {
      setTotalWage(Number(wage) * Number(hours));
    } else if (selectedPayType === "일당" && wage) {
      if (startDate && endDate) {
        // 날짜 차이를 일수로 계산
        const diffTime = endDate.getTime() - startDate.getTime();
        const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1; // 시작일과 종료일 포함
        setTotalWage(Number(wage) * diffDays);
      } else {
        setTotalWage(0);
      }
    } else {
      setTotalWage(0);
    }
  }, [wage, hours, selectedPayType, startDate, endDate]);

  const handleSubmit = async () => {
    // 장르와 역할 선택 여부 검사 추가
    if (!selectedGenre) {
      alert("장르를 선택해주세요.");
      return;
    }

    if (selectedRoleCategories.size === 0) {
      alert("최소 하나 이상의 역할을 선택해주세요.");
      return;
    }
    if (
      !title ||
      !location ||
      !startDate ||
      !endDate ||
      !content ||
      !selectedGenre
    ) {
      alert("필수 입력 항목을 모두 채워주세요.");
      return;
    }

    if (startDate > endDate) {
      alert("시작 날짜가 종료 날짜보다 늦을 수 없습니다.");
      return;
    }

    const boardData = {
      title,
      content,
      startDate: startDate.toISOString().split("T")[0],
      endDate: endDate.toISOString().split("T")[0],
      payType: selectedPayType,
      pay: wage,
      duration: hours ? Number(hours) : 0,
      status: true,
      authorId: userAtom?.userInfo?.userId || 0,
      availablePositions,
      genre: selectedGenre,
      roles: Array.from(selectedRoleCategories),
      location: [
        {
          address: location,
          name: location,
          latitude: 0,
          longitude: 0,
        },
      ],
    };

    console.log("게시글 작성 데이터:", boardData);

    const formData = new FormData();
    const boardBlob = new Blob([JSON.stringify(boardData)], {
      type: "application/json",
    });
    formData.append("board", boardBlob);

    if (image) {
      formData.append("files", image);
    }

    console.log("게시글 작성 form 데이터:", formData);

    try {
      await createPost(formData);
      alert("게시글이 성공적으로 작성되었습니다.");
      router.push("/");
    } catch (error) {
      console.error(error);
      alert("게시글 작성에 실패했습니다.");
    }
  };

  return (
    <div className="max-w-2xl mx-auto p-6 bg-white shadow-md rounded-lg space-y-6">
      <div>
        <h2 className="text-lg font-bold mb-2">장르</h2>
        <CategorySelector
          categories={genreCategoryList}
          selectedCategory={selectedGenre}
          setSelectedCategory={setSelectedGenre}
          color="blue"
        />
      </div>

      <div>
        <h2 className="text-lg font-bold mb-2">역할</h2>
        <MultiCategorySelector
          categories={roleCategoryList}
          selectedCategories={selectedRoleCategories}
          setSelectedCategories={setSelectedRoleCategories}
          color="green"
        />
      </div>

      <InputField
        title="제목"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      <InputField
        title="장소"
        value={location}
        onChange={(e) => setLocation(e.target.value)}
      />

      <div>
        <h2 className="text-lg font-bold mb-2">날짜</h2>
        <div className="flex w-full gap-2">
          <DatePicker
            selected={startDate}
            onChange={(date) => setStartDate(date)}
            dateFormat="yyyy-MM-dd"
            placeholderText="시작 날짜"
            className="w-full p-2 border border-gray-300 rounded-md"
            showYearDropdown
            scrollableYearDropdown
            yearDropdownItemNumber={125} // 현재 연도 - 1900 = 약 125년
            minDate={new Date("1900-01-01")} // 1900년 1월 1일이 최소 선택 가능 날짜
            // 현재 날짜 보다 2년 뒤 오늘 날짜를 최대 선택 가능 날짜로 설정
            maxDate={(() => {
              if (endDate) {
                return endDate;
              }
              const today = new Date();
              today.setFullYear(today.getFullYear() + 2);
              return today;
            })()}
          />
          <DatePicker
            selected={endDate}
            onChange={(date) => setEndDate(date)}
            dateFormat="yyyy-MM-dd"
            placeholderText="종료 날짜"
            className="w-full p-2 border border-gray-300 rounded-md"
            showYearDropdown
            scrollableYearDropdown
            yearDropdownItemNumber={125} // 현재 연도 - 1900 = 약 125년
            minDate={
              startDate || new Date("1900-01-01") // 시작 날짜가 없으면 1900년 1월 1일이 최소 선택 가능 날짜
            }
            // 현재 날짜 보다 2년 뒤 오늘 날짜를 최대 선택 가능 날짜로 설정
            maxDate={(() => {
              const today = new Date();
              today.setFullYear(today.getFullYear() + 2);
              return today;
            })()}
          />
        </div>
      </div>

      <div>
        <h2 className="text-lg font-bold mb-2">급여</h2>
        <div className="flex gap-4">
          <ToggleButton
            label="시급"
            color="black"
            isToggle={selectedPayType === "시급"}
            onClick={() => setSelectedPayType("시급")}
          />
          <ToggleButton
            label="일당"
            color="black"
            isToggle={selectedPayType === "일당"}
            onClick={() => setSelectedPayType("일당")}
          />
        </div>
        <div className="mt-2 space-y-2">
          <InputBox
            placeholder="원"
            value={wage.toString()}
            onChange={(e) => {
              const value = e.target.value;
              if (value === "" || /^\d+$/.test(value)) {
                setWage(Number(value) || 0);
              }
            }}
          />
          {selectedPayType === "시급" && (
            <InputBox
              placeholder="시간"
              value={hours}
              onChange={(e) => {
                const value = e.target.value;
                if (value === "" || /^\d+$/.test(value)) {
                  setHours(value);
                }
              }}
            />
          )}
        </div>
        <div className="mt-2 font-bold text-gray-700">
          총 급여: {totalWage.toLocaleString()} 원
        </div>
      </div>

      <InputField
        title="내용"
        value={content}
        onChange={(e) => setContent(e.target.value)}
        placeholder="구체적인 일정 및 동선 등의 정보 입력"
      />

      <InputField
        title="인원 수"
        value={availablePositions.toString()}
        onChange={(e) => {
          const value = e.target.value;
          if (value === "" || /^\d+$/.test(value)) {
            setAvailablePositions(Number(value) || 0);
          }
        }}
      />

      <div>
        <h2 className="text-lg font-bold mb-2">사진</h2>
        <input
          type="file"
          accept="image/*"
          className="block w-full text-sm text-gray-700 border border-gray-300 rounded-md p-2"
          onChange={(e) => setImage(e.target.files?.[0] || null)}
        />
      </div>

      <button
        onClick={handleSubmit}
        className="w-full p-3 bg-blue-500 text-white rounded-md"
      >
        게시글 작성
      </button>
    </div>
  );
};

export default PostCreatePage;
