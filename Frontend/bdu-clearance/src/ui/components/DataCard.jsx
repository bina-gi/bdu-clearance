function DataCard({ dataName, dataValue, icon, iconClass }) {
  return (
    <div className=" bg-blue-100 rounded p-3 flex justify-between gap-6 items-start">
      <span>
        <p className="text-lightgray text-sm font-medium">{dataName}</p>
        <p className="font-bold text-2xl mt-3">{dataValue}</p>
      </span>
      <span className={`rounded-xl p-2 ${iconClass}`}>{icon}</span>
    </div>
  );
}

export default DataCard;
