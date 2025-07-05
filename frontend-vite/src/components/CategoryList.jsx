import '../styles/components/CategoryList.css';
import CategoryCard from './CategoryCard';

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