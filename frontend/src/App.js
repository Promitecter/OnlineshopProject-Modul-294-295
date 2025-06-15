import { useEffect, useState } from 'react';

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
      // Formular zurücksetzen und Liste neu laden
      setName(''); setDescription(''); setPrice(''); setImageUrl('');
      loadProducts();
    })
    .catch(err => console.error('Fehler beim Anlegen:', err));
  };

  if (loading) return <p>Lädt …</p>;

  return (
    <div style={{ padding: '2rem' }}>
      <h1>Produkte</h1>

      {/* === Formular zum Hinzufügen === */}
      <section style={{ marginBottom: '2rem' }}>
        <h2>Neues Produkt hinzufügen</h2>
        <form onSubmit={handleSubmit} style={{ display: 'grid', gap: '0.5rem', maxWidth: '400px' }}>
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

      {/* === Produktliste === */}
      {products.length === 0
        ? <p>Keine Produkte gefunden.</p>
        : <div style={{
            display: 'grid',
            gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
            gap: '1rem'
          }}>
            {products.map(p => (
              <div key={p.id} style={{
                border: '1px solid #ccc',
                borderRadius: '8px',
                padding: '1rem'
              }}>
                <img
                  src={p.imageUrl}
                  alt={p.name}
                  style={{ width: '100%', borderRadius: '4px' }}
                />
                <h2>{p.name}</h2>
                <p>{p.description}</p>
                <p style={{ fontWeight: 'bold' }}>{p.price.toFixed(2)} CHF</p>
              </div>
            ))}
          </div>
      }
    </div>
  );
}

export default App;