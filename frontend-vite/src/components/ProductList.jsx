import '../styles/components/ProductList.css';
import ProductCard from './ProductCard';

export default function ProductList({ products, onDelete, onEdit }) {
  return (
    <div className="products-grid">
      {products.map(p => (
        <ProductCard
          key={p.id}
          product={p}
          onDelete={onDelete}
          onEdit={onEdit}
        />
      ))}
    </div>
  );
}