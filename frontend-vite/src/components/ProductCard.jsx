import '../styles/components/ProductCard.css';

export default function ProductCard({ product, onDelete, onEdit }) {
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

      <div className="card-actions">
        {typeof onEdit === 'function' && (
          <button
            className="edit-button"
            onClick={() => onEdit(product)}
          >
            Bearbeiten
          </button>
        )}
        {typeof onDelete === 'function' && (
          <button
            className="delete-button"
            onClick={() => onDelete(product.id)}
          >
            Löschen
          </button>
        )}
      </div>
    </div>
  );
}