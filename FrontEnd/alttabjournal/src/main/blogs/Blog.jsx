import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import JavaBlog from "./JavaBlog"; // Generic blog component
import "./Blog.css";

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
      // Special case for Valorant blog
      fetch("http://localhost:8080/api/agents/all")
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
      // Dynamic blog article
      fetch(`http://localhost:8080/api/articles/${slug}`)
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

  // Render dynamic article
  if (article) return <JavaBlog blog={article} />;

  // Render Valorant blog
  if (isValorantBlog) {
    return (
      <div className="blog-container">
        <header className="banner mb-10 text-center">
          <h1 className="glow-text text-3xl font-bold mb-2">
            ⚡ Top 10 Valorant Agents for Rank Push ⚡
          </h1>
          <p className="banner-subtitle text-lg text-gray-300">
            Dominate Ranked. Master Your Agent. Crush Your Opponents.
          </p>
        </header>

        <main className="blog-content space-y-10">
          {agents.map((agent, idx) => (
            <div key={idx} className="agent">
              <img src={agent.imageUrl} alt={agent.name} />
              <h2>{agent.title}</h2>
              <blockquote>“{agent.quote}”</blockquote>
              <p>{agent.description}</p>
              <ul>
                <li>
                  🟢 <strong>Strengths:</strong> {agent.strengths.join(", ")}
                </li>
                <li>
                  🔴 <strong>Weaknesses:</strong> {agent.weaknesses.join(", ")}
                </li>
              </ul>
            </div>
          ))}

          <section className="mt-10 border-t border-gray-600 pt-6">
            <h2>🏆 Final Thoughts</h2>
            <p>
              If you’re aiming to rank up fast, focus on agents with solo carry
              potential like Reyna, Phoenix, and Raze. But controllers like
              Clove and Brimstone offer strong map control, while supports like
              Sage can swing rounds in your favor. Ultimately, the best agent is
              the one that suits your playstyle and helps your team secure wins.
            </p>
          </section>
        </main>
      </div>
    );
  }

  return null; // Fallback (should not reach here)
}
