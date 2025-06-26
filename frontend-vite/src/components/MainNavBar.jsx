import '../styles/components/MainNavBar.css';
import { Link } from 'react-router-dom';

export default function MainNavBar() {
  return (
    <nav className="main-navbar">
      <ul className="nav-list">
        <li><Link to="/">Startseite</Link></li>
        <li><Link to="/admin">Admin</Link></li>
        <li><Link to="/about">Über uns</Link></li>
        <li><Link to="/contact">Kontakt</Link></li>
      </ul>
    </nav>
  );
}