function TableCell({ children, className }) {
  return (
    <td className="p-1 lg:p-2 xl:p-4 align-top">
      <div className={className}>{children}</div>
    </td>
  );
}

export default TableCell;
