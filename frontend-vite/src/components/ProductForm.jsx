import "../styles/components/ProductForm.css";
import { useState, useEffect } from "react";

export default function ProductForm({ initialProduct = null, onSubmit }) {
  const isEdit = Boolean(initialProduct);

  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState("");
  const [imageUrl, setImageUrl] = useState("");
  const [categoryId, setCategoryId] = useState("");
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/categories")
      .then((res) => res.json())
      .then((list) => setCategories(list))
      .catch((err) => console.error("Error loading categories", err));
  }, []);

  useEffect(() => {
    if (isEdit) {
      setName(initialProduct.name);
      setDescription(initialProduct.description);
      setPrice(initialProduct.price);
      setImageUrl(initialProduct.imageUrl || "");
      setCategoryId(initialProduct.category?.id || "");
    } else {
      setName("");
      setDescription("");
      setPrice("");
      setImageUrl("");
      setCategoryId("");
    }
  }, [initialProduct, isEdit]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const parsedPrice = parseFloat(price);
    if (parsedPrice < 0) {
      alert("Der Preis muss positiv sein.");
      return;
    }
    if (!categoryId) {
      alert("Bitte eine Kategorie auswählen.");
      return;
    }

    const payload = {
      name,
      description,
      price: parsedPrice,
      imageUrl,
      category: { id: categoryId },
    };

    const url = isEdit
      ? `http://localhost:8080/api/products/${initialProduct.id}`
      : "http://localhost:8080/api/products";
    const method = isEdit ? "PUT" : "POST";

    let res = null;
    try {
      res = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      const saved = await res.json();

      onSubmit(saved);
      if (!isEdit) {
        setName("");
        setDescription("");
        setPrice("");
        setImageUrl("");
        setCategoryId("");
      }
    } catch (error) {
      console.error("Network error", error);
      alert("Fehler beim Speichern des Produkts");
      return;
    }
  };

  return (
    <form className="product-form" onSubmit={handleSubmit}>
      <h2>{isEdit ? "Produkt bearbeiten" : "Neues Produkt anlegen"}</h2>

      <label className="category-select">
        <select
          value={categoryId}
          onChange={(e) => setCategoryId(parseInt(e.target.value, 10))}
          required
        >
          <option value="">- Kategorie auswählen -</option>
          {categories.map((category) => (
            <option key={category.id} value={category.id}>
              {category.name}
            </option>
          ))}
        </select>
      </label>

      <input
        placeholder="Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
        required
        minLength={1}
        maxLength={100}
      />

      <textarea
        placeholder="Beschreibung (1-2000 Zeichen)"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        minLength={1}
        maxLength={2000}
        required
      />

      <input
        type="number"
        step="0.05"
        min="0"
        placeholder="Preis in CHF inkl. MWST"
        value={price}
        onChange={(e) => setPrice(e.target.value)}
        required
      />

      <input
        placeholder="Bild-URL"
        value={imageUrl}
        onChange={(e) => setImageUrl(e.target.value)}
      />

      <button type="submit">{isEdit ? "Speichern" : "Hinzufügen"}</button>
    </form>
  );
}
