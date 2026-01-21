import React from "react";

const Input = React.forwardRef(
  ({ label, id, error, className, ...props }, ref) => {
    return (
      <input
        id={id}
        ref={ref}
        className={`bg-gray-50 border border-gray-300 text-gray-900 rounded-lg outline-blue-400 block w-full p-2.5 shadow-sm transition-all duration-200 ease-in-out ${
          error ? "border-red-500 focus:border-red-500 focus:ring-red-500" : ""
        } ${className}`}
        {...props}
      />
    );
  },
);

Input.displayName = "Input";

export default Input;
