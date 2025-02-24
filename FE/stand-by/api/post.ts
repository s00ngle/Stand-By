export const getPosts = async () => {
  const response = await fetch("/api/board");
  if (!response.ok) {
    throw new Error("게시글 목록을 불러오는데 실패하였습니다.");
  }
  return response.json();
};

export const getPost = async (postId: string) => {
  const response = await fetch(`/api/board/${postId}`);
  if (!response.ok) {
    throw new Error(`게시글(id : ${postId})을 불러오는데 실패하였습니다.`);
  }
  return response.json();
};

export const createPost = async (formData: FormData) => {
  const response = await fetch("/api/board/create", {
    method: "POST",
    body: formData, // 이미지를 포함한 FormData 전송
  });

  if (!response.ok) {
    throw new Error("게시글을 생성하는데 실패하였습니다.");
  }

  return response;
};

export const deletePost = async (postId: number) => {
  const response = await fetch(`/api/board/${postId}`, {
    method: "DELETE",
  });

  if (!response.ok) {
    throw new Error(`게시글(id : ${postId})을 삭제하는데 실패하였습니다.`);
  }

  return response;
};

export const closePost = async (postId: number) => {
  const response = await fetch(`/api/board/${postId}/close`, {
    method: "PATCH",
  });

  if (!response.ok) {
    throw new Error(`게시글(id : ${postId})을 마감하는데 실패하였습니다.`);
  }

  return response;
};
