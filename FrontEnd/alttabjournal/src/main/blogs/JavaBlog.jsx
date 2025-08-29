import React from "react";
import "./JavaBlog.css";

const BlogPage = ({ blog }) => {
  return (
    <div className="java-blog-container">
      {/* Title */}
      <h1 className="blog-title">{blog.title}</h1>

      {/* Meta */}
      <p className="blog-meta">
        By {blog.author} • {blog.date}
      </p>

      {/* Sections */}
      {blog.sections.map((section, sectionIndex) => (
        <div key={section.id || sectionIndex} className="blog-section">
          {/* Section Title */}
          <h2 className="section-title">
            {sectionIndex + 1}. {section.title}
          </h2>

          {/* Section Content */}
          {section.content.map((block, i) => {
            switch (block.type) {
              case "paragraph":
                return block.text ? (
                  <p key={i} className="section-paragraph">
                    {block.text}
                  </p>
                ) : null;

              case "list":
                return block.items?.length > 0 ? (
                  <ul key={i} className="section-list">
                    {block.items.map((item, idx) => (
                      <li key={idx}>{item}</li>
                    ))}
                  </ul>
                ) : null;

              case "quote":
                return block.text ? (
                  <blockquote key={i} className="section-quote">
                    {block.text}
                  </blockquote>
                ) : null;

              case "code":
                return block.text ? (
                  <pre key={i} className="section-code">
                    <code>{block.text}</code>
                  </pre>
                ) : null;

              case "image":
                return block.src ? (
                  <div key={i} className="section-image">
                    <img src={block.src} alt="" />
                  </div>
                ) : null;

              case "divider":
                return <hr key={i} className="section-divider" />;

              default:
                return null;
            }
          })}
        </div>
      ))}
    </div>
  );
};

export default BlogPage;
