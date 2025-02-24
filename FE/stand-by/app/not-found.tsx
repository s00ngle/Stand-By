import Button from "@/components/Button";
import Link from "next/link";

const NotFound = () => {
  return (
    <div className="flex flex-1 flex-col items-center justify-center text-white gap-4">
      <h1 className="text-9xl font-extrabold tracking-widest text-gray-600 animate-pulse">
        404
      </h1>
      <p className="text-2xl text-gray-600 font-semibold">
        페이지를 찾을 수 없습니다.
      </p>
      <p className=" text-gray-400 text-sm">
        요청하신 페이지가 존재하지 않거나 이동되었습니다.
      </p>
      <Link href="/">
        <Button label="홈으로 돌아가기" />
      </Link>
    </div>
  );
};

export default NotFound;
