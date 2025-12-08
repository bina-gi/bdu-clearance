function TableButton({
  title,
  className = "text-gray-400 hover:text-blue-600 hover:bg-blue-50",
  icon,
  handleClick,
}) {
  return (
    <button
      className={"p-2  rounded-lg transition-colors " + className}
      title={title}
      onClick={handleClick}
    >
      {icon}
    </button>
  );
}

export default TableButton;
