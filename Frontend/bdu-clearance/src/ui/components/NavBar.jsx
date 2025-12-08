import { NavLink } from "react-router-dom";

function NavBar({ icon, navName, page, isAccount = false, onClick }) {
  return (
    <NavLink
      className={
        "flex items-center w-full  hover:bg-gray-300 " +
        (isAccount
          ? "justify-center w-full h-16 mt-auto bg-blue-200"
          : "h-12 px-3 mt-2  rounded")
      }
      to={page}
      onClick={onClick}
    >
      <span className="w-6 h-6 stroke-current">{icon}</span>
      <span className="ml-2 text-sm font-medium">{navName}</span>
    </NavLink>
  );
}

export default NavBar;
