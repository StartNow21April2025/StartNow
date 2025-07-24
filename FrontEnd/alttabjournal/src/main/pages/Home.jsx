// src/pages/Home.jsx
import HeroSection from "../components/HeroSection";
import TrendingSection from "../components/TrendingSection";
// import other sections when ready...

export default function Home() {
  return (
    <div>
      <HeroSection />
      <TrendingSection />
      {/* Add other sections like <TrendingSection />, etc. */}
    </div>
  );
}
