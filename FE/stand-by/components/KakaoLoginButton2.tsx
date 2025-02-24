import Image from "next/image";
import KakaoLoginButtonImage from "@/public/kakao-login.png";

const KakaoLoginButton2 = () => {
  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.NEXT_PUBLIC_KAKAO_REST_API_KEY}&redirect_uri=${process.env.NEXT_PUBLIC_KAKAO_REDIRECT_URI}&response_type=code&prompt=login`;

  const handleClick = () => {
    window.location.href = KAKAO_AUTH_URL;
  };

  return (
    <button
      onClick={handleClick}
      className="social-login-button group relative overflow-hidden cursor-pointer"
    >
      <Image
        src={KakaoLoginButtonImage}
        alt={"kakao-login"}
        width={500}
        height={0}
        priority
        className="transition-all duration-300 group-hover:brightness-90"
      />
    </button>
  );
};

export default KakaoLoginButton2;
