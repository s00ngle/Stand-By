import { StaticImageData } from "next/image";

interface Location {
  address: string;
  latitude: number;
  longitude: number;
}

export interface Post {
  boardId: number;
  availablePositions: number;
  date: { startDate: string; endDate: string };
  description: string;
  duration: number;
  genre: string;
  images: string[];
  location: Location[];
  nickname: string;
  pay: number;
  paymentType: string;
  profileImgUrl: string;
  roles: string[];
  title: string;
}
