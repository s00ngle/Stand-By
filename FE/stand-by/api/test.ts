export const testAPI = async () => {
  const response = await fetch(`/api/test`);
  if (!response.ok) {
    throw new Error("테스트 API 호출에 실패하였습니다.");
  }
  return response.json();
};
