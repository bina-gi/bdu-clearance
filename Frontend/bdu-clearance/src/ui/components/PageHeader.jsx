function PageHeader({ title, description }) {
  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-900">{title}</h1>
      <p className="text-gray-500 text-sm">{description}</p>
    </div>
  );
}

export default PageHeader;
