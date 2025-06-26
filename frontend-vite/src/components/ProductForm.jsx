import '../styles/components/ProductForm.css';
import { useState, useEffect } from 'react';

export default function ProductForm({ initialProduct = null, onSubmit }) {
  const isEdit = Boolean(initialProduct);
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState('');
  const [imageUrl, setImageUrl] = useState('');

  useEffect(() => {
    if (isEdit) {
      setName(initialProduct.name);
      setDescription(initialProduct.description);
      setPrice(initialProduct.price);
      setImageUrl(initialProduct.imageUrl || '');
    } else {

      setName('');
      setDescription('');
      setPrice('');
      setImageUrl('');
    }
  }, [initialProduct]);

  const handleSubmit = async e => {
    e.preventDefault();

    const payload = {
      name,
      description,
      price: parseFloat(price),
      imageUrl
    };

    const url    = isEdit
      ? `http://localhost:8080/api/products/${initialProduct.id}`
      : `http://localhost:8080/api/products`;
    const method = isEdit ? 'PUT' : 'POST';

    const res = await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!res.ok) {
      console.error('Save failed', await res.text());
      return;
    }
    const saved = await res.json();

    onSubmit(saved);

    if (!isEdit) {
      setName(''); setDescription(''); setPrice(''); setImageUrl('');
    }
  };

  return (
    <form className='product-form' onSubmit={handleSubmit}>
      <h2>{isEdit ? 'Produkt bearbeiten' : 'Neues Produkt anlegen'}</h2>
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
      <button type="submit">{isEdit ? 'Speichern' : 'Hinzufügen'}</button>
    </form>
  );
}