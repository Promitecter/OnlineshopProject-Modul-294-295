import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import ProductForm from '../ProductForm';

beforeEach(() => {
    // Mock für fetch, damit keine echten Netzwerk-Requests gemacht werden
    global.fetch = vi.fn((url) => {
        // Kategorien-Request
        if (url.includes('/api/categories')) {
            return Promise.resolve({
                ok: true,
                json: async () => [
                    { id: 1, name: 'Helme' },
                    { id: 2, name: 'Bekleidung' }
                ]
            });
        }
        // Produkt-POST-Request
        return Promise.resolve({
            ok: true,
            json: async () => ({
                id: 42,
                name: 'NeuesProdukt',
                description: 'Beschreibung',
                price: 19.95,
                imageUrl: 'https://img',
                category: { id: 2 }
            })
        });
    });
});

afterEach(() => {
    vi.resetAllMocks();
});

test('ProductForm: Felder ausfüllen und absenden ruft onSubmit mit korrektem Payload auf', async () => {
    // Wir erstellen eine Mock-Funktion, um zu prüfen, ob onSubmit aufgerufen wird
    const onSubmit = vi.fn();

    // Das Formular wird im Create-Modus gerendert
    render(<ProductForm initialProduct={null} onSubmit={onSubmit} />);

    // Warte, bis die Kategorien geladen sind
    await waitFor(() => {
        expect(screen.getByRole('option', { name: 'Helme' })).toBeInTheDocument();
    });

    // Kategorie auswählen (z.B. "Bekleidung" mit id 2)
    await userEvent.selectOptions(screen.getByRole('combobox'), '2');

    // Name eingeben
    await userEvent.type(screen.getByPlaceholderText('Name'), 'NeuesProdukt');

    // Beschreibung eingeben
    await userEvent.type(screen.getByPlaceholderText('Beschreibung (1-2000 Zeichen)'), 'Beschreibung');

    // Preis eingeben
    const priceInput = screen.getByPlaceholderText('Preis in CHF inkl. MWST');
    await userEvent.clear(priceInput);
    await userEvent.type(priceInput, '19.95');

    // Bild-URL eingeben
    await userEvent.type(screen.getByPlaceholderText('Bild-URL'), 'https://img');

    // Formular absenden
    await userEvent.click(screen.getByRole('button', { name: /Hinzufügen/i }));

    // Prüfen, ob onSubmit mit dem richtigen Wert aufgerufen wurde
    await waitFor(() => {
        expect(onSubmit).toHaveBeenCalledWith(
            expect.objectContaining({
                id: 42,
                name: 'NeuesProdukt',
                description: 'Beschreibung',
                price: 19.95,
                imageUrl: 'https://img',
                category: { id: 2 }
            })
        );
    });
});