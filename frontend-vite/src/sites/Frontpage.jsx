import '../styles/pages/Frontpage.css';
import { useEffect, useState } from 'react';
import ProductList from '../components/ProductList';

export default function Frontpage() {
  const [products,    setProducts]    = useState([]);
  const [categories,  setCategories]  = useState([]);
  const [loading,     setLoading]     = useState(true);
  const [error,       setError]       = useState(null);

  useEffect(() => {
    /* Kategorien und Produkte parallel laden, heisst, dass wir beide Fetch-Anfragen gleichzeitig ausführen.
    Vorteil: Schnellere Ladezeiten, da wir nicht auf die eine oder andere Anfrage warten müssen.
    Nachteil: Komplexere Fehlerbehandlung, da wir die Fehler beider Anfragen berücksichtigen müssen. */
    Promise.all([
      fetch('http://localhost:8080/api/products').then(res => res.json()),
      fetch('http://localhost:8080/api/categories').then(res => res.json()),
    ])
      .then(([prods, cats]) => {
        setProducts(prods);
        setCategories(cats);
        setLoading(false);
      })
      .catch(err => {
        console.error('Fehler beim Laden:', err);
        setError('Daten konnten nicht geladen werden.');
        setLoading(false);
      });
  }, []);

  if (loading) return <p>Lädt …</p>;
  if (error)   return <p className="error">{error}</p>;

  return (
    <div className="frontpage">
      <h1>Unsere Produkte direkt vom Bauernhof</h1>
      <ProductList
        products={products}
        categories={categories}
      />
    </div>
  );
}