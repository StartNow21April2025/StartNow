import { useEffect, useState } from "react";
import { fetchSectionContent } from "../api/homePageSectionContentApi";
import { defaultHomePageSectionContent } from "../model/defaultHomePageSectionContent";
import { Link } from "react-router-dom";
import "./HeroSection.css";

export default function HeroSection() {
  const [slides, setSlides] = useState([defaultHomePageSectionContent]);
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    fetchSectionContent("hero").then(setSlides);
  }, []);

  const prevSlide = () => {
    setCurrentIndex((prev) => (prev === 0 ? slides.length - 1 : prev - 1));
  };

  const nextSlide = () => {
    setCurrentIndex((prev) => (prev === slides.length - 1 ? 0 : prev + 1));
  };

  if (!slides.length) return <div>Loading...</div>;

  const current = slides[currentIndex];

  return (
    <div className="hero-section">
      <h2 className="hero-title">Best of the week...</h2>
      <div className="hero-wrapper">
        <Link to={`/blog/${current.slug}`}>
          <img
            src={current.imageUrl}
            alt={current.title}
            className="hero-image"
          />

          <div className="hero-overlay">
            <span className="hero-tag">{current.tag}</span>
            <h3 className="hero-heading">{current.title}</h3>
            <p className="hero-meta">
              {current.author} · {current.date}
            </p>
          </div>
        </Link>
        <button onClick={prevSlide} className="hero-arrow left">
          &#8592;
        </button>
        <button onClick={nextSlide} className="hero-arrow right">
          &#8594;
        </button>
      </div>
    </div>
  );
}
