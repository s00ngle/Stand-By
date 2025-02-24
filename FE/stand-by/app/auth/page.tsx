"use client";

import KakaoLoginButton2 from "@/components/KakaoLoginButton2";
import Loading from "@/components/Loading";
import { motion } from "framer-motion";
import { useState } from "react";

const AuthPage = () => {
  const [loading, setLoading] = useState(false);

  return (
    <motion.div
      className="flex-1 p-8 flex flex-col justify-center gap-4"
      initial={{ opacity: 0, y: 50 }} // 초기 상태 (아래에서 등장)
      animate={{ opacity: 1, y: 0 }} // 애니메이션 (제자리로 이동하며 나타남)
      exit={{ opacity: 0, y: -50 }} // 종료 애니메이션 (위로 사라짐)
      transition={{ duration: 0.5 }} // 애니메이션 지속 시간
    >
      {loading ? <Loading /> : <KakaoLoginButton2 />}
    </motion.div>
  );
};

export default AuthPage;
