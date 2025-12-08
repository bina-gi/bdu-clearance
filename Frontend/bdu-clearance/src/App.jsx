import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";

import AppLayout from "./ui/AppLayout";
import Home from "./pages/Home";
import RequestClearance from "./features/Student/RequestClearance";
import Search from "./features/Student/Search";

function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route element={<AppLayout />}>
            <Route path="/" element={<Home />} />
            <Route path="request-clearance" element={<RequestClearance />} />
            <Route path="search" element={<Search />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
