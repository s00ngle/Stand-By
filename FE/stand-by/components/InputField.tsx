import Button from "./Button";
import InputBox from "./Inputbox";

interface InputFieldProps {
  title: string;
  value: string; // Pass the value to InputField
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void; // onChange handler to update the value
  label?: string;
  showButton?: boolean;
  placeholder?: string;
  onButtonClick?: () => void;
  required?: boolean;
}

const InputField = ({
  title,
  value,
  onChange = () => {},
  label = "",
  showButton = true,
  placeholder = "",
  onButtonClick,
  required = false,
}: InputFieldProps) => {
  return (
    <div className="flex flex-col justify-center gap-1">
      <h1 className="text-lg font-bold">{title}</h1>
      <div className="flex items-center gap-2">
        <InputBox
          value={value}
          onChange={onChange}
          placeholder={placeholder}
          required={required}
        />{" "}
        {/* Bind value and onChange */}
        {showButton && label && (
          <div>
            <Button label={label} onClick={onButtonClick} />
          </div>
        )}
      </div>
    </div>
  );
};

export default InputField;
