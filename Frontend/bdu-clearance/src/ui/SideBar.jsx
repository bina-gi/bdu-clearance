import { useState } from "react";
import { navBarIcons } from "../assets/icons";
import NavBar from "./components/NavBar";
import Logo from "./Logo";
import { FileCheck, LayoutDashboard, Search } from "lucide-react";

function SideBar() {
  const [isOpen, setIsOpen] = useState(false);

  const toggleSidebar = () => {
    setIsOpen((prev) => !prev);
  };

  return (
    <>
      <div
        className={`w-dvw h-dvh bg-black/10 fixed ${isOpen ? "block" : "hidden"} lg:hidden z-30 blur-2xl`}
        onClick={toggleSidebar}
      ></div>
      <aside
        className={`
          fixed top-0 left-0 z-40 w-60 h-full bg-light  shadow-sm border border-gray-200 transition-transform
          lg:translate-x-0 ${isOpen ? "translate-x-0" : "-translate-x-full"}  
          `}
      >
        <button
          onClick={toggleSidebar}
          className="rounded rounded-l-none  absolute left-60 -top-2 lg:hidden"
        >
          <NavBar icon={navBarIcons.menu} />
        </button>

        <div className="flex flex-col items-center min-h-dvh overflow-hidden text-gray-700">
          <Logo />

          <div className="w-full px-2">
            <div className="flex flex-col items-center w-full mt-3 border-t border-gray-300 pl-2">
              <NavBar icon={<LayoutDashboard />} navName="Dashboard" page="/" />
              <NavBar
                icon={<FileCheck />}
                navName="Request Clearance"
                page="request-clearance"
              />
              <NavBar icon={<Search />} navName="Search" page="search" />
              <NavBar icon={navBarIcons.docs} navName="Docs" page="docs" />
              <NavBar
                icon={navBarIcons.messages}
                navName="Message"
                page="message"
              />
            </div>
          </div>

          <NavBar navName="Account" icon={navBarIcons.account} isAccount />
        </div>
      </aside>
    </>
  );
}

export default SideBar;
