const Loading: React.FC = () => {
  return (
    <div className="mt-1 flex justify-center items-center flex-1 bg-gray-50">
      <div className="w-16 h-16 border-4 border-t-4 border-r-4 border-transparent border-solid rounded-full animate-spin border-t-blue-500 border-r-blue-500" />
    </div>
  );
};

export default Loading;
