// src/components/TrendingSection.jsx
import { useEffect, useState } from "react";
import { fetchSectionContent } from "../api/homePageSectionContentApi";
import { defaultHomePageSectionContent } from "../model/defaultHomePageSectionContent";
import { Link } from "react-router-dom";
import "./TrendingSection.css"; // ← Import the CSS file

const tags = ["All", "Career", "Food", "Sports", "Gaming"];

export default function TrendingSection() {
  const [articles, setArticles] = useState([defaultHomePageSectionContent]);
  const [selectedTag, setSelectedTag] = useState("All");

  useEffect(() => {
    fetchSectionContent("trending").then(setArticles);
  }, []);

  const filtered =
    selectedTag === "All"
      ? articles
      : articles.filter((a) => a.tag === selectedTag);

  return (
    <section className="trending-section">
      <h2 className="trending-heading">Trending Now</h2>

      <div className="tag-filters">
        {tags.map((tag) => (
          <button
            key={tag}
            className={`tag-button ${selectedTag === tag ? "active-tag" : ""}`}
            onClick={() => setSelectedTag(tag)}
          >
            {tag}
          </button>
        ))}
      </div>

      <div className="trending-grid">
        {filtered.map((article, index) => (
          <Link
            to={`/blog/${article.slug}`}
            key={index}
            className="trending-card"
          >
            <img
              src={article.imageUrl}
              alt={article.title}
              className="trending-image"
            />
            <div className="trending-content">
              <span className="tag-label">{article.tag}</span>
              <h3 className="trending-title">{article.title}</h3>
              <p className="trending-description">{article.description}</p>
            </div>
          </Link>
        ))}
      </div>
    </section>
  );
}
