"use client";

import { getPosts } from "@/api/post";
import { testAPI } from "@/api/test";
import { useEffect, useState } from "react";

const TestPage = () => {
  const [testData, setTestData] = useState(null);
  const [testError, setTestError] = useState<string | null>(null);
  const [image, setImage] = useState<string | null>(null);
  const [testImage, setTestImage] = useState<string | null>(null);
  const [boardData, setBoardData] = useState(null);

  useEffect(() => {
    testAPI()
      .then((response) => setTestData(response))
      .catch((err) => setTestError(err.message));
  }, []);

  const handleImageUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => setImage(reader.result as string);
      reader.readAsDataURL(file);
    }
  };

  useEffect(() => {
    const res = fetch("https://cdn-icons-png.flaticon.com/512/103/103093.png")
      .then((response) => response.blob())
      .then((blob) => {
        const url = URL.createObjectURL(blob);
        setTestImage(url);
      });
  }, []);

  useEffect(() => {
    getPosts().then((response) => setBoardData(response));
    console.log(boardData);
  }, []);

  return (
    <div className="p-4">
      <h1 className="text-xl font-bold">Test Page</h1>
      <p>This is a test page.</p>

      {/* 이미지 출력 */}
      {testImage && (
        <div className="mt-4 flex justify-center">
          <img
            src={testImage}
            alt="Test"
            className="max-w-full h-auto rounded-lg shadow-md"
          />
        </div>
      )}

      {/* 이미지 업로드 버튼 */}
      <div className="mt-4">
        <label className="block w-full px-4 py-2 text-center bg-blue-500 text-white rounded cursor-pointer">
          이미지 업로드
          <input
            type="file"
            accept="image/*"
            className="hidden"
            onChange={handleImageUpload}
          />
        </label>
      </div>

      {/* 이미지 미리보기 */}
      {image && (
        <div className="mt-4 flex justify-center">
          <img
            src={image}
            alt="Uploaded"
            className="max-w-full h-auto rounded-lg shadow-md"
          />
        </div>
      )}

      {/* 데이터 출력 */}
      {testError ? (
        <p className="bg-gray-100 p-2 rounded text-red-500">
          Error: {testError}
        </p>
      ) : (
        <pre className="bg-gray-100 p-2 rounded overflow-auto">
          {JSON.stringify(testData, null, 2)}
        </pre>
      )}
    </div>
  );
};

export default TestPage;
