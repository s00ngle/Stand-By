import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

export function middleware(request: NextRequest) {
  const token = request.cookies.get("token");
  const path = request.nextUrl.pathname;
  const isAuthPage = ["/login", "/signup", "/auth"].includes(path);

  console.log("Middleware 실행");
  console.log("Current cookies:", request.cookies.getAll());
  console.log("Current token:", token?.value);
  console.log("Current path:", path);
  console.log("Is auth page:", isAuthPage);

  if (!token) {
    if (!isAuthPage) {
      return NextResponse.redirect(new URL("/auth", request.url));
    }
  } else {
    if (isAuthPage) {
      return NextResponse.redirect(new URL("/", request.url));
    }
  }

  return NextResponse.next();
}

export const config = {
  matcher: ["/((?!api|_next/static|_next/image|favicon.ico).*)"],
};
