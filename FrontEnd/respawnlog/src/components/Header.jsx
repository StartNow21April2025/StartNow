const Header = () => (
  <header className="bg-gray-800 bg-opacity-90 p-6 shadow-md">
    <div className="container mx-auto flex justify-between items-center">
      <h1 className="text-2xl font-bold text-green-400">RespawnLog</h1>
      <nav className="space-x-4">
        <a href="#" className="hover:text-green-400">Home</a>
        <a href="#" className="hover:text-green-400">Reviews</a>
        <a href="#" className="hover:text-green-400">Guides</a>
        <a href="#" className="hover:text-green-400">News</a>
      </nav>
    </div>
  </header>
);

export default Header;