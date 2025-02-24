export const sendEvaluation = async (review: Review) => {
  console.log(review);
  const response = await fetch(`/api/board/score`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(review),
  });
  if (!response.ok) {
    throw new Error("평가 작성에 실패하였습니다.");
  }
  return response.json();
};

export const getEvaluation = async (userId: string) => {
  const response = await fetch(`/api/profiles/evaluations/${userId}`);
  if (!response.ok) {
    throw new Error("평가 조회에 실패하였습니다.");
  }
  return response.json();
};
