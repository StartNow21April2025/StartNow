import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import FeaturedPost from "./components/FeaturedPost";
import LatestArticles from "./components/LatestArticles";
import Footer from "./components/Footer";
import Blog from "./components/Blog"; // New Blog Component
import Community from './components/Community';

const App = () => (
  <Router>
    <div
      className="font-sans min-h-screen bg-cover bg-center"
      style={{
        backgroundImage:
          "url('https://images.pexels.com/photos/3762956/pexels-photo-3762956.jpeg')",
      }}
    >
      <div className="bg-black bg-opacity-70 min-h-screen">
        <Header />
        <Routes>
          <Route
            path="/"
            element={
              <>
                <FeaturedPost />
                <LatestArticles />
              </>
            }
          />
          <Route path="/blog/:slug" element={<Blog />} />
          <Route path="/community" element={<Community />} />
        </Routes>
        <Footer />
      </div>
    </div>
  </Router>
);

export default App;
