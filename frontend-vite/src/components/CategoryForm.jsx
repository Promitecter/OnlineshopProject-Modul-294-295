import '../styles/components/CategoryForm.css';
import { useState, useEffect } from 'react';

export default function CategoryForm({ initialCategory = null, onSubmit }) {
    const isEdit = Boolean(initialCategory);

    // Form-State
    const [name, setName] = useState('');

    // 1) initialCategory in Form-State mappen
    useEffect(() => {
    if (isEdit) {
        setName(initialCategory.name);
    } else {
        setName('');
    }
    }, [initialCategory, isEdit]);

    const handleSubmit = async e => {
    e.preventDefault();

    const payload = {
        name
    };

    const url = isEdit
        ? `http://localhost:8080/api/categories/${initialCategory.id}`
        : 'http://localhost:8080/api/categories';
    const method = isEdit ? 'PUT' : 'POST';

    const res = await fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });

    if (!res.ok) {
        const text = await res.text();
        console.error('Save failed', text);
        alert(`Fehler beim Speichern, da der Name bereits existiert. Bitte einen anderen Namen wählen.`);
        return;
    }
    const saved = await res.json();
    alert(`Kategorie ${isEdit ? 'bearbeitet' : 'angelegt'}: ${saved.name}`);
    onSubmit(saved);
    };

    return (
    <form className="category-form" onSubmit={handleSubmit}>
        <h2>{isEdit ? 'Kategorie bearbeiten' : 'Neue Kategorie anlegen'}</h2>
        <input
            placeholder="Kategoriename"
            value={name}
            onChange={e => setName(e.target.value)}
            required
        />
        <button type="submit">{isEdit ? 'Speichern' : 'Hinzufügen'}</button>
    </form>
    );
}