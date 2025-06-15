import { useEffect, useState } from 'react';
import './App.css';

function App() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);

  // Formular-States
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState('');
  const [imageUrl, setImageUrl] = useState('');

  // Produkte laden
  const loadProducts = () => {
    setLoading(true);
    fetch('http://localhost:8080/api/products')
      .then(res => res.json())
      .then(data => {
        setProducts(data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setLoading(false);
      });
  };

  useEffect(() => {
    loadProducts();
  }, []);

  // Formular-Handler
  const handleSubmit = (e) => {
    e.preventDefault();
    const newProduct = { name, description, price: parseFloat(price), imageUrl };
    fetch('http://localhost:8080/api/products', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newProduct)
    })
    .then(res => res.json())
    .then(created => {
      setName('');
      setDescription('');
      setPrice('');
      setImageUrl('');
      loadProducts();
    })
    .catch(err => console.error('Fehler beim Anlegen:', err));
  };

  if (loading) return <p className="loading">Lädt …</p>;

  return (
    <div className="container">
      <h1>Produkte</h1>

      <section className="add-section">
        <h2>Neues Produkt hinzufügen</h2>
        <form onSubmit={handleSubmit} className="add-form">
          <input
            placeholder="Name"
            value={name}
            onChange={e => setName(e.target.value)}
            required
          />
          <textarea
            placeholder="Beschreibung"
            value={description}
            onChange={e => setDescription(e.target.value)}
          />
          <input
            type="number"
            step="0.01"
            placeholder="Preis"
            value={price}
            onChange={e => setPrice(e.target.value)}
            required
          />
          <input
            placeholder="Bild-URL"
            value={imageUrl}
            onChange={e => setImageUrl(e.target.value)}
          />
          <button type="submit">Hinzufügen</button>
        </form>
      </section>

      {products.length === 0 ? (
        <p className="no-products">Keine Produkte gefunden.</p>
      ) : (
        <div className="products-grid">
          {products.map(p => (
            <div key={p.id} className="product-card">
              <img src={p.imageUrl} alt={p.name} className="product-image" />
              <h2 className="product-title">{p.name}</h2>
              <p>{p.description}</p>
              <p className="product-price">{p.price.toFixed(2)} CHF</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default App;