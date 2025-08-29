import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import JavaBlog from "./JavaBlog";
import ValorantBlog from "./ValorantBlog";
import { API_BASE_URL } from "../config/api.js";

export default function Blog() {
  const { slug } = useParams();
  const [article, setArticle] = useState(null);
  const [agents, setAgents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [notFound, setNotFound] = useState(false);

  const isValorantBlog = slug === "top-10-valorant-agents-for-rank-push";

  useEffect(() => {
    setLoading(true);
    setNotFound(false);

    if (isValorantBlog) {
      fetch(`${API_BASE_URL}/api/agents/all`)
        .then((res) => res.json())
        .then((data) => {
          const transformed = data.map((agent) => ({
            ...agent,
            strengths: agent.strengths?.split(",").map((s) => s.trim()) || [],
            weaknesses: agent.weaknesses?.split(",").map((w) => w.trim()) || [],
          }));
          setAgents(transformed);
        })
        .catch(() => setNotFound(true))
        .finally(() => setLoading(false));
    } else {
      fetch(`${API_BASE_URL}/api/articles/${slug}`)
        .then((res) => {
          if (!res.ok) throw new Error("Not found");
          return res.json();
        })
        .then((data) => setArticle(data))
        .catch(() => setNotFound(true))
        .finally(() => setLoading(false));
    }
  }, [slug]);

  if (loading)
    return <p className="text-center mt-12 text-gray-400">Loading...</p>;
  if (notFound)
    return <p className="text-center mt-12 text-red-400">Blog not found.</p>;

  if (article) return <JavaBlog blog={article} />;
  if (isValorantBlog) return <ValorantBlog agents={agents} />;

  return null;
}
