import { useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Home from "./pages/Home";
import Blog from "./blogs/Blog";
import Community from "./components/Community";
import Footer from "./components/Footer";
import Sidebar from "./components/Sidebar";

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(false);

  return (
    <Router>
      <Header onToggleSidebar={() => setSidebarOpen(true)} />
      <Sidebar isOpen={sidebarOpen} onClose={() => setSidebarOpen(false)} />
      <div className="page-wrapper">
        <div className="main-container">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/blog/:slug" element={<Blog />} />
            <Route path="/community" element={<Community />} />
          </Routes>
        </div>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
