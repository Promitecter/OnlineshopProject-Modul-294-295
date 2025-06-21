import '../styles/components/ProductForm.css';
import { useState } from 'react';
// import './ProductForm.css'; // optional
// import '../App.css';

export default function ProductForm({ onCreated }) {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState('');
  const [imageUrl, setImageUrl] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    const newProduct = {
      name,
      description,
      price: parseFloat(price),
      imageUrl
    };
    fetch('http://localhost:8080/api/products', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newProduct)
    })
      .then(res => res.json())
      .then(created => {
        // Callback ans Eltern-Component
        if (typeof onCreated === 'function') {
          onCreated(created);
        }
        // Formular zurücksetzen
        setName('');
        setDescription('');
        setPrice('');
        setImageUrl('');
      })
      .catch(err => console.error('Fehler beim Anlegen:', err));
  };

  return (
    <form onSubmit={handleSubmit} className="product-form">
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
  );
}