"use client";

import React, { useState } from "react";

const ReportPage = () => {
  const [reason, setReason] = useState(""); // 신고 이유
  const [details, setDetails] = useState(""); // 신고 상세 내용
  const [isSubmitted, setIsSubmitted] = useState(false);

  const handleSubmit = async () => {
    if (!reason) {
      alert("신고 이유를 선택해주세요.");
      return;
    }

    // API 요청 보내기
    try {
      const response = await fetch("/api/report", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          postId: 123, // 예: 신고 대상 게시물 ID
          userId: 456, // 예: 신고자 ID
          reason,
          details,
        }),
      });

      if (response.ok) {
        setIsSubmitted(true);
      } else {
        alert("신고를 처리하는 데 실패했습니다.");
      }
    } catch (error) {
      console.error("Error submitting report:", error);
      alert("신고 요청 중 오류가 발생했습니다.");
    }
  };

  if (isSubmitted) {
    return <div className="p-6 text-center">신고가 접수되었습니다. 감사합니다!</div>;
  }

  return (
    <div className="p-6 max-w-lg mx-auto">
      <h1 className="text-2xl font-bold mb-4">게시물 신고하기</h1>
      <div className="mb-4">
        <label className="block text-lg font-medium mb-2">신고 이유</label>
        <select
          value={reason}
          onChange={(e) => setReason(e.target.value)}
          className="w-full border rounded p-2"
        >
          <option value="">선택해주세요</option>
          <option value="spam">스팸</option>
          <option value="inappropriate">부적절한 내용</option>
          <option value="other">기타</option>
        </select>
      </div>
      <div className="mb-4">
        <label className="block text-lg font-medium mb-2">상세 내용</label>
        <textarea
          value={details}
          onChange={(e) => setDetails(e.target.value)}
          placeholder="신고 이유를 자세히 작성해주세요."
          className="w-full border rounded p-2"
        />
      </div>
      <button
        onClick={handleSubmit}
        className="bg-red-500 text-white px-4 py-2 rounded w-full"
      >
        신고 제출
      </button>
    </div>
  );
};

export default ReportPage;
