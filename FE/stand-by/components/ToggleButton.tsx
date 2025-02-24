type ToggleButtonProps = {
  label: string;
  isToggle?: boolean;
  color?: "blue" | "green" | "red" | "black";
  onClick?: () => void;
};

const ToggleButton = ({
  label,
  isToggle = false,
  color = "blue",
  onClick,
}: ToggleButtonProps) => {
  const buttonStyles =
    color === "blue"
      ? isToggle
        ? `bg-blue-500 text-white`
        : `bg-blue-50 text-blue-500`
      : color === "red"
      ? isToggle
        ? `bg-red-500 text-white`
        : `bg-red-50 text-red-500`
      : color === "green"
      ? isToggle
        ? `bg-lime-500 text-white`
        : `bg-lime-50 text-lime-500`
      : color === "black"
      ? isToggle
        ? `bg-black text-white`
        : `bg-gray-200 text-black`
      : "";
  return (
    <button
      onClick={onClick}
      className={`w-fit px-3 py-1 transition-all font-bold rounded-full
        ${buttonStyles}`}
    >
      {label}
    </button>
  );
};

export default ToggleButton;
