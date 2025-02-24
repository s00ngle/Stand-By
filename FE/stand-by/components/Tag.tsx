type TagProps = {
  label: string;
  onClick?: () => void;
  bgColor?: string;
  textColor?: string;
};

const Tag = ({
  label,
  onClick,
  bgColor = "bg-blue-500",
  textColor = "text-white",
}: TagProps) => {
  return (
    <div
      className={`inline-flex w-fit h-fit px-3 py-[6px] rounded-3xl text-[10px] font-bold whitespace-nowrap ${bgColor} ${textColor}`}
      onClick={onClick}
    >
      {label}
    </div>
  );
};

export default Tag;
