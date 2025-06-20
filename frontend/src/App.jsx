import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainNavBar  from './components/MainNavBar';
import Frontpage   from './sites/Frontpage';
import Admin       from './sites/Admin';
import About       from './sites/About';
import Contact     from './sites/Contact';

function App() {
  return (
    <BrowserRouter>
      <MainNavBar />

      <Routes>
        <Route path="/"        element={<Frontpage />} />
        <Route path="/admin"   element={<Admin />} />
        <Route path="/about"   element={<About />} />
        <Route path="/contact" element={<Contact />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;