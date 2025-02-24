export const getPortfolios = async (userid: string) => {
  const response = await fetch(`/api/portfolio/${userid}`);
  if (!response.ok) {
    throw new Error("포트폴리오 목록을 불러오는데 실패하였습니다.");
  }
  return response.json();
};

export const createPortfolio = async (formData: FormData, userid: number) => {
  const response = await fetch(`/api/portfolio/${userid}`, {
    method: "POST",
    body: formData, // 이미지를 포함한 FormData 전송
  });

  if (!response.ok) {
    throw new Error("포트폴리오를 등록하는데 실패하였습니다.");
  }

  return response.json();
};

export const modifyPortfolio = async (formData: FormData, cardId?: number) => {
  const response = await fetch(`/api/portfolio/card/${cardId}`, {
    method: "PUT",
    body: formData, // 이미지를 포함한 FormData 전송
  });

  if (!response.ok) {
    throw new Error("포트폴리오를 수정하는데 실패하였습니다.");
  }

  return response.json();
};

export const deletePortfolio = async (cardId?: number) => {
  const response = await fetch(`/api/portfolio/card/${cardId}`, {
    method: "DELETE",
  });

  if (!response.ok) {
    throw new Error("포트폴리오를 삭제하는데 실패하였습니다.");
  }

  return response.json();
};
