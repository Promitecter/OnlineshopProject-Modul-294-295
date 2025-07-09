import '../styles/components/MainNavBar.css';
import { NavLink } from 'react-router-dom';

/* Hier wird die Hauptnavigation der Anwendung definiert. Wir müssen den NavLink nehmen,
um die Navigation zwischen den Seiten zu ermöglichen. mit isActive sagen wir, welche Seite gerade aktiv ist.
Das kann dann im CSS angesteuert werden um die aktive Seite hervorzuheben. */
export default function MainNavBar() {
  return (
    <nav className="main-navbar">
      <ul className="nav-list">
        <li>
          <NavLink
            to="/"
            /* Das end-Attribut sorgt dafür, dass der Link nur aktiv ist, wenn er exakt mit der URL übereinstimmt. */
            end
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