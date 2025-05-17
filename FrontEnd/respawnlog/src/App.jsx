import Header from './components/Header';
import FeaturedPost from './components/FeaturedPost';
import LatestArticles from './components/LatestArticles';
import Footer from './components/Footer';

const App = () => (
  <div className="font-sans min-h-screen bg-cover bg-center" style={{ backgroundImage: "url('https://images.pexels.com/photos/3762956/pexels-photo-3762956.jpeg')" }}>
    <div className="bg-black bg-opacity-70 min-h-screen">
      <Header />
      <FeaturedPost />
      <LatestArticles />
      <Footer />
    </div>
  </div>
);

export default App;