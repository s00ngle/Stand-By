import React from "react";
import {
  User,
  Calendar,
  Ruler,
  Weight,
  Film,
  Clock,
  Trophy,
} from "lucide-react";
import { ProfileData } from "@/types/Profile";
import Tag from "@/components/Tag";

const ProfileStats = ({ profileData }: { profileData: ProfileData }) => {
  const stats = [
    {
      icon: <User className="w-5 h-5 text-blue-500" />,
      label: "성별",
      value: profileData.gender,
      show: !!profileData.gender,
    },
    {
      icon: <Calendar className="w-5 h-5 text-green-500" />,
      label: "생년월일",
      value: profileData.birthDate,
      show: !!profileData.birthDate,
    },
    {
      icon: <Ruler className="w-5 h-5 text-purple-500" />,
      label: "키",
      value: `${profileData.height}cm`,
      show: !!profileData.height,
    },
    {
      icon: <Weight className="w-5 h-5 text-red-500" />,
      label: "몸무게",
      value: `${profileData.weight}kg`,
      show: !!profileData.weight,
    },
    {
      icon: <Film className="w-5 h-5 text-yellow-500" />,
      label: "참여작품 수",
      value: profileData.workCounts,
      show: !!profileData.workCounts,
    },
    {
      icon: <Clock className="w-5 h-5 text-indigo-500" />,
      label: "경력",
      value: profileData.workYears,
      show: !!profileData.workYears,
    },
  ];

  const visibleStats = stats.filter((stat) => stat.show);

  if (visibleStats.length === 0) return null;

  return (
    <div className="bg-white rounded-xl p-6 shadow-sm">
      <div className="flex flex-col gap-2">
        {visibleStats.map((stat, index) => (
          <div
            key={index}
            className="flex items-center space-x-4 p-2 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors"
          >
            {stat.icon}
            <div>
              <div className="text-sm text-gray-500">{stat.label}</div>
              <div className="font-medium">{stat.value}</div>
            </div>
          </div>
        ))}
      </div>

      {(profileData.mostGenre ||
        (profileData.mostRoleList && profileData.mostRoleList.length > 0)) && (
        <div className="mt-2">
          {profileData.mostGenre && (
            <div className="flex items-center space-x-4 p-2 bg-gray-50 rounded-lg">
              <Trophy className="w-5 h-5 text-orange-500" />
              <div>
                <div className="text-sm text-gray-500">선호 장르</div>
                <div className="mt-1">
                  <Tag
                    label={profileData.mostGenre}
                    bgColor="bg-lime-50"
                    textColor="text-lime-500"
                  />
                </div>
              </div>
            </div>
          )}

          {profileData.mostRoleList && profileData.mostRoleList.length > 0 && (
            <div className="flex items-center space-x-4 p-2 bg-gray-50 rounded-lg">
              <Trophy className="w-5 h-5 text-teal-500" />
              <div>
                <div className="text-sm text-gray-500">선호 역할</div>
                <div className="mt-1 flex flex-wrap gap-2">
                  {profileData.mostRoleList.slice(0, 3).map((role, index) => (
                    <Tag
                      label={role}
                      bgColor="bg-blue-50"
                      textColor="text-blue-500"
                    />
                  ))}
                </div>
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default ProfileStats;
