// import './ProductCard.css';
// import '../App.css';
import '../styles/components/ProductCard.css';

export default function ProductCard({ product, onDelete }) {
  return (
    <div className="product-card">
      {product.imageUrl && (
        <img
          src={product.imageUrl}
          alt={product.name}
          className="product-image"
        />
      )}
      <h2>{product.name}</h2>
      <p>{product.description}</p>
      <p className="product-price">CHF {product.price.toFixed(2)}</p>

      {typeof onDelete === 'function' && (
        <button
          className="delete-button"
          onClick={() => onDelete(product.id)}
        >
          Löschen
        </button>
      )}
    </div>
  );
}