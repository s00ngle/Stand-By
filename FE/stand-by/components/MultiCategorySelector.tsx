"use client";
import React, { useState } from "react";
import ToggleButton from "./ToggleButton";

interface IProps {
  categories: string[];
  selectedCategories: Set<string>;
  setSelectedCategories: React.Dispatch<React.SetStateAction<Set<string>>>;
  color?: "blue" | "green" | "red";
}

const MultiCategorySelector = ({
  categories,
  selectedCategories,
  setSelectedCategories,
  color,
}: IProps) => {
  const handleCategoryClick = (category: string) => {
    if (selectedCategories.has(category)) {
      removeSelectedList(category);
    } else {
      addSelectedList(category);
    }
  };

  const addSelectedList = (value: string) => {
    setSelectedCategories((e) => new Set<string>(e).add(value));
  };

  const removeSelectedList = (value: string) => {
    setSelectedCategories((e) => {
      const next = new Set<string>(e);
      next.delete(value);
      return next;
    });
  };

  return (
    <div>
      <div className="flex flex-wrap justify-center gap-2">
        {categories.map((category) => (
          <ToggleButton
            key={category}
            label={category}
            color={color}
            isToggle={selectedCategories.has(category)}
            onClick={() => handleCategoryClick(category)}
          />
        ))}
      </div>
    </div>
  );
};

export default MultiCategorySelector;
