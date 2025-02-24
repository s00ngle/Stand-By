import { Post } from "@/types/Post";
import Image from "next/image";
import Tag from "./Tag";
import Link from "next/link";

const PostCard = ({ postData }: { postData: Post }) => {
  return (
    <Link
      href={`/post/${postData.boardId}`}
      className="w-full flex flex-col rounded-xl overflow-hidden shadow-lg transition-transform active:scale-95 hover:scale-105"
    >
      {postData.images.length > 0 && (
        <img
          src={postData.images[0]}
          alt={postData.title}
          width={500}
          height={0}
          className="w-full object-cover h-40"
        />
      )}
      <div className="px-4 py-5 flex flex-col gap-4">
        <div className="flex justify-between items-center">
          <div className="flex flex-col">
            <div className="text-md font-bold">{postData.title}</div>
            <div className="text-sm text-neutral-500">
              {postData.date.startDate} - {postData.date.endDate}
            </div>
            <div className="text-sm text-neutral-500">
              {postData.location?.[0]?.address ?? "주소 정보 없음"}
            </div>
          </div>
          <Tag
            label={postData.availablePositions ? "구인중" : "마감"}
            bgColor={
              postData.availablePositions ? "bg-blue-500" : "bg-gray-300"
            }
          />
        </div>
        <div className="flex justify-between gap-2">
          <Tag
            label={postData.genre}
            bgColor="bg-lime-50"
            textColor="text-lime-500"
          />
          <div className="flex gap-2">
            {postData.roles.map((role) => (
              <Tag
                key={role}
                label={role}
                bgColor="bg-blue-50"
                textColor="text-blue-500"
              />
            ))}
          </div>
        </div>
      </div>
    </Link>
  );
};

export default PostCard;
