import { render, screen, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import ProductForm from "../ProductForm";

beforeEach(() => {
  global.fetch = vi.fn((url) => {
    if (url.includes("/api/categories")) {
      return Promise.resolve({
        ok: true,
        json: async () => [
          { id: 1, name: "Helme" },
          { id: 2, name: "Bekleidung" },
        ],
      });
    }
    return Promise.resolve({
      ok: true,
      json: async () => ({
        id: 42,
        name: "NeuesProdukt",
        description: "Beschreibung",
        price: 19.95,
        imageUrl: "https://img",
        category: { id: 2 },
      }),
    });
  });
});

afterEach(() => {
  vi.resetAllMocks();
});

test("ProductForm: Felder ausfüllen und absenden ruft onSubmit mit korrektem Payload auf", async () => {
  const onSubmit = vi.fn();

  render(<ProductForm initialProduct={null} onSubmit={onSubmit} />);

  await waitFor(() => {
    expect(screen.getByRole("option", { name: "Helme" })).toBeInTheDocument();
  });

  await userEvent.selectOptions(screen.getByRole("combobox"), "2");

  await userEvent.type(screen.getByPlaceholderText("Name"), "NeuesProdukt");

  await userEvent.type(
    screen.getByPlaceholderText("Beschreibung (1-2000 Zeichen)"),
    "Beschreibung"
  );

  const priceInput = screen.getByPlaceholderText("Preis in CHF inkl. MWST");
  await userEvent.clear(priceInput);
  await userEvent.type(priceInput, "19.95");

  await userEvent.type(screen.getByPlaceholderText("Bild-URL"), "https://img");

  await userEvent.click(screen.getByRole("button", { name: /Hinzufügen/i }));

  await waitFor(() => {
    expect(onSubmit).toHaveBeenCalledWith(
      expect.objectContaining({
        id: 42,
        name: "NeuesProdukt",
        description: "Beschreibung",
        price: 19.95,
        imageUrl: "https://img",
        category: { id: 2 },
      })
    );
  });
});
