"use client";
import Button from "@/components/Button";
import ApplicantCard from "@/components/ApplicantCard";
import Tag from "@/components/Tag";
import Image from "next/image";
import { motion } from "framer-motion";
import MainImage from "@/public/main-img.png";
import { useEffect, useState } from "react";
import { closePost, deletePost, getPost } from "@/api/post";
import { useParams, useRouter } from "next/navigation";
import Loading from "@/components/Loading";
import { useAtomValue } from "jotai";
import { userStateAtom } from "@/atoms/userAtom";
import ApplyModal from "@/components/ApplyModal";
import { ProfileData } from "@/types/Profile";
import { getProfile } from "@/api/profile";
import DevaultAvatar from "@/public/default-avatar.png";
import Link from "next/link";
import { cancelApplication } from "@/api/apply";
import EvaluationModal from "@/components/EvaluationModal";

const PostPage = () => {
  const { postId } = useParams();

  interface Location {
    address: string;
    latitude: number;
    longitude: number;
  }

  interface Post {
    title: string;
    description: string;
    location: Location[];
    date: {
      startDate: string;
      endDate: string;
    };
    pay: number;
    paymentType: string;
    boardId: number;
    genre: string;
    roles: string[];
    authorId: number;
    status: boolean;
    end: boolean;
    images: string[];
  }

  interface Applicant {
    applicantId: number;
    applicantStatus: string; // 신청, 면접합격, 최종합격, 거절
    boardId: number;
    employerId: number;
    memo: string | null;
    profileImage: string | null;
    role: string;
    updatedAt: string;
    nickname: string;
  }

  const handleCancelApplication = async ({
    boardId,
    userId,
  }: {
    boardId: number;
    userId: number;
  }) => {
    try {
      await cancelApplication(boardId, userId);
      // 취소 성공 후 데이터 다시 불러오기
      if (typeof postId === "string") {
        const response = await getPost(postId);
        setPost(response.board);
        setApplicantList(response.apllicantList);
        setMyApplication(
          response.apllicantList.find(
            (applicant: Applicant) => applicant.applicantId === userId
          ) || null
        );
      }
    } catch (error) {
      console.error("Failed to cancel application:", error);
    }
  };

  const handleApplicationSuccess = async () => {
    // 지원 성공 후 데이터 다시 불러오기
    if (typeof postId === "string") {
      const response = await getPost(postId);
      setPost(response.board);
      setApplicantList(response.apllicantList);
      setMyApplication(
        response.apllicantList.find(
          (applicant: Applicant) => applicant.applicantId === userId
        ) || null
      );
    }
    setOpenApply(false);
  };

  const [post, setPost] = useState<Post | null>(null);
  const [applicantList, setApplicantList] = useState<Applicant[] | null>(null);
  const [authorProfile, setAuthorProfile] = useState<ProfileData | null>(null);
  const [loading, setLoading] = useState(false);
  const [profileLoading, setProfileLoading] = useState(false);

  const userInfo = useAtomValue(userStateAtom);
  const availablePositions: boolean = true;
  const [isOP, setIsOP] = useState<boolean>(false);
  const [isEmployee, setIsEmployee] = useState<boolean>(true);
  const [openApply, setOpenApply] = useState<boolean>(false);

  const userId = userInfo?.userInfo?.userId || 0;

  const [myApplication, setMyApplication] = useState<Applicant | null>(null);
  const [evaluationModal, setEvaluationModal] = useState<boolean>(false);

  const router = useRouter();

  // Post 데이터 fetch
  useEffect(() => {
    const fetchPostData = async () => {
      try {
        if (typeof postId === "string") {
          setLoading(true);
          const response = await getPost(postId);
          console.log("Post data received:", response);

          // 게시물 데이터
          setPost(response.board);

          // 지원자 데이터
          setApplicantList(response.apllicantList);

          setMyApplication(
            response.apllicantList.find(
              (applicant: Applicant) => applicant.applicantId === userId
            ) || null
          );

          setIsOP(response.board.authorId === userInfo?.userInfo?.userId);
          setIsEmployee(userInfo?.userInfo?.role === "employee");
        } else {
          console.error("Invalid postId:", postId);
        }
      } catch (error) {
        console.error("Failed to fetch post data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchPostData();
  }, [postId, userInfo]);

  // Author Profile fetch
  useEffect(() => {
    const fetchAuthorProfile = async () => {
      if (post?.authorId) {
        try {
          setProfileLoading(true);
          const profileResponse = await getProfile(post.authorId.toString());
          console.log("Author profile received:", profileResponse);
          setAuthorProfile(profileResponse.profile);
        } catch (error) {
          console.error("Failed to fetch author profile:", error);
        } finally {
          setProfileLoading(false);
        }
      }
    };

    fetchAuthorProfile();
  }, [post?.authorId]);

  // Profile data debugging log
  useEffect(() => {
    console.log("Current authorProfile state:", authorProfile);
  }, [authorProfile]);

  const updateApplicantStatus = (applicantId: number, newStatus: string) => {
    setApplicantList(
      (prevList) =>
        prevList?.map((applicant) =>
          applicant.applicantId === applicantId
            ? { ...applicant, applicantStatus: newStatus }
            : applicant
        ) || null
    );
  };

  if (loading) {
    return <Loading />;
  }

  if (!post) {
    return <div>게시글을 불러오는 중입니다...</div>;
  }

  console.log("Rendering with authorProfile:", authorProfile);

  return (
    <motion.div
      initial={{ opacity: 0, y: 50 }}
      animate={{ opacity: 1, y: 0 }}
      exit={{ opacity: 0, y: -50 }}
      transition={{ duration: 0.5 }}
    >
      <div className="flex flex-col p-8 gap-4">
        <div className="w-full flex-col rounded-xl overflow-hidden bg-gray-50 shadow-md">
          {/* 상단 이미지 부분 */}
          <div className="relative">
            <div className="absolute top-4 right-4">
              <Tag
                label={availablePositions ? "구인중" : "마감"}
                bgColor={availablePositions ? "bg-blue-500" : "bg-gray-300"}
              />
            </div>
            {post.images.length > 0 ? (
              <img
                src={post.images[0]}
                width={500}
                height={500}
                alt="standby-main"
                className="w-full object-cover h-50"
              />
            ) : null}
          </div>

          {/* Author Profile Section */}
          {/* Author Profile Section */}
          <div className="px-8 pt-4 flex items-center gap-4 border-b border-gray-200 pb-4">
            {profileLoading ? (
              <>
                <div className="w-12 h-12 rounded-full bg-gray-200 animate-pulse" />
                <div className="w-20 h-4 bg-gray-200 animate-pulse rounded" />
              </>
            ) : (
              <Link
                href={`/profile/${authorProfile?.userId}`}
                className="flex items-center gap-3"
              >
                {authorProfile?.profileImage ? (
                  <img
                    src={authorProfile.profileImage}
                    alt="Author profile"
                    className="w-12 h-12 rounded-full object-cover"
                  />
                ) : (
                  <Image
                    src={DevaultAvatar}
                    width={1000}
                    height={1000}
                    className="rounded-full w-12 h-12 object-cover"
                    alt="Profile Image"
                  />
                )}
                <span className="font-bold text-lg">
                  {authorProfile?.nickname || "Unknown"}
                </span>
              </Link>
            )}
          </div>

          {/* 게시물 상세 정보 */}
          <div className="my-4 px-8 flex justify-between items-center">
            <div className="w-full flex flex-col">
              <span className="text-2xl font-bold py-2">{post.title}</span>
              <div className="py-2 flex flex-col">
                <span className="text-md font-bold">내용</span>
                <span className="text-sm text-gray-400">
                  {post.description}
                </span>
              </div>
              <div className="py-2 flex flex-col">
                <span className="text-md font-bold">장소</span>
                <span className="text-sm text-gray-400">
                  {post.location[0].address}
                </span>
              </div>
              <div className="py-2 flex flex-col">
                <span className="text-md font-bold">날짜</span>
                <span className="text-sm text-gray-400">
                  {post.date.startDate} - {post.date.endDate}
                </span>
              </div>
              <div className="py-2 flex justify-between items-center">
                <div className="flex flex-col">
                  <span className="text-md font-bold">급여</span>
                  <span className="text-sm text-gray-400">
                    {post.pay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
                    원
                  </span>
                </div>
                <Tag
                  label={post.paymentType}
                  bgColor={
                    post.paymentType === "시급" ? "bg-lime-500" : "bg-blue-500"
                  }
                />
              </div>
            </div>
          </div>
        </div>

        {isOP && (
          <div className="flex flex-col gap-2">
            {applicantList?.map(
              (applicant) => {
                return (
                  // 마감했을 때(post.status : false)는 최종합격자만 보여줌
                  // 마감하지 않았을 때(post.status : true)는 모든 지원자 보여줌
                  post.status || applicant.applicantStatus === "최종합격" ? (
                    <ApplicantCard
                      key={applicant.applicantId}
                      nickname={applicant.nickname}
                      role={applicant.role}
                      applicantStatus={applicant.applicantStatus}
                      userId={applicant.applicantId}
                      boardId={post.boardId}
                      onStatusUpdate={updateApplicantStatus}
                      memo={applicant.memo || ""}
                      isBoardClosed={!post.status}
                      authorId={post.authorId}
                    />
                  ) : null
                );
              }

              // (
              //   <ApplicantCard
              //     key={applicant.applicantId}
              //     nickname={applicant.nickname}
              //     role={applicant.role}
              //     applicantStatus={applicant.applicantStatus}
              //     userId={applicant.applicantId}
              //     boardId={post.boardId}
              //     onStatusUpdate={updateApplicantStatus}
              //     memo={applicant.memo || ""}
              //   />
              // )
            )}

            <Button
              label={post.status ? "마감하기" : "마감됨"}
              onClick={() => {
                closePost(post.boardId);
                alert("게시글이 마감되었습니다.");
                router.push("/");
              }}
              variant={post.status ? "default" : "disabled"}
            />
            <Button
              label="글 삭제"
              variant="warning"
              onClick={() => {
                deletePost(post.boardId);
                alert("게시글이 삭제되었습니다.");
                router.push("/");
              }}
            />
          </div>
        )}

        {/* 구직자 */}

        {/* 게시글이 마감되었을 때 */}
        {/* 지원을 하지 않았거나 최종합격 하지 않은 사람 */}
        {isEmployee &&
          !post.status &&
          (!myApplication || myApplication.applicantStatus !== "최종합격") && (
            <Button label="지원 마감" variant="disabled" />
          )}

        {/* 게시글이 마감되었을 때 */}
        {/* 최종 합격자 */}
        {isEmployee &&
          !post.status &&
          myApplication &&
          myApplication.applicantStatus === "최종합격" && (
            <Button
              label="구인자 평가"
              onClick={() => setEvaluationModal(true)}
            />
          )}

        {/* 마감되지 않았을 때 */}
        {/* 구직자인데 지원한 상태 */}
        {isEmployee &&
          myApplication &&
          post.status &&
          (myApplication.applicantStatus === "지원완료" ? (
            <Button
              label="지원 취소"
              onClick={() =>
                handleCancelApplication({
                  boardId: post.boardId,
                  userId: userId,
                })
              }
            />
          ) : myApplication.applicantStatus === "거절" ? (
            <Button label="거절됨" variant="disabled" />
          ) : myApplication.applicantStatus === "면접합격" ? (
            <Button label="면접 합격" variant="disabled" />
          ) : myApplication.applicantStatus === "최종합격" ? (
            <Button label="최종 합격" variant="disabled" />
          ) : (
            <Button label="Unknown Status" variant="disabled" />
          ))}

        {/* 구직자인데 지원하지 않은 상태 */}
        {isEmployee && !myApplication && post.status && (
          <Button label="지원하기" onClick={() => setOpenApply(true)} />
        )}
      </div>

      {/* 지원하기 모달 */}
      {userId && (
        <ApplyModal
          isOpen={openApply}
          userId={userId}
          boardId={post.boardId}
          categories={post.roles}
          onCancel={() => setOpenApply(false)}
          onConfirm={handleApplicationSuccess}
        />
      )}
      <EvaluationModal
        evaluatorId={userId}
        evaluateeId={post.authorId}
        boardId={post.boardId}
        role={"employer"}
        onConfirm={() => setEvaluationModal(false)}
        onCancel={() => setEvaluationModal(false)}
        isOpen={evaluationModal}
      />
    </motion.div>
  );
};

export default PostPage;
