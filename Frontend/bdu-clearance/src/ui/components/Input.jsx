function Input({ className = "", ...props }) {
  return (
    <input
      {...props}
      className={`p-2 hover:bg-white focus:border-2 focus:border-sky-600 border bg-white border-gray-400  outline-0 rounded ${className}`}
    />
  );
}

export default Input;
