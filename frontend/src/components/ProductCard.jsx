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
      <h2 className="product-title">{product.name}</h2>
      <p>{product.description}</p>
      <p className="product-price">{product.price.toFixed(2)} CHF</p>
      <button
        className="delete-button"
        onClick={() => onDelete(product.id)}
      >
        Löschen
      </button>
    </div>
  );
}