function RadioInput({ label, ...props }) {
  return (
    <div className="flex gap-1 items-center px-2">
      <input {...props} />
      {label && (
        <label
          htmlFor={props.id}
          className="text-normal font-normal text-gray-900"
        >
          {label}
        </label>
      )}
    </div>
  );
}

export default RadioInput;
