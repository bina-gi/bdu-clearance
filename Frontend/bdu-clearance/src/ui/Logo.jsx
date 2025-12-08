import { Link } from "react-router-dom";
import logo from "../assets/logo.png";

function Logo() {
  return (
    <Link className="flex items-center flex-col w-full px-3 mt-3" href="#">
      <img src={logo} alt="Bahir Dar University" className="max-w-18" />
      <span className="text-center">
        <p className="font-bold text-xl text-blue-400">Bahir Dar University</p>
        <p className="font-light text-sm ">
          Student Clearance Management System
        </p>
      </span>
    </Link>
  );
}

export default Logo;
