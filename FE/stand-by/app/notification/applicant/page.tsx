"use client";

import React, { useState } from "react";

const ApplicantNotificationPage = () => {
  const [notifications, setNotifications] = useState([
    {
      id: 1,
      message: "XX게시글에서 xx님의 지원신청을 수락하였습니다.",
      time: "15분 전",
      link: "/post/1",
    },
    {
      id: 2,
      message: "XX게시글에서 xxx님의 지원신청을 거절하였습니다.",
      time: "30분 전",
      link: "/post/2",
    },
  ]);

  const handleDelete = (id: number) => {
    setNotifications(notifications.filter((notification) => notification.id !== id));
  };

  return (
    <div className="p-4 sm:p-6 max-w-2xl mx-auto">
      <h1 className="text-2xl font-bold text-blue-600 mb-6 text-center">
        알림 (구직자 페이지)
      </h1>

      <div className="space-y-4">
        {notifications.map((notification) => (
          <div
            key={notification.id}
            className="flex flex-col p-4 border rounded-lg shadow bg-blue-50 w-full"
          >
            {/* 첫 번째 큰 div (파란색) */}
            <div className="flex items-center justify-between w-full">
              {/* 종 아이콘 */}
              <div className="text-blue-600 text-3xl flex-shrink-0">🔔</div>

              {/* 본문 내용 */}
              <div className="flex flex-col text-left w-full px-2">
                <p className="text-lg text-blue-700 font-semibold leading-tight break-words">
                  {notification.message}
                </p>
              </div>

              {/* X 버튼 (오른쪽 끝) */}
              <button
                onClick={() => handleDelete(notification.id)}
                className="text-red-500 hover:text-red-600 text-lg flex-shrink-0"
              >
                ❌
              </button>
            </div>

            {/* 두 번째 큰 div (하단 파란색) */}
            <div className="flex justify-between items-center mt-3">
              {/* 게시글 이동 버튼 */}
              <a
                href={notification.link}
                className="bg-white border border-blue-600 text-blue-600 px-4 py-2 rounded-lg hover:bg-blue-100 w-full text-center"
              >
                게시글로 이동
              </a>

              {/* 알림 시간 */}
              <span className="text-sm text-gray-500">{notification.time}</span>
            </div>
          </div>
        ))}
      </div>

      {/* 알림이 없을 때 */}
      {notifications.length === 0 && (
        <p className="text-center text-gray-500 mt-6">알림이 없습니다.</p>
      )}
    </div>
  );
};

export default ApplicantNotificationPage;
