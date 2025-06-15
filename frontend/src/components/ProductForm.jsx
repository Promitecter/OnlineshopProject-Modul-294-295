import { useState } from 'react';

export default function ProductForm({ onAdd }) {
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState('');
  const [imageUrl, setImageUrl] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onAdd({ name, description, price: parseFloat(price), imageUrl });
    setName('');
    setDescription('');
    setPrice('');
    setImageUrl('');
  };

  return (
    <section className="add-section">
      <h2>Neues Produkt hinzufügen</h2>
      <form onSubmit={handleSubmit} className="add-form">
        <input
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
        />
        <textarea
          placeholder="Beschreibung"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />
        <input
          type="number"
          step="0.01"
          placeholder="Preis"
          value={price}
          onChange={(e) => setPrice(e.target.value)}
          required
        />
        <input
          placeholder="Bild-URL"
          value={imageUrl}
          onChange={(e) => setImageUrl(e.target.value)}
        />
        <button type="submit">Hinzufügen</button>
      </form>
    </section>
  );
}