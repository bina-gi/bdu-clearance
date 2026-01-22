import { useState, useMemo } from "react";
import { navBarIcons } from "../assets/icons";
import NavBar from "./components/NavBar";
import Logo from "./Logo";
import { getNavigationsForUser } from "../assets/navigations";
import useAuth from "../hooks/useAuth";

function SideBar() {
  const [isOpen, setIsOpen] = useState(false);
  const { role, organizationalUnit } = useAuth();

  const toggleSidebar = () => {
    setIsOpen((prev) => !prev);
  };

  const filteredNavigations = useMemo(() => {
    return getNavigationsForUser(role, organizationalUnit);
  }, [role, organizationalUnit]);

  return (
    <>
      <div
        className={`w-dvw h-dvh bg-black/10 fixed ${isOpen ? "block" : "hidden"} lg:hidden z-30 blur-2xl`}
        onClick={toggleSidebar}
      ></div>
      <aside
        className={`
          fixed top-0 left-0 z-40 w-70 h-full bg-light  shadow-sm border border-gray-200 transition-transform
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
              {filteredNavigations.map((navigation) => (
                <NavBar
                  key={navigation.title}
                  icon={navigation.icon}
                  navName={navigation.title}
                  page={navigation.href}
                />
              ))}
            </div>
          </div>

          <NavBar
            navName="Account"
            icon={navBarIcons.account}
            isAccount
            page="/account"
          />
        </div>
      </aside>
    </>
  );
}

export default SideBar;
