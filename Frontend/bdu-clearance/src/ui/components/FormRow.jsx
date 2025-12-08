function FormRow({ label, error, children }) {
  return (
    <div className="flex flex-col gap-1 ">
      {label && (
        <label
          htmlFor={children.props.id}
          className="text-normal font-medium text-navy"
        >
          {label}
        </label>
      )}

      {children}

      {error && <span className="text-sm text-red-700">{error}</span>}
    </div>
  );
}

export default FormRow;
