import { Routes, Route } from 'react-router-dom';
import MainNavBar from './components/MainNavBar';
import Footer from './components/Footer';
import Frontpage  from './sites/Frontpage';
import Admin      from './sites/Admin';
import About      from './sites/About';
import Contact    from './sites/Contact';

/* Haupt-App-Komponente, dient als zentraler Ort für die Navigation und das Routing.
Der Footer wird hier ebenfalls eingebunden, aber nicht im main-content, da er nicht im Hauptcontainer sein soll. */
export default function App() {
  return (
    <>
      <MainNavBar />
      <main className="main-content">
        <Routes>
          <Route path="/"        element={<Frontpage />} />
          <Route path="/admin"   element={<Admin />} />
          <Route path="/about"   element={<About />} />
          <Route path="/contact" element={<Contact />} />
        </Routes>
      </main>
      <Footer />
    </>
  );
}