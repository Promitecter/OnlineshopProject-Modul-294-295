import '../styles/components/ProductList.css';
import ProductCard from './ProductCard';
//import './ProductList.css'; // optional
// import '../App.css';

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