type ButtonProps = {
  label: string;
  variant?: "default" | "outline" | "disabled" | "warning"; // Add "warning" variant
  onClick?: () => void;
};

const Button = ({ label, variant = "default", onClick }: ButtonProps) => {
  const buttonStyles =
    variant === "outline"
      ? "text-blue-500 bg-white border-blue-500 hover:bg-blue-500 hover:text-white" // Styles for outline button
      : variant === "disabled"
      ? "text-gray-400 bg-gray-200 border-gray-200 cursor-not-allowed" // Styles for disabled state
      : variant === "warning"
      ? "text-red-500 bg-white border-red-500 hover:bg-red-500 hover:text-white" // Styles for warning (red) button
      : "bg-blue-500 text-white border-blue-500 hover:bg-blue-700 hover:text-white hover:border-blue-700"; // Default styles

  return (
    <button
      className={`w-full whitespace-nowrap cursor-pointer flex justify-center items-center p-2 rounded-xl border-2 transition-all duration-300 ${buttonStyles}`}
      onClick={variant !== "disabled" ? onClick : undefined} // Prevent click in disabled state
      disabled={variant === "disabled"} // Disable the button in the disabled state
    >
      <p className="text-base font-bold whitespace-nowrap">{label}</p>
    </button>
  );
};

export default Button;
