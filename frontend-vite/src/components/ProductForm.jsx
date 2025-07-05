import '../styles/components/ProductForm.css';
import { useState, useEffect } from 'react';

export default function ProductForm({ initialProduct = null, onSubmit }) {
  const isEdit = Boolean(initialProduct);

  // Form-State
  const [name, setName]               = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice]             = useState('');
  const [imageUrl, setImageUrl]       = useState('');
  const [categoryId, setCategoryId]   = useState('');
  const [categories, setCategories]   = useState([]);

  // 1) Kategorien beim Mount laden
  // Der Ausdruck "useEffect" macht, dass dieser Code nur einmal beim ersten Rendern der Komponente ausgeführt wird.
  useEffect(() => {
    fetch('http://localhost:8080/api/categories')
      .then(res => res.json())
      .then(list => setCategories(list))
      .catch(err => console.error('Error loading categories', err));
  }, []);

  // 2) initialProduct in Form-State mappen
  useEffect(() => {
    if (isEdit) {
      setName(initialProduct.name);
      setDescription(initialProduct.description);
      setPrice(initialProduct.price);
      setImageUrl(initialProduct.imageUrl || '');
      setCategoryId(initialProduct.category?.id || '');
    } else {
      setName('');
      setDescription('');
      setPrice('');
      setImageUrl('');
      setCategoryId('');
    }
  }, [initialProduct, isEdit]);

  const handleSubmit = async e => {
    e.preventDefault();

    const parsedPrice = parseFloat(price);
    if (parsedPrice < 0) {
      alert('Der Preis muss positiv sein.');
      return;
    }
    if (!categoryId) {
      alert('Bitte eine Kategorie auswählen.');
      return;
    }

    const payload = {
      name,
      description,
      price: parsedPrice,
      imageUrl,
      category: { id: categoryId }
    };

    const url    = isEdit
      ? `http://localhost:8080/api/products/${initialProduct.id}`
      : 'http://localhost:8080/api/products';
    const method = isEdit ? 'PUT' : 'POST';

    const res = await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!res.ok) {
      const err = await res.json().catch(() => ({ message: 'Unknown error' }));
      console.error('Save failed', err);
      return;
    }
    const saved = await res.json();
    onSubmit(saved);

    if (!isEdit) {
      // nur beim Neuanlegen zurücksetzen
      setName('');
      setDescription('');
      setPrice('');
      setImageUrl('');
      setCategoryId('');
    }
  };

  return (
    <form className="product-form" onSubmit={handleSubmit}>
      <h2>{isEdit ? 'Produkt bearbeiten' : 'Neues Produkt anlegen'}</h2>

      <label className="category-select">
        <select
          value={categoryId}
          onChange={e => setCategoryId(parseInt(e.target.value, 10))} // parseInt für die ID und die 10 bedeutet, dass es eine Dezimalzahl ist
          required
        >
          <option value="">- Kategorie auswählen -</option>
          {categories.map(category => (
            <option key={category.id} value={category.id}>
              {category.name}
            </option>
          ))}
        </select>
      </label>

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
        step="0.05"
        min="0"
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