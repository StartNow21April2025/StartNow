const Header = () => (
  <header className="bg-gray-800 bg-opacity-90 p-6 shadow-md">
    <div className="container mx-auto flex justify-between items-center">
      <h1 className="text-2xl font-bold text-green-400 hover:text-white">
        <a href="/">AltTabJournal</a>
      </h1>
      <nav className="space-x-4">
        <a href="/" className="text-green-400 hover:text-white">
          Home
        </a>
        <a href="#" className="text-green-400 hover:text-white">
          Reviews
        </a>
        <a href="#" className="text-green-400 hover:text-white">
          Guides
        </a>
        <a href="#" className="text-green-400 hover:text-white">
          News
        </a>
        <a href="/community" className="text-green-400 hover:text-white">
          Community
        </a>
      </nav>
    </div>
  </header>
);

export default Header;
