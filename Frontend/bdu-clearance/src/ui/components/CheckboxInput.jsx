function CheckboxInput({ label, icon, iconColor, ...props }) {
  return (
    <container className="flex gap-1 items-center justify-between px-4 w-full h-12 rounded-lg bg-gray-50 hover:bg-gray-100 border shadow-sm border-gray-200 ">
      <div className="flex gap-2 items-center">
        <span className={iconColor}>{icon}</span>
        {label && (
          <label
            htmlFor={props.id}
            className="text-xl font-medium text-gray-600"
          >
            {label}
          </label>
        )}
      </div>
      <input type="checkbox" {...props} className="h-5 w-5" />
    </container>
  );
}

export default CheckboxInput;
