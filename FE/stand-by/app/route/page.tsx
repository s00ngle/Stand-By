import Button from "@/components/Button";
import Link from "next/link";

const routes = [
  { path: "/", label: "Home" },
  { path: "/auth", label: "Auth" },
  { path: "/login", label: "Login" },
  { path: "/signup", label: "Sign Up" },
  { path: "/profile/[userid]", label: "Profile", dynamic: { userid: "1" } },
  { path: "/portfolio/[userid]", label: "Portfolio", dynamic: { userid: "1" } },
  { path: "/board", label: "Board" },
  { path: "/post/[postid]", label: "Post", dynamic: { postid: "10" } },
  { path: "/post/create", label: "Post Create" },
  { path: "/test", label: "Test" },
  { path: "/evaluation", label: "Evaluation" },
  { path: "/report", label: "Report" },
  { path: "/notification/applicant", label: "Noti-Apply" },
  { path: "/notification/employer", label: "Noti-Employ" },
];

const RoutePage = () => {
  return (
    <div className="flex flex-1 flex-col p-8">
      <h1 className="text-xl font-bold">Route Page</h1>
      <div className="flex gap-3 flex-wrap mt-4">
        {routes.map(({ path, label, dynamic }) => {
          // 동적 경로를 실제 값으로 변환
          const resolvedPath = dynamic
            ? Object.entries(dynamic).reduce(
                (acc, [key, value]) => acc.replace(`[${key}]`, value),
                path
              )
            : path;

          return (
            <div key={path}>
              <Link href={resolvedPath}>
                <Button label={label} />
              </Link>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default RoutePage;
