import { useEffect, useState } from "react";
import SignInModal from "../pages/SignInModal";
import RegisterModal from "../pages/RegisterModal";
import "./Header.css"; //
import { API_BASE_URL } from "../config/api.js";

export default function Header({ onToggleSidebar }) {
  const [showTopics, setShowTopics] = useState(false);
  const [showSignIn, setShowSignIn] = useState(false);
  const [showRegister, setShowRegister] = useState(false);
  const [user, setUser] = useState(null);

  useEffect(() => {
    const fetchCurrentUser = async () => {
      try {
        const res = await fetch(`${API_BASE_URL}/api/auth/me`, {
          credentials: "include", // send the cookie
        });
        if (res.ok) {
          const userData = await res.json();
          setUser(userData);
        } else {
          setUser(null);
        }
      } catch (err) {
        console.error("Failed to fetch user", err);
      }
    };
    fetchCurrentUser();
  }, []);

  const handleLogout = async () => {
    try {
      await fetch(`${API_BASE_URL}/api/auth/logout`, {
        method: "POST",
        credentials: "include",
      });
      setUser(null);
    } catch (err) {
      console.error("Logout failed", err);
    }
  };

  return (
    <header className="site-header">
      <button className="toggle-btn" onClick={onToggleSidebar}>
        ☰
      </button>
      <div className="header-container">
        <div className="logo">
          <a href="/">AltTabJournal</a>
        </div>

        <nav className="nav-links">
          <a href="/">Home</a>
          <div className="topics-dropdown">
            <button onClick={() => setShowTopics(!showTopics)}>Topics ▾</button>
            {showTopics && (
              <div className="dropdown-menu">
                <a href="#">Wellness</a>
                <a href="#">Gaming</a>
                <a href="#">Travel</a>
                <a href="#">Design</a>
              </div>
            )}
          </div>
          <a href="#">About</a>
          <a href="#">Profile</a>
        </nav>

        <div className="header-actions">
          <input
            type="text"
            placeholder="What are you looking for?"
            className="search-input"
          />
          {user ? (
            <>
              <span className="welcome-text">
                Welcome, {user.name || "User"}
              </span>
              <button onClick={handleLogout} className="auth-link">
                Logout
              </button>
            </>
          ) : (
            <>
              <button onClick={() => setShowSignIn(true)} className="auth-link">
                Sign In
              </button>
              <button
                onClick={() => setShowRegister(true)}
                className="register-btn"
              >
                Register
              </button>
            </>
          )}
        </div>
      </div>

      {showSignIn && (
        <SignInModal
          onClose={() => setShowSignIn(false)}
          onLoginSuccess={(userData) => {
            setUser(userData);
            setShowSignIn(false);
          }}
        />
      )}

      {showRegister && (
        <RegisterModal
          onClose={() => setShowRegister(false)}
          onRegisterSuccess={(userData) => {
            setUser(userData);
            setShowRegister(false);
          }}
        />
      )}
    </header>
  );
}
