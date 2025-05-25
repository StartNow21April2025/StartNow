import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import "./Blog.css";

const backgrounds = {
  "top-10-valorant-agents-for-rank-push": "/assets/images/background.jpg",
  default: "/assets/images/background.jpg", // Fallback background
};

const Blog = () => {
  const { slug } = useParams();
  const [article, setArticle] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch(`http://localhost:8080/api/articles/${slug}`)
      .then((res) => res.json())
      .then((data) => {
        setArticle(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Failed to fetch article:", err);
        setLoading(false);
      });
  }, [slug]);

  if (loading) {
    return <p className="text-gray-300 text-center mt-12">Loading...</p>;
  }

  if (!article || !article.fullContent) {
    return <p className="text-red-500 text-center mt-12">Article not found</p>;
  }

  const backgroundImage = backgrounds[slug] || backgrounds["default"];

  return (
    <div
      className="blog-container"
      style={{ backgroundImage: `url(${backgroundImage})` }}
    >
      <div className="container mx-auto px-4 py-8 max-w-3xl">
        <div
          className="blog-content"
          dangerouslySetInnerHTML={{ __html: article.fullContent }}
        />
      </div>
    </div>
  );
};

export default Blog;
