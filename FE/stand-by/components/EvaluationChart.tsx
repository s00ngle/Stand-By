import { useEffect, useState } from "react";

interface IProps {
  labels: string[];
  scores: number[];
  maxScore: number;
  margin?: number;
}

const EvaluationChart = ({ labels, scores, maxScore, margin }: IProps) => {
  const [scoreState, setScoreState] = useState<number[]>([0, 0, 0, 0, 0]);

  useEffect(() => {
    setScoreState(scores);
  }, [scores]);

  const calc = (
    scores: readonly number[],
    maxScore: number,
    margin: number = 0.3
  ) => {
    return scores
      .map((score, i, array) => {
        const angle = ((i * (360 / array.length) + 90) * Math.PI) / 180;
        const x = (
          50 +
          50 * Math.cos(angle) * ((margin + score) / (maxScore + margin))
        ).toFixed(1);
        const y = (
          50 -
          50 * Math.sin(angle) * ((margin + score) / (maxScore + margin))
        ).toFixed(1);

        return `${x}% ${y}%`;
      })
      .toString();
  };

  return (
    <div className="w-full px-12 py-8">
      <div className="relative flex justify-center items-center aspect-square">
        <div
          className="absolute bg-gray-200/50 w-full h-full"
          style={{
            clipPath: `polygon(2.4% 34.5%, 20.6% 90.5%, 79.4% 90.5%, 97.6% 34.5%, 50% 0%)`,
          }}
        ></div>
        <div
          className="absolute bg-blue-400/70 w-full h-full transition-all ease-out duration-500"
          style={{ clipPath: `polygon(${calc(scoreState, maxScore, margin)})` }}
        ></div>
        {labels.map((label, i, array) => (
          <div
            key={i}
            className="h-fit flex flex-col justify-center"
            style={{
              position: "absolute",
              left: `${(
                50 +
                60 * Math.cos(((i * (360 / array.length) + 90) / 180) * Math.PI)
              ).toFixed(1)}%`,
              bottom: `${(
                50 +
                60 * Math.sin(((i * (360 / array.length) + 90) / 180) * Math.PI)
              ).toFixed(1)}%`,
              transform: `translateX(-50%) translateY(50%)`,
            }}
          >
            <span className="text-center text-nowrap text-md">{label}</span>
            <span className="text-gray-500 text-center text-nowrap text-sm">
              {scores[i]}
            </span>
          </div>
        ))}
      </div>
    </div>
  );
};

export default EvaluationChart;
