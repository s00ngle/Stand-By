interface IProps {
  isClosed: boolean;
}

const Aperture = ({ isClosed }: IProps) => {
  const bladeStyleCommon =
    "absolute border-b-1 w-full h-full border-gray-300 bg-gray-400 transition-all duration-200 ease-[cubic-bezier(0.25, 0.46, 0.45, 0.94)] box-border pointer-events-none";

  const open = [
    "-translate-y-[102%] translate-x-[0%]",
    "-translate-y-[49%] translate-x-[88%]",
    "translate-y-[51%] translate-x-[88%]",
    "translate-y-[102%] translate-x-[0%]",
    "translate-y-[51%] -translate-x-[88%]",
    "-translate-y-[51%] -translate-x-[88%]",
  ];

  const closed = [
    "-translate-y-[75%] translate-x-[0%]",
    "-translate-y-[30%] translate-x-[67%]",
    "translate-y-[30%] translate-x-[67%]",
    "translate-y-[75%] translate-x-[0%]",
    "translate-y-[30%] -translate-x-[67%]",
    "-translate-y-[30%] -translate-x-[67%]",
  ];

  const rotation = [
    "rotate-0",
    "rotate-60",
    "rotate-120",
    "rotate-180",
    "rotate-240",
    "rotate-300",
  ];

  return (
    <div className="w-full relative overflow-hidden rounded-full block aspect-square pointer-events-none">
      {[...Array(6)].map((_v, i) => (
        <div
          style={{ clipPath: "polygon(0 0, 15% 0, 73% 100%, 0% 100%)" }}
          className={`${bladeStyleCommon} ${rotation[i]} ${
            isClosed ? closed[i] : open[i]
          }`}
        ></div>
      ))}
    </div>
  );
};

export default Aperture;
