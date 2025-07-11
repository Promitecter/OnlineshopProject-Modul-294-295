import { Routes, Route, useLocation } from "react-router-dom";
import MainNavBar from "./components/MainNavBar";
import Footer from "./components/Footer";
import MainSlider from "./components/MainSlider";
import Frontpage from "./sites/Frontpage";
import Admin from "./sites/Admin";
import About from "./sites/About";
import Contact from "./sites/Contact";
import heroImage from "./assets/HeaderLogoReact.png";

export default function App() {
  const { pathname } = useLocation();

  return (
    <>
      <MainNavBar />
      {/* Slider nur auf "/" und außerhalb des .main-content */}
      {pathname === "/" && (
        <div className="slider-wrapper">
          <MainSlider
            imageUrl={heroImage}
            alt="Hero Image"
            caption="Unsere Highlights"
          />
        </div>
      )}
      <main className="main-content">
        <Routes>
          <Route path="/" element={<Frontpage />} />
          <Route path="/admin" element={<Admin />} />
          <Route path="/about" element={<About />} />
          <Route path="/contact" element={<Contact />} />
        </Routes>
      </main>
      <Footer />
    </>
  );
}
