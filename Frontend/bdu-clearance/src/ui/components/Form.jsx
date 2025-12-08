function Form({ children, onSubmit }) {
  return (
    <form
      className="p-8 bg-gray-200 shadow-2xl gray-amber-600 rounded-xl m-auto min-w-90"
      onSubmit={onSubmit}
    >
      {children}
    </form>
  );
}

export default Form;
