const InputBox = ({
  placeholder = "",
  value = "", // Accept value as a prop
  onChange = () => {}, // Accept an onChange handler
  required = false,
}: {
  placeholder?: string;
  value: string; // Bind to value prop
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void; // Handle input changes
  required?: boolean;
}) => {
  return (
    <input
      className="w-full border-gray-300 border-4 border-double p-3 rounded-xl focus:border-4 focus:border-solid focus:outline-none"
      type="text"
      value={value} // Use value prop to show existing data
      onChange={onChange} // Bind the onChange handler to update the state
      placeholder={placeholder}
      required={required}
    />
  );
};

export default InputBox;
