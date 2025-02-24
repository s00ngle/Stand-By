"use client";

import React from "react";
import { Radar } from "react-chartjs-2";
import {
  Chart as ChartJS,
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend);

interface Scores {
  "시간 준수": number;
  "업무 수행 능력": number;
  "커뮤니케이션": number;
  "팀워크": number;
  "책임감": number;
}

interface RadarChartProps {
  scores: Scores;
}

const RadarChart: React.FC<RadarChartProps> = ({ scores }) => {
  const labels = Object.keys(scores);
  const dataValues = Object.values(scores);

  const data = {
    labels,
    datasets: [
      {
        label: "평가 점수",
        data: dataValues,
        backgroundColor: "rgba(59, 130, 246, 0.2)",
        borderColor: "rgba(59, 130, 246, 1)",
        borderWidth: 2,
      },
    ],
  };

  const options = {
    scales: {
      r: {
        beginAtZero: true,
        min: 0,
        max: 5,
        ticks: { stepSize: 1, showLabelBackdrop: false },
        grid: { circular: true },
        pointLabels: { font: { size: 14 } },
      },
    },
  };

  return <Radar data={data} options={options} />;
};

export default RadarChart;
