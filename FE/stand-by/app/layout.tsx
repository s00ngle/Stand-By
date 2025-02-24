import type { Metadata } from "next";
import "./globals.css";
import Header from "@/components/Header";
import Providers from "@/components/Providers";

export const metadata: Metadata = {
  title: "스탠바이",
  description: "촬영인들을 위한 구인/구직 플랫폼 스탠바이",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ko" className="">
      <body className="bg-gray-100">
        <Providers>
          <div className="max-w-[440px] min-w-[300px] mx-auto bg-white min-h-dvh flex flex-col shadow">
            <Header />
            {children}
          </div>
        </Providers>
      </body>
    </html>
  );
}
