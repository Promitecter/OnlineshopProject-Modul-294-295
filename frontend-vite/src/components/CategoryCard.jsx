import '../styles/components/CategoryCard.css';

/* Diese Komponente stellt eine Kategorie-Karte dar, die die Kategorieinformationen anzeigt
und Schaltflächen zum Bearbeiten und Löschen der Kategorie bereitstellt. Siehe die Admin Seite */
export default function CategoryCard({ category, onDelete, onEdit }) {
    return (
        <div className="category-card">
        <h2>{category.name}</h2>
        {/* Hier kommen die card-actions, heisst die Schaltflächen zum Bearbeiten und Löschen */}
        <div className="card-actions">
            {typeof onEdit === 'function' && (
            <button
                className="edit-button"
                onClick={() => onEdit(category)}
            >
                Bearbeiten
            </button>
            )}
            {typeof onDelete === 'function' && (
            <button
                className="delete-button"
                onClick={() => onDelete(category.id)}
            >
                Löschen
            </button>
            )}
        </div>
        </div>
    );
}