import { atom } from "jotai";
import { Portfolio } from "@/types/Portfolio";

export const portfolioAtom = atom<Portfolio[]>([]);
