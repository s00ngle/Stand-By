export const sendApplication = async (
  boardId: number,
  userId: number,
  category: string | null
) => {
  const response = await fetch(`/api/board/${boardId}/applicant/${userId}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ role: category }),
  });
  if (!response.ok) {
    throw new Error("지원에 실패하였습니다.");
  }
  return response.json();
};

export const cancelApplication = async (boardId: number, userId: number) => {
  const response = await fetch(`/api/board/${boardId}/applicant/${userId}`, {
    method: "DELETE",
  });
  if (!response.ok) {
    throw new Error("지원 취소가 실패하였습니다.");
  }
  return response.json();
};

export const acceptApplication = async (boardId: number, userId: number) => {
  const response = await fetch(`/api/board/${boardId}/accept/${userId}`, {
    method: "PATCH",
    // headers: {
    //   "Content-Type": "application/json",
    // },
  });
  if (!response.ok) {
    throw new Error("지원 수락에 실패하였습니다.");
  }
  return response.json();
};

export const acceptFinalApplication = async (
  boardId: number,
  userId: number
) => {
  const response = await fetch(`/api/board/${boardId}/final/${userId}`, {
    method: "PATCH",
    // headers: {
    //   "Content-Type": "application/json",
    // },
  });
  if (!response.ok) {
    throw new Error("지원 최종 수락에 실패하였습니다.");
  }
  return response.json();
};

export const rejectApplication = async (boardId: number, userId: number) => {
  const response = await fetch(`/api/board/${boardId}/reject/${userId}`, {
    method: "PATCH",
    // headers: {
    //   "Content-Type": "application/json",
    // },
  });
  if (!response.ok) {
    throw new Error("지원 거절에 실패하였습니다.");
  }
  return response.json();
};

export const memoApplication = async (
  boardId: number,
  userId: number,
  memos: string | null
) => {
  const response = await fetch(
    `/api/board/${boardId}/applicant/${userId}/memos`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ memos }),
    }
  );
  if (!response.ok) {
    throw new Error("구인자 메모에 실패하였습니다.");
  }
  return response.json();
};
