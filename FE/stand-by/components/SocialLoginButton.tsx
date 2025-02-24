import Image from "next/image";

interface SocialLoginButtonProps {
  src: string; // 이미지 경로
  alt: string; // 이미지 설명
  width: number; // 이미지 가로 크기
  height: number; // 이미지 세로 크기
  onClick?: () => void; // 클릭 시 동작
}

const SocialLoginButton = ({
  src,
  alt,
  width,
  height,
  onClick,
}: SocialLoginButtonProps) => {
  return (
    <button
      onClick={onClick}
      className="social-login-button group relative overflow-hidden"
    >
      <Image
        src={src}
        alt={alt}
        width={width}
        height={height}
        priority
        className="transition-all duration-300 group-hover:brightness-90"
      />
    </button>
  );
};

export default SocialLoginButton;
