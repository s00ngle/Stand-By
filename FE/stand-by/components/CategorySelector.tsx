"use client";
import React, { useState } from "react";
import ToggleButton from "./ToggleButton";

interface IProps {
  categories: string[];
  selectedCategory: string | null;
  setSelectedCategory: React.Dispatch<React.SetStateAction<string | null>>;
  color?: "blue" | "green" | "red";
}

const CategorySelector = ({
  categories,
  selectedCategory,
  setSelectedCategory,
  color,
}: IProps) => {
  const handleCategoryClick = (category: string) => {
    if (selectedCategory === category) {
      setSelectedCategory(null);
    } else {
      setSelectedCategory(category);
    }
  };

  return (
    <div>
      <div className="flex flex-wrap justify-center gap-2">
        {categories.map((category) => (
          <ToggleButton
            key={category}
            label={category}
            color={color}
            isToggle={selectedCategory === category}
            onClick={() => handleCategoryClick(category)}
          />
        ))}
      </div>
    </div>
  );
};

export default CategorySelector;
