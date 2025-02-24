import React, { useState } from "react";
import { ChevronDown, ChevronUp, Search, X, RefreshCw } from "lucide-react";
import MultiCategorySelector from "./MultiCategorySelector";
import { SearchQuery } from "@/types/SearchQuery";
import { genreCategoryList, roleCategoryList } from "@/data/postCategories";

interface IProps {
  onSearch: (searchQuery: SearchQuery) => void;
}

const SearchBoxWithFilter = ({ onSearch }: IProps) => {
  const [genreList, setGenreList] = useState<Set<string>>(new Set<string>());
  const [roleList, setRoleList] = useState<Set<string>>(new Set<string>());
  const [isOpen, setIsOpen] = useState(false);

  const totalFilters = genreList.size + roleList.size;

  const handleSearch = () => {
    onSearch({
      genre: [...genreList],
      role: [...roleList],
    } as SearchQuery);
    setIsOpen(false);
  };

  const handleReset = () => {
    setGenreList(new Set<string>());
    setRoleList(new Set<string>());
  };

  return (
    <div className="relative mx-auto max-w-2xl w-full p-4">
      {/* 필터 버튼 */}
      <button
        onClick={() => setIsOpen(!isOpen)}
        className="w-full flex items-center justify-between px-4 py-3 bg-white rounded-lg shadow-sm border border-gray-200 hover:border-gray-300 transition-all duration-200"
      >
        <div className="flex items-center gap-2">
          <span className="text-gray-700">필터</span>
          {genreList.size > 0 && (
            <span className="px-2 py-0.5 text-sm bg-lime-100 text-lime-600 rounded-full">
              {genreList.size}
            </span>
          )}
          {roleList.size > 0 && (
            <span className="px-2 py-0.5 text-sm bg-blue-100 text-blue-600 rounded-full">
              {roleList.size}
            </span>
          )}
        </div>
        <ChevronDown
          className={`w-5 h-5 text-gray-500 transition-all duration-300 ${
            isOpen ? "-scale-y-100" : ""
          }`}
        />
      </button>

      {/* 드롭다운 패널 */}
      <div
        className={`absolute left-4 right-4 z-50 mt-2 bg-white rounded-lg shadow-lg border border-gray-200 transition-all duration-300 ease-in-out
          ${
            isOpen
              ? "opacity-100 translate-y-0"
              : "opacity-0 -translate-y-4 pointer-events-none"
          }`}
      >
        <div className="p-6">
          {/* 장르 선택 */}
          <div className="space-y-4">
            <div className="pb-4">
              <div className="flex justify-between items-center mb-3">
                <h3 className="text-lg font-semibold text-gray-900">장르</h3>
                {genreList.size > 0 && (
                  <button
                    onClick={() => setGenreList(new Set<string>())}
                    className="text-sm text-gray-500 hover:text-gray-700"
                  >
                    초기화
                  </button>
                )}
              </div>
              <MultiCategorySelector
                categories={genreCategoryList}
                selectedCategories={genreList}
                setSelectedCategories={setGenreList}
                color="green"
              />
            </div>

            {/* 구분선 */}
            <div className="border-t border-gray-200" />

            {/* 역할 선택 */}
            <div className="pt-4">
              <div className="flex justify-between items-center mb-3">
                <h3 className="text-lg font-semibold text-gray-900">역할</h3>
                {roleList.size > 0 && (
                  <button
                    onClick={() => setRoleList(new Set<string>())}
                    className="text-sm text-gray-500 hover:text-gray-700"
                  >
                    초기화
                  </button>
                )}
              </div>
              <MultiCategorySelector
                categories={roleCategoryList}
                selectedCategories={roleList}
                setSelectedCategories={setRoleList}
              />
            </div>
          </div>

          {/* 버튼 그룹 */}
          <div className="flex gap-3 mt-6">
            <button
              onClick={handleSearch}
              className="flex-1 flex items-center justify-center gap-2 px-4 py-2.5 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors duration-200"
            >
              <Search className="w-4 h-4" />
              <span>검색하기</span>
            </button>
            {totalFilters > 0 && (
              <button
                onClick={handleReset}
                className="px-4 py-2.5 text-gray-600 bg-gray-100 rounded-lg hover:bg-gray-200 transition-colors duration-200"
                title="필터 초기화"
              >
                <RefreshCw className="w-4 h-4" />
              </button>
            )}
            <button
              onClick={() => setIsOpen(false)}
              className="px-4 py-2.5 text-gray-600 bg-gray-100 rounded-lg hover:bg-gray-200 transition-colors duration-200"
            >
              <X className="w-4 h-4" />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SearchBoxWithFilter;
