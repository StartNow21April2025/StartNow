import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import ArticleCard from './ArticleCard';

const LatestArticles = () => {
  const [articles, setArticles] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/articles')
      .then(res => res.json())
      .then(data => setArticles(data))
      .catch(err => console.error("Failed to fetch articles", err));
  }, []);

  return (
    <section className="container mx-auto mt-12 px-4">
      <h2 className="text-2xl font-bold text-green-400 mb-4">Latest Articles</h2>
      <div className="grid md:grid-cols-3 gap-6">
        {articles.length > 0 ? (
          articles.map((article, idx) => (
            <Link key={idx} to={`/blog/${article.slug}`}>
              <ArticleCard title={article.title} description={article.description} slug={article.slug} />
            </Link>
          ))
        ) : (
          <p className="text-gray-300">Loading articles...</p>
        )}
      </div>
    </section>
  );
};

export default LatestArticles;