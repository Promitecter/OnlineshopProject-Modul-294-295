import "../styles/components/CategoryForm.css";
import { useState, useEffect } from "react";

/* Diese Komponente stellt ein Formular zum Erstellen und Bearbeiten von Kategorien bereit.
Sowohl für die Erstellung neuer Kategorien als auch für die Bearbeitung bestehender Kategorien kann dieses Formular verwendet werden. */
export default function CategoryForm({ initialCategory = null, onSubmit }) {
  const isEdit =
    Boolean(
      initialCategory
    ); /* Dieser Boolean zeigt an, ob wir eine Kategorie bearbeiten, oder eine neue Kategorie anlegen. */

  /* useState heisst, dass wir einen Zustand für den Namen der Kategorie erstellen. Genauer gesagt, wir erstellen eine Variable "name" und eine Funktion "setName", um den Wert von "name" zu aktualisieren. */
  const [name, setName] = useState("");

  /* useEffect wird verwendet, um den Zustand des Formulars zu aktualisieren, wenn sich die initialCategory ändert.
    Wenn wir eine Kategorie bearbeiten, setzen wir den Namen auf den Namen der initialCategory.
    Wenn wir eine neue Kategorie anlegen, setzen wir den Namen auf einen leeren String. */
  useEffect(() => {
    if (isEdit) {
      setName(initialCategory.name);
    } else {
      setName("");
    }
  }, [initialCategory, isEdit]);

  /* Hier wird die Formularübermittlung behandelt. Asynchron bedeutet, dass wir auf die Antwort des Servers warten, bevor wir fortfahren.
    Genauer gesagt, wir warten auf die Antwort des Fetch-Requests. */
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!name.trim()) {
      /* Überprüfung auf leeren Kategorienamen, beispielsweise mit einem Leerschlag. */
      alert("Bitte einen gültigen Kategorienamen eingeben.");
      return;
    }

    /* Hier erstellen wir das Payload-Objekt, das die Daten enthält, die wir an den Server senden möchten.
    Der Payload ist nötig, da die Daten im JSON-Format gesendet werden müssen und wir sicherstellen müssen, dass die Struktur korrekt ist. */
    const payload = {
      name,
    };

    /* Hier wird die URL und die HTTP-Methode festgelegt, je nachdem, ob wir eine Kategorie bearbeiten oder eine neue Kategorie anlegen. */
    /* Wenn wir eine Kategorie bearbeiten, verwenden wir die PUT-Methode und die ID der Kategorie */
    /* ? und : ist eine Schreibweise, für ein if-else Statement */
    const url = isEdit
      ? `http://localhost:8080/api/categories/${initialCategory.id}`
      : "http://localhost:8080/api/categories";
    const method = isEdit ? "PUT" : "POST";

    /* Hier wird der Fetch-Request ausgeführt, um die Daten an den Server zu senden. */
    /* Wir verwenden die URL, die Methode und die Header, um den Server zu informieren, dass wir JSON-Daten senden. */
    /* Der Body enthält die Daten, die wir senden möchten, und wir wandeln das Payload-Objekt in einen JSON-String um. */
    /* Wenn der Request fehlschlägt, wird eine Fehlermeldung angezeigt. */
    /* Wenn der Request erfolgreich ist, wird die Antwort als JSON geparst und eine Erfolgsmeldung angezeigt. */
    /* onSubmit ist eine Callback-Funktion, die aufgerufen wird, wenn das Formular erfolgreich übermittelt wurde. */
    const res = await fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });

    if (!res.ok) {
      const text = await res.text();
      console.error("Save failed", text);
      alert(
        `Fehler beim Speichern, da der Name bereits existiert. Bitte einen anderen Namen wählen.`
      );
      return;
    }
    const saved = await res.json();
    alert(`Kategorie ${isEdit ? "bearbeitet" : "angelegt"}: ${saved.name}`);
    onSubmit(saved);
  };

  /* Hier wird das Formular, gemäss der Logik, gerendert. */
  return (
    <form className="category-form" onSubmit={handleSubmit}>
      <h2>{isEdit ? "Kategorie bearbeiten" : "Neue Kategorie anlegen"}</h2>
      <input
        placeholder="Kategoriename"
        value={name}
        onChange={(e) => setName(e.target.value)}
        required
        minLength={1} /* Mindestlänge für den Kategorienamen */
        maxLength={50} /* Maximallänge für den Kategorienamen */
      />
      <button type="submit">{isEdit ? "Speichern" : "Hinzufügen"}</button>
    </form>
  );
}
