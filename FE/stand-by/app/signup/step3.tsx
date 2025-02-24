import Button from "@/components/Button";
import InputField from "@/components/InputField";
import Link from "next/link";
import { useState } from "react";

const JoinStep3 = () => {
  const [selectedRole, setSelectedRole] = useState("");

  return (
    <>
      <div className="flex-1 flex flex-col mb-10">
        <div className="flex-1 flex flex-col justify-center gap-4">
          <InputField
            title="닉네임"
            label="중복확인"
            placeholder="닉네임을 입력하세요"
            showButton={true}
            onChange={() => {}}
            value=""
          />
        </div>
        <div className="flex gap-4">
          <Button
            label="구인자"
            variant={selectedRole === "employer" ? "default" : "outline"}
            onClick={() => {
              setSelectedRole("employer");
            }}
          />
          <Button
            label="구직자"
            variant={selectedRole === "jobSeeker" ? "default" : "outline"}
            onClick={() => {
              setSelectedRole("jobSeeker");
            }}
          />
        </div>
      </div>
      <div className="flex flex-col gap-2 mt-4">
        <Link href="/">
          <Button label="회원가입" />
        </Link>
      </div>
    </>
  );
};

export default JoinStep3;
