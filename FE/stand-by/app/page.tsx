"use client";

import Loading from "@/components/Loading";
import PostCard from "@/components/PostCard";
import SearchBoxWithFilter from "@/components/SearchBoxWithFilter";
import { motion } from "framer-motion";
import { useEffect, useState } from "react";
import MarvelLogo from "@/public/marvel-logo.png";
import AvengersLogo from "@/public/avengers-logo.png";
import { SearchQuery } from "@/types/SearchQuery";
import { Post } from "@/types/Post";
import { getPosts } from "@/api/post";
import { useAtom } from "jotai";
import { userStateAtom } from "@/atoms/userAtom";
import Link from "next/link";
import { Pencil } from "lucide-react";

const MainPage = () => {
  const [originalPosts, setOriginalPosts] = useState<Post[]>([]); // 원본 데이터를 저장할 상태
  const [filteredPosts, setFilteredPosts] = useState<Post[]>([]); // 필터링된 데이터를 저장할 상태
  const [loading, setLoading] = useState(true);
  const [user, setUser] = useAtom(userStateAtom);

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        setLoading(true);
        const postData = await getPosts();
        setOriginalPosts(postData);
        setFilteredPosts(postData); // 초기에는 필터링된 결과도 원본과 동일
        console.log("포스트 데이터 fetch 성공, postData:", postData);
      } catch (e) {
        console.error(e);
        // setOriginalPosts(tempPostData);
        // setFilteredPosts(tempPostData);
      } finally {
        setLoading(false);
      }
    };

    fetchPosts();
  }, []);

  const HandleSearch = (searchQuery: SearchQuery) => {
    console.log(searchQuery);

    // 검색어가 없으면 원본 데이터로 복원
    if (!searchQuery.role.length && !searchQuery.genre.length) {
      setFilteredPosts(originalPosts);
      return;
    }

    const filtered = originalPosts.filter((post) => {
      // 역할 필터링
      const hasMatchingRole = post.roles.some((role) =>
        searchQuery.role.includes(role)
      );

      // 장르 필터링
      const hasMatchingGenre = searchQuery.genre.includes(post.genre);

      // 역할이나 장르 중 하나라도 일치하면 true 반환
      return hasMatchingRole || hasMatchingGenre;
    });

    setFilteredPosts(filtered); // 필터링된 결과만 업데이트
  };

  // const tempPostData: Post[] = [
  //   {
  //     boardId: 1,
  //     availablePositions: 1,
  //     date: { startDate: "2025/01/13", endDate: "2025/01/16" },
  //     description: "촬영 현장 스태프 모집",
  //     duration: 3,
  //     genre: "장편",
  //     images: [MarvelLogo.src],
  //     location: [{ address: "대전 광역시 유성구", latitude: 0, longitude: 0 }],
  //     nickname: "nickname",
  //     pay: 100000,
  //     paymentType: "hourly",
  //     profileImgUrl: MarvelLogo.src,
  //     roles: ["촬영", "편집", "녹음"],
  //     title: "촬영 현장 스태프 모집",
  //   },
  //   {
  //     boardId: 2,
  //     availablePositions: 0,
  //     date: { startDate: "2025/01/15", endDate: "2025/01/17" },
  //     description: "녹음 스튜디오 스태프 모집",
  //     duration: 2,
  //     genre: "드라마",
  //     images: [AvengersLogo.src],
  //     location: [{ address: "서울특별시 강남구", latitude: 0, longitude: 0 }],
  //     nickname: "nickname",
  //     pay: 100000,
  //     paymentType: "hourly",
  //     profileImgUrl: AvengersLogo.src,
  //     roles: ["녹음", "편집"],
  //     title: "녹음 스튜디오 스태프 모집",
  //   },
  //   {
  //     boardId: 3,
  //     availablePositions: 1,
  //     date: { startDate: "2025/01/13", endDate: "2025/01/16" },
  //     description: "편집 스튜디오 인원 모집",
  //     duration: 3,
  //     genre: "단편",
  //     images: [MarvelLogo.src],
  //     location: [{ address: "대전 광역시 유성구", latitude: 0, longitude: 0 }],
  //     nickname: "nickname",
  //     pay: 100000,
  //     paymentType: "hourly",
  //     profileImgUrl: MarvelLogo.src,
  //     roles: ["편집"],
  //     title: "편집 스튜디오 인원 모집",
  //   },
  // ];

  if (loading) {
    return <Loading />;
  }

  return (
    <>
      <motion.div
        initial={{ opacity: 0, y: 50 }}
        animate={{ opacity: 1, y: 0 }}
        exit={{ opacity: 0, y: -50 }}
        transition={{ duration: 0.5 }}
        className="relative"
      >
        <SearchBoxWithFilter onSearch={HandleSearch} />
        <div className="flex flex-col px-8 py-4 gap-4">
          {filteredPosts.map((post) => (
            <PostCard key={post.boardId} postData={post} />
          ))}
        </div>
      </motion.div>
      {user?.userInfo?.role === "employer" && (
        <Link
          href={`/post/create`}
          className="fixed bottom-8 right-8 w-14 h-14 cursor-pointer bg-blue-500 rounded-full flex items-center justify-center text-white shadow-lg hover:bg-blue-600 transition duration-300"
        >
          <Pencil />
        </Link>
      )}
    </>
  );
};

export default MainPage;
