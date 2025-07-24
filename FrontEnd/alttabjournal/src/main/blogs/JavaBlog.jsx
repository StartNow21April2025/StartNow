import { useState } from "react";
import "./JavaBlog.css"; // import your custom CSS file

const JavaBlog = ({ blog }) => {
  const [openSection, setOpenSection] = useState(null);

  return (
    <div className="java-blog-container">
      <h1 className="blog-title">{blog.title}</h1>
      <p className="blog-meta">
        By {blog.author} | {blog.date}
      </p>

      {/* Table of Contents */}
      <div className="blog-toc">
        <h2 className="toc-heading">📚 Table of Contents </h2>
        <ul className="toc-list">
          {blog.sections.map((section) => (
            <li key={section.id}>
              <a
                href={`#${section.id}`}
                onClick={() => setOpenSection(section.id)}
                className="toc-link"
              >
                {section.title}
              </a>
            </li>
          ))}
        </ul>
      </div>

      {/* Blog Sections */}
      {blog.sections.map((section) => (
        <div key={section.id} id={section.id} className="blog-section">
          <button
            className="section-toggle"
            onClick={() =>
              setOpenSection(openSection === section.id ? null : section.id)
            }
          >
            {section.title}
          </button>
          {openSection === section.id && (
            <p className="section-content">{section.content}</p>
          )}
        </div>
      ))}
    </div>
  );
};

export default JavaBlog;
