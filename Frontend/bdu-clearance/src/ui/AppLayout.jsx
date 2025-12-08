import { Outlet } from "react-router-dom";
import SideBar from "./SideBar";

function AppLayout() {
  return (
    <div className="max-w-full min-h-dvh">
      <SideBar />
      <div className="flex-1 lg:ml-60 ml-0 mx-auto">
        <Outlet />
      </div>
    </div>
  );
}

export default AppLayout;
