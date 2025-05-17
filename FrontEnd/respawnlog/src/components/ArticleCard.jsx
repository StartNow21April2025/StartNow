const ArticleCard = ({ title, description }) => (
  <div className="bg-gray-800 bg-opacity-80 p-4 rounded-xl">
    <h3 className="text-lg font-semibold text-white">{title}</h3>
    <p className="text-gray-400 mt-2">{description}</p>
  </div>
);

export default ArticleCard;