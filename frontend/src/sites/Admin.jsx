import '../styles/pages/Admin.css';
import { useEffect, useState } from 'react';
import ProductList from '../components/ProductList';
import ProductForm from '../components/ProductForm';
// import '../App.css';

export default function Admin() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading]   = useState(true);

  const loadProducts = () => {
    setLoading(true);
    fetch('http://localhost:8080/api/products')
      .then(res => res.json())
      .then(data => {
        setProducts(data);
        setLoading(false);
      })
      .catch(err => {
        console.error('Fehler beim Laden:', err);
        setLoading(false);
      });
  };

  useEffect(() => {
    loadProducts();
  }, []);

  const handleDelete = (id) => {
    fetch(`http://localhost:8080/api/products/${id}`, { method: 'DELETE' })
      .then(() => loadProducts())
      .catch(err => console.error('Fehler beim Löschen:', err));
  };

  const handleCreated = () => {
    loadProducts();
  };

  if (loading) return <p>Lädt …</p>;

  return (
    <div className="admin">
      <h1>Admin-Bereich: Produkte verwalten</h1>
      <ProductForm onCreated={handleCreated} />
      <ProductList products={products} onDelete={handleDelete} />
    </div>
  );
}