"use client";

import { useSearchParams } from "next/navigation";
import { useState } from "react";

const SearchBox = () => {
  const [searchValue, setSearchValue] = useState("");
  const searchParams = useSearchParams();

  const handleSearch = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchValue(e.target.value);
    const params = new URLSearchParams(searchParams.toString());
    params.set("query", e.target.value);
    // Assuming you want to update the URL with the new search value
    window.history.replaceState(
      {},
      "",
      `${window.location.pathname}?${params}`
    );
  };

  return (
    <input
      type="text"
      value={searchValue}
      onChange={handleSearch}
      placeholder="Search..."
    />
  );
};

export default SearchBox;
