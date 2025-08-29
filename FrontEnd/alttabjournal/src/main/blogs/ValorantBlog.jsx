import "./ValorantBlog.css";

export default function ValorantBlog({ agents }) {
  return (
    <div className="val-blog-container">
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
            potential like Reyna, Phoenix, and Raze. But controllers like Clove
            and Brimstone offer strong map control, while supports like Sage can
            swing rounds in your favor. Ultimately, the best agent is the one
            that suits your playstyle and helps your team secure wins.
          </p>
        </section>
      </main>
    </div>
  );
}
