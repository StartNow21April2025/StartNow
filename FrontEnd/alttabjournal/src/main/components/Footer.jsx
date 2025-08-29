import "./Footer.css"; //

export default function Footer() {
  return (
    <footer className="site-footer">
      <div className="footer-container">
        <div className="footer-column">
          <h3 className="footer-title">AltTabJournal</h3>
          <p className="footer-tagline">
            Level up your knowledge, one scroll at a time.
          </p>
        </div>

        <div className="footer-column">
          <h4>Quick Links</h4>
          <ul>
            <li>
              <a href="/">Home</a>
            </li>
            <li>
              <a href="/topics">Topics</a>
            </li>
            <li>
              <a href="/about">About</a>
            </li>
          </ul>
        </div>

        <div className="footer-column">
          <h4>Follow Us</h4>
          <ul>
            <li>
              <a href="#">Twitter</a>
            </li>
            <li>
              <a href="#">YouTube</a>
            </li>
            <li>
              <a href="#">Discord</a>
            </li>
          </ul>
        </div>
      </div>

      <div className="footer-bottom">
        <p>© {new Date().getFullYear()} AltTabJournal. All rights reserved.</p>
      </div>
    </footer>
  );
}
