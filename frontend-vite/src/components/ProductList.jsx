import { useState } from 'react';
import '../styles/components/ProductList.css';
import ProductCard from './ProductCard';

export default function ProductList({
  products,
  categories,
  onDelete,
  onEdit
}) {
  // State für den aktuell gewählten Filter (null = alle)
  const [selectedCategoryId, setSelectedCategoryId] = useState(null);

  // Gefilterte Liste
  const filtered = selectedCategoryId
    ? products.filter(products => products.category?.id === selectedCategoryId)
    : products;

  return (
    <>
      {/* 1. Kategorie-Filter-Leiste */}
      <div className="category-filters">
        <button
          className={selectedCategoryId === null ? 'active' : ''}
          onClick={() => setSelectedCategoryId(null)}
        >
          Alle
        </button>
        {categories.map(categories => (
          <button
            key={categories.id}
            className={selectedCategoryId === categories.id ? 'active' : ''}
            onClick={() => setSelectedCategoryId(categories.id)}
          >
            {categories.name}
          </button>
        ))}
      </div>

      {/* 2. Produkt-Grid */}
      <div className="products-grid">
        {filtered.map(products => (
          <ProductCard
            key={products.id}
            product={products}
            onDelete={onDelete}
            onEdit={onEdit}
          />
        ))}
      </div>
    </>
  );
}