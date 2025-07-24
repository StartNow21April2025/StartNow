import "./Sidebar.css";

export default function Sidebar({ isOpen, onClose }) {
  return (
    <div
      className={`sidebar-overlay ${isOpen ? "visible" : ""}`}
      onClick={onClose}
    >
      <div className="sidebar" onClick={(e) => e.stopPropagation()}>
        <button className="close-btn" onClick={onClose}>
          ✕
        </button>
        <div className="sidebar-content">
          <a href="#">Dashboard</a>
          <a href="#">Bookmarks</a>
          <a href="#">Saved Posts</a>
          <a href="#">Settings</a>
        </div>
      </div>
    </div>
  );
}
