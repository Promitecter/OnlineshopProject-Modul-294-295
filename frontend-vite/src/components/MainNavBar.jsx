import '../styles/components/MainNavBar.css';
import { NavLink } from 'react-router-dom';

export default function MainNavBar() {
  return (
    <nav className="main-navbar">
      <ul className="nav-list">
        <li>
          <NavLink
            to="/"
            className={({ isActive }) =>
            isActive ? 'nav-link active' : 'nav-link'
            }
          >
            Startseite
          </NavLink>
        </li>
        <li>
          <NavLink
            to="/admin"
            className={({ isActive }) =>
            isActive ? 'nav-link active' : 'nav-link'
            }
          >
            Admin
          </NavLink>
        </li>
        <li>
          <NavLink
            to="/about"
            className={({ isActive }) =>
            isActive ? 'nav-link active' : 'nav-link'
            }
          >
            Über uns
          </NavLink>
        </li>
        <li>
          <NavLink
            to="/contact"
            className={({ isActive }) =>
            isActive ? 'nav-link active' : 'nav-link'
            }
          >
            Kontakt
          </NavLink>
        </li>
      </ul>
    </nav>
  );
}