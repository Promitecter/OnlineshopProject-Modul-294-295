import '../styles/components/CategoryList.css';
import CategoryCard from './CategoryCard';

/* Diese Komponente stellt eine Liste von Kategorien dar.
    Sie zeigt alle vorhandenen Kategorien in einem Grid-Layout an. */
    /* categories.map sagt aus, dass für jede Kategorie in der Liste ein CategoryCard-Element gerendert wird. */
export default function CategoryList({ categories, onDelete, onEdit }) {
    return (
        <div className="categories-grid">
        {categories.map(c => (
            <CategoryCard
            key={c.id}
            category={c}
            onDelete={onDelete}
            onEdit={onEdit}
            />
        ))}
        </div>
    );
}