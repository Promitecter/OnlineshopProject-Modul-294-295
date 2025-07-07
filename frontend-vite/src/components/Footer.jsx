import '../styles/components/Footer.css';

export default function Footer() {
    return (
        <footer className="main-footer">
        <p>© {new Date().getFullYear()} Mein Onlineshop. Alle Rechte vorbehalten.</p>
        </footer>
    );
}