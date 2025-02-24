import React from "react";

const InterviewModal = () => {
  return (
    <div className="bg-white rounded-lg shadow-lg p-6 w-full max-w-md text-center">
      {/* 제목 */}
      <h2 className="text-xl font-bold text-gray-900 mb-6">면접을 보시겠습니까?</h2>

      {/* 버튼 컨테이너 */}
      <div className="flex justify-center w-full gap-8">
        {/* 취소 버튼 */}
        <div className="border border-red-500 text-red-500 px-6 py-2 rounded-lg font-bold">
          취소
        </div>

        {/* 확인 버튼 */}
        <div className="bg-blue-500 text-white px-6 py-2 rounded-lg font-bold">
          확인
        </div>
      </div>
    </div>
  );
};

export default InterviewModal;  
