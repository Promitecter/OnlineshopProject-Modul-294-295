// Dieser Test prüft die Funktionalität der ProductCard-Komponente,
// insbesondere ob sie die Produktinformationen korrekt anzeigt und die Callback-Funktionen für Löschen und Bearbeiten aufruft.
// Er verwendet React Testing Library, um die Komponente zu rendern und Interaktionen zu simulieren.
// Die Komponente wird mit einem Beispielprodukt gerendert, und es wird überprüft, ob die richtigen Informationen angezeigt werden.
import { render, screen, fireEvent } from '@testing-library/react';
import ProductCard from '../ProductCard';

describe('ProductCard', () => {
    const product = {
        id: 1,
        name: 'Testprodukt',
        description: 'Eine kurze Beschreibung',
        price: 12.35,
        imageUrl: 'https://example.com/image.jpg',
        category: { id: 1, name: 'Helme' }
    };

test('zeigt Name, Beschreibung und Preis an und ruft onDelete/onEdit auf', () => {
    const onDelete = vi.fn();
    const onEdit   = vi.fn();

    render(
        <ProductCard
            product={product}
            onDelete={onDelete}
            onEdit={onEdit}
        />
    );

    // Inhalte gerendert?
    expect(screen.getByText('Testprodukt')).toBeInTheDocument();
    expect(screen.getByText('Eine kurze Beschreibung')).toBeInTheDocument();
    expect(screen.getByText('CHF 12.35')).toBeInTheDocument();

    // Löschen-Button klickt und Callback aufrufen
    fireEvent.click(screen.getByText('Löschen'));
    expect(onDelete).toHaveBeenCalledWith(1);

    // 3) Bearbeiten-Button klickt und Callback aufrufen
    fireEvent.click(screen.getByText('Bearbeiten'));
    expect(onEdit).toHaveBeenCalledWith(product);
    });
});