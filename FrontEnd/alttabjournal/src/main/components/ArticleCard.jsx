import { Link } from "react-router-dom";

const ArticleCard = ({ title, description, slug }) => (
  <Link to={`/blog/${slug}`}>
    <div className="bg-gray-800 bg-opacity-80 p-4 rounded-xl cursor-pointer hover:bg-gray-700 transition">
      <h3 className="text-lg font-semibold text-white">{title}</h3>
      <p className="text-gray-400 mt-2">{description}</p>
    </div>
  </Link>
);

export default ArticleCard;