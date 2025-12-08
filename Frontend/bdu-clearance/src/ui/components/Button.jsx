function Button({
  size = "medium",
  variation = "primary",
  children,
  className = "",
  ...props
}) {
  // Size variants
  const sizeClasses = {
    small: "text-[1rem] px-2 py-1 uppercase font-semibold text-center",
    medium: "text-[1.2rem] px-4 py-3 font-medium",
    large: "text-[1.4rem] px-6 py-3 font-medium",
  };

  // Color + variation styles
  const variationClasses = {
    primary: `
      text-gray-100
      bg-navy
      hover:bg-blueish
    `,
    secondary: `
      text-gray-600
      bg-gray-100
      border border-gray-300
      hover:bg-gray-50
    `,
    danger: `
      text-red-100
      bg-red-600
      hover:bg-red-700
    `,
  };

  const base =
    "border-none rounded-sm shadow-sm transition-colors duration-200 m-2";

  return (
    <button
      className={`
        ${base}
        ${sizeClasses[size]}
        ${variationClasses[variation]}
        ${className}
      `}
      {...props}
    >
      {children}
    </button>
  );
}

export default Button;
